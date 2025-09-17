package com.guibedan.service;

import com.guibedan.controller.dto.*;
import com.guibedan.entity.PasswordResetToken;
import com.guibedan.entity.Role;
import com.guibedan.entity.User;
import com.guibedan.exceptions.BadCredentialsException;
import com.guibedan.exceptions.NotValidTokenException;
import com.guibedan.exceptions.UserExistsException;
import com.guibedan.exceptions.UserNotExistsException;
import com.guibedan.service.strategy.EmailStrategy;
import com.guibedan.utils.EmailTemplates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class AuthService {

    @Inject
    BCryptService bCryptService;

    @Inject
    JwtService jwtService;

    @Inject
    @Named("GmailSmtpEmailStrategy")
    EmailStrategy emailStrategy;

    @Transactional
    public void register(CreateUserDto createUserDto) {
        var role = Role.findById(Role.Values.BASIC.getRoleId());

        var existsUser = User.findByUsernameOptional(createUserDto.username());

        if (existsUser.isPresent()) {
            throw new UserExistsException();
        }

        User user = new User();
        user.username = createUserDto.username();
        user.password = bCryptService.hashPassword(createUserDto.password());
        user.role = Set.of((Role) role);

        user.persist();
    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {

        var user = User.findByUsernameOptional(loginDto.username()).orElseThrow(UserExistsException::new);

        if (!bCryptService.verifyPassword(loginDto.password(), user.password)) {
            throw new BadCredentialsException();
        }

        var expiresIn = 300L;
        var accessToken = jwtService.generateToken(user, expiresIn);

        return new TokenDto(accessToken, expiresIn);
    }

    @Transactional
    public void sendResetPasswordMail(RecoverPasswordDto recoverPasswordDto) throws MessagingException {
        var existsUser = User.findByUsernameOptional(recoverPasswordDto.username());

        if (existsUser.isEmpty()) {
            throw new UserNotExistsException();
        }

        var token = UUID.randomUUID();
        var expiryDate = Instant.now().plus(1, ChronoUnit.HOURS);

        PasswordResetToken entity = new PasswordResetToken();
        entity.user = existsUser.get();
        entity.token = token;
        entity.expiryDate = expiryDate;

        entity.persist();

        var emailResetPassword = EmailTemplates.resetPasswordEmail(token.toString());
        emailStrategy.sendEmail(recoverPasswordDto.username(), "Recuperação de senha", emailResetPassword);
    }

    @Transactional
    public void resetPassword(String token, ResetPasswordDto resetPasswordDto) {

        if (token == null || token.isEmpty()) {
            throw new NotValidTokenException("Token não pode ser nulo ou vazio.");
        }

        var existsValidToken = PasswordResetToken.findByTokenAndAfterDateNowOptional(UUID.fromString(token), Instant.now());

        if (existsValidToken.isEmpty()) {
            throw new NotValidTokenException("A senha já foi trocada ou expirou o tempo.");
        }

        var validTokenEntity = existsValidToken.get();

        var userEntity = validTokenEntity.user;
        userEntity.password = bCryptService.hashPassword(resetPasswordDto.newPassword());

        validTokenEntity.delete();
    }

}

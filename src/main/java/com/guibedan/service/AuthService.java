package com.guibedan.service;

import com.guibedan.controller.dto.CreateUserDto;
import com.guibedan.controller.dto.LoginDto;
import com.guibedan.controller.dto.TokenDto;
import com.guibedan.entity.Role;
import com.guibedan.entity.User;
import com.guibedan.exceptions.BadCredentialsException;
import com.guibedan.exceptions.UserExistsException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class AuthService {

    private final BCryptService bCryptService;
    private final JwtService jwtService;

    public AuthService(BCryptService bCryptService, JwtService jwtService) {
        this.bCryptService = bCryptService;
        this.jwtService = jwtService;
    }

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

        User.persist(user);
    }

    public TokenDto login(LoginDto loginDto) {

        var user = User.findByUsernameOptional(loginDto.username()).orElseThrow(UserExistsException::new);

        if (!bCryptService.verifyPassword(loginDto.password(), user.password)) {
            throw new BadCredentialsException();
        }

        var expiresIn = 300L;
        var accessToken = jwtService.generateToken(user, expiresIn);

        return new TokenDto(accessToken, expiresIn);
    }

}

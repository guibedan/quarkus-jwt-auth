package com.guibedan.service;

import com.guibedan.controller.dto.UpdatePasswordDto;
import com.guibedan.entity.User;
import com.guibedan.exceptions.BadCredentialsException;
import com.guibedan.exceptions.UserNotExistsException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final BCryptService bCryptService;

    public UserService(BCryptService bCryptService) {
        this.bCryptService = bCryptService;
    }

    public List<User> findAll(Integer page, Integer pageSize) {
        return User.findAll().page(page, pageSize).list();
    }

    @Transactional
    public void updatePassword(UpdatePasswordDto updatePasswordDto, UUID userId) {

        var user = (User) User.findByIdOptional(userId).orElseThrow(UserNotExistsException::new);

        if (!bCryptService.verifyPassword(updatePasswordDto.oldPassword(), user.password)) {
            throw new BadCredentialsException();
        }

        user.password = bCryptService.hashPassword(updatePasswordDto.newPassword());
    }

}

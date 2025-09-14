package com.guibedan.controller;

import com.guibedan.controller.dto.CreateUserDto;
import com.guibedan.controller.dto.LoginDto;
import com.guibedan.controller.dto.RecoverPasswordDto;
import com.guibedan.controller.dto.ResetPasswordDto;
import com.guibedan.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(@Valid CreateUserDto createUserDto) {
        authService.register(createUserDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    @Transactional
    public Response login(LoginDto loginDto) {
        var response = authService.login(loginDto);
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("/send/reset-password/mail")
    @Transactional
    public Response sendResetPasswordMail(@Valid RecoverPasswordDto recoverPasswordDto) throws MessagingException {
        authService.sendResetPasswordMail(recoverPasswordDto);
        return Response.noContent().build();
    }

    @POST
    @Path("/reset-password")
    @Transactional
    public Response resetPassword(@QueryParam("token") String token, @Valid ResetPasswordDto resetPasswordDto) {
        authService.resetPassword(token, resetPasswordDto);
        return Response.noContent().build();
    }

}

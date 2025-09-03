package com.guibedan.controller;

import com.guibedan.controller.dto.CreateUserDto;
import com.guibedan.controller.dto.LoginDto;
import com.guibedan.service.AuthService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    public Response register(CreateUserDto createUserDto) {
        authService.register(createUserDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginDto loginDto) {
        var response = authService.login(loginDto);
        return Response.status(Response.Status.OK).entity(response).build();
    }

}

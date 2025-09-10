package com.guibedan.controller;

import com.guibedan.controller.dto.UpdatePasswordDto;
import com.guibedan.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;
    private final JsonWebToken jwt;

    @Inject
    public UserController(UserService userService, JsonWebToken jwt) {
        this.userService = userService;
        this.jwt = jwt;
    }

    @GET
    @RolesAllowed({"ADMIN"})
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                              @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var response = userService.findAll(page, pageSize);
        return Response.ok(response).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"ADMIN", "BASIC"})
    public Response updatePassword(UpdatePasswordDto updatePasswordDto) {
        userService.updatePassword(updatePasswordDto, UUID.fromString(jwt.getSubject()));
        return Response.noContent().build();
    }

}

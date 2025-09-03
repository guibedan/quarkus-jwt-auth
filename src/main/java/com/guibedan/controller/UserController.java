package com.guibedan.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/users")
public class UserController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers() {
        return "Hello World!";
    }

}

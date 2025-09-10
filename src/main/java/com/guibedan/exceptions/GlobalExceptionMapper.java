package com.guibedan.exceptions;

import com.guibedan.controller.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        Response.Status status;
        ErrorResponse response = switch (exception) {
            case BadCredentialsException e -> {
                status = Response.Status.UNAUTHORIZED;
                yield new ErrorResponse("Bad credentials", "Usuário ou senha incorretos.");
            }
            case UserExistsException e -> {
                status = Response.Status.CONFLICT;
                yield new ErrorResponse("User exists", "Usuário já existe no banco.");
            }
            case UserNotExistsException e -> {
                status = Response.Status.NOT_FOUND;
                yield new ErrorResponse("User not found", "Usuário não existe.");
            }
            default -> {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                yield new ErrorResponse(
                        "Internal Server Error",
                        exception.getMessage() != null ? exception.getMessage() : "Ocorreu um erro inesperado."
                );
            }
        };

        return Response.status(status)
                .entity(response)
                .build();
    }

}

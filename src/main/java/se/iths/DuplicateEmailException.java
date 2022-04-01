package se.iths;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DuplicateEmailException extends WebApplicationException {
    public DuplicateEmailException() {
        super(Response.status(Response.Status.CONFLICT).entity("Email already exists").type(MediaType.TEXT_PLAIN_TYPE).build());
    }

    public DuplicateEmailException(String message) {
        super(Response.status(Response.Status.CONFLICT).entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());
    }
}

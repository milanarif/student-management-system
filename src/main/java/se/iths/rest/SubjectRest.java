package se.iths.rest;

import se.iths.entity.Subject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("subjects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectRest {

    @Inject
    SubjectService subjectService;

    @POST
    public Response createSubject(Subject subject) {
        if (subject.getName() == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("include name").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
          subjectService.createSubject(subject);
          return Response.status(201).entity(subject).build();
        }
    }

    @GET
    public Response getAllSubjects(){
        List<Subject> foundSubjects = subjectService.getAllSubjects();

        if (foundSubjects.size() > 0) {
            return Response.ok(foundSubjects).build();
        } else {
            return Response.ok("Weird echo....").build();
        }
    }

    @Path("/query")
    @GET
    public Response findSubjectByName(@QueryParam("name") String name) {
        List<Subject> foundSubject = subjectService.getSubjectByName(name);

        if (foundSubject.size() > 0) {
            return Response.status(200).entity(foundSubject).build();
        } else {
            return Response.status(200).entity("No subject match your search: " + name).build();
        }
    }

    @Path("{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Long id) {
        Subject subject = subjectService.deleteSubject(id);
        if (subject == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Subject by that ID: " + id + " was not found.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            return Response.ok("Subject " + subject.getName() + " was successfully removed.").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }
}

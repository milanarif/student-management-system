package se.iths.rest;

import se.iths.entity.Teacher;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("teachers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherRest {

    @Inject
    TeacherService teacherService;

    @POST
    public Response createTeacher(Teacher teacher) {
        if (teacher.getFirstName() == null || teacher.getLastName() == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("New teacher must include firstname & lastname").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            teacherService.createTeacher(teacher);
            return Response.status(201).entity(teacher).build();
        }
    }

    @Path("{id}")
    @GET
    public Response findTeacherById(@PathParam("id") Long id) {
        Teacher foundTeacher = teacherService.getTeacherById(id);
        if (foundTeacher == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Teacher by that ID: " + id + " was not found.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            return Response.ok(foundTeacher).build();
        }
    }

    @GET
    public Response getAllTeachers(){
        List<Teacher> foundTeachers = teacherService.getAllTeachers();

        if (foundTeachers.size() > 0) {
            return Response.ok(foundTeachers).build();
        } else {
            return Response.ok("Weird echo....").build();
        }
    }

    @Path("{id}")
    @DELETE
    public Response deleteTeacher(@PathParam("id") Long id) {
        Teacher teacher = teacherService.deleteTeacher(id);
        if (teacher == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Teacher by that ID: " + id + " was not found.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            return Response.ok("Teacher " + teacher.getFirstName() + " " + teacher.getLastName() + " was successfully removed.").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }
}

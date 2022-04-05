package se.iths.rest;

import se.iths.HttpError;
import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.service.SubjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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

    @Path("{id}")
    @GET
    public Response getSubjectById(@PathParam("id") Long id){
        Subject foundSubject = subjectService.getSubjectById(id);
        if (foundSubject == null) {
            //throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Subject by that ID: " + id + " was not found.").type(MediaType.APPLICATION_JSON).build());
            return Response.status(404).entity(new HttpError(404, "Subject by that ID: " + id + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else {
            List<Student> enrolledStudents = subjectService.getEnrolledStudents(id);
            Teacher assignedTeacher = subjectService.getTeacher(id);
            List<String> students = new ArrayList<>();
            enrolledStudents.stream().forEach(s -> students.add(s.getFirstName() + " " + s.getLastName()));


            return Response.ok("Subject: " + foundSubject.getName() + "\nTeacher: " + assignedTeacher.getFirstName() + " " +  assignedTeacher.getLastName() + "\nStudents: " + students).build();
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

    @Path("/enroll/{id}")
    @POST
    public Response enrollStudent(@PathParam("id") Long subjectId, Student student) {
        subjectService.enrollStudent(subjectId, student.getId());

        return Response.status(Response.Status.OK).entity("Student enrolled to course " + subjectId).build();
    }

    @Path("/assign/{id}")
    @POST
    public Response assignTeacher(@PathParam("id") Long subjectId, Teacher teacher) {
        subjectService.assignTeacher(subjectId, teacher.getId());

        return Response.status(Response.Status.OK).entity("Teacher enrolled to course " + subjectId).build();
    }
}

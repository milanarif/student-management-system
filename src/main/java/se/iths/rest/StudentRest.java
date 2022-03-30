package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {

  @Inject
  StudentService studentService;

    @Path("")
    @POST
    public Response createStudent(Student student){
        if (student.getFirstName().isBlank() || student.getLastName().isBlank() || student.getEmail().isBlank()) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("New user must include firstname, lastname and email").type(MediaType.TEXT_PLAIN_TYPE).build());
        }

        if(!studentService.getByEmail(student.getEmail()).isEmpty()) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity("Email: " + student.getEmail() + " is already taken.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            studentService.createStudent(student);
            return Response.status(201).entity(student).build();
        }
    }

    @Path("")
    @PUT
    public Response updateStudent(Student student) {
        if (student.getId() == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("You must include id in request body").type(MediaType.TEXT_PLAIN_TYPE).build());
        }

        Student targetStudent = studentService.getStudentById(student.getId());

        if (targetStudent == null) {
            return createStudent(student);
        }
        else if (!student.getEmail().equals(targetStudent.getEmail()) && !studentService.getByEmail(student.getEmail()).isEmpty()) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity("Email: " + student.getEmail() + " is already taken.").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        else {
            studentService.updateStudent(student);
            return Response.ok(student).build();
        }
    }

    @Path("{id}")
    @PATCH
    public Response patchStudent(@PathParam("id") Long id, Student student) {
        List<String> response = new ArrayList();
        boolean emailExists = false;

        if (studentService.getStudentById(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student by that ID: " + id + " was not found.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            if (student.getEmail() != null) {
                studentService.updateStudentEmail(id, student.getEmail(), emailExists);
                response.add("Email: " + student.getEmail());
            }
            if (!emailExists) {
                if (student.getFirstName() != null) {
                    studentService.updateStudentFirstName(id, student.getFirstName());
                    response.add("FirstName: " + student.getFirstName());
                }
                if (student.getLastName() != null) {
                    studentService.updateStudentLastName(id, student.getLastName());
                    response.add("LastName: " + student.getLastName());
                }
                if (student.getPhoneNumber() != null) {
                    studentService.updateStudentPhoneNumber(id, student.getPhoneNumber());
                    response.add("PhoneNumber: " + student.getPhoneNumber());
                }
            }
        }
        return Response.status(Response.Status.OK).entity("Student successfully updated.\n" + response).build();
    }


    @Path("{id}")
    @GET
    public Response findStudentById(@PathParam("id") Long id) {
        Student foundStudent = studentService.getStudentById(id);
        if (foundStudent == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student by that ID: " + id + " was not found.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            return Response.ok(foundStudent).build();
        }
    }

    @Path("")
    @GET
    public Response getAllStudents(){
        List<Student> foundStudents = studentService.getAllStudents();

        if (foundStudents.size() > 0) {
            return Response.ok(foundStudents).build();
        } else {
            return Response.ok("Weird echo....").build();
        }
    }

    @Path("{id}")
    @DELETE
    public Response deleteStudent(@PathParam("id") Long id) {
        Student student = studentService.deleteStudent(id);
        if (student == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Student by that ID: " + id + " was not found.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            return Response.ok("Student " + student.getFirstName() + " " + student.getLastName() + " was successfully removed.").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }

    @Path("/query")
    @GET
    public Response findStudentByLastName(@QueryParam("lastName") String lastName) {
        List<Student> foundStudents = studentService.getStudentsByLastName(lastName);

        if (foundStudents.size() > 0) {
            return Response.status(200).entity(foundStudents).build();
        } else {
            return Response.status(200).entity("No student match your search: " + lastName).build();
        }
    }
}

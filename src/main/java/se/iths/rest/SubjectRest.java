package se.iths.rest;

import se.iths.HttpError;
import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.service.StudentService;
import se.iths.service.SubjectService;
import se.iths.service.TeacherService;

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

    @Inject
    StudentService studentService;

    @Inject
    TeacherService teacherService;

    @POST
    public Response createSubject(Subject subject) {
        if (subject.getName() == null) {
            return Response.status(400).entity(new HttpError(400, "Name is required for Subject")).type(MediaType.APPLICATION_JSON
            ).build();
        } else {
            subjectService.createSubject(subject);
            return Response.status(201).entity(subject).build();
        }
    }

    @GET
    public Response getAllSubjects() {
        List<Subject> foundSubjects = subjectService.getAllSubjects();

        if (foundSubjects.size() > 0) {
            return Response.ok(foundSubjects).build();
        } else {
            return Response.ok("Weird echo....").build();
        }
    }

    @Path("{id}")
    @GET
    public Response getSubjectById(@PathParam("id") Long id) {
        Subject foundSubject = subjectService.getSubjectById(id);
        if (foundSubject == null) {
            return Response.status(404).entity(new HttpError(404, "Subject by that ID: " + id + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else {
            List<Student> enrolledStudents = subjectService.getEnrolledStudents(id);
            Teacher assignedTeacher = subjectService.getTeacher(id);
            String teacher;
            List<String> students = new ArrayList<>();

            if (enrolledStudents.size() == 0) {
                students.add("No students added to subject");
            } else {
                enrolledStudents.stream().forEach(s -> students.add(s.getFirstName() + " " + s.getLastName()));
            }

            if (assignedTeacher == null) {
                teacher = "No teacher added to subject";
            } else {
                teacher = assignedTeacher.getFirstName() + " " + assignedTeacher.getLastName();
            }
            return Response.ok("Subject: " + foundSubject.getName() + "\nTeacher: " + teacher + "\nStudents: " + students).build();
        }
    }

    @Path("{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Long id) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject == null) {
            return Response.status(404).entity(new HttpError(404, "Subject by that ID: " + id + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else {
            subjectService.deleteSubject(id);
            return Response.ok("Subject " + subject.getName() + " was successfully removed.").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }

    @Path("{id}/addStudent")
    @POST
    public Response enrollStudent(@PathParam("id") Long subjectId, Student student) {

        Subject foundSubject = subjectService.getSubjectById(subjectId);
        Student foundStudent = studentService.getStudentById(student.getId());

        if (foundSubject == null) {
            return Response.status(404).entity(new HttpError(404, "Subject by that ID: " + subjectId + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else if (foundStudent == null) {
            return Response.status(404).entity(new HttpError(404, "Student by that ID: " + student.getId() + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else {
            subjectService.enrollStudent(subjectId, student.getId());
            return Response.status(Response.Status.OK).entity("Student enrolled to course " + subjectId).build();
        }
    }

    @Path("{id}/addTeacher")
    @POST
    public Response assignTeacher(@PathParam("id") Long subjectId, Teacher teacher) {
        Subject foundSubject = subjectService.getSubjectById(subjectId);
        Teacher foundTeacher = teacherService.getTeacherById(teacher.getId());

        if (foundSubject == null) {
            return Response.status(404).entity(new HttpError(404, "Subject by that ID: " + subjectId + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else if (foundTeacher == null) {
            return Response.status(404).entity(new HttpError(404, "Teacher by that ID: " + teacher.getId() + " was not found.")).type(MediaType.APPLICATION_JSON
            ).build();
        } else {
            subjectService.assignTeacher(subjectId, teacher.getId());
            return Response.status(Response.Status.OK).entity("Teacher enrolled to course " + subjectId).build();
        }
    }
}

package se.iths.service;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import se.iths.entity.Student;

@Transactional
public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public Student getStudentById(Long id) {
        Student student = entityManager.find(Student.class, id);
        return student;
    }

    public List<Student> getByEmail(String email) {
        return entityManager
            .createQuery("SELECT s from Student s WHERE s.email=:email", Student.class)
            .setParameter("email", email)
            .getResultList();
    }

    public Student createStudent(Student student) {
        entityManager.persist(student);
        return student;
    }

    public List<Student> getStudentsByLastName(String lastName) {
        List<Student> students = entityManager
                .createQuery("SELECT s FROM Student s WHERE s.lastName=:lastName", Student.class)
                .setParameter("lastName", lastName)
                .getResultList();
        return students;
    }

    public List<Student> getAllStudents() {
        List<Student> students = entityManager
                .createQuery("Select s from Student s", Student.class)
                .getResultList();
        return students;
    }

    public void updateStudent(Student student) {
        entityManager.merge(student);
        //return entityManager.merge(student);
    }

    public Student deleteStudent(Long id) {
        Student foundStudent = entityManager.find(Student.class, id);
        if (foundStudent == null) {
            return null;
        } else {
            entityManager.remove(foundStudent);
            return foundStudent;
        }
    }

    public void updateStudentFirstName(Long id, String firstName) {
        Student student = entityManager.find(Student.class, id);
        student.setFirstName(firstName);
    }

    public void updateStudentLastName(Long id, String lastName) {
        Student student = entityManager.find(Student.class, id);
        student.setLastName(lastName);
    }

    public Boolean updateStudentEmail(Long id, String email, boolean emailExists) {
        Student student = entityManager.find(Student.class, id);
        List<Student> allStudents = getAllStudents();
        List<Student> doesStudentEmailExist = allStudents.stream().filter(s -> s.getEmail().equals(email)).collect(Collectors.toList());

        if (doesStudentEmailExist.size() > 0) {
            emailExists = true;
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity("Student with that Email: " + email + " already exists.").type(MediaType.TEXT_PLAIN_TYPE).build());
        } else {
            student.setEmail(email);
        }
        return emailExists;
    }

    public void updateStudentPhoneNumber(Long id, String phoneNumber) {
        Student student = entityManager.find(Student.class, id);
        student.setPhoneNumber(phoneNumber);
    }
}

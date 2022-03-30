package se.iths.service;

import java.util.List;

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

}

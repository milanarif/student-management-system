package se.iths.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import se.iths.entity.Student;
import se.iths.entity.Teacher;
import se.iths.entity.Subject;

@Transactional
public class SubjectService {
    
    @PersistenceContext
    EntityManager entityManager;

    public Subject getSubjectById(Long id) {
        return entityManager.find(Subject.class, id);
    }

    public Subject createSubject(Subject subject) {
        entityManager.persist(subject);
        return subject;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = entityManager
                .createQuery("Select s from Student s", Subject.class)
                .getResultList();
        return subjects;
    }

    public List<Subject> getSubjectByName(String name) {
        List<Subject> subjects = entityManager
                .createQuery("SELECT s FROM Subject s WHERE s.name=:name", Subject.class)
                .setParameter("name", name)
                .getResultList();
        return subjects;
    }

    public Student enrollStudent(Long subjectId, Long studentId) {
        Subject subject = entityManager.find(Subject.class, subjectId);
        Student student = entityManager.find(Student.class, studentId);
        List<Student> students = subject.getStudents();
        students.add(student);
        subject.setStudents(students);
        entityManager.persist(subject);
        return student;
    }

    public Teacher assignTeacher(Long subjectId, Long teacherId) {
        Subject subject = entityManager.find(Subject.class, subjectId);
        Teacher teacher = entityManager.find(Teacher.class, teacherId);
        subject.setTeacher(teacher);
        entityManager.persist(subject);
        return teacher;
    }

    public List<Student> getEnrolledStudents(Long id) {
        return entityManager.find(Subject.class, id).getStudents();
    }

    public Teacher getTeacher(Long id) {
        return entityManager.find(Subject.class, id).getTeacher();
    }

    public Subject deleteSubject(Long id) {
        Subject subject = entityManager.find(Subject.class, id);
        entityManager.remove(subject);
        return subject;
    }
}

package se.iths.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import se.iths.entity.Teacher;

@Transactional
public class TeacherService {
    
    @PersistenceContext
    EntityManager entityManager;

    public Teacher getTeacherById(Long id) {
        return entityManager.find(Teacher.class, id);
    }

    public Teacher addTeacher(Teacher teacher) {
        entityManager.persist(teacher);
        return teacher;
    }

    public Teacher deleteTeacher(Long id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        entityManager.remove(teacher);
        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        return entityManager
                .createQuery("Select t from Teacher t", Teacher.class)
                .getResultList();
    }
    
}

package se.iths.util;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class SampleDataGenerator {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void generateData() {

        Student student1 = new Student("Kalle", "Anka", "kalle@ankeborg.se", "080808");
        Student student2 = new Student("Mona", "Sahlin", "mona@toblerone.se", "0707070");

        Teacher teacher1 = new Teacher("Bosse", "Karlsson");
        Teacher teacher2 = new Teacher("Berit", "Bagare");

        Subject subject1 = new Subject("Kemi");
        Subject subject2 = new Subject("Engelska");


        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.persist(subject1);
        entityManager.persist(subject2);

    }

}
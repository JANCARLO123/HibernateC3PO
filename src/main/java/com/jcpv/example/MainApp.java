package com.jcpv.example;

import com.jcpv.example.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by JanCarlo on 29/06/2017.
 */
public class MainApp {
    private static final Logger logger = LogManager.getLogger(MainApp.class);
    public static void main(String[] args) {

        Session session = null;
        Transaction transaction= null;
        try{
            session= HibernateUtil.getSessionFactory().openSession();
            transaction= session.getTransaction();

            transaction.begin();
            logger.info("Save");
            Person  personSave = new Person();
            personSave.setName("Jan Save");
            session.save(personSave);

            logger.info("Persist");
            Person  personPersist = new Person();
            personPersist.setName("Jan Persist");
            session.persist(personPersist);

            logger.info("SaveOrUpdat");
            Person  personSaveOrUpdate = new Person();
            personSaveOrUpdate.setName("Jan SaveOrUpdate");
            session.saveOrUpdate(personSaveOrUpdate);

            Person person1 = session.get(Person.class, 1L);
            if (person1 != null) {
                logger.info(person1.getName());
            }

            // Obtain an entity using load() method
            Person person2 = session.load(Person.class, 2L);
            logger.info(person2.getName());

            // Obtain an entity using byId() method
            Person person3 = session.byId(Person.class).getReference(3L);
            logger.info(person3.getName());




            transaction.commit();

        }catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();

            }
            logger.error("Failed to save customer..." + ex);
        }finally {
            if (session != null) {
                session.close();
            }
        }
        HibernateUtil.shutdown();
    }
}

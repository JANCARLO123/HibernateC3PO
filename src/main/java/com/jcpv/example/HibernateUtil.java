package com.jcpv.example;

import com.jcpv.example.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JanCarlo on 29/06/2017.
 */
public class HibernateUtil {
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null){
            try{
                StandardServiceRegistryBuilder registryBuilder  = new StandardServiceRegistryBuilder();
                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL,"jdbc:mysql://127.0.0.1:3306/example1?useSSL=false&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC");
                settings.put(Environment.USER,"user");
                settings.put(Environment.PASS,"Pa$$w0rd");
                settings.put(Environment.HBM2DDL_AUTO,"update");
                settings.put(Environment.SHOW_SQL, true);


                // c3p0 configuration
                settings.put(Environment.C3P0_MIN_SIZE, 5);         //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, 20);        //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);//Number of connections acquired at a time when pool is exhausted
                settings.put(Environment.C3P0_TIMEOUT, 1800);       //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
                settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);


                registryBuilder.applySettings(settings);
                registry=registryBuilder.build();
                logger.info("Hibernate Registry builder created.");

                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Person.class);
                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory= metadata.getSessionFactoryBuilder().build();

            }catch (Exception ex){
                logger.error("SessionFactory creation failed");
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                ex.printStackTrace();
            }


        }return sessionFactory;

    }

    public static void shutdown() {
        logger.info("registry closed");
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}

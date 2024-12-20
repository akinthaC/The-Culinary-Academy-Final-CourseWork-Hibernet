package lk.ijse.config;

import lk.ijse.Entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {

    private  static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;

    private FactoryConfiguration() {
       Configuration configuration=new Configuration();
       Properties properties=new Properties();

       try {
           properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       configuration.setProperties(properties);
       configuration.addAnnotatedClass(User.class).addAnnotatedClass(Student.class).addAnnotatedClass(Program.class).addAnnotatedClass(Intake.class).addAnnotatedClass(Student_Program.class).addAnnotatedClass(Payment.class);

        sessionFactory = configuration.buildSessionFactory();
    }


    public static FactoryConfiguration getInstance(){
        return(factoryConfiguration==null)?factoryConfiguration=
                new FactoryConfiguration():factoryConfiguration;

    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}

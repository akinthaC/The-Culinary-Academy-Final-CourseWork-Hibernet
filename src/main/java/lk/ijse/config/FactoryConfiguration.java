package lk.ijse;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

    private  static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;

    private FactoryConfiguration() {
       Configuration configuration=new Configuration().configure().addAnnotatedClass();
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
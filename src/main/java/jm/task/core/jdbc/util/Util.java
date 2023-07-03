package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_1.1.4";
    private static final String USER = "postgres";
    private static final String PASS = "zhum99";

    private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String HIBERNATE_CONNECTION_URL = "jdbc:postgresql://localhost:5432/db_1.1.4";
    private static final String HIBERNATE_USERNAME = "postgres";
    private static final String HIBERNATE_PASSWORD = "zhum99";

    private static Connection jdbcConnection;
    private static SessionFactory hibernateSessionFactory;

    public static Connection connectWithJDBC() {
        try {
            Class.forName(JDBC_DRIVER);
            jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected with JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jdbcConnection;
    }

    public static SessionFactory connectWithHibernate() {
        try {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", JDBC_DRIVER)
                .setProperty("hibernate.connection.url", HIBERNATE_CONNECTION_URL)
                .setProperty("hibernate.connection.username", HIBERNATE_USERNAME)
                .setProperty("hibernate.connection.password", HIBERNATE_PASSWORD)
                .setProperty("hibernate.dialect", HIBERNATE_DIALECT)
                .addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        hibernateSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        System.out.println("Connected with Hibernate");
    } catch (Throwable e) {
        e.printStackTrace();
    }
        return hibernateSessionFactory;
    }

    public static void closeConnections() {
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.close();
                System.out.println("JDBC connection closed");
            }

            if (hibernateSessionFactory != null) {
                hibernateSessionFactory.close();
                System.out.println("Hibernate session factory closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.example.retoconjuntogestorpedidoshibernate.domain;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase utilitaria para la gestión de la sesión de Hibernate.
 */
@Log
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            //Configura la sesión de Hibernate usando la configuración predeterminada.
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();

            log.info("SessionFactory creado"); // Registro de la creación del SessionFactory
        } catch (Exception e) {
            e.printStackTrace();
            //log.severe("Error al crear Sessionfactory()"); // Mensaje de error en caso de excepción.
        }
    }

    /**
     * Obtiene la sesión de fábrica de Hibernate.
     *
     * @return la sesión de fábrica de Hibernate.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

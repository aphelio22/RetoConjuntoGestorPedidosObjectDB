package com.example.retoconjuntogestorpedidoshibernate.domain;

import lombok.Getter;
import lombok.extern.java.Log;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Proporciona un EntityManagerFactory para interactuar con la Base de Datos ObjectDB.
 *
 * Esta clase utiliza la anotación de Lombok, por lo que automáticamente genera
 * los métodos getter y un logger de Java util (log) para el registro de mensajes.
 */
@Log
public class ObjectDBUtil {

    @Getter
    private final static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("data.odb");
    }
}

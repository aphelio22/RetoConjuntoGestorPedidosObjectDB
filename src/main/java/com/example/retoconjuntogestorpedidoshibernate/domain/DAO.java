package com.example.retoconjuntogestorpedidoshibernate.domain;

import java.util.ArrayList;

/**
 * Interfaz genérica para operaciones básicas en la Base de Datos.
 *
 * @param <T> tipo genérico para entidades específicas.
 */
public interface DAO<T> {

    /**
     * Recupera todos los elementos del tipo específico de la Base de Datos.
     *
     * @return una lista de todos los elementos del tipo T.
     */
    public ArrayList<T> getAll();

    /**
     * Recupera un elemento del tipo T basado en su identificador único.
     *
     * @param id el identificador único del elemento a recuperar.
     * @return el elemento del tipo T correspondiente al identificador proporcionado.
     */
    public T get(Integer id);

    /**
     * Guarda un nuevo elemento del tipo T en la Base de Datos.
     *
     * @param data el elemento del tipo T a guardar.
     * @return el elemento del tipo T guardado.
     */
    public T save(T data);

    /**
     * Actualiza un elemento del tipo T existente en la Base de Datos.
     *
     * @param data el elemento del tipo T a actualizar.
     */
    public void update(T data);

    /**
     * Elimina un elemento del tipo T de la Base de Datos.
     *
     * @param data el elemento del tipo T a eliminar
     */
    public void delete(T data);
}

package com.example.retoconjuntogestorpedidoshibernate.domain.item;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.ObjectDBUtil;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el acceso a datos para la entidad Item.
 * Proporciona métodos para realizar operaciones en la Base de Datos relacionadas con la entidad Item.
 */
public class ItemDAO implements DAO<Item> {

    /**
     * Obtiene todos los ítems almacenados en la Base de Datos.
     *
     * @return ArrayList con todos los ítems almacenados en la Base de Datos.
     */
    @Override
    public ArrayList<Item> getAll() {
        var salida = new ArrayList<Item>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Item> query = entityManager.createQuery("select i from Item i", Item.class);
            salida = (ArrayList<Item>) query.getResultList();
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * Obtiene un ítem por su Id.
     *
     * @param id El Id del ítem que se desea obtener.
     * @return El ítem correspondiente al Id especificado.
     */
    @Override
    public Item get(Integer id) {
        //Do nothing.
        return null;
    }

    /**
     * Guarda un ítem en la Base de Datos.
     *
     * @param data El ítem que se desea guardar en la Base de Datos.
     * @return El ítem guardado en la Base de Datos.
     */
    @Override
    public Item save(Item data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(data);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }finally {
            entityManager.close();
        }
        return data;
    }

    /**
     * Actualiza un ítem en la Base de Datos.
     *
     * @param data El ítem que se desea actualizar en la Base de Datos.
     */
    @Override
    public Item update(Item data) {
        return null;
    }

    /**
     * Elimina un ítem de la Base de Datos.
     *
     * @param data El ítem que se desea eliminar de la Base de Datos.
     */
    @Override
    public void delete(Item data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(data);
            entityManager.remove(data);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void saveAll(List<Item> data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            for (Item i : data) {
                entityManager.persist(i);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}

package com.example.retoconjuntogestorpedidoshibernate.domain.pedido;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.ObjectDBUtil;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el acceso a datos para la entidad Pedido.
 * Proporciona métodos para realizar operaciones en la Base de Datos relacionadas con la entidad Pedido.
 */
public class PedidoDAO implements DAO<Pedido> {

    /**
     * Obtiene todos los pedidos almacenados en la Base de Datos.
     *
     * @return ArrayList con todos los pedidos almacenados en la Base de Datos.
     */
    @Override
    public ArrayList<Pedido> getAll() {
        var salida = new ArrayList<Pedido>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Pedido> query = entityManager.createQuery("select ped from Pedido ped", Pedido.class);
            salida = (ArrayList<Pedido>) query.getResultList();
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * Obtiene un pedido por su Id.
     *
     * @param id El Id del pedido que se desea obtener.
     * @return El pedido correspondiente al Id especificado.
     */
    @Override
    public Pedido get(Integer id) {
        Pedido salida = null;
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Pedido> query = entityManager.createQuery("select ped from Pedido ped where ped.id = :id", Pedido.class);
            query.setParameter("id", id);
            var resultado = query.getResultList();
            if (resultado.size() > 0) {
                salida = resultado.get(0);
            }
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * Guarda un pedido en la Base de Datos.
     *
     * @param data El pedido que se desea guardar en la Base de Datos.
     * @return El pedido guardado en la Base de Datos.
     */
    @Override
    public Pedido save(Pedido data) {
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
     * Actualiza un pedido en la Base de Datos.
     *
     * @param data El pedido que se desea actualizar en la Base de Datos.
     */
    public Pedido update(Pedido data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Utiliza el método merge para actualizar la entidad en la base de datos.
            data = entityManager.merge(data);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // Maneja la excepción adecuadamente (puede imprimir o lanzar una excepción personalizada).
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return data;
    }

    /**
     * Elimina un pedido de la Base de Datos.
     *
     * @param data El pedido que se desea eliminar de la Base de Datos.
     */
    public void delete(Pedido data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Si la entidad no está gestionada, primero la adjuntamos al contexto de persistencia.
            if (!entityManager.contains(data)) {
                data = entityManager.merge(data);
            }
            entityManager.remove(data);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * Guarda una lista de pedidos en la Base de Datos.
     *
     * @param data La lista de pedidos a ser guardados en la Base de Datos.
     */
    @Override
    public void saveAll(List<Pedido> data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            for (Pedido p : data) {
                entityManager.persist(p);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}

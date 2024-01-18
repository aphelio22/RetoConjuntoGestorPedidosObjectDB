package com.example.retoconjuntogestorpedidoshibernate.domain.producto;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.ObjectDBUtil;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el acceso a datos para la entidad Producto.
 * Proporciona métodos para realizar operaciones en la Base de Datos relacionadas con la entidad Producto.
 */
public class ProductoDAO implements DAO<Producto> {

    /**
     * Obtiene todos los productos almacenados en la Base de Datos.
     *
     * @return ArrayList con todos los productos almacenados en la Base de Datos.
     */
    @Override
    public ArrayList<Producto> getAll() {
        var salida = new ArrayList<Producto>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Producto> query = entityManager.createQuery("select p from Producto p", Producto.class);
            salida = (ArrayList<Producto>) query.getResultList();
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * Obtiene un producto por su Id.
     *
     * @param id El Id del producto que se desea obtener.
     * @return El producto correspondiente al Id especificado.
     */
    @Override
    public Producto get(Integer id) {
        //Do nothing.
        return null;
    }

    /**
     * Guarda un producto en la Base de Datos.
     *
     * @param data El producto que se desea guardar en la Base de Datos.
     * @return El producto guardado en la Base de Datos.
     */
    @Override
    public Producto save(Producto data) {
        //Do nothing.
        return null;
    }

    /**
     * Actualiza un producto en la Base de Datos.
     *
     * @param data El producto que se desea actualizar en la Base de Datos.
     */
    @Override
    public Producto update(Producto data) {
        return null;
    }

    /**
     * Elimina un producto de la Base de Datos.
     *
     * @param data El producto que se desea eliminar de la Base de Datos.
     */
    @Override
    public void delete(Producto data) {
        //Do nothing.
    }

    @Override
    public void saveAll(List<Producto> data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            for (Producto pr : data) {
                entityManager.persist(pr);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}

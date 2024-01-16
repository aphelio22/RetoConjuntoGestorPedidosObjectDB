package com.example.retoconjuntogestorpedidoshibernate.domain.item;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

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
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Query<Item> query = sesion.createQuery("from Item", Item.class);
            salida = (ArrayList<Item>) query.getResultList();
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                //Comienza la transacción.
                transaction = session.beginTransaction();

                //Guarda el nuevo ítem en la Base de Datos.
                session.save(data);

                //Commit de la transacción.
                transaction.commit();
            } catch (Exception e) {
                //Maneja cualquier excepción que pueda ocurrir durante la transacción.
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return data;
        }
    }

    /**
     * Actualiza un ítem en la Base de Datos.
     *
     * @param data El ítem que se desea actualizar en la Base de Datos.
     */
    @Override
    public void update(Item data) {
        //Do nothing.
    }

    /**
     * Elimina un ítem de la Base de Datos.
     *
     * @param data El ítem que se desea eliminar de la Base de Datos.
     */
    @Override
    public void delete(Item data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Item item = session.get(Item.class, data.getId());
            session.remove(item);
        });
    }
}

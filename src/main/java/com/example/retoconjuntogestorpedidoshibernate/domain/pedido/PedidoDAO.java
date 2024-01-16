package com.example.retoconjuntogestorpedidoshibernate.domain.pedido;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.HibernateUtil;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Pedido> query = sesion.createQuery("from Pedido", Pedido.class);
            salida = (ArrayList<Pedido>) query.getResultList();
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
        var salida = new Pedido();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Pedido.class, id);
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
     * Actualiza un pedido en la Base de Datos.
     *
     * @param data El pedido que se desea actualizar en la Base de Datos.
     */
    @Override
    public void update(Pedido data) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            //Comienza la transacción.
            transaction = session.beginTransaction();

            //Actualiza el pedido en la Base de Datos.
            session.update(data);

            //Commit de la transacción.
            transaction.commit();
        } catch (Exception e) {
            //Maneja cualquier excepción que pueda ocurrir durante la transacción.
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Elimina un pedido de la Base de Datos.
     *
     * @param data El pedido que se desea eliminar de la Base de Datos.
     */
    @Override
    public void delete(Pedido data) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Pedido pedido = session.get(Pedido.class, data.getId());
            session.remove(pedido);
        });
    }
}

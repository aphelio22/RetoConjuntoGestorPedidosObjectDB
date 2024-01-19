package com.example.retoconjuntogestorpedidoshibernate.domain.usuario;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.ObjectDBUtil;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.exceptions.UsuarioInexistente;
import javax.persistence.NoResultException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el acceso a datos para la entidad Usuario.
 * Proporciona métodos para realizar operaciones en la Base de Datos relacionadas con la entidad Usuario.
 */
public class UsuarioDAO implements DAO<Usuario> {

    /**
     * Obtiene todos los usuarios almacenados en la Base de Datos.
     *
     * @return ArrayList con todos los usuarios almacenados en la Base de Datos.
     */
    @Override
    public ArrayList<Usuario> getAll() {
        var salida = new ArrayList<Usuario>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u", Usuario.class);
            salida = (ArrayList<Usuario>) query.getResultList();
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * Obtiene un usuario por su Id.
     *
     * @param id El Id del usuario que se desea obtener.
     * @return El usuario correspondiente al Id especificado.
     */
    @Override
    public Usuario get(Integer id) {
        Usuario salida = null;
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u where u.id = :id", Usuario.class);
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
     * Guarda un usuario en la Base de Datos.
     *
     * @param data El usuario que se desea guardar en la Base de Datos.
     * @return El usuario guardado en la Base de Datos.
     */
    @Override
    public Usuario save(Usuario data) {
        //Do nothing.
        return null;
    }

    /**
     * Actualiza un usuario en la Base de Datos.
     *
     * @param data El usuario que se desea actualizar en la Base de Datos.
     */
    @Override
    public Usuario update(Usuario data) {
        return null;
    }

    /**
     * Elimina un usuario de la Base de Datos.
     *
     * @param data El usuario que se desea eliminar de la Base de Datos.
     */
    @Override
    public void delete(Usuario data) {
        //Do nothing.
    }

    @Override
    public void saveAll(List<Usuario> data) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            for (Usuario u : data) {
                entityManager.persist(u);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Valida la existencia de un usuario en la Base de Datos.
     *
     * @param email El nombre de usuario (email) que se va a validar.
     * @param password La contraseña asociada al usuario que se va a validar.
     * @return El objeto Usuario si se encuentra en la Base de Datos, de lo contrario, retorna null.
     * @throws UsuarioInexistente Excepción lanzada cuando el usuario no existe en la Base de Datos.
     */
    public Usuario validateUser(String email, String password) throws UsuarioInexistente {
        Usuario result = null;
        ArrayList<Usuario> lista = new ArrayList<>();
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u where u.email = :email and u.contrasenha = :password", Usuario.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            lista = (ArrayList<Usuario>) query.getResultList();

            /*
            List<Usuario> resultList = query.getResultList();
            System.out.println("Número de resultados: " + resultList.size());
            for (Usuario user : resultList) {
                System.out.println("Usuario: " + user);
            }
            */

            try {
                result = lista.get(0);
                System.out.println(result);
            } catch (NoResultException e) {
                throw new UsuarioInexistente("Usuario inexistente");
            }
        } finally {
            entityManager.close();
        }

        return result;
    }
}

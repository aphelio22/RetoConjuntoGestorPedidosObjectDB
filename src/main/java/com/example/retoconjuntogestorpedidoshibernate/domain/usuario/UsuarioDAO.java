package com.example.retoconjuntogestorpedidoshibernate.domain.usuario;

import com.example.retoconjuntogestorpedidoshibernate.domain.DAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.HibernateUtil;
import com.example.retoconjuntogestorpedidoshibernate.exceptions.UsuarioInexistente;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

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
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()){
            Query<Usuario> query = sesion.createQuery("from Usuario", Usuario.class);
            salida = (ArrayList<Usuario>) query.getResultList();
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
        var salida = new Usuario();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            salida = session.get(Usuario.class, id);
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
    public void update(Usuario data) {
        //Do nothing.
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

    /**
     * Valida la existencia de un usuario en la Base de Datos.
     *
     * @param username El nombre de usuario (email) que se va a validar.
     * @param password La contraseña asociada al usuario que se va a validar.
     * @return El objeto Usuario si se encuentra en la Base de Datos, de lo contrario, retorna null.
     * @throws UsuarioInexistente Excepción lanzada cuando el usuario no existe en la Base de Datos.
     */
    public Usuario validateUser(String username, String password) throws UsuarioInexistente {
        Usuario result = null;


        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //Se crea una consulta para obtener el usuario por su nombre de usuario (email) y contraseña.
            Query<Usuario> query = session.createQuery("from Usuario where email=:u and contrasenha=:p", Usuario.class);

            //Se establecen los parámetros de la consulta.
            query.setParameter("u", username);
            query.setParameter("p", password);


            try {
                //Se intenta obtener un único resultado de la consulta.
                result = query.getSingleResult();
            } catch (NoResultException e){
                //Si no se encuentra ningún resultado, se lanza una excepción UsuarioInexistente.
                throw new UsuarioInexistente("Usuario inexistente");
        }
        return result;
    }
}
}

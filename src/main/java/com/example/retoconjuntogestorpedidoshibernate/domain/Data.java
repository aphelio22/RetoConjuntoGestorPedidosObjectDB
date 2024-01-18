package com.example.retoconjuntogestorpedidoshibernate.domain;

import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import com.example.retoconjuntogestorpedidoshibernate.domain.producto.Producto;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.PedidoDAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.producto.ProductoDAO;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Data {
    public static List<Usuario> generarUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();

        // Generar usuarios sin asignar pedidos
        listaUsuarios.add(new Usuario("Jorge Alarcón Navarro", "1234Patata", "jorge@gmail.com", null));
        listaUsuarios.add(new Usuario("Marta López Pérez", "Contraseña", "marta@gmail.com", null));
        listaUsuarios.add(new Usuario("Jose Carrasco Navarro", "1234Pass", "jose@gmail.com", null));
        listaUsuarios.add(new Usuario("Carlos Fernan Gutiérrez", "Lalaland1234", "carlos@gmail.com", null));

        return listaUsuarios;
    }

    public static List<Pedido> generarPedidos(){
        List<Pedido> listaPedidos = new ArrayList<>();
        listaPedidos.add(new Pedido("PED-001", "2023-09-12", 4050.0, generarUsuarios().get(0), null));
        listaPedidos.add(new Pedido("PED-002", "2023-09-15", 1198.0, generarUsuarios().get(1), null));
        listaPedidos.add(new Pedido("PED-003", "2023-09-11", 1975.0, generarUsuarios().get(2), null));
        listaPedidos.add(new Pedido("PED-004", "2023-09-10", 8385.0, generarUsuarios().get(3), null));
        listaPedidos.add(new Pedido("PED-005", "2023-09-12", 2495.0, generarUsuarios().get(0), null));
        listaPedidos.add(new Pedido("PED-006", "2023-09-20", 1593.0, generarUsuarios().get(0), null));
        listaPedidos.add(new Pedido("PED-007", "2023-09-22", 4093.0, generarUsuarios().get(1),null));
        listaPedidos.add(new Pedido("PED-008", "2023-09-25", 2794.0, generarUsuarios().get(1), null));
        listaPedidos.add(new Pedido("PED-009", "2023-09-27", 4394.0, generarUsuarios().get(2), null));
        listaPedidos.add(new Pedido("PED-010", "2023-09-30", 536.0, generarUsuarios().get(2), null));
        listaPedidos.add(new Pedido("PED-011", "2023-10-02", 1993.0, generarUsuarios().get(3), null));
        listaPedidos.add(new Pedido("PED-012", "2023-10-05", 2395.0, generarUsuarios().get(0), null));
        listaPedidos.add(new Pedido("PED-013", "2023-10-07", 3433.0, generarUsuarios().get(1), null));
        listaPedidos.add(new Pedido("PED-014", "2023-10-09", 1897.0, generarUsuarios().get(2), null));
        listaPedidos.add(new Pedido("PED-015", "2023-10-12", 3592.0, generarUsuarios().get(3), null));
        return listaPedidos;
    }

    public static void asignarPedidosAUsuarios(List<Pedido> pedidos) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();

        try {
            for (Pedido pedido : pedidos) {
                Usuario usuario = pedido.getUsuario();
                if (usuario != null) {
                    entityManager.getTransaction().begin();

                    // Recuperar usuario existente de la base de datos
                    Usuario usuarioExistente = entityManager.find(Usuario.class, usuario.getId());

                    if (usuarioExistente != null) {
                        if (usuarioExistente.getPedidos() == null) {
                            usuarioExistente.setPedidos(new ArrayList<>());
                        }
                        usuarioExistente.getPedidos().add(pedido);
                    }

                    entityManager.getTransaction().commit();
                }
            }
        } finally {
            entityManager.close();
        }
    }

    public static List<Item> generarItems(){
        List<Item> listaItems = new ArrayList<>();
        listaItems.add(new Item(generarPedidos().get(0), 5, generarProductos().get(0)));
        listaItems.add(new Item(generarPedidos().get(0), 3, generarProductos().get(2)));
        listaItems.add(new Item(generarPedidos().get(1), 2, generarProductos().get(4)));
        listaItems.add(new Item(generarPedidos().get(2), 3, generarProductos().get(1)));
        listaItems.add(new Item(generarPedidos().get(3), 5, generarProductos().get(4)));
        listaItems.add(new Item(generarPedidos().get(4), 3, generarProductos().get(1)));
        listaItems.add(new Item(generarPedidos().get(5), 3, generarProductos().get(2)));
        listaItems.add(new Item(generarPedidos().get(6), 5, generarProductos().get(2)));
        listaItems.add(new Item(generarPedidos().get(7), 1, generarProductos().get(1)));
        listaItems.add(new Item(generarPedidos().get(8), 3, generarProductos().get(2)));
        listaItems.add(new Item(generarPedidos().get(9), 2, generarProductos().get(1)));
        listaItems.add(new Item(generarPedidos().get(10), 2, generarProductos().get(4)));
        listaItems.add(new Item(generarPedidos().get(11), 1, generarProductos().get(4)));
        listaItems.add(new Item(generarPedidos().get(12), 2, generarProductos().get(2)));
        listaItems.add(new Item(generarPedidos().get(13), 2, generarProductos().get(3)));
        listaItems.add(new Item(generarPedidos().get(14), 1, generarProductos().get(3)));
        return listaItems;
    }

    public static List<Producto> generarProductos(){
        List<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("Smartphone", 299.0, 50));
        listaProductos.add(new Producto("Portátil", 799.0, 30));
        listaProductos.add(new Producto("Auriculares Inalámbricos", 79.0, 100));
        listaProductos.add(new Producto("Televisor LED", 599.0, 20));
        listaProductos.add(new Producto("Tablet", 199.0, 40));
        return listaProductos;
    }
}

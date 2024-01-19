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
        listaUsuarios.add(new Usuario("Jorge Alarcón Navarro", "1234Patata", "jorge@gmail.com", new ArrayList<>()));
        listaUsuarios.add(new Usuario("Marta López Pérez", "Contraseña", "marta@gmail.com", new ArrayList<>()));
        listaUsuarios.add(new Usuario("Jose Carrasco Navarro", "1234Pass", "jose@gmail.com", new ArrayList<>()));
        listaUsuarios.add(new Usuario("Carlos Fernan Gutiérrez", "Lalaland1234", "carlos@gmail.com", new ArrayList<>()));

        return listaUsuarios;
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

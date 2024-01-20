package com.example.retoconjuntogestorpedidoshibernate.domain.usuario;

import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import javax.persistence.*;
import javafx.scene.Scene;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa a un usuario en el sistema.
 */
@Data
@Entity
public class Usuario implements Serializable {

    public Usuario(String nombre, String contrasenha, String email, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.contrasenha = contrasenha;
        this.email = email;
        this.pedidos = pedidos;
    }

    /**
     * Id del usuario.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Contraseña del usuario.
     */
    private String contrasenha;

    /**
     * Email del usuario.
     */
    private String email;

    /**
     * Lista de pedidos que el usuario tiene asignados.
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>(0);

    public void addPedido(Pedido pedido) {
        pedido.setUsuario(this);
        pedidos.add(pedido);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nombre, usuario.nombre) && Objects.equals(contrasenha, usuario.contrasenha) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, contrasenha, email);
    }
}

package com.example.retoconjuntogestorpedidoshibernate.domain.usuario;

import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import jakarta.persistence.*;
import javafx.scene.Scene;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un usuario en el sistema.
 */
@Data
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {
    /**
     * Id del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "contrasenha")
    private String contrasenha;

    /**
     * Email del usuario.
     */
    @Column(name = "email")
    private String email;

    /**
     * Lista de pedidos que el usuario tiene asignados.
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>(0);
}

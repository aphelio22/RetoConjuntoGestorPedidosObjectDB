package com.example.retoconjuntogestorpedidoshibernate.domain.pedido;

import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un pedido en el sistema.
 */
@Data
@Entity
@Table(name = "Pedido")
public class Pedido implements Serializable {

    /**
     * Id del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Código de pedido del pedido.
     */
    @Column(name = "codigo_pedido")
    private String codigo_pedido;

    /**
     * Fecha de realización del pedido.
     */
    @Column(name = "fecha")
    private String fecha;

    /**
     * Precio total del pedido.
     */
    @Column(name = "total")
    private Double total;

    /**
     * Usuario que ha realizado el pedido.
     */
    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    private Usuario usuario;

    /**
     * Lista de items que contiene el pedido.
     */
    @OneToMany(mappedBy = "codigo_pedido", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Item> items = new ArrayList<>();

    /**
     * Representación en forma de cadena de texto del objeto Pedido.
     *
     * @return Representación en forma de cadena de texto del objeto Pedido.
     */
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo_pedido='" + codigo_pedido + '\'' +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", usuario=" + usuario.getId() +
                ", items=" + items +
                '}';
    }
}

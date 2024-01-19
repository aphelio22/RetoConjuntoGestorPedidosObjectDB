package com.example.retoconjuntogestorpedidoshibernate.domain.pedido;

import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un pedido en el sistema.
 */
@Data
@Entity
@NoArgsConstructor
public class Pedido implements Serializable {

    public Pedido(String codigo_pedido, String fecha, Double total, Usuario usuario, List<Item> items) {
        this.codigo_pedido = codigo_pedido;
        this.fecha = fecha;
        this.total = total;
        this.usuario = usuario;
        this.items = items;
    }

    /**
     * Id del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Código de pedido del pedido.
     */
    private String codigo_pedido;

    /**
     * Fecha de realización del pedido.
     */
    private String fecha;

    /**
     * Precio total del pedido.
     */
    private Double total;

    /**
     * Usuario que ha realizado el pedido.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuario;

    /**
     * Lista de items que contiene el pedido.
     */
    @OneToMany(mappedBy = "codigo_pedido", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Item> items = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) && Objects.equals(codigo_pedido, pedido.codigo_pedido) && Objects.equals(fecha, pedido.fecha) && Objects.equals(total, pedido.total) && Objects.equals(usuario, pedido.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo_pedido, fecha, total, usuario);
    }

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

package com.example.retoconjuntogestorpedidoshibernate.domain.item;

import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import com.example.retoconjuntogestorpedidoshibernate.domain.producto.Producto;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Clase que representa un ítem en un pedido.
 * Esta entidad almacena información sobre un ítem específico incluyendo su ID, pedido asociado, cantidad y producto.
 */
@Data
@Entity
@NoArgsConstructor
public class Item implements Serializable {

    public Item(Pedido codigo_pedido, Integer cantidad, Producto producto) {
        this.codigo_pedido = codigo_pedido;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Código del pedido asociado.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Pedido codigo_pedido;

    /**
     * Cantidad del producto que hay contenido en el item.
     */
    private Integer cantidad;

    /**
     * Producto que hay contenido en el item.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private Producto producto;



    /**
     * Método para obtener una representación en texto del objeto Item.
     * Devuelve una cadena que muestra información sobre el ítem incluyendo su ID, código de pedido, cantidad y producto.
     *
     * @return Una representación en texto del objeto Item.
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", codigo_pedido='" + codigo_pedido.getCodigo_pedido() + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", producto=" + producto +
                '}';
    }
}

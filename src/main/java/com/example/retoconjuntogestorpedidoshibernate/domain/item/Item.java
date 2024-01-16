package com.example.retoconjuntogestorpedidoshibernate.domain.item;

import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import com.example.retoconjuntogestorpedidoshibernate.domain.producto.Producto;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * Clase que representa un ítem en un pedido.
 * Esta entidad almacena información sobre un ítem específico incluyendo su ID, pedido asociado, cantidad y producto.
 */
@Data
@Entity
@Table(name = "Item")
public class Item implements Serializable {

    /**
     * Id del item del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Código del pedido asociado.
     */
    @ManyToOne
    @JoinColumn(name = "codigo_pedido", referencedColumnName = "codigo_pedido")
    private Pedido codigo_pedido;

    /**
     * Cantidad del producto que hay contenido en el item.
     */
    @Column(name = "cantidad")
    private Integer cantidad;

    /**
     * Producto que hay contenido en el item.
     */
    @OneToOne
    @JoinColumn(name = "producto")
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

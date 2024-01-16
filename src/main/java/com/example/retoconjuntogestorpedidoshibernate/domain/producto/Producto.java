package com.example.retoconjuntogestorpedidoshibernate.domain.producto;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * Clase que representa un producto en el sistema.
 */
@Data
@Entity
@Table(name = "Producto")
public class Producto implements Serializable {

    /**
     * Id del prodcuto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del prodcuto.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Precio del producto.
     */
    @Column(name = "precio")
    private Double precio;

    /**
     * Cantidad disponible del producto.
     */
    @Column(name = "cantidad_disponible")
    private Integer cantidad_disponible;

    /**
     * Representación en forma de cadena de texto del objeto Producto.
     *
     * @return Nombre del producto como representación en forma de cadena de texto.
     */
    @Override
    public String toString() {
        return nombre;
    }
}

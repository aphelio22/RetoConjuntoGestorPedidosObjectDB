package com.example.retoconjuntogestorpedidoshibernate.domain.producto;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Clase que representa un producto en el sistema.
 */
@Data
@Entity
@NoArgsConstructor
public class Producto implements Serializable {
    public Producto(String nombre, Double precio, Integer cantidad_disponible) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad_disponible = cantidad_disponible;
    }

    /**
     * Id del prodcuto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del prodcuto.
     */
    private String nombre;

    /**
     * Precio del producto.
     */
    private Double precio;

    /**
     * Cantidad disponible del producto.
     */
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

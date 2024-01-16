package com.example.retoconjuntogestorpedidoshibernate.exceptions;

/**
 * Esta excepción se lanza cuando se intenta acceder a un usuario que no existe.
 */
public class UsuarioInexistente extends Exception {
    /**
     * Crea una nueva instancia de la excepción con el mensaje de error proporcionado.
     *
     * @param message El mensaje de error que describe la razón de la excepción.
     */
    public UsuarioInexistente(String message) {
        super(message);
    }
}

package com.example.retoconjuntogestorpedidoshibernate.controllers;

import com.example.retoconjuntogestorpedidoshibernate.HelloApplication;
import com.example.retoconjuntogestorpedidoshibernate.Sesion;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.Usuario;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.UsuarioDAO;
import com.example.retoconjuntogestorpedidoshibernate.exceptions.UsuarioInexistente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la ventana de Login.
 */
public class LoginController implements Initializable {
    /**
     * TextField donde el usuario pone su email.
     */
    @FXML
    private TextField userField;

    /**
     * TextField donde el usuario pone su contraseña.
     */
    @FXML
    private PasswordField passField;

    /**
     * Inicializa la vista de inicio de sesión.
     *
     * @param url            La URL de inicialización.
     * @param resourceBundle El ResourceBundle utilizado para la inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Do nothing.
    }

    /**
     * Método para el inicio de sesión.
     *
     * @param actionEvent El evento de acción que desencadena el intento de inicio de sesión.
     */
    @FXML
    public void login(ActionEvent actionEvent) {
        // Obtiene el usuario y la contraseña ingresados en los campos correspondientes.
        String user = userField.getText();
        String pass = passField.getText();
        Usuario usuario = null;

        try {
            // Intenta validar el usuario utilizando el método correspondiente de la clase UsuarioDAO.
            usuario = (new UsuarioDAO()).validateUser(user, pass);

            // Si se valida correctamente, establece el usuario en la sesión actual.
            Sesion.setUsuario(usuario);

            // Muestra un mensaje de confirmación de inicio de sesión.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("¡Hola!");
            alert.setHeaderText("Inicio correcto");
            alert.setContentText("Bienvenid@, " + usuario.getNombre() + ".");
            alert.showAndWait();

            // Carga la ventana PedidosUsuarioController.
            HelloApplication.loadFXMLUsuario("pedidosUsuario-controller.fxml");

        } catch (UsuarioInexistente e) {

            // Si ocurre un error debido a un usuario inexistente, muestra un mensaje de advertencia.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("¡Algo ha fallado!");
            alert.setContentText("Usuario no encontrado. Puede que el usuario no exista o la contraseña sea incorrecta.");
            alert.showAndWait();
            System.out.println("Usuario no encontrado.");
        }

    }
}


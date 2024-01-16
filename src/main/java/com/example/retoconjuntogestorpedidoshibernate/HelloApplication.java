package com.example.retoconjuntogestorpedidoshibernate;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase HelloApplication es la clase principal de la aplicación JavaFX.
 * Proporciona métodos para cargar diferentes archivos FXML y cambiar las escenas en la ventana principal.
 */
public class HelloApplication extends Application {
    private static Stage myStage;

    /**
     * El método start es llamado al iniciar la aplicación. Establece la escena inicial y la muestra en la ventana.
     *
     * @param stage El objeto Stage representa la ventana principal de la aplicación.
     * @throws IOException Excepción lanzada si hay problemas al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-controller.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.getIcons().add(new Image("C:\\Users\\jrgal\\IdeaProjects\\RetoConjuntoGestorPedidosHibernate\\src\\main\\resources\\images\\gatitoAmazonasFelizIconoApp.png", 100, 100, true, true));
        stage.setTitle("Gestor de pedidos: Miauzon");
        stage.setScene(scene);
        stage.show();
        //hola
    }

    /**
     * El método loadFXMLUsuario carga y muestra una nueva escena en la ventana principal, con el tamaño especificado.
     * En este caso la ventana de Usuario con los pedidos listados.
     *
     * @param ruta La ruta al archivo FXML que se cargará.
     */
    public static void loadFXMLUsuario(String ruta) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 858, 650);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * El método loadFXMLDetalles carga y muestra una nueva escena en la ventana principal, con el tamaño especificado.
     * En este caso la ventana de detalles del pedido con su información especificada.
     *
     * @param ruta La ruta al archivo FXML que se cargará.
     */
    public static void loadFXMLDetalles(String ruta) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 600, 427);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * El método loadFXMLCrearProducto carga y muestra una nueva escena en la ventana principal, con el tamaño especificado.
     * En este caso la pantalla de AnhadirItemController donde el usuario podrá añadir nuevos items al pedido.
     *
     * @param ruta La ruta al archivo FXML que se cargará.
     */
    public static void loadFXMLCrearProducto(String ruta) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 600, 427);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * El método loadFXMLLogin carga y muestra una nueva escena en la ventana principal, con el tamaño especificado.
     * En este caso la pantalla de Login donde el usuario podrá acceder a su sesión.
     *
     * @param ruta La ruta al archivo FXML que se cargará.
     */
    public static void loadFXMLLogin(String ruta) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(ruta));
            Scene scene = new Scene(fxmlLoader.load(), 600, 427);
            myStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * El método main inicia la aplicación JavaFX llamando al método launch.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación).
     */
    public static void main(String[] args) {
        launch();
    }
}
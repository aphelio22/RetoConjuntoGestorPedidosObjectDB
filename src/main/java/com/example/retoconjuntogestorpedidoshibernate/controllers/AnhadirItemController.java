package com.example.retoconjuntogestorpedidoshibernate.controllers;

import com.example.retoconjuntogestorpedidoshibernate.HelloApplication;
import com.example.retoconjuntogestorpedidoshibernate.Sesion;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.ItemDAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.PedidoDAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.producto.Producto;
import com.example.retoconjuntogestorpedidoshibernate.domain.producto.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la funcionalidad de añadir ítems a un pedido.
 */
public class AnhadirItemController implements Initializable {

    /**
     * Spinner con la cantidad de un mismo producto que se va añadir al pedido.
     */
    @FXML
    private Spinner<Integer> spCantidad;

    /**
     * Combo Box que contiene todos los productos que se pueden agregar a un item y por consiguiente a un pedido.
     */
    @FXML
    private ComboBox<Producto> comboProducto;

    /**
     * Observable que contiene una lista de todos los productos disponibles, se usa para añadirlos al Combo Box.
     */
    private ObservableList<Producto> observableListProductos;

    /**
     * Label que muestra la cantidad disponible que hay en un producto al seleccionarlo en el Combo Box.
     */
    @FXML
    private Label lbInfoCantidad;

    /**
     * Inicializa el controlador y establece los valores iniciales.
     *
     * @param url            La URL de inicialización.
     * @param resourceBundle El ResourceBundle utilizado para la inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Observable que se usará para gestionar una lista de productos disponibles en un Combo Box.
        observableListProductos = FXCollections.observableArrayList();
        //Se crea un nuevo ProductoDAO.
        ProductoDAO productoDAO = new ProductoDAO();
        //Se rellena el Observable con todos los productos.
        observableListProductos.setAll(productoDAO.getAll());
        //Se carga el Combo Box con el Observable.
        comboProducto.setItems(observableListProductos);
        //Se selecciona el primer producto del Combo Box por defecto.
        comboProducto.getSelectionModel().selectFirst();
        //Se establece el Spinner para que solo pueda llegar hasta 100 con paso 1, teniendo como predeterminado el 1.
        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1));
        //Muestra la cantidad disponible que hay de cada producto seleccionado.
        lbInfoCantidad.setText("Cantidad disponible: " + comboProducto.getSelectionModel().getSelectedItem().getCantidad_disponible());
        comboProducto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lbInfoCantidad.setText("Cantidad disponible: " + newValue.getCantidad_disponible());
            }
        });
    }

    /**
     * Método para agregar un ítem al pedido actual.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     */
    @Deprecated
    public void aceptar(ActionEvent actionEvent) {

        //Se crea una instancia de Pedido con el pedido actual de la sesión.
        Pedido pedido = Sesion.getPedido();

        //Si el pedido es distinto de nulo se crea un nuevo item para ese pedido  y se retorna a la ventana de DetallesPedidoController.
        Producto productoSeleccionado = comboProducto.getSelectionModel().getSelectedItem();
        Integer cantidadAgregada = spCantidad.getValue();
        Integer cantidadDisponible = productoSeleccionado.getCantidad_disponible();

        if (cantidadAgregada > cantidadDisponible) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cantidad excedida");
            alert.setHeaderText("No se puede agregar esa cantidad");
            alert.setContentText("Cantidad de producto disponible: " + productoSeleccionado.getCantidad_disponible());
            alert.showAndWait();
        } else {
            Item item = new Item();
            item.setCodigo_pedido(pedido);
            item.setCantidad(spCantidad.getValue());
            item.setProducto(productoSeleccionado);

            Sesion.setItem((new ItemDAO()).save(item));
            Sesion.setItem(item);

            HelloApplication.loadFXMLDetalles("detallesPedido-controller.fxml");
        }
    }

    /**
     * Método para cerrar la sesión actual y cargar la pantalla de inicio de sesión.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     */
    @Deprecated
    public void logOut(ActionEvent actionEvent) {
        //Se settea el usuario actual a null y vuelve al LoginController.
        Sesion.setUsuario(null);
        HelloApplication.loadFXMLLogin("login-controller.fxml");
    }

    /**
     * Método para mostrar información "Acerca de".
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     */
    @Deprecated
    public void mostrarAcercaDe(ActionEvent actionEvent) {
        // Muestra información "Acerca de" en una ventana de diálogo.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de: ");
        alert.setHeaderText("Creado por: ");
        alert.setContentText("Jorge Alarcón Navarro, 2ºDAM");
        alert.showAndWait();
    }

    /**
     * Método para volver atrás.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     */
    @Deprecated
    public void volverAtrás(ActionEvent actionEvent) {
        //Vuelve a la pantalla inmediatamente anterior.
        HelloApplication.loadFXMLDetalles("detallesPedido-controller.fxml");
    }
}

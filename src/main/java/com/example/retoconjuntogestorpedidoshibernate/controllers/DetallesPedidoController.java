package com.example.retoconjuntogestorpedidoshibernate.controllers;

import com.example.retoconjuntogestorpedidoshibernate.HelloApplication;
import com.example.retoconjuntogestorpedidoshibernate.Sesion;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.Item;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.ItemDAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.PedidoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controlador para la vista que muestra los detalles de un pedido y sus ítems asociados.
 */
public class DetallesPedidoController implements Initializable {

    /**
     * Tabla en la que se muestran todos los item de un pedido.
     */
    @FXML
    private TableView<Item> tvItem;

    /**
     * Columna en la que se muestra el id del item de un pedido.
     */
    @FXML
    private TableColumn<Item, String> cIdItem;

    /**
     * Columna en la que se muestra el Código de Pedido con el que se relaciona el item.
     */
    @FXML
    private TableColumn<Item, String> cCPedido;

    /**
     * Columna en la que se muestra la cantidad de un determinado producto que contiene el item de un pedido.
     */
    @FXML
    private TableColumn<Item, String> cCantidad;

    /**
     * Columna en la que se muestra el nombre de un determinado producto que contiene el item de un pedido.
     */
    @FXML
    private TableColumn<Item, String> cProducto;

    /**
     * Label de bienvenida de la ventana.
     */
    @FXML
    private Label lbPrueba;

    /**
     * instancia de ItemDAO.
     */
    private ItemDAO itemDAO = new ItemDAO();

    /**
     * Observable que se usará como intermediario entre la Base de Datos y la tabla de items de un pedido de la ventana.
     */
    private ObservableList<Item> observableList;


    /**
     * Inicializa la vista de detalles de un pedido con todos los items que este tiene asociado.
     *
     * @param url            La URL de inicialización.
     * @param resourceBundle El ResourceBundle utilizado para la inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Configuración de las celdas de la tabla para mostrar información específica de los ítems.
        cIdItem.setCellValueFactory((fila) -> {
            String cIdItem = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(cIdItem);
        });

        cCPedido.setCellValueFactory((fila) -> {
            String cCPedido = String.valueOf(fila.getValue().getCodigo_pedido().getCodigo_pedido());
            return new SimpleStringProperty(cCPedido);
        });

        cCantidad.setCellValueFactory((fila) -> {
            String cCantidad = String.valueOf(fila.getValue().getCantidad());
            return new SimpleStringProperty(cCantidad);
        });

        cProducto.setCellValueFactory((fila) -> {
            String cProducto = String.valueOf(fila.getValue().getProducto().getNombre() + "/" + fila.getValue().getProducto().getPrecio() + "€");
            return new SimpleStringProperty(cProducto);
        });

        //Declaración del Observable que servirá de intermediario.
        observableList = FXCollections.observableArrayList();

        //Se llena el Observable con todos los items de un determinado pedido.
        Sesion.setPedido((new PedidoDAO()).get(Sesion.getPedido().getId()));
        observableList.setAll(Sesion.getPedido().getItems());

        //Se llena la tabla con el Observable.
        tvItem.setItems(observableList);

        //Se establece el label de bienvenida de la ventana.
        lbPrueba.setText("Items del pedido: " + Sesion.getPedido().getCodigo_pedido());

        //Actualiza el total del pedido después de agregar el item.
        actualizarPedido();

    }

    /**
     * Método para cerrar la sesión actual y cargar la pantalla de inicio de sesión.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     */
    @FXML
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
    @FXML
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
    @FXML
    public void volverAtrás(ActionEvent actionEvent) {
        //Vuelve a la pantalla inmediatamente anterior.
        HelloApplication.loadFXMLUsuario("pedidosUsuario-controller.fxml");
    }

    /**
     * Método para añadir un nuevo ítem al pedido.
     *
     * @param actionEvent El evento de acción que desencadena la operación de añadir un ítem.
     */
    @FXML
    public void anhadirItem(ActionEvent actionEvent) {
        // Crea un nuevo ítem.
        var item = new Item();

        // Establece el ítem recién creado en la sesión actual para su posterior uso.
        Sesion.setItem(item);

        //Lleva a la pantalla de AnhadirItemController.
        HelloApplication.loadFXMLCrearProducto("anhadirItem-controller.fxml");
    }

    /**
     * Método para eliminar un ítem seleccionado del pedido.
     *
     * @param actionEvent El evento de acción que desencadena la operación.
     *                    Si no se selecciona un ítem, se muestra un mensaje de advertencia.
     */
    @FXML
    public void eliminarItem(ActionEvent actionEvent) {

        //Se coge el item seleccionado.
        Item itemSeleccionado = tvItem.getSelectionModel().getSelectedItem();

        //Confirmación de eliminación mediante un diálogo de confirmación.
        if (itemSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Deseas borrar el item: " + itemSeleccionado.getId() + ", que contiene el producto: " + itemSeleccionado.getProducto().getNombre() + "?");
            var result = alert.showAndWait().get();

            //Si se confirma la eliminación, se borra el ítem seleccionado de la lista y de la base de datos.
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                itemDAO.delete(itemSeleccionado);
                observableList.remove(itemSeleccionado);

                //Calcula el nuevo total del pedido y lo actualiza en la Base de Datos.
                Pedido pedidoActual = Sesion.getPedido();
                Double nuevoTotal = calcularTotalPedido(pedidoActual) - (itemSeleccionado.getProducto().getPrecio() * itemSeleccionado.getCantidad()) ;
                System.out.println(nuevoTotal);
                pedidoActual.setTotal(nuevoTotal);

                //Actualiza el total del pedido en la Base de Datos
                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.update(pedidoActual);
            }
        } else {
            //Muestra un mensaje de error o advertencia al usuario si no se ha seleccionado ningún pedido para eliminar.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un item para eliminar.");
            alert.showAndWait();
        }
    }

    /**
     * Calcula el total del pedido basado en la cantidad de cada producto y su precio.
     *
     * @param pedido El pedido del cual se calculará el total.
     * @return El total del pedido, calculado como la suma del precio de cada producto multiplicado por su cantidad.
     */
    private Double calcularTotalPedido(Pedido pedido) {
        //Inicializa la variable total como 0.0 para almacenar el total del pedido.
        Double total  = 0.0;

        //Itera a través de los items del pedido para calcular el total.
        for (Item item : pedido.getItems()){

            //Obtiene el precio del producto y lo multiplica por la cantidad, sumando al total.
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        //Retorna el total calculado del pedido.
        return total;
    }

    /**
     * Método que actualiza el pedido en la Base de Datos después de agregar o eliminar un item.
     */
    private void actualizarPedido() {
        Pedido pedidoActual = Sesion.getPedido();
        Double nuevoTotal = calcularTotalPedido(pedidoActual);
        pedidoActual.setTotal(nuevoTotal);

        try {
            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.update(pedidoActual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void visualizarPedido(ActionEvent actionEvent) {
        String codigo_pedido = Sesion.getPedido().getCodigo_pedido();
        Stage primaryStage = new Stage();
        System.out.println(codigo_pedido);
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/RetoDos", "root", "Enterprise1701Voyager74656");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("cod_pedido", codigo_pedido);

            JasperPrint jasperPrint = JasperFillManager.fillReport("reportePedido.jasper", hashMap, connection);

            SwingNode swingNode = new SwingNode();
            createSwingContent(swingNode, jasperPrint);

            StackPane root = new StackPane();
            root.getChildren().add(swingNode);

            Scene scene = new Scene(root, 800, 600);

            primaryStage.getIcons().add(new Image("C:\\Users\\jrgal\\IdeaProjects\\RetoConjuntoGestorPedidosHibernate\\src\\main\\resources\\images\\gatitoAmazonasFelizIconoApp.png", 100, 100, true, true));
            primaryStage.setTitle("Detalles Pedido");
            primaryStage.setScene(scene);
            primaryStage.show();

            JRPdfExporter exp = new JRPdfExporter();
            exp.setExporterInput(new SimpleExporterInput(jasperPrint));
            exp.setExporterOutput(new SimpleOutputStreamExporterOutput("pedido.pdf"));
            exp.setConfiguration(new SimplePdfExporterConfiguration());
            exp.exportReport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    private void createSwingContent(final SwingNode swingNode, JasperPrint jasperPrint) {
        SwingUtilities.invokeLater(() -> {
            JRViewer viewer = new JRViewer(jasperPrint);
            swingNode.setContent(viewer);
        });
    }
}

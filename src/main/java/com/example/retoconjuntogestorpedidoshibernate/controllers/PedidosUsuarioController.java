package com.example.retoconjuntogestorpedidoshibernate.controllers;



import com.example.retoconjuntogestorpedidoshibernate.HelloApplication;
import com.example.retoconjuntogestorpedidoshibernate.Sesion;
import com.example.retoconjuntogestorpedidoshibernate.domain.ObjectDBUtil;
import com.example.retoconjuntogestorpedidoshibernate.domain.item.ItemDAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.Pedido;
import com.example.retoconjuntogestorpedidoshibernate.domain.pedido.PedidoDAO;
import com.example.retoconjuntogestorpedidoshibernate.domain.usuario.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controlador para la ventana de gestión de pedidos de usuario.
 */
public class PedidosUsuarioController implements Initializable {

    /**
     * Label de bienvenida con el nombre completo del usuario.
     */
    @FXML
    private Label lbUsuario;

    /**
     * Tabla que muestra los pedidos de un usuario.
     */
    @FXML
    private TableView<Pedido> tvPedidos;

    /**
     * Columna en la que se muestra el id de los pedidos de ese usuario.
     */
    @FXML
    private TableColumn<Pedido, String> cIdPedido;

    /**
     * Columna en la que muestra el código de pedido de los pedidos de ese usuario.
     */
    @FXML
    private TableColumn<Pedido, String> cCPedido;

    /**
     * Columna en la que se muestra la fecha de realización del pedido.
     */
    @FXML
    private TableColumn<Pedido, String> cFecha;

    /**
     * Columna en la que se muestra el nombre del usuario que ha realizado el pedido
     */
    @FXML
    private TableColumn<Pedido, String> cUsuario;

    /**
     * Columna en la que se muestra el coste total del pedido, en euros.
     */
    @FXML
    private TableColumn<Pedido, String> cTotal;

    /**
     * Observable que sirve de intermediario entre la Base de Datos y la tabla en la que se muestran los pedidos.
     */
    private ObservableList<Pedido> observableList;

    /**
     * Instancia de PedidoDAO.
     */
    private PedidoDAO pedidoDAO = new PedidoDAO();


    /**
     * Inicializa la ventana de gestión de pedidos de usuario.
     *
     * @param url            La URL de inicialización.
     * @param resourceBundle El ResourceBundle utilizado para la inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Configuración de las celdas de la tabla para mostrar información específica de los ítems.
        cIdPedido.setCellValueFactory((fila) -> {
            String id = String.valueOf(fila.getValue().getId());
            return new SimpleStringProperty(id);
        });
        cCPedido.setCellValueFactory((fila) -> {
            String cPedido = fila.getValue().getCodigo_pedido();
            return new SimpleStringProperty(cPedido);
        });
        cFecha.setCellValueFactory((fila) -> {
            String cFecha = fila.getValue().getFecha();
            return new SimpleStringProperty(cFecha);
        });
        cUsuario.setCellValueFactory((fila) -> {
            String cUsuario = String.valueOf(fila.getValue().getUsuario().getNombre());
            return new SimpleStringProperty(cUsuario);
        });
        cTotal.setCellValueFactory((fila) -> {
            String cTotal = String.valueOf(fila.getValue().getTotal() + "€");
            return new SimpleStringProperty(cTotal);
        });

        //Declaración del Observable que servirá de intermediario.
        observableList = FXCollections.observableArrayList();

        /*
         * Actualiza el objeto de usuario almacenado en la sesión con la información más reciente de la base de datos.
         * Obtiene el usuario actual a partir de su ID, actualiza la información del usuario en la sesión.
         */
        Sesion.setUsuario((new UsuarioDAO()).get(Sesion.getUsuario().getId()));

        //Método que carga la lista de pedidos que un usuario tiene en la tabla usando el Observable.
        cargarLista();

        //Se establece el mensaje de bienvenida en el label.
        lbUsuario.setText("Bienvenid@: " + Sesion.getUsuario().getNombre());

        /*
         * Establece un listener para el evento de selección de un pedido en la tabla de pedidos.
         * Cuando se selecciona un pedido, actualiza el pedido almacenado en la sesión.
         */
        tvPedidos.getSelectionModel().selectedItemProperty().addListener((observableValue, pedido, t1) -> {
            Sesion.setPedido(t1);
        });

        /*
         * Establece un evento para manejar el doble clic del ratón en la tabla de pedidos.
         * Cuando se hace doble clic en un pedido, actualiza el pedido almacenado en la sesión y carga los detalles del pedido.
         */
        tvPedidos.setOnMouseClicked(event -> {
            //Verifica si se ha realizado un doble clic con el botón primario del ratón.
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                //Obtiene el pedido seleccionado.
                Pedido selectedPedido = tvPedidos.getSelectionModel().getSelectedItem();
                if (selectedPedido != null) {
                    //Se actualiza el pedido almacenado en la sesión.
                    Sesion.setPedido(selectedPedido);
                    //Carga los detalles del pedido en una nueva ventana.
                    HelloApplication.loadFXMLDetalles("detallesPedido-controller.fxml");
                }
            }
        });
    }

    /**
     * Carga la lista de pedidos que un usuario tiene en la tabla usando el Observable.
     */
    private void cargarLista() {

        //Obtiene la lista de pedidos del usuario actual y la asigna a la lista Observable.
        observableList.setAll(Sesion.getUsuario().getPedidos());

        //Establece la lista Observable como los datos a mostrar en la tabla.
        tvPedidos.setItems(observableList);
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
        //Muestra información "Acerca de" en una ventana de diálogo.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de: ");
        alert.setHeaderText("Creado por: ");
        alert.setContentText("Jorge Alarcón Navarro, 2ºDAM");
        alert.showAndWait();
    }

    /**
     * Método para agregar un nuevo pedido.
     *
     * @param actionEvent El evento de acción que desencadena la operación de añadir un pedido.
     */
    @FXML
    public void anhadir(ActionEvent actionEvent) {

        Pedido nuevoPedido = new Pedido();
        establecerId(nuevoPedido);
        establecerCodigoPedido(nuevoPedido);
        establecerFecha(nuevoPedido);
        nuevoPedido.setUsuario(Sesion.getUsuario());

        //Si el pedido no tiene items dentro el total se establece a '0.0'.
        if (nuevoPedido.getItems().isEmpty()) {
            nuevoPedido.setTotal(0.0);
        }

        //Agrega el nuevo pedido a la lista Observable.
        observableList.add(nuevoPedido);

        //Actualiza la tabla.
        tvPedidos.setItems(observableList);
        Sesion.setPedido((new PedidoDAO()).save(nuevoPedido));
        Sesion.setPedido(nuevoPedido);

        //Alerta que indica que el pedido fue creado con éxito.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Éxito!");
        alert.setHeaderText("Tu pedido ha sido creado");
        alert.setContentText("Código de pedido: " + Sesion.getPedido().getCodigo_pedido());
        alert.showAndWait();

        //Después de la alerta, lleva a la ventana DetallesPedidoController del respectivo pedido.
        HelloApplication.loadFXMLDetalles("detallesPedido-controller.fxml");

    }

    /**
     * Establece el id para el nuevo pedido.
     *
     * @param pedido Pedido al que se le establecerá el nuevo id.
     */
    private void establecerId(Pedido pedido) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Integer> query = entityManager.createQuery("select MAX(p.id) FROM Pedido p", Integer.class);
            Integer ultimoId = query.getSingleResult();
            if (ultimoId != null){
                //Incrementa y establece el último id.
                pedido.setId(ultimoId + 1);
            } else { //Si no hay pedidos en la Base de Datos, el id será por defecto '1'.
                pedido.setId(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece el código de pedido para el nuevo pedido.
     *
     * @param pedido Pedido al que se le establecerá el nuevo código de pedido.
     */
    private void establecerCodigoPedido(Pedido pedido) {
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<String> query = entityManager.createQuery("select MAX(p.codigo_pedido) FROM Pedido p", String.class);
            String ultimoCodigoPedido = query.getSingleResult();
            if (ultimoCodigoPedido != null) {
                //Incrementa el último código de pedido.
                Integer ultimoNumero = Integer.parseInt(ultimoCodigoPedido.substring(4));
                Integer nuevoNumero = ultimoNumero + 1;
                String nuevoCodigoPedido = "PED-" + String.format("%03d", nuevoNumero);
                //Establece el nuevo código de pedido.
                pedido.setCodigo_pedido(nuevoCodigoPedido);
            }else { //Si no hay pedidos en la Base de Datos, el código de pedido será por defecto 'PED-001'.
                pedido.setCodigo_pedido("PED-001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece la fecha del nuevo pedido.
     *
     * @param pedido Pedido al que se le establecerá la fecha.
     */
    private void establecerFecha(Pedido pedido) {
        //Establece la fecha actual por defecto.
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());
        pedido.setFecha(fechaActual);
    }

    /**
     * Método para eliminar un pedido seleccionado.
     *
     * @param actionEvent El evento de acción que desencadena la operación de eliminar un pedido.
     */
    @FXML
    public void eliminar(ActionEvent actionEvent) {
        ItemDAO itemDAO = new ItemDAO();
        //Se coge el pedido seleccionado.
        Pedido pedidoSeleccionado = tvPedidos.getSelectionModel().getSelectedItem();

        //Confirmación de eliminación mediante un diálogo de confirmación.
        if (pedidoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Deseas borrar el pedido: " + pedidoSeleccionado.getCodigo_pedido() + "?");
            var result = alert.showAndWait().get();

            //Si se confirma la eliminación, se borra el ítem seleccionado de la lista y de la base de datos.
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                pedidoDAO.delete(pedidoSeleccionado);
                observableList.remove(pedidoSeleccionado);
            }
            System.out.println("Pedidos: " + pedidoDAO.getAll());
            System.out.println("Items: " + itemDAO.getAll());
        } else {
            //Muestra un mensaje de error o advertencia al usuario si no se ha seleccionado ningún pedido para eliminar.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un pedido para eliminar.");
            alert.showAndWait();
        }
    }
}

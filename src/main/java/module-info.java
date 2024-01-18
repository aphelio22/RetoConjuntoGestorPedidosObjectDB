module com.example.retoconjuntogestorpedidoshibernate {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires java.naming;
    requires java.sql;
    requires javafx.swing;
    requires jasperreports;
    requires javax.persistence;

    opens com.example.retoconjuntogestorpedidoshibernate.domain.usuario;
    opens com.example.retoconjuntogestorpedidoshibernate.domain.item;
    opens com.example.retoconjuntogestorpedidoshibernate.domain.producto;
    opens com.example.retoconjuntogestorpedidoshibernate.domain.pedido;

    opens com.example.retoconjuntogestorpedidoshibernate to javafx.fxml;
    exports com.example.retoconjuntogestorpedidoshibernate;
    exports com.example.retoconjuntogestorpedidoshibernate.controllers;
    opens com.example.retoconjuntogestorpedidoshibernate.controllers to javafx.fxml;
    exports com.example.retoconjuntogestorpedidoshibernate.domain;
    opens com.example.retoconjuntogestorpedidoshibernate.domain to javafx.fxml;
}
module com.example.retoconjuntogestorpedidoshibernate {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires javafx.swing;
    requires jasperreports;

    opens com.example.retoconjuntogestorpedidoshibernate.domain.usuario;
    opens com.example.retoconjuntogestorpedidoshibernate.domain.item;
    opens com.example.retoconjuntogestorpedidoshibernate.domain.producto;
    opens com.example.retoconjuntogestorpedidoshibernate.domain.pedido;

    opens com.example.retoconjuntogestorpedidoshibernate to javafx.fxml;
    exports com.example.retoconjuntogestorpedidoshibernate;
    exports com.example.retoconjuntogestorpedidoshibernate.controllers;
    opens com.example.retoconjuntogestorpedidoshibernate.controllers to javafx.fxml;
}
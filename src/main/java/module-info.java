module com.averagegames.ultimatetowerdefense {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires static lombok;
    requires java.validation;
    requires java.logging;

    exports com.averagegames.ultimatetowerdefense;
    opens com.averagegames.ultimatetowerdefense to javafx.fxml;
}
module com.averagegames.ultimatetowerdefense {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires static lombok;
    requires org.reflections;
    requires java.validation;
    requires java.logging;

    opens com.averagegames.ultimatetowerdefense to javafx.fxml;
    exports com.averagegames.ultimatetowerdefense;
}
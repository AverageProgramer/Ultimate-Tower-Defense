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

    opens com.averagegames.ultimatetowerdefense to javafx.fxml;
    exports com.averagegames.ultimatetowerdefense;
    exports com.averagegames.ultimatetowerdefense.scenes;
    opens com.averagegames.ultimatetowerdefense.scenes to javafx.fxml;
}
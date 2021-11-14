module ru.projectfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens ru.projectfx to javafx.fxml;
    exports ru.projectfx;
    exports ru.projectfx.controllers;
    opens ru.projectfx.controllers to javafx.fxml;
}
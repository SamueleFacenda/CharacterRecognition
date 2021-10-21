module pack.characterrecognition {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires gson;

    opens pack.characterrecognition to javafx.fxml;
    exports pack.characterrecognition;
    exports pack.characterrecognition.primaryGUI;
    opens pack.characterrecognition.primaryGUI to javafx.fxml;
    exports pack.characterrecognition.test;
    opens pack.characterrecognition.test to javafx.fxml;
    exports pack.characterrecognition.supportClass;
    opens pack.characterrecognition.supportClass to javafx.fxml;
}
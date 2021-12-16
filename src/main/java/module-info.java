module pack.characterrecognition {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires gson;

    opens pack.characterrecognition to javafx.fxml;
    exports pack.characterrecognition;
    exports pack.characterrecognition.primaryGUI;
    opens pack.characterrecognition.primaryGUI to javafx.fxml;
    exports pack.characterrecognition.test;
    opens pack.characterrecognition.test to javafx.fxml;
    exports pack.characterrecognition.supportClass;
    opens pack.characterrecognition.supportClass to javafx.fxml, gson;
    exports pack.characterrecognition.supportClass.saves;
    opens pack.characterrecognition.supportClass.saves to gson, javafx.fxml;
    exports pack.characterrecognition.supportClass.figures;
    opens pack.characterrecognition.supportClass.figures to gson, javafx.fxml;
    exports pack.characterrecognition.supportClass.other;
    opens pack.characterrecognition.supportClass.other to gson, javafx.fxml;
}
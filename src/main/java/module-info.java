module pack.characterrecognition {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires gson;
    requires jfxrt;
    requires rt;

    opens pack.characterrecognition to javafx.fxml;
    exports pack.characterrecognition;
}
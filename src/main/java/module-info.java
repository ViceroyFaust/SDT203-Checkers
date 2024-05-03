module rybchynskyi.checkersgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports rybchynskyi.checkersgui.ui;
    opens rybchynskyi.checkersgui.ui to javafx.fxml;
    exports rybchynskyi.checkersgui.core;
    opens rybchynskyi.checkersgui.core to javafx.fxml;
}
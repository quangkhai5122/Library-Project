module login {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.datatransfer;
    requires java.net.http;
    requires com.google.gson;
    requires java.sql;

    opens login to javafx.fxml;
    opens dashboard to javafx.fxml;
    exports login;
    exports dashboard;
}
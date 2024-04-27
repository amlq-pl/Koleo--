module Koleo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens pl.tcs.oopproject to javafx.fxml;
    exports pl.tcs.oopproject;
}
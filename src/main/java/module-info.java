module Koleo {
    requires javafx.controls;
    requires javafx.fxml;
    opens pl.tcs.oopproject to javafx.fxml;
    exports pl.tcs.oopproject;
}
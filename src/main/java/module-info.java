module Koleo {
	requires javafx.graphics;
	requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens pl.tcs.oopproject to javafx.fxml;
    exports pl.tcs.oopproject;
    exports pl.tcs.oopproject.database;
	exports pl.tcs.oopproject.viewmodel.users;
	exports pl.tcs.oopproject.viewmodel.exception;
    opens pl.tcs.oopproject.view.sceneControllers to javafx.fxml;
    opens pl.tcs.oopproject.database to javafx.fxml;
}
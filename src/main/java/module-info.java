module Koleo {
    requires net.synedra.validatorfx;
	requires javafx.graphics;
	requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens pl.tcs.oopproject to javafx.fxml;
    exports pl.tcs.oopproject;
	exports pl.tcs.oopproject.viewmodel.users;
	exports pl.tcs.oopproject.viewmodel.exception;
	exports pl.tcs.oopproject.viewmodel.carriage;
	exports pl.tcs.oopproject.viewmodel.seat;
	exports pl.tcs.oopproject.viewmodel.connection;
	exports pl.tcs.oopproject.viewmodel.place;
    opens pl.tcs.oopproject.view.sceneControllers to javafx.fxml;
    opens pl.tcs.oopproject.model to javafx.fxml;
	exports pl.tcs.oopproject.viewmodel.station;
    exports pl.tcs.oopproject.postgresDatabaseIntegration;
    opens pl.tcs.oopproject.postgresDatabaseIntegration to javafx.fxml;
    exports pl.tcs.oopproject.model.databaseIntegration;
    opens pl.tcs.oopproject.model.databaseIntegration to javafx.fxml;
}
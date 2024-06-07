module Koleo {
    requires net.synedra.validatorfx;
	requires javafx.graphics;
	requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires org.jetbrains.annotations;
	opens pl.tcs.oopproject to javafx.fxml;
    exports pl.tcs.oopproject;
	exports pl.tcs.oopproject.viewmodel.users;
	exports pl.tcs.oopproject.model.exception;
	exports pl.tcs.oopproject.viewmodel.connection;
	exports pl.tcs.oopproject.viewmodel.place;
	exports pl.tcs.oopproject.model.discount;
    opens pl.tcs.oopproject.view.sceneControllers to javafx.fxml;
	opens pl.tcs.oopproject.view.componentControllers to javafx.fxml;
	exports pl.tcs.oopproject.model.station;
    exports pl.tcs.oopproject.postgresDatabaseIntegration;
    opens pl.tcs.oopproject.postgresDatabaseIntegration to javafx.fxml;
    exports pl.tcs.oopproject.model.databaseIntegration;
    opens pl.tcs.oopproject.model.databaseIntegration to javafx.fxml;
	exports pl.tcs.oopproject.model.carriage;
	exports pl.tcs.oopproject.model.seat;
	exports pl.tcs.oopproject.model.connection;
	exports pl.tcs.oopproject.model.assignedSeat;
	exports pl.tcs.oopproject.model.users;
	exports pl.tcs.oopproject.model.ticket;
	exports pl.tcs.oopproject.viewmodel.tickets;
}
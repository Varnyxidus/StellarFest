module StellarFest {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	exports main;
	opens model to javafx.base;
}
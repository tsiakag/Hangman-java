module com.tp18130.tpel18130 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javax.json;

    opens tpel18130.main_code to javafx.fxml;
    exports tpel18130.main_code;
}
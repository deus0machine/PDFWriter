module com.example.simple_pdf_writer {
    requires javafx.controls;
    requires javafx.fxml;
    requires aspose.pdf;
    requires itextpdf;


    opens com.example.simple_pdf_writer to javafx.fxml;
    exports com.example.simple_pdf_writer;
}
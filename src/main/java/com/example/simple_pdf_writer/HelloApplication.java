package com.example.simple_pdf_writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox main = new VBox(10);

        HBox first_fields = new HBox(5);
        Label first_on_first = new Label("Первое поле: ");
        TextField first_on_first_text = new TextField();
        first_fields.getChildren().addAll(first_on_first, first_on_first_text);

        HBox second_fields = new HBox(5);
        Label first_on_second = new Label("Второе поле: ");
        TextField first_on_second_text = new TextField();
        second_fields.getChildren().addAll(first_on_second, first_on_second_text);

        HBox third_fields = new HBox(5);
        Label first_on_third = new Label("Третье поле: ");
        TextField first_on_third_text = new TextField();
        third_fields.getChildren().addAll(first_on_third, first_on_third_text);

        Button create_document = new Button("Создать документ");
        create_document.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Document document = new Document();

                try {
                    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\User\\Desktop\\filetest.pdf"));
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                document.open();

                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                //Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK); Русский не работает((99(
                Paragraph chunk = new Paragraph("HSoasasjjdaspdsaoso!", font);
                chunk.setAlignment(Element.ALIGN_CENTER);
                try {
                    document.add(chunk);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Поздравление");
                alert.setHeaderText(null);
                alert.setContentText("Превосходно, грациас!");
                alert.initModality(Modality.APPLICATION_MODAL);

                alert.showAndWait();

                document.close();
            }
        });

        Insets insets = new Insets(30,30,30,30);
        main.setPadding(insets);
        main.getChildren().addAll(first_fields, second_fields, third_fields, create_document);
        main.setAlignment(Pos.CENTER);
        Scene scene = new Scene(main, 320, 240);
        stage.setTitle("PdfCreator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
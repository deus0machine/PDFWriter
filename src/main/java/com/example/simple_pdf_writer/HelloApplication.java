package com.example.simple_pdf_writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
                    document.add(new Paragraph("\n"));
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

                PdfPTable table = new PdfPTable(5);

                try {
                    addTableHeader(table);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
                addRows(table);


                try {
                    document.add(table);
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
    private void addTableHeader(PdfPTable table) throws DocumentException {
        float[] columnWidths = {1f, 1f, 1f, 1f, 1f};

        table.setWidths(columnWidths);
        Stream.of("column header 1", "column header 2", "column header 3", "column header 4", "column header 5")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
        table.addCell("row 1, col 4");
        table.addCell("row 1, col 5");
    }
    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    public static void main(String[] args) {
        launch();
    }
}
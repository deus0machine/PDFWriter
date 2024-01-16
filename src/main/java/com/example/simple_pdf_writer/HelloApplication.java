package com.example.simple_pdf_writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
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
        Label first_on_first = new Label("Номер акта: ");
        TextField first_on_first_text = new TextField("5");
        first_on_first_text.setMaxWidth(50);
        first_fields.getChildren().addAll(first_on_first, first_on_first_text);

        HBox prilag = new HBox(5);
        Label prilaglab = new Label("Прилагается к: ");
        TextField prilagtext = new TextField("dogovory ob oplate");
        prilagtext.setMaxWidth(300);
        prilag.getChildren().addAll(prilaglab, prilagtext);

        HBox second_fields = new HBox(5);
        Label first_on_second = new Label("Правообладатель: ");
        TextField first_on_second_text = new TextField("Kortiokov Ivan Johnson");
        second_fields.getChildren().addAll(first_on_second, first_on_second_text);

        HBox third_fields = new HBox(5);
        Label first_on_third = new Label("Приобретатель: ");
        TextField first_on_third_text = new TextField("Filimonov Petr Edsui");
        third_fields.getChildren().addAll(first_on_third, first_on_third_text);

        HBox four_fields = new HBox(5);
        Label date = new Label("Дата: ");
        TextField datetext = new TextField("25.01.2024");
        four_fields.getChildren().addAll(date, datetext);

        HBox city = new HBox(5);
        Label citylab = new Label("Сумма: ");
        TextField citytext = new TextField("139500");
        city.getChildren().addAll(citylab, citytext);

        HBox tovar = new HBox(5);
        Label tovarlab = new Label("Товар: ");
        TextField tovartext = new TextField("New Year Custom Petards");
        tovar.getChildren().addAll(tovarlab, tovartext);

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

                Font font2 = FontFactory.getFont("arial.ttf", "Cp1251", BaseFont.EMBEDDED, 14, Font.NORMAL, BaseColor.BLACK);
                Font font3 = FontFactory.getFont("arial.ttf", "Cp1251", BaseFont.EMBEDDED, 12, Font.NORMAL, BaseColor.BLACK);
                //Font font = FontFactory.getFont(FontFactory.SYMBOL, 16, BaseColor.BLACK);
                Paragraph chunk = new Paragraph("Prilozhenie No: _" + first_on_first_text.getText() + "_   k \n _"+ prilagtext.getText() + "_ \n No "+ first_on_first_text.getText()+ " ot "+ datetext.getText(), font2);

                chunk.setAlignment(Element.ALIGN_RIGHT);
                try {
                    document.add(chunk);
                    document.add(new Paragraph("\n"));
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
                Paragraph chunk2 = new Paragraph("Akt priema-peredachi isluchitelnux prav No _"+ first_on_first_text.getText() + "_",font2);
                chunk2.setAlignment(Element.ALIGN_CENTER);
                try {
                    document.add(chunk2);
                    document.add(new Paragraph("\n"));
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100);

                PdfPCell cell = new PdfPCell(new Phrase("Pravoobladatel' v lice " + first_on_second_text.getText() + " deistvuishiu na osnovanii passporta \n s odnou storonu" +
                        ", imenuemui dalee 'Proobladatel'', i Priobretatel' v lice \n " + first_on_third_text.getText() + " , imenuemui dalee Priobretatel' ", font3));
                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                // ... (добавление других параграфов)
                try {
                    document.add(table);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

                PdfPTable table2 = new PdfPTable(1);
                table2.setWidthPercentage(100);
                PdfPCell cell2 = new PdfPCell(new Phrase("\t \t \t1. Pravoobladatel' peredal, a Priobretatel' v svoyu ochered' prinyal isklyuchitel'nye prava" +
                        " na _"+ tovartext.getText()+ "_  v sootvetstvii s usloviyami Dogovora.", font3));
                cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);
                cell2 = new PdfPCell(new Phrase("\t \t \t 2. Priobretatel' peredal Pravoobladatelyu "+ citytext.getText()+" rubley " +
                        "nalichnymi v kachestve oplaty za priobretenie prav.", font3));
                cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);
                cell2 = new PdfPCell(new Phrase("\t \t \t 3. Pravoobladatel' pretenziy ne imeet.", font3));
                cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);
                cell2 = new PdfPCell(new Phrase("\t \t \t 4. Nastoyashchiy dokument sostavlen v dvukh ravnakh po yuridicheskoy sile ekzemplaryakh, po odnomu dlya kazhdogo iz kontragentov.", font3));
                cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                table2.setSpacingBefore(20f);
                try {
                    document.add(table2);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

                Paragraph fin = new Paragraph("Pravoobladatel': " + first_on_second_text.getText());
                Paragraph fin2 = new Paragraph("Priobretatel': " + first_on_third_text.getText());
                try {
                    document.add(new Paragraph("\n"));
                    document.add(fin);
                    document.add(fin2);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
               /* PdfPTable table = new PdfPTable(5);
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
                }*/


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
        main.getChildren().addAll(first_fields, prilag, second_fields, third_fields, four_fields, city, tovar, create_document);
        main.setAlignment(Pos.CENTER);
        Scene scene = new Scene(main, 400, 300);
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
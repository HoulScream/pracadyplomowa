package common;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.ClientEntity;
import entity.ItemEntity;
import entity.RentalDetailsEntity;
import javafx.scene.control.ComboBox;
import tableview.ClientTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static common.ConfigurationBuilder.*;


public class PDFBuilder {

    private static double bail = 0;
    private static double price = 0;
    private static double penality = 0;
    private static double sum = 0;

    public static void generateRentalConfirmation(ComboBox<ClientTableView> clientComboBox) throws DocumentException, IOException {
        Document document = new Document();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        Date currentDate = new Date();

        String filename = clientComboBox.getValue().getName() + "_" + dateFormat.format(currentDate) + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(orderconfirmationfolder +"\\" + filename));
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font helvetica16 = new Font(helvetica, 12);
        Font helvetica16red = new Font(helvetica, 16);
        helvetica16red.setColor(255, 0, 0);
        Font helvetica12 = new Font(helvetica, 10);

        document.add(new Paragraph("Wypożyczalnia 1.0 - Potwierdzenie wypożyczenia towaru:", helvetica16red));
        document.add(new Paragraph("Dokument wygenerowany automatycznie dnia: " + dateFormat.format(currentDate), helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(hirename, helvetica12));
        document.add(new Paragraph(hireadress, helvetica12));
        document.add(new Paragraph(hirepostalcode, helvetica12));
        document.add(new Paragraph("NIP: "+hirenip, helvetica12));
        document.add(new Paragraph("Telefon: "+hirephonenumber, helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Wypożycza klientowi:", helvetica16));
        document.add(new Paragraph(clientComboBox.getValue().getName(), helvetica12));
        document.add(new Paragraph(clientComboBox.getValue().getAddress(), helvetica12));
        document.add(new Paragraph(clientComboBox.getValue().getPostalcode(), helvetica12));
        document.add(new Paragraph("NIP: " + clientComboBox.getValue().getNip(), helvetica12));
        document.add(new Paragraph("Telefon: " + clientComboBox.getValue().getTelephonenumber(), helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Przedmioty:", helvetica16));
        document.add(new Paragraph(" "));
        document.add(createRentalTable(clientComboBox));
        document.add(new Paragraph(" "));
        DecimalFormat dec = new DecimalFormat("####0.00 zł");
        Paragraph p = new Paragraph(dec.format(bail), helvetica16red);
        bail = 0;
        document.add(new Paragraph("Kaucja do zapłaty: ", helvetica16));
        document.add(p);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Klient został poinformowany, że: ", helvetica16));
        document.add(new Paragraph("1. Wypożyczenie towaru wiąże się z wniesieniem kaucji.", helvetica12));
        document.add(new Paragraph("2. Kaucja zostaje zwrócona klientowi podczas zwrotu towaru.", helvetica12));
        document.add(new Paragraph("3. Kaucja stanowi zabezpieczenie w przypadku uszkodzenia towaru.", helvetica12));
        document.add(new Paragraph("4. Maksymalny czas wypożyczenia przedmiotu wynosi "+ maxrentaltime +" dni", helvetica12));
        document.add(new Paragraph("5. Kara za niezwrócenie towaru w terminie wynosi "+ dailypenality +" zł za każdy dzień zwłoki (za każdy przedmiot).", helvetica12));
        document.add(new Paragraph("6. Podczas zwrotu towaru klient zobowiązany jest uiścić opłatę za wypożyczenie zgodną z cennikiem.", helvetica12));
        document.add(new Paragraph("7. Niniejszy dokument został spożądzony w dwóch identycznie brzmmiących egzemplarzach.", helvetica12));
        document.add(new Paragraph("8. W pozostałych przypadkach stosowane będą przepisy Kodeksu Cywilnego.", helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("    .........................................                                                            .........................................", helvetica12));
        document.add(new Paragraph("    (Podpis wypożyczającego)                                                                   (Podpis klienta)", helvetica12));
        document.close();
    }

    public static void generateReturnConfirmation(ComboBox<ClientTableView> clientComboBox) throws DocumentException, IOException {
        Document document = new Document();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
        Date currentDate = new Date();

        String filename = clientComboBox.getValue().getName() + "_" + dateFormat.format(currentDate) + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream("D:\\Dokumenty\\Praca Inżynierska\\pracadyplomowa\\confirmation\\return\\" + filename));
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font helvetica16 = new Font(helvetica, 12);
        Font helvetica16red = new Font(helvetica, 16);
        helvetica16red.setColor(255, 0, 0);
        Font helvetica12 = new Font(helvetica, 10);

        document.add(new Paragraph("Wypożyczalnia 1.0 - Potwierdzenie zwrotu towaru:", helvetica16red));
        document.add(new Paragraph("Dokument wygenerowany automatycznie dnia: " + dateFormat.format(currentDate), helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(hirename, helvetica12));
        document.add(new Paragraph(hireadress, helvetica12));
        document.add(new Paragraph(hirepostalcode, helvetica12));
        document.add(new Paragraph("NIP: "+hirenip, helvetica12));
        document.add(new Paragraph("Telefon: "+hirephonenumber, helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Przyjmuje zwrot od klienta:", helvetica16));
        document.add(new Paragraph(clientComboBox.getValue().getName(), helvetica12));
        document.add(new Paragraph(clientComboBox.getValue().getAddress(), helvetica12));
        document.add(new Paragraph(clientComboBox.getValue().getPostalcode(), helvetica12));
        document.add(new Paragraph("NIP: " + clientComboBox.getValue().getNip(), helvetica12));
        document.add(new Paragraph("Telefon: " + clientComboBox.getValue().getTelephonenumber(), helvetica12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Przedmiotów:", helvetica16));
        document.add(new Paragraph(" "));
        document.add(createReturnTable(clientComboBox));
        document.add(new Paragraph(" "));
        DecimalFormat dec = new DecimalFormat("####0.00 zł");
        Paragraph p = new Paragraph(dec.format(bail), helvetica16red);
        bail = 0;
        document.add(new Paragraph("Kaucja do zwrotu: ", helvetica16));
        document.add(p);
        document.add(new Paragraph(" "));
        Paragraph r = new Paragraph(dec.format(price), helvetica16red);
        price = 0;
        document.add(new Paragraph("Opłata za wypożyczenie: ", helvetica16));
        document.add(r);
        document.add(new Paragraph(" "));
        Paragraph s = new Paragraph(dec.format(penality), helvetica16red);
        penality = 0;
        document.add(new Paragraph("Kara za opóźniony zwrot: ", helvetica16));
        document.add(s);
        document.add(new Paragraph(" "));
        Paragraph t = new Paragraph(dec.format(sum), helvetica16red);
        document.add(new Paragraph("Razem do zapłaty: ", helvetica16));
        document.add(t);
        sum = 0;
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("    .........................................                                                            .........................................", helvetica12));
        document.add(new Paragraph("    (Podpis wypożyczającego)                                                                   (Podpis klienta)", helvetica12));
        document.close();
    }

    private static PdfPTable createReturnTable(ComboBox<ClientTableView> clientComboBox) {
        // a table with three columns
        float[] columnWidths = {2, 10, 2, 4, 4, 4, 4};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);

        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            ClientEntity client = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());
            List<RentalDetailsEntity> detailsList = client.getRentalDetailsList();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date currentDate = new Date();

            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font helvetica8 = new Font(helvetica, 8);
            table.addCell(new PdfPCell(new Phrase("ID:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Nazwa przedmiotu:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Ilość:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Kaucja zwrotna:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Dzienna opłata za wypożyczenie:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Data wypożyczenia:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Data zwrotu:", helvetica8)));

            for (RentalDetailsEntity item : detailsList) {
                ItemEntity ie = manager.find(ItemEntity.class, item.getItemId());
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getItemId()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(ie.getName(), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getRentalCount()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ie.getBail()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ie.getRentalprice()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(dateFormat.format(item.getRentalDate())), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(dateFormat.format(currentDate)), helvetica8)));
                //liczenie kaucji
                bail = bail + ie.getBail() * item.getRentalCount();
                //liczenie ceny wypożyczenia
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                DateFormat dateFormat1 = new SimpleDateFormat("dd MM yyyy");
                LocalDate firstDate = LocalDate.parse(dateFormat1.format(currentDate), formatter);
                LocalDate secondDate = LocalDate.parse(dateFormat1.format(item.getRentalDate()), formatter);
                long temp = ChronoUnit.DAYS.between(secondDate, firstDate) + 1;
                int days = (int) temp;
                price = price + (days * item.getRentalCount() * ie.getRentalprice());
                //liczenie kary za zwłokę
                Timestamp ts = item.getRentalDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);
                cal.add(Calendar.DAY_OF_WEEK, maxrentaltime);
                Date maxReturnDate = new Date();
                maxReturnDate.setTime(cal.getTime().getTime());
                LocalDate firstDate1 = LocalDate.parse(dateFormat1.format(currentDate), formatter);
                LocalDate secondDate1 = LocalDate.parse(dateFormat1.format(maxReturnDate), formatter);
                if (currentDate.getTime() > maxReturnDate.getTime()) {
                    long temp1 = ChronoUnit.DAYS.between(secondDate1, firstDate1);
                    int days1 = (int) temp1;
                    penality = penality + (days1 * item.getRentalCount() * dailypenality);
                }
                sum = price + penality;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return table;
    }

    private static PdfPTable createRentalTable(ComboBox<ClientTableView> clientComboBox) {
        // a table with three columns
        float[] columnWidths = {2, 10, 2, 4, 4, 4, 4};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);

        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            ClientEntity client = manager.find(ClientEntity.class, clientComboBox.getValue().getClient_id());
            List<RentalDetailsEntity> detailsList = client.getRentalDetailsList();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font helvetica8 = new Font(helvetica, 8);
            table.addCell(new PdfPCell(new Phrase("ID:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Nazwa przedmiotu:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Ilość:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Kaucja zwrotna:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Dzienna opłata za wypożyczenie:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Data wypożyczenia:", helvetica8)));
            table.addCell(new PdfPCell(new Phrase("Ostateczny termin zwrotu:", helvetica8)));

            for (RentalDetailsEntity item : detailsList) {
                ItemEntity ie = manager.find(ItemEntity.class, item.getItemId());
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getItemId()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(ie.getName(), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getRentalCount()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ie.getBail()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ie.getRentalprice()), helvetica8)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(dateFormat.format(item.getRentalDate())), helvetica8)));
                Timestamp ts = item.getRentalDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);
                cal.add(Calendar.DAY_OF_WEEK, maxrentaltime);
                Date maxReturnDate = new Date();
                maxReturnDate.setTime(cal.getTime().getTime());
                table.addCell(new PdfPCell(new Phrase(String.valueOf(dateFormat.format(maxReturnDate)), helvetica8)));
                bail = bail + ie.getBail() * item.getRentalCount();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return table;
    }
}

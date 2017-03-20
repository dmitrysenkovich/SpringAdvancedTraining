package com.epam.spring.core.web.pdf;

import com.epam.spring.core.domain.Ticket;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

public class PdfCreator {
    private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    public byte[] toPdf(Collection<Ticket> tickets) {
        return generatePdf(tickets, false);
    }

    public byte[] report(Collection<Ticket> tickets) {
        return generatePdf(tickets, true);
    }

    private byte[] generatePdf(Collection<Ticket> tickets, boolean report) {
        byte[] pdfInBytes = null;

        try {
            Document document = new Document();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            if (report) {
                addMetaData(document);
                addTitlePage(document);
            }
            createTable(document, tickets);

            document.close();

            pdfInBytes = byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return pdfInBytes;
    }

    private void addMetaData(Document document) {
        document.addTitle("Purchased tickets for event");
    }

    private void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        createEmptyLine(preface, 1);
        preface.add(new Paragraph("Purchased tickets for event report", TIME_ROMAN));
        document.add(preface);
    }

    private void createTable(Document document, Collection<Ticket> tickets) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        createEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(4);

        addCell(table, "User");
        addCell(table, "Event");
        addCell(table, "Auditorium");
        addCell(table, "Seat");

        table.setHeaderRows(1);

        for (Ticket ticket : tickets) {
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
            table.addCell(ticket.getEvent().getName());
            table.addCell(ticket.getAuditorium().getName());
            table.addCell(Long.toString(ticket.getSeat()));
        }

        document.add(table);
    }

    private void createEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addCell(PdfPTable table, String phrase) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(pdfPCell);
    }
}

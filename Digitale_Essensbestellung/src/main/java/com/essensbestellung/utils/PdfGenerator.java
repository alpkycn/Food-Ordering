package com.essensbestellung.utils;

import com.essensbestellung.dto.UserInvoiceTable;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PdfGenerator {

    
    public byte[] generateInvoicePdf(List<UserInvoiceTable> rows, Long userId, String startDate, String endDate, int anzahl) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            addCompanyHeader(document);

            
            Paragraph title = new Paragraph("Mitarbeiter-Abrechnung", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            
            document.add(new Paragraph("\n"));

            
            document.add(new Paragraph("Kunde Nr.: " + userId, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
            document.add(new Paragraph("Zeitraum: " + startDate + " bis " + endDate, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
            document.add(new Paragraph("Anzahl der Bestellungen: " + anzahl, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL)));
            
            document.add(new Paragraph("\n"));

            // Add the table
            PdfPTable table = createTable(rows);
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    
    private PdfPTable createTable(List<UserInvoiceTable> rows) {
        PdfPTable table = new PdfPTable(6); 
        table.setWidthPercentage(100);

        try {
            table.setWidths(new float[]{1, 2, 2, 3, 2, 2}); 
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        
        table.addCell(createHeaderCell("SNr"));
        table.addCell(createHeaderCell("Bestell-ID"));
        table.addCell(createHeaderCell("BestellDatum"));
        table.addCell(createHeaderCell("Leistungsart"));
        table.addCell(createHeaderCell("Lieferdatum"));
        table.addCell(createHeaderCell("Preis"));

        // Add data rows
        for (UserInvoiceTable row : rows) {
            table.addCell(createDataCell(String.valueOf(row.getSerialNumber())));
            table.addCell(createDataCell(String.valueOf(row.getOrderId())));
            table.addCell(createDataCell(row.getOrderDate().toString()));
            table.addCell(createDataCell(row.getLeistungsart()));
            table.addCell(createDataCell(row.getLieferdatum().toString()));
            table.addCell(createDataCell(row.getPreis()));
        }

        return table;
    }

    
    private PdfPCell createHeaderCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell.setBackgroundColor(new BaseColor(200, 200, 200)); // Light gray background
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    
    private PdfPCell createDataCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    private void addCompanyHeader(Document document) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3, 2}); 
    
        
        PdfPCell leftCell = new PdfPCell(new Phrase(""));
        leftCell.setBorder(Rectangle.NO_BORDER);
        
        
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    
        Paragraph companyName = new Paragraph("Sozial-Arbeiten-Wohnen Borna gGmbH", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
        companyName.setAlignment(Element.ALIGN_RIGHT);
        Paragraph address = new Paragraph("Am Wilhelmsschacht 1\n04552 Borna", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
        address.setAlignment(Element.ALIGN_RIGHT);
        Paragraph phone = new Paragraph("Telefon: 03433/209790", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
        phone.setAlignment(Element.ALIGN_RIGHT);

        Paragraph companyName1 = new Paragraph("\nVerpflegung WEbM", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
        companyName1.setAlignment(Element.ALIGN_RIGHT);

        Paragraph address1 = new Paragraph("Am Wihlemschacht 1\n04552 Borna", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
        address1.setAlignment(Element.ALIGN_RIGHT);
        Paragraph phone1 = new Paragraph("Telefon: 03433/20979103", new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL));
        phone1.setAlignment(Element.ALIGN_RIGHT);
        Paragraph sender = new Paragraph("\n" + "\n" +
                        "Sozial Arbeiten - Am Wilhelmsschacht 1 - 04552 Borna", 
                new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE));
        sender.setAlignment(Element.ALIGN_LEFT);
        
        

        rightCell.addElement(companyName);
        rightCell.addElement(address);
        rightCell.addElement(phone);
        rightCell.addElement(companyName1);
        rightCell.addElement(address1);
        rightCell.addElement(phone1);

        
        leftCell.addElement(sender);
    
        headerTable.addCell(leftCell);
        headerTable.addCell(rightCell);
    
        document.add(headerTable);
        document.add(new Paragraph("\n"));
    
        
    }
    
    
    
}
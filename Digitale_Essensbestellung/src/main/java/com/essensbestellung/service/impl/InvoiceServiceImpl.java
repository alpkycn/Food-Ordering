package com.essensbestellung.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.dto.UserInvoiceTable;
import com.essensbestellung.entities.FoodOrder;
import com.essensbestellung.enums.MealType;
import com.essensbestellung.service.InvoiceService;
import com.essensbestellung.utils.PdfGenerator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private FoodOrderServiceImpl foodOrderService;

    private int anzahl = 0;

    @Override
    public List<UserInvoiceTable> generateInvoiceTable(List<FoodOrder> foodOrders) {
        List<UserInvoiceTable> table = new ArrayList<>();
        int serialNumber = 1;

        for (FoodOrder order : foodOrders) {
            
            String leistungsart = "";

                if (order.getMealtype() == MealType.ROT) {
                     leistungsart = order.isWith_salad() ? "Mittagessen Rot (mit Salat)" : "Mittagessen Rot (ohne Salat)";
                } else if (order.getMealtype() == MealType.BLAU) {
                    leistungsart = order.isWith_salad() ? "Mittagessen Blau (mit Salat)" : "Mittagessen Blau (ohne Salat)";
                } else if (order.getMealtype() == MealType.NURSALAT) {
                    leistungsart = "Salat";
                }


            // Create a table row with default price "0,00"
            UserInvoiceTable row = new UserInvoiceTable(
                    serialNumber,
                    order.getId(),
                    order.getOrderdate(),
                    leistungsart,
                    order.getDelivery_date(),
                    "0,00"
            );
            table.add(row);
            serialNumber++;
            anzahl++;
        }

        return table;
    }

    private byte[] generateUserInvoicePdf(List<FoodOrder> foodOrders, LocalDate startDate, LocalDate endDate) {
        if (foodOrders == null || foodOrders.isEmpty()) {
            return generateEmptyPdf("Keine Bestellungen fÃ¼r den eingegebenen Mitarbeiter und den Zeitraum gefunden.");
        }

       
        Long userId = foodOrders.get(0).getUser().getId();

        
        List<UserInvoiceTable> tableRows = generateInvoiceTable(foodOrders);

        
        String startDateStr = startDate.toString();
        String endDateStr = endDate.toString();

       
        return pdfGenerator.generateInvoicePdf(tableRows, userId, startDateStr, endDateStr, anzahl);
    }

    private byte[] generateEmptyPdf(String message) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document();

    try {
        PdfWriter.getInstance(document, outputStream);
        document.open();

       
        Paragraph title = new Paragraph("Mitarbeiter-Abrechnung", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        
        document.add(new Paragraph("\n" + message, new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC)));

        document.close();
    } catch (DocumentException e) {
        e.printStackTrace();
    }

    return outputStream.toByteArray();
    }

    @Override
    public byte[] createInvoiceForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        
        List<FoodOrder> foodOrders = foodOrderService.getFoodOrdersByUserAndDateRange(userId, startDate, endDate);

        
        return generateUserInvoicePdf(foodOrders, startDate, endDate);
    }




}
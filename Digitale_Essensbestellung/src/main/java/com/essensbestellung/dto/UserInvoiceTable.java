package com.essensbestellung.dto;

import java.time.LocalDate;

public class UserInvoiceTable{
    private int serialNumber;
    private Long orderId;
    private String leistungsart;
    private LocalDate lieferdatum;
    private String preis;
    private LocalDate orderdate;

    // Constructor
    public UserInvoiceTable(int serialNumber, Long orderId, LocalDate orderdate, String leistungsart, LocalDate lieferdatum, String preis) {
        this.serialNumber = serialNumber;
        this.orderId = orderId;
        this.orderdate = orderdate;
        this.leistungsart = leistungsart;
        this.lieferdatum = lieferdatum;
        this.preis = preis;
    }

    // Getters and setters
    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLeistungsart() {
        return leistungsart;
    }

    public void setLeistungsart(String leistungsart) {
        this.leistungsart = leistungsart;
    }

    public LocalDate getLieferdatum() {
        return lieferdatum;
    }

    public void setLieferdatum(LocalDate lieferdatum) {
        this.lieferdatum = lieferdatum;
    }

    public LocalDate getOrderDate() {
        return orderdate;
    }

    public void setOrderDate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public String getPreis() {
        return preis;
    }

    public void setPreis(String preis) {
        this.preis = preis;
    }
}

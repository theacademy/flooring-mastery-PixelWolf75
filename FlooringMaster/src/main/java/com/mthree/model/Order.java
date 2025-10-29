package com.mthree.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;

public class Order {

    int orderNumber;
    String customerName;
    String state;
    LocalDate orderDate;
    BigDecimal taxRate;
    String productType;
    BigDecimal costPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;
    BigDecimal materialCost;
    BigDecimal area;
    BigDecimal laborCost;
    BigDecimal tax;
    BigDecimal total;

    public Order(int orderNumber, String customerName, String state, LocalDate orderDate, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot)
    {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.orderDate = orderDate;
        this.taxRate = taxRate;
        this.area = area;
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.setLaborCost();
        this.setMaterialCost();
        this.setTax();
        this.setTotal();
    }

    public String toString()
    {
        return orderNumber + "," + customerName + "," + state + "," + taxRate.toString() + "," + productType + "," + area.toString() + "," + costPerSquareFoot.toString() + "," + laborCostPerSquareFoot.toString() + "," + materialCost.toString() + "," + laborCost.toString() + "," + tax.toString() + "," + total.toString();
    }

    // Getters and Setters
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        this.setTax(); // Recalculate tax when tax rate changes
        this.setTotal(); // Recalculate total when tax changes
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
        this.setMaterialCost(); // Recalculate material cost when cost per square foot changes
        this.setTax(); // Recalculate tax when material cost changes
        this.setTotal(); // Recalculate total when tax changes
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.setLaborCost(); // Recalculate labor cost when labor cost per square foot changes
        this.setTax(); // Recalculate tax when labor cost changes
        this.setTotal(); // Recalculate total when tax changes
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
        this.setLaborCost(); // Recalculate labor cost when area changes
        this.setMaterialCost(); // Recalculate material cost when area changes
        this.setTax(); // Recalculate tax when costs change
        this.setTotal(); // Recalculate total when tax changes
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    // Private setters for derived values, not exposed to the user.
    private void setLaborCost() {
        this.laborCost = this.area.multiply(this.laborCostPerSquareFoot);
    }

    private void setMaterialCost() {
        this.materialCost = this.area.multiply(this.costPerSquareFoot);
    }

    private void setTax() {
        this.tax = (this.materialCost.add(this.laborCost)).multiply(this.taxRate.divide(new BigDecimal(100), RoundingMode.CEILING));
    }

    private void setTotal() {
        this.total = this.materialCost.add(this.laborCost.add(this.tax));
    }
}

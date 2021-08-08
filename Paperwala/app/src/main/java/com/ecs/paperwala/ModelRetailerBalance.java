package com.ecs.paperwala;

public class ModelRetailerBalance
{
    private String DistributorSaleId;
    private String SaleOrder;
    private String RetailerId;
    private String RetailerName;
    private String TransDate;
    private double PaidAmount;
    private double BalanceAmount;
    private double PrvBalanceAmount;
    private double FinalBalaceAmount;
    private String CreatedDate;
    private String PaperName;
    private double PaperRate;
   private double TotalPrice;
    private double PaperQuantity;
    private double PamphletQuantity;
    private double PamphletRate;
    private double TotalPamphletAmount;
    private double TotalFinalAmount;

    @Override
    public String toString() {
        return "ModelRetailerBalance{" +
                "DistributorSaleId='" + DistributorSaleId + '\'' +
                ", SaleOrder='" + SaleOrder + '\'' +
                ", RetailerId='" + RetailerId + '\'' +
                ", RetailerName='" + RetailerName + '\'' +
                ", TransDate='" + TransDate + '\'' +
                ", PaidAmount=" + PaidAmount +
                ", BalanceAmount=" + BalanceAmount +
                ", PrvBalanceAmount=" + PrvBalanceAmount +
                ", FinalBalaceAmount=" + FinalBalaceAmount +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", PaperName='" + PaperName + '\'' +
                ", PaperRate=" + PaperRate +
                ", TotalPrice=" + TotalPrice +
                ", PaperQuantity=" + PaperQuantity +
                ", PamphletQuantity=" + PamphletQuantity +
                ", PamphletRate=" + PamphletRate +
                ", TotalPamphletAmount=" + TotalPamphletAmount +
                ", TotalFinalAmount=" + TotalFinalAmount +
                '}';
    }

    public String getDistributorSaleId() {
        return DistributorSaleId;
    }

    public void setDistributorSaleId(String distributorSaleId) {
        DistributorSaleId = distributorSaleId;
    }

    public String getSaleOrder() {
        return SaleOrder;
    }

    public void setSaleOrder(String saleOrder) {
        SaleOrder = saleOrder;
    }

    public String getRetailerId() {
        return RetailerId;
    }

    public void setRetailerId(String retailerId) {
        RetailerId = retailerId;
    }

    public String getRetailerName() {
        return RetailerName;
    }

    public void setRetailerName(String retailerName) {
        RetailerName = retailerName;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public double getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        PaidAmount = paidAmount;
    }

    public double getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        BalanceAmount = balanceAmount;
    }

    public double getPrvBalanceAmount() {
        return PrvBalanceAmount;
    }

    public void setPrvBalanceAmount(double prvBalanceAmount) {
        PrvBalanceAmount = prvBalanceAmount;
    }

    public double getFinalBalaceAmount() {
        return FinalBalaceAmount;
    }

    public void setFinalBalaceAmount(double finalBalaceAmount) {
        FinalBalaceAmount = finalBalaceAmount;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getPaperName() {
        return PaperName;
    }

    public void setPaperName(String paperName) {
        PaperName = paperName;
    }

    public double getPaperRate() {
        return PaperRate;
    }

    public void setPaperRate(double paperRate) {
        PaperRate = paperRate;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getPaperQuantity() {
        return PaperQuantity;
    }

    public void setPaperQuantity(double paperQuantity) {
        PaperQuantity = paperQuantity;
    }

    public double getPamphletQuantity() {
        return PamphletQuantity;
    }

    public void setPamphletQuantity(double pamphletQuantity) {
        PamphletQuantity = pamphletQuantity;
    }

    public double getPamphletRate() {
        return PamphletRate;
    }

    public void setPamphletRate(double pamphletRate) {
        PamphletRate = pamphletRate;
    }

    public double getTotalPamphletAmount() {
        return TotalPamphletAmount;
    }

    public void setTotalPamphletAmount(double totalPamphletAmount) {
        TotalPamphletAmount = totalPamphletAmount;
    }

    public double getTotalFinalAmount() {
        return TotalFinalAmount;
    }

    public void setTotalFinalAmount(double totalFinalAmount) {
        TotalFinalAmount = totalFinalAmount;
    }

    public ModelRetailerBalance(String distributorSaleId, String saleOrder, String retailerId, String retailerName, String transDate, double paidAmount, double balanceAmount, double prvBalanceAmount, double finalBalaceAmount, String createdDate, String paperName, double paperRate, double totalPrice, double paperQuantity, double pamphletQuantity, double pamphletRate, double totalPamphletAmount, double totalFinalAmount) {
        DistributorSaleId = distributorSaleId;
        SaleOrder = saleOrder;
        RetailerId = retailerId;
        RetailerName = retailerName;
        TransDate = transDate;
        PaidAmount = paidAmount;
        BalanceAmount = balanceAmount;
        PrvBalanceAmount = prvBalanceAmount;
        FinalBalaceAmount = finalBalaceAmount;
        CreatedDate = createdDate;
        PaperName = paperName;
        PaperRate = paperRate;
        TotalPrice = totalPrice;
        PaperQuantity = paperQuantity;
        PamphletQuantity = pamphletQuantity;
        PamphletRate = pamphletRate;
        TotalPamphletAmount = totalPamphletAmount;
        TotalFinalAmount = totalFinalAmount;
    }

    public ModelRetailerBalance() {
    }
}

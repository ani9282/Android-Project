package com.ecs.paperwala;

public class ModelSaleOrder
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

    @Override
    public String toString() {
        return "ModelSaleOrder{" +
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

    public ModelSaleOrder(String distributorSaleId, String saleOrder, String retailerId, String retailerName, String transDate, double paidAmount, double balanceAmount, double prvBalanceAmount, double finalBalaceAmount, String createdDate) {
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
    }

    public ModelSaleOrder() {
    }
}

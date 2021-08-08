package com.ecs.paperwala;

public class ModelClass {
    private String RetailerId;
    private String RetailerName;
    private String RetailerAddress;
    private String MobileNo;
    private Double RemainingAMT;
    private String city_id;
    private String distribute_id;

    @Override
    public String toString() {
        return "ModelClass{" +
                "RetailerId='" + RetailerId + '\'' +
                ", RetailerName='" + RetailerName + '\'' +
                ", RetailerAddress='" + RetailerAddress + '\'' +
                ", MobileNo='" + MobileNo + '\'' +
                ", RemainingAMT=" + RemainingAMT +
                ", city_id='" + city_id + '\'' +
                ", distribute_id='" + distribute_id + '\'' +
                '}';
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

    public String getRetailerAddress() {
        return RetailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        RetailerAddress = retailerAddress;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public Double getRemainingAMT() {
        return RemainingAMT;
    }

    public void setRemainingAMT(Double remainingAMT) {
        RemainingAMT = remainingAMT;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistribute_id() {
        return distribute_id;
    }

    public void setDistribute_id(String distribute_id) {
        this.distribute_id = distribute_id;
    }

    public ModelClass(String retailerId, String retailerName, String retailerAddress, String mobileNo, Double remainingAMT, String city_id, String distribute_id) {
        RetailerId = retailerId;
        RetailerName = retailerName;
        RetailerAddress = retailerAddress;
        MobileNo = mobileNo;
        RemainingAMT = remainingAMT;
        this.city_id = city_id;
        this.distribute_id = distribute_id;
    }

    public ModelClass() {
    }
}

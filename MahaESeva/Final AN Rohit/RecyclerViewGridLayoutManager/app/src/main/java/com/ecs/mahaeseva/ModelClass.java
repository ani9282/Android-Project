package com.ecs.mahaeseva;

public class ModelClass
{
     int id;
    private String cname;
    private String certificate_type;
    private String address;
    private String mobile;
    private String price;
    private String payment_status;

    public ModelClass() {

    }

    public ModelClass(int id, String cname, String certificate_type, String address, String mobile, String price, String payment_status) {
        this.id = id;
        this.cname = cname;
        this.certificate_type = certificate_type;
        this.address = address;
        this.mobile = mobile;
        this.price = price;
        this.payment_status = payment_status;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", certificate_type='" + certificate_type + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", price='" + price + '\'' +
                ", payment_status='" + payment_status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}

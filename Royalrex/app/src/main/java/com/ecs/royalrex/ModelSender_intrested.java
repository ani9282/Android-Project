package com.ecs.royalrex;

public class ModelSender_intrested
{
    private int id;
    private String driver_name;
    private String driver_mobile;
    private String vehical_name;
    private String vehical_number;
    private String price;

    @Override
    public String toString() {
        return "ModelSender_intrested{" +
                "id=" + id +
                ", driver_name='" + driver_name + '\'' +
                ", driver_mobile='" + driver_mobile + '\'' +
                ", vehical_name='" + vehical_name + '\'' +
                ", vehical_number='" + vehical_number + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_mobile() {
        return driver_mobile;
    }

    public void setDriver_mobile(String driver_mobile) {
        this.driver_mobile = driver_mobile;
    }

    public String getVehical_name() {
        return vehical_name;
    }

    public void setVehical_name(String vehical_name) {
        this.vehical_name = vehical_name;
    }

    public String getVehical_number() {
        return vehical_number;
    }

    public void setVehical_number(String vehical_number) {
        this.vehical_number = vehical_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ModelSender_intrested(int id, String driver_name, String driver_mobile, String vehical_name, String vehical_number, String price) {
        this.id = id;
        this.driver_name = driver_name;
        this.driver_mobile = driver_mobile;
        this.vehical_name = vehical_name;
        this.vehical_number = vehical_number;
        this.price = price;
    }

    public ModelSender_intrested() {
    }
}

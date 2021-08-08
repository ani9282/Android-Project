package com.ecs.royalrex;

public class ModelDriver
{
        private int id;
        private String vehical_name;
        private String vehical_number;
        private String owner_mobile;
        private String min_capacity;
        private String max_capacity;
        private String length;
        private String  width;
        private String height;
        private String driver_name;
        private String driver_mobile;
        private String pickup_location;
        private String date;
        private String time;

    @Override
    public String toString() {
        return "ModelDriver{" +
                "id=" + id +
                ", vehical_name='" + vehical_name + '\'' +
                ", vehical_number='" + vehical_number + '\'' +
                ", owner_mobile='" + owner_mobile + '\'' +
                ", min_capacity='" + min_capacity + '\'' +
                ", max_capacity='" + max_capacity + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_mobile='" + driver_mobile + '\'' +
                ", pickup_location='" + pickup_location + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOwner_mobile() {
        return owner_mobile;
    }

    public void setOwner_mobile(String owner_mobile) {
        this.owner_mobile = owner_mobile;
    }

    public String getMin_capacity() {
        return min_capacity;
    }

    public void setMin_capacity(String min_capacity) {
        this.min_capacity = min_capacity;
    }

    public String getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(String max_capacity) {
        this.max_capacity = max_capacity;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ModelDriver(int id, String vehical_name, String vehical_number, String owner_mobile, String min_capacity, String max_capacity, String length, String width, String height, String driver_name, String driver_mobile, String pickup_location, String date, String time) {
        this.id = id;
        this.vehical_name = vehical_name;
        this.vehical_number = vehical_number;
        this.owner_mobile = owner_mobile;
        this.min_capacity = min_capacity;
        this.max_capacity = max_capacity;
        this.length = length;
        this.width = width;
        this.height = height;
        this.driver_name = driver_name;
        this.driver_mobile = driver_mobile;
        this.pickup_location = pickup_location;
        this.date = date;
        this.time = time;
    }

    public ModelDriver() {
    }
}

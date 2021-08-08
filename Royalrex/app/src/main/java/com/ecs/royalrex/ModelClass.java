package com.ecs.royalrex;

public class ModelClass {
    private int id;
    private String name;
   private String first_name;
   private String last_name;
   private String mobile;
   private  String role;
   private String date;
   private String time;
   private String vehical;
   private String source;
   private String destination;
   private String weight;
   private String status;
   private String driver_name;
   private String driver_mobile;
   private String location;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", role='" + role + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", vehical='" + vehical + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", weight='" + weight + '\'' +
                ", status='" + status + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_mobile='" + driver_mobile + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getVehical() {
        return vehical;
    }

    public void setVehical(String vehical) {
        this.vehical = vehical;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ModelClass(int id, String name, String first_name, String last_name, String mobile, String role, String date, String time, String vehical, String source, String destination, String weight, String status, String driver_name, String driver_mobile, String location) {
        this.id = id;
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile = mobile;
        this.role = role;
        this.date = date;
        this.time = time;
        this.vehical = vehical;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.driver_name = driver_name;
        this.driver_mobile = driver_mobile;
        this.location = location;
    }

    public ModelClass() {
    }
}

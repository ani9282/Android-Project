package com.ecs.newtemp;

public class ModelData {
    private int id;
    private String temp;
    private String humidity;

    public ModelData(int id, String temp, String humidity) {
        this.id = id;
        this.temp = temp;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }
}

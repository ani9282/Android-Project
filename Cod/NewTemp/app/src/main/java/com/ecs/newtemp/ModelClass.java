package com.ecs.newtemp;

public class ModelClass
{
    private int id;
    private String name;
    private String qty;
    private String type;

    public ModelClass() {
    }

    public ModelClass(int id, String name, String qty, String type) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qty='" + qty + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public int getId() {
        return id;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

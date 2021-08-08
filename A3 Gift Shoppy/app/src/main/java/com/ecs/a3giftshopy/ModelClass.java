package com.ecs.a3giftshopy;

public class ModelClass
{
    private int id;
    private String name;
    private String photo;
    private String price;
    private String description;

    @Override
    public String toString() {
        return "ModelClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelClass(int id, String name, String photo, String price, String description) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.description = description;
    }

    public ModelClass() {
    }

}

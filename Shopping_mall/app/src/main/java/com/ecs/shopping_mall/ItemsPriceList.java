
package com.ecs.shopping_mall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsPriceList {

    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("updatedAt")
    @Expose
    private long updatedAt;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("itemsPriceId")
    @Expose
    private String itemsPriceId;
    @SerializedName("itemsQuantity")
    @Expose
    private int itemsQuantity;
    @SerializedName("itemsUnit")
    @Expose
    private String itemsUnit;
    @SerializedName("itemsPrice")
    @Expose
    private double itemsPrice;
    @SerializedName("itemsId")
    @Expose
    private String itemsId;
    @SerializedName("userItemCount")
    @Expose
    private int userItemCount;
    @SerializedName("discountAmount")
    @Expose
    private double discountAmount;
    @SerializedName("discountPercentage")
    @Expose
    private double discountPercentage;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getItemsPriceId() {
        return itemsPriceId;
    }

    public void setItemsPriceId(String itemsPriceId) {
        this.itemsPriceId = itemsPriceId;
    }

    public int getItemsQuantity() {
        return itemsQuantity;
    }

    public void setItemsQuantity(int itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }

    public String getItemsUnit() {
        return itemsUnit;
    }

    public void setItemsUnit(String itemsUnit) {
        this.itemsUnit = itemsUnit;
    }

    public double getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(double itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public String getItemsId() {
        return itemsId;
    }

    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }

    public int getUserItemCount() {
        return userItemCount;
    }

    public void setUserItemCount(int userItemCount) {
        this.userItemCount = userItemCount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}


package com.ecs.shopping_mall;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelClass {

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
    @SerializedName("itemsId")
    @Expose
    private String itemsId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("itemsName")
    @Expose
    private String itemsName;
    @SerializedName("itemsSubType")
    @Expose
    private String itemsSubType;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("storagesUses")
    @Expose
    private String storagesUses;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("benefits")
    @Expose
    private String benefits;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("itemsPriceList")
    @Expose
    private List<ItemsPriceList> itemsPriceList = null;
    @SerializedName("itemsImagesList")
    @Expose
    private List<ItemsImagesList> itemsImagesList = null;
    @SerializedName("pincodeList")
    @Expose
    private Object pincodeList;

    @Override
    public String toString() {
        return "ModelClass{" +
                "active=" + active +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deleted=" + deleted +
                ", itemsId='" + itemsId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", itemsName='" + itemsName + '\'' +
                ", itemsSubType='" + itemsSubType + '\'' +
                ", about='" + about + '\'' +
                ", storagesUses='" + storagesUses + '\'' +
                ", availability='" + availability + '\'' +
                ", benefits='" + benefits + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", itemsPriceList=" + itemsPriceList +
                ", itemsImagesList=" + itemsImagesList +
                ", pincodeList=" + pincodeList +
                '}';
    }

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

    public String getItemsId() {
        return itemsId;
    }

    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getItemsSubType() {
        return itemsSubType;
    }

    public void setItemsSubType(String itemsSubType) {
        this.itemsSubType = itemsSubType;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStoragesUses() {
        return storagesUses;
    }

    public void setStoragesUses(String storagesUses) {
        this.storagesUses = storagesUses;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ItemsPriceList> getItemsPriceList() {
        return itemsPriceList;
    }

    public void setItemsPriceList(List<ItemsPriceList> itemsPriceList) {
        this.itemsPriceList = itemsPriceList;
    }

    public List<ItemsImagesList> getItemsImagesList() {
        return itemsImagesList;
    }

    public void setItemsImagesList(List<ItemsImagesList> itemsImagesList) {
        this.itemsImagesList = itemsImagesList;
    }

    public Object getPincodeList() {
        return pincodeList;
    }

    public void setPincodeList(Object pincodeList) {
        this.pincodeList = pincodeList;
    }
}

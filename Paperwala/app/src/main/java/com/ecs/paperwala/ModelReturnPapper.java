package com.ecs.paperwala;

public class ModelReturnPapper
{
    private String paper_name;
    private String paper_rate;
    private String pamphlete_rate;
    private String paper_qty;
    private String pamphlete_qty;
    private String paper_total;
    private String pamphlete_total;
    private String total_final_price;

    @Override
    public String toString() {
        return "ModelReturnPapper{" +
                "paper_name='" + paper_name + '\'' +
                ", paper_rate='" + paper_rate + '\'' +
                ", pamphlete_rate='" + pamphlete_rate + '\'' +
                ", paper_qty='" + paper_qty + '\'' +
                ", pamphlete_qty='" + pamphlete_qty + '\'' +
                ", paper_total='" + paper_total + '\'' +
                ", pamphlete_total='" + pamphlete_total + '\'' +
                ", total_final_price='" + total_final_price + '\'' +
                '}';
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getPaper_rate() {
        return paper_rate;
    }

    public void setPaper_rate(String paper_rate) {
        this.paper_rate = paper_rate;
    }

    public String getPamphlete_rate() {
        return pamphlete_rate;
    }

    public void setPamphlete_rate(String pamphlete_rate) {
        this.pamphlete_rate = pamphlete_rate;
    }

    public String getPaper_qty() {
        return paper_qty;
    }

    public void setPaper_qty(String paper_qty) {
        this.paper_qty = paper_qty;
    }

    public String getPamphlete_qty() {
        return pamphlete_qty;
    }

    public void setPamphlete_qty(String pamphlete_qty) {
        this.pamphlete_qty = pamphlete_qty;
    }

    public String getPaper_total() {
        return paper_total;
    }

    public void setPaper_total(String paper_total) {
        this.paper_total = paper_total;
    }

    public String getPamphlete_total() {
        return pamphlete_total;
    }

    public void setPamphlete_total(String pamphlete_total) {
        this.pamphlete_total = pamphlete_total;
    }

    public String getTotal_final_price() {
        return total_final_price;
    }

    public void setTotal_final_price(String total_final_price) {
        this.total_final_price = total_final_price;
    }

    public ModelReturnPapper(String paper_name, String paper_rate, String pamphlete_rate, String paper_qty, String pamphlete_qty, String paper_total, String pamphlete_total, String total_final_price) {
        this.paper_name = paper_name;
        this.paper_rate = paper_rate;
        this.pamphlete_rate = pamphlete_rate;
        this.paper_qty = paper_qty;
        this.pamphlete_qty = pamphlete_qty;
        this.paper_total = paper_total;
        this.pamphlete_total = pamphlete_total;
        this.total_final_price = total_final_price;
    }

    public ModelReturnPapper() {
    }
}

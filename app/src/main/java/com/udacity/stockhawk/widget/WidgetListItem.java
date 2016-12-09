package com.udacity.stockhawk.widget;

/**
 * Created by ahmed on 12/8/2016.
 */

public class WidgetListItem {

    private String name;
    private String history;
    private double price;
    private double change;

    public WidgetListItem(String name, double price, double change, String history) {
        this.name = name;
        this.price = price;
        this.change = change;
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}

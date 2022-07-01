package models;

public class Order {
    private int id;
    private String date;
    private int amountOfSales;
    private int price;

    public Order(){}

    public Order(int id, String date, int amountOfSales, int price) {
        this.id = id;
        this.date = date;
        this.amountOfSales = amountOfSales;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmountOfSales() {
        return amountOfSales;
    }

    public void setAmountOfSales(int amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] toStringMassive(){
        String[] result = new String[4];
        result[0] = id + "";
        result[1] = date;
        result[2] = amountOfSales + "";
        result[3] = price + "";
        return result;
    }
}

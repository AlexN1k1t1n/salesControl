package models;

public class Product {

    private int id;
    private String name;
    private int amount;
    private int price;
    private int summary;

    Product(){}

    public Product(int id, String name, int amount, int price, int summary) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.summary = summary;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSummary() {
        return summary;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }

    public String[] toStringMassive(){
        String[] result = new String[5];
        result[0] = id+"";
        result[1] = name;
        result[2] = amount+"";
        result[3] = price+"";
        result[4] = summary+"";
        return result;
    }

}

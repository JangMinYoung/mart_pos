package com.example.minyoung.test_pos;

public class SingerItem {
    String product_name;
    String number;
    int unit_price;
    int quantity;
    int price;
    int price_history;
    String date;
    String method;


    public SingerItem(String number, String product_name, int unit_price, int quantity, int price){
        this.number = number;
        this.product_name = product_name;
        this.unit_price = unit_price;
        this.quantity=quantity;
        this.price=price;
    }
    public SingerItem(String date, String method, int price_history){
        this.date = date;
        this.method = method;
        this.price_history = price_history;
    }

    public String getName(){return product_name;}
    public void setName(String product_name){this.product_name = product_name;}
    public String getNumber(){return number;}
    public void setNumber(String number){this.number = number;}
    public int getUnit_price(){return unit_price;}
    public void setUnit_price(int unit_price){this.unit_price = unit_price;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity = quantity;}
    public int getPrice(){return price;}
    public void setPrice(int price){this.price = price;}

    public int getPrice_history(){return price_history;}
    public void setPrice_history(int price_history){this.price_history = price_history;}
    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}
    public String getMethod(){return method;}
    public void setMethod(String method){this.method = method;}

    public String getName2(){return product_name;}
    public void setName2(String product_name){this.product_name = product_name;}
    public String getNumber2(){return number;}
    public void setNumber2(String number){this.number = number;}
    public int getUnit_price2(){return unit_price;}
    public void setUnit_price2(int unit_price){this.unit_price = unit_price;}
    public int getQuantity2(){return quantity;}
    public void setQuantity2(int quantity){this.quantity = quantity;}
    public int getPrice2(){return price;}
    public void setPrice2(int price){this.price = price;}


}

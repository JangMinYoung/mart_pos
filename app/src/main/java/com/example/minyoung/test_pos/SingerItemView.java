package com.example.minyoung.test_pos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {
    TextView product_name_text;
    TextView unit_price_text;
    TextView price_text;
    TextView number_text;
    TextView quantity_text;

    public SingerItemView(Context context) {
        super(context);
        init(context);
    }

    public SingerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item, this, true);

        number_text = (TextView) findViewById(R.id.number);
        quantity_text = (TextView) findViewById(R.id.quantity);
        price_text= (TextView) findViewById(R.id.price);
        unit_price_text = (TextView) findViewById(R.id.unit_price);
        product_name_text = (TextView) findViewById(R.id.product_name);

    }

    public void setName(String product_name) { product_name_text.setText(product_name); }
    public void setNumber(String number) { number_text.setText(number); }
    public void setPrice(int price) { price_text.setText(String.valueOf(price)); }
    public void setQuantity(int quantity) { quantity_text.setText(String.valueOf(quantity)); }
    public void setUnit_price(int unit_price) { unit_price_text.setText(String.valueOf(unit_price)); }


}
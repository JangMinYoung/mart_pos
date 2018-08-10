package com.example.minyoung.test_pos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by minyoung on 2017-12-10.
 */

public class SingerItemReceipt extends LinearLayout {

    TextView product_name_text2;
    TextView unit_price_text2;
    TextView price_text2;
    TextView number_text2;
    TextView quantity_text2;

    public SingerItemReceipt(Context context) {
        super(context);
        init(context);
    }

    public SingerItemReceipt(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.receipt_item, this, true);

        number_text2 = (TextView) findViewById(R.id.receipt_bacode);
        quantity_text2 = (TextView) findViewById(R.id.receipt_number);
        price_text2= (TextView) findViewById(R.id.receipt_total);
        unit_price_text2 = (TextView) findViewById(R.id.receipt_unit);
        product_name_text2 = (TextView) findViewById(R.id.receipt_name);

    }
    public void setName2(String product_name) { product_name_text2.setText(product_name); }
    public void setNumber2(String number) { number_text2.setText(number); }
    public void setPrice2(int price) { price_text2.setText(String.valueOf(price)); }
    public void setQuantity2(int quantity) { quantity_text2.setText(String.valueOf(quantity)); }
    public void setUnit_price2(int unit_price) { unit_price_text2.setText(String.valueOf(unit_price)); }
}

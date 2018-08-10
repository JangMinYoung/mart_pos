package com.example.minyoung.test_pos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView_history extends LinearLayout {
    TextView method_text;
    TextView date_text;
    TextView price_text_history;


    public SingerItemView_history(Context context) {
        super(context);
        init(context);
    }

    public SingerItemView_history(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item2, this, true);

        price_text_history = (TextView) findViewById(R.id.price);
        method_text = (TextView) findViewById(R.id.method);
        date_text= (TextView) findViewById(R.id.date);

    }

    public void setPrice_history(int price) { price_text_history.setText(String.valueOf(price)); }
    public void setMethod(String method) { method_text.setText(method); }
    public void setDate(String date) { date_text.setText(date); }





}
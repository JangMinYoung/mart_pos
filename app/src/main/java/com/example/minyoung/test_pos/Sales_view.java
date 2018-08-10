package com.example.minyoung.test_pos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Sales_view extends AppCompatActivity {
    TextView select;
    TextView total;
    int money_value;
    int[] money=new int[3];

//    public class node{
//        String str;
//        int str_money;
//        public node(String str, int str_money){
//            this.str=str;
//            this.str_money=str_money;
//        }
//        public void add(String str, int str_money){
//            money
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_view);
        select=(TextView)findViewById(R.id.select);
        total=(TextView)findViewById(R.id.total);

        DatabaseReference database_init = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mConditionRef=database_init.child("SalesView");
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while(child.hasNext()) {
                    String temp=child.next().getKey();
                    if (temp.equals("Day")) {
                        money[0] = dataSnapshot.child(temp).getValue(Integer.class);
                    }
                    else if(temp.equals("Week")){
                        money[1]=dataSnapshot.child(temp).getValue(Integer.class);
                    }
                    else{
                        money[2]=dataSnapshot.child(temp).getValue(Integer.class);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sales_view, menu);
        return true;
    }
    public int view(String str){
        if(str.equals("Day")){
            money_value=money[0];
            total.setText(Integer.toString(money_value));
        }
        else if(str.equals("Week")){
            money_value=money[1];
            total.setText(Integer.toString(money_value));
        }
        else{
            money_value=money[2];
            total.setText(Integer.toString(money_value));
        }
        return money_value;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.day:
                select.setText("오늘");
                view("Day");
                return true;
            case R.id.week:
                select.setText("이번 주");
                view("Week");
                return true;
            case R.id.month:
                select.setText("이번 달");
                view("Month");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}

package com.example.minyoung.test_pos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;

public class receipt extends AppCompatActivity {
    int receivedMoney=0,total=0,usePoint=0,willReceive=0;
    String receiptMethod=null;
    int quantity,unit_price,unit_sum;
    String product_name,bacode;
//    Button ok;
    ListView listView;
    TextView receipt_date,receipt_received,receipt_total,receipt_point,receipt_mothod,receipt_will;
    String date;

    SingerAdapter adapter = new SingerAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
//        ok = (Button) findViewById(R.id.ok);
        receipt_date = (TextView) findViewById(R.id.receipt_date);
        receipt_mothod = (TextView) findViewById(R.id.receipt_method);
        receipt_received = (TextView) findViewById(R.id.receipt_received);
        receipt_total = (TextView) findViewById(R.id.receipt_price);
        receipt_point = (TextView) findViewById(R.id.receipt_sale);
        receipt_will = (TextView) findViewById(R.id.receipt_willreceive);

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        listView = (ListView) findViewById(R.id.listview3);

//        ok.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent_move=new Intent(getApplicationContext(),Sales_Detail.class);
//                startActivity(intent_move);
//            }
//        });

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mConditionRef = database.child("SaleHistory").child(date).child("Product");

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.allRemove();
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while (child.hasNext()) {
                    bacode = child.next().getKey();
                    product_name = dataSnapshot.child(bacode).child("Name").getValue(String.class);
                    unit_price = dataSnapshot.child(bacode).child("Price").getValue(Integer.class);
                    quantity = dataSnapshot.child(bacode).child("PaymentQuantity").getValue(Integer.class);
                    unit_sum = unit_price * quantity;
                    add(bacode, product_name, unit_price, quantity, unit_sum);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //영수증 출력에 필요한 변수들 저장
        final DatabaseReference database_receipt = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mConditionRef_receipt = database_receipt.child("SaleHistory").child(date);

        mConditionRef_receipt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                receivedMoney=0,total=0,usePoint=0,willReceive=0
                //    TextView receipt_received,receipt_total,receipt_point,receipt_mothod,receipt_will;
                receivedMoney = dataSnapshot.child("ReceivedMoney").getValue(Integer.class);
                total = dataSnapshot.child("TotalCost").getValue(Integer.class);
                usePoint = dataSnapshot.child("Point").getValue(Integer.class);
                receiptMethod = dataSnapshot.child("PaymentWay").getValue(String.class);

                receipt_date.setText("판매일자 : " + date);
                willReceive = total - usePoint;
                receipt_mothod.setText("[" + receiptMethod + "]                                                 " + String.valueOf(willReceive));
                receipt_point.setText("할인 금액                                                " + String.valueOf(usePoint));
                receipt_received.setText("받은 돈                                                 " + String.valueOf(receivedMoney));
                receipt_total.setText("총 판매액                                             " + String.valueOf(total));
                receipt_will.setText("받을 금액                                             " + String.valueOf(willReceive));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });

    }
    public void add(String number, String product_name,int unit_price,int quantity,int price) {
        listView = (ListView) findViewById(R.id.listview3);
        adapter.addItem(adapter.getCount(),new SingerItem(number, product_name, unit_price,quantity,price));
        listView.setAdapter(adapter);
        return;
    }
    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();
        @Override
        public int getCount() {
            return items.size();
        }
        public void allRemove(){
            items.removeAll(items);
        }
        public void addItem(int index,SingerItem item) {items.add(index,item);}
        public void removeItem(int position){
            items.remove(position);
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemReceipt view = new SingerItemReceipt(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName2(item.getName2());
            view.setNumber2(item.getNumber2());
            view.setPrice2(item.getPrice2());
            view.setUnit_price2(item.getUnit_price2());
            view.setQuantity2(item.getQuantity2());
            return view;
        }
    }
    public void move(View view){
       finish();
    }

}

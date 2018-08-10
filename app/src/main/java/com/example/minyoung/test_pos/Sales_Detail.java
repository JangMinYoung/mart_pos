package com.example.minyoung.test_pos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Sales_Detail extends AppCompatActivity {
    Button refund,receipt;
    ListView listView;
    LinearLayout receipt_parent,receipt_product;
    SingerAdapter adapter = new SingerAdapter();
    int quantity,unit_price,select_index,unit_sum;
    String date,product_name,bacode,remove_item;
    int remove_money;
    int refund_day,refund_week,refund_month;
    int[] money=new int[3];
    boolean selected_exist;


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
        public String SelectBacode(int position){return  items.get(position).number;}
//        public String product_name(int position){return items.get(position).product_name;}
//        public int product_price(int position){return items.get(position).unit_price;}
//        public int product_name(int position){return items.get(position).product_name;}

        public int SelectMoney(int position){return items.get(position).price;}

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setNumber(item.getNumber());
            view.setPrice(item.getPrice());
            view.setUnit_price(item.getUnit_price());
            view.setQuantity(item.getQuantity());
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        Intent intent=getIntent();

        date=intent.getExtras().getString("Date");
//        receipt_parent=(LinearLayout)findViewById(R.id.receipt_parent);
//        receipt_product=(LinearLayout)findViewById(R.id.receipt_product);

        listView = (ListView) findViewById(R.id.listView);
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference mConditionRef=database.child("SaleHistory").child(date).child("Product");
        final DatabaseReference mConditionRef2=database.child("SalesView");

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter.allRemove();
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while(child.hasNext()) {
                    bacode=child.next().getKey();
                    product_name = dataSnapshot.child(bacode).child("Name").getValue(String.class);
                    unit_price=dataSnapshot.child(bacode).child("Price").getValue(Integer.class);
                    quantity=dataSnapshot.child(bacode).child("PaymentQuantity").getValue(Integer.class);
                    unit_sum=unit_price*quantity;
                    add(bacode,product_name,unit_price,quantity,unit_sum);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mConditionRef2.addValueEventListener(new ValueEventListener() {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                select_index=position;
                selected_exist=true;
            }
        });

        refund=(Button)findViewById(R.id.refund);
        refund.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (selected_exist == true) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setTitle("환불");
                    alertDialogBuilder.setMessage("해당 물품을 환불 하시겠습니까?").setCancelable(false).setPositiveButton("수락", new DialogInterface.OnClickListener() {
                        //수락을 클릭 했을 때 실행되는 action
                        public void onClick(DialogInterface dialog, int id) {
                            remove_item = adapter.SelectBacode(select_index);
                            Fun_refund();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        receipt=(Button)findViewById(R.id.receipt);
        receipt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                receipt_print(date);
            }
        });


    }
    public void add(String number, String product_name,int unit_price,int quantity,int price) {
        listView = (ListView) findViewById(R.id.listView);
        adapter.addItem(adapter.getCount(),new SingerItem(number, product_name, unit_price,quantity,price));
        listView.setAdapter(adapter);
        return;
    }

    public void Fun_refund(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mConditionRef_refund=database.child("SaleHistory").child(date).child("Product");
        remove_money=adapter.SelectMoney(select_index);
//        adapter.removeItem(select_index);

        refund_day=money[0]-remove_money;
        refund_week=money[1]-remove_money;
        refund_month=money[2]-remove_money;
        final DatabaseReference mConditionRef=database.child("SalesView");
        mConditionRef.child("Day").setValue(refund_day);
        mConditionRef.child("Week").setValue(refund_week);
        mConditionRef.child("Month").setValue(refund_month);
        mConditionRef_refund.child(remove_item).removeValue();

        return;
    }
    public void receipt_print(String date){
        Intent intent_receipt=new Intent(getApplicationContext(),receipt.class);
        intent_receipt.putExtra("date",date);
        startActivityForResult(intent_receipt,1);
    }

}

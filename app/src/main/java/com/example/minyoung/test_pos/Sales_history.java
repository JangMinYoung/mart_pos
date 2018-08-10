package com.example.minyoung.test_pos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class Sales_history extends AppCompatActivity {
    ListView listView;
    SingerAdapter adapter = new SingerAdapter();
    String date;
    EditText text;
    String date_array[];
    Button history_search;
    int exist=0;
    int i=0;

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

        //        public void removeItem(int position){
//            items.remove(position);
//        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        public String select_Date(int position){
            return items.get(position).date;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemView_history view = new SingerItemView_history(getApplicationContext());

            SingerItem item = items.get(position);
            view.setDate(item.getDate());
            view.setMethod(item.getMethod());
            view.setPrice_history(item.getPrice_history());
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);
        text=(EditText)findViewById(R.id.search_date);
        history_search=(Button)findViewById(R.id.history_search);
        listView = (ListView) findViewById(R.id.listView2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Sales_Detail.class);
                date=adapter.select_Date(position);
                intent.putExtra("Date",date);
                startActivity(intent);
            }
        });


    }
    public void HistorySearch(View view){

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mConditionRef=database.child("SaleHistory");
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.allRemove();
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while(child.hasNext()){
                    date=child.next().getKey();
                    date_array=date.split(",");
                    if(date_array[0].equals(text.getText().toString())) {
                        exist=1;
                        String method = dataSnapshot.child(date).child("PaymentWay").getValue(String.class);
                        int price = dataSnapshot.child(date).child("TotalCost").getValue(Integer.class);
                        add(date, method, price);
                    }
                }
                //해당 날짜에 판매 내역이 없는 경우 오늘 날짜와 비교하여 오늘 날자로 초기화
                if(exist==0){
                    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    String today_date=df.format(new Date());
                    if(!today_date.equals(text.getText().toString())){
                        text.setText(today_date);
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void add(String date, String method, int price) {
        listView = (ListView) findViewById(R.id.listView2);

        adapter.addItem(adapter.getCount(),new SingerItem(date, method,price));
        listView.setAdapter(adapter);
        return;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                Intent intent=new Intent(getApplicationContext(), Sales_view.class);
                startActivityForResult(intent,1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

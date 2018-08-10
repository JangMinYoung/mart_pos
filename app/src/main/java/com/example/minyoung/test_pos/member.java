package com.example.minyoung.test_pos;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class member extends AppCompatActivity {
    Button search,ok,cancel;
    EditText name_edit,phone_edit;
    String phone_array[];
    String name,phone,back_phone;
    int point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        phone_edit=(EditText)findViewById(R.id.phone_edit);
        name_edit=(EditText)findViewById(R.id.name_edit);
        search=(Button)findViewById(R.id.search);
        ok=(Button)findViewById(R.id.ok);
        cancel=(Button)findViewById(R.id.cancel);

        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
               back_phone=phone_search();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        ok.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(name_edit!=null&&phone_edit!=null) {
                   name=name_edit.getText().toString();
                    phone=phone_edit.getText().toString();
                    if (!name.equals("") && !phone.equals("")) {
                        if(back_phone==null){
                            Toast.makeText(getApplicationContext(),"핸드폰 조회가 필요합니다.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            sign_up(name, phone);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"전화번호와 이름을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void sign_up(String name, String phone){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mConditionRef=database.child("User").child(back_phone);
        User user=new User(name, phone,0);
        Map<String, Object> users_add = user.toMap();
        mConditionRef.updateChildren(users_add);
        Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public String phone_search() {
        name=name_edit.getText().toString();
        phone=phone_edit.getText().toString();
        phone_array=phone.split("-");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mConditionRef=database.child("User");
        mConditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int a=0;
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while(child.hasNext()) {
                    DataSnapshot temp=child.next();
                    String key=temp.getKey();
                    if(key.equals(phone_array[2])){
                        point=temp.child("Point").getValue(Integer.class);
                        Toast.makeText(getApplicationContext(),"이미 무목마트의 회원입니다. ",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"회원님의 포인트는 "+String.valueOf(point)+"점 입니다.",Toast.LENGTH_SHORT).show();
                        phone_edit.setText("");
                        a=1;
                        return;
                    }
                }
                if(a==0){
                        Toast.makeText(getApplicationContext(),"회원 가입이 가능한 전화번호 입니다.",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return phone_array[2];
    }
    public static class User {

        public String add_name;
        public String add_phone;
        public int point;
        public Map<String, Boolean> stars = new HashMap<>();

        public User(String add_name, String add_phone,int point) {
            this.add_name=add_name;
            this.add_phone=add_phone;
            this.point=point;
        }
        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("Name", add_name);
            result.put("phoneNumer",add_phone);
            result.put("Point", point);


            return result;
        }

    }
}
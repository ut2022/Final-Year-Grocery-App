package com.smartechindia.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Button logout=findViewById(R.id.logout);
        String namee=getIntent().getStringExtra("name");
        String numberr=getIntent().getStringExtra("number");
        String addresss=getIntent().getStringExtra("address");

        TextView name=findViewById(R.id.user_name);
        TextView number=findViewById(R.id.user_number);
        TextView address=findViewById(R.id.user_address);
        TextView myorder=findViewById(R.id.myorder);

        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this,MyOrder.class);
                startActivity(intent);
            }
        });
        name.setText(namee);
        number.setText(numberr);
        address.setText(addresss);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("TOKEN","");
                editor.apply();

                Intent intent=new Intent(UserProfile.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        ImageView imageView=findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
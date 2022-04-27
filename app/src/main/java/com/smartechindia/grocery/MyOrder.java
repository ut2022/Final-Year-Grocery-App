package com.smartechindia.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartechindia.grocery.Adapter.OrderAdapter;
import com.smartechindia.grocery.Adapter.ProductAdapter;
import com.smartechindia.grocery.Adapter.Support;
import com.smartechindia.grocery.api.RetrofitClient;
import com.smartechindia.grocery.api.api;
import com.smartechindia.grocery.model.DASHBOARD_RESPONSE;
import com.smartechindia.grocery.model.Dashboard_Data;
import com.smartechindia.grocery.model.Dashboard_SendData;
import com.smartechindia.grocery.model.Myorder_Data;
import com.smartechindia.grocery.model.Myorder_Dataa;
import com.smartechindia.grocery.model.Myorder_RESPONSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        SharedPreferences prfs = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
        String Astatus = prfs.getString("TOKEN", "");
        if (Astatus.isEmpty()) {
            Intent intent=new Intent(MyOrder.this,Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(MyOrder.this,"Login Please !",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        api api= RetrofitClient.getRetrofit().create(api.class);
        Call<Myorder_RESPONSE> call;
        RecyclerView recyclerView=findViewById(R.id.recycleview);
        TextView text=findViewById(R.id.text);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        call = api.MYORDER_RESPONSE_CALL(new Dashboard_SendData(Astatus));
        call.enqueue(new Callback<Myorder_RESPONSE>() {
            @Override
            public void onResponse(Call<Myorder_RESPONSE> call, Response<Myorder_RESPONSE> response) {
                    List<Myorder_Dataa> userList = new ArrayList<>();
                OrderAdapter adapter;

                if(response.body().isStatus()) {
                    int vv = response.body().getData().size();
                    if (vv != 0) {
                        userList = response.body().getData();
                        adapter = new OrderAdapter(getBaseContext(), userList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());


                    } else {
                                   text.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(MyOrder.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                 }
            }
            @Override
            public void onFailure(Call<Myorder_RESPONSE> call, Throwable t) {
                Toast.makeText(MyOrder.this,"Server Down !", Toast.LENGTH_LONG).show();
            }
        });

    }
}
package com.smartechindia.grocery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartechindia.grocery.Adapter.ProductAdapter;
import com.smartechindia.grocery.api.RetrofitClient;
import com.smartechindia.grocery.api.api;
import com.smartechindia.grocery.database.DatabaseHelper;
import com.smartechindia.grocery.model.Dashboard_Data;
import com.smartechindia.grocery.model.ORDER_SendData;
import com.smartechindia.grocery.model.Otp_Response;
import com.smartechindia.grocery.model.order_product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Cart extends AppCompatActivity {
    List<Dashboard_Data> Datalist = new ArrayList<>();
    ProductAdapter adapter;
    private boolean isDataload=false;
    DatabaseHelper mDatabaseHelper;

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Cart.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recyclerView=findViewById(R.id.recycleview);
        ProgressBar progressBar =findViewById(R.id.progress);
        TextView text =findViewById(R.id.text);
        ImageView imagess =findViewById(R.id.imagess);
        mDatabaseHelper= new DatabaseHelper(Cart.this);
        Datalist=(List<Dashboard_Data>)getIntent().getExtras().getSerializable("lists");
        Button order=findViewById(R.id.placeorder);

        SharedPreferences prfs = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
        String Astatus = prfs.getString("TOKEN", "");


        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Cart.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Astatus.isEmpty()) {
                    Intent intent=new Intent(Cart.this,Profile.class);
                    Toast.makeText(Cart.this, "Login Please !", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return;
                }
                new AlertDialog.Builder(Cart.this)
                        .setTitle("ORDER PLACE")
                        .setMessage("Are You Sure For Place Order ?")
                        .setIcon(R.drawable.logo)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {


                                order_product[] arr=new order_product[mDatabaseHelper.getDataRow()];
                                int i;int j=0;
                                for (i = 0; i < Datalist.size(); i++) {
                                    if (mDatabaseHelper.getValue(Integer.parseInt(Datalist.get(i).getId())) > 0) {
                                       arr[j]= new order_product(Datalist.get(i).getId(),String.valueOf(mDatabaseHelper.getValue(Integer.parseInt(Datalist.get(i).getId()))));
                                         j=j+1;
                                    }
                                }
                                Log.d("array size", String.valueOf(j));
                                Log.d("array is", arr.toString());
                                api api= RetrofitClient.getRetrofit().create(api.class);
                                Call<Otp_Response> call=api.ORDER_RESPONSE_CALL(new ORDER_SendData(Astatus, Arrays.asList(arr)));
                                call.enqueue(new Callback<Otp_Response>() {
                                    @Override
                                    public void onResponse(Call<Otp_Response> call, Response<Otp_Response> response) {
                                        if(response.body().isStatus())
                                        {
                                            Toast.makeText(Cart.this, "Order Successfully Done !", Toast.LENGTH_SHORT).show();
                                            mDatabaseHelper.ClearDatabase();
                                            Intent intent=new Intent(Cart.this,MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else
                                        {
                                            Toast.makeText(Cart.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Otp_Response> call, Throwable t) {

                                        Log.d("problem",t.getMessage());
                                        Toast.makeText(Cart.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        List<Dashboard_Data> Datalistt = new ArrayList<>();
        int i;
        for (i = 0; i < Datalist.size(); i++) {

            if (mDatabaseHelper.getValue(Integer.parseInt(Datalist.get(i).getId())) > 0) {
                Datalistt.add(Datalist.get(i));
            }
        }

        if(Datalistt.size()==0)
        {

            text.setVisibility(View.VISIBLE);
            imagess.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            order.setVisibility(View.GONE);

        }else {

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ProductAdapter(getBaseContext(), Datalistt);
            adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onViewClick(String position, int pro) {
                    if(pro==1)
                    {
                        mDatabaseHelper.addData(position,"1");
                    }
                }
            });
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            progressBar.setVisibility(View.GONE);
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
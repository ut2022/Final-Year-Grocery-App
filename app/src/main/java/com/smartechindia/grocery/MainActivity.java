package com.smartechindia.grocery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.smartechindia.grocery.Adapter.ProductAdapter;
import com.smartechindia.grocery.Adapter.Support;
import com.smartechindia.grocery.api.RetrofitClient;
import com.smartechindia.grocery.api.api;
import com.smartechindia.grocery.database.DatabaseHelper;
import com.smartechindia.grocery.model.DASHBOARD_RESPONSE;
import com.smartechindia.grocery.model.Dashboard_Data;
import com.smartechindia.grocery.model.Dashboard_SendData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    List<Dashboard_Data> userList = new ArrayList<>();
    ProductAdapter adapter;
    private boolean isDataload=false;
    private boolean isDataloadd=false;
    DatabaseHelper  mDatabaseHelper;
    ;
    List<String> products=new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner=findViewById(R.id.spinner);
        Spinner spinners=findViewById(R.id.spinners);

        ImageView imageView=findViewById(R.id.profile);
        ImageView support=findViewById(R.id.support);
        ImageView whatsaap=findViewById(R.id.whatsapp);
        mDatabaseHelper = new DatabaseHelper(this);


        api api= RetrofitClient.getRetrofit().create(api.class);
        Call<DASHBOARD_RESPONSE> call;
        RecyclerView recyclerView=findViewById(R.id.recycleview);
        ProgressBar progressBar =findViewById(R.id.progress);
        ImageView cart=findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(MainActivity.this,Cart.class);
              intent.putExtra("lists", (Serializable)userList);
              startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences prfs = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
        String Astatus = prfs.getString("TOKEN", "");
        if (Astatus.isEmpty()) {
            Astatus="";
        }
        call = api.DASHBOARD_RESPONSE_CALL(new Dashboard_SendData(Astatus));
        call.enqueue(new Callback<DASHBOARD_RESPONSE>() {
          @RequiresApi(api = Build.VERSION_CODES.N)
          @Override
          public void onResponse(Call<DASHBOARD_RESPONSE> call, Response<DASHBOARD_RESPONSE> response) {
              if(response.body().isStatus())
              {
                  int vv=response.body().getData().size();
                  if(vv!=0)
                  {
                      userList = response.body().getData();
                      adapter = new ProductAdapter(getBaseContext(), userList);
                      adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                          @Override
                          public void onViewClick(String position, int pro) {
                              if(pro==1)
                              {
                                  mDatabaseHelper.addData(position,"1");
                              }
                          }
                      });
                      imageView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              if(isNetworkAvailable(MainActivity.this)) {

                                  SharedPreferences prfs = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
                                  String Astatus = prfs.getString("TOKEN", "");
                                  if (Astatus.isEmpty()) {
                                      Intent intent = new Intent(MainActivity.this, Profile.class);
                                      startActivity(intent);

                                  } else {
                                      Intent intent = new Intent(MainActivity.this, UserProfile.class);
                                      intent.putExtra("name",response.body().getUser().get(0).getName());
                                      intent.putExtra("number",response.body().getUser().get(0).getMobile());
                                      intent.putExtra("address",response.body().getUser().get(0).getAddress());
                                      startActivity(intent);
                                  }
                              }else{
                                  Toast.makeText(MainActivity.this, "Check Your Internet Connection !", Toast.LENGTH_LONG).show();
                              }
                          }
                      });
                             String numbers=response.body().getSupport().get(0).getMobile();
                      whatsaap.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              if(isNetworkAvailable(MainActivity.this)) {
                                  try {
                                      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+91"+numbers+"&text=Hey, I Want Help From You"));
                                      startActivity(intent);
                                  } catch (Exception e) {
                                      Toast.makeText(MainActivity.this, "Whatsapp not installed..!", Toast.LENGTH_LONG).show();
                                  }
                              }else
                              {
                                  Toast.makeText(MainActivity.this, "Check Your Internet Connection !", Toast.LENGTH_LONG).show();

                              }
                          }
                      });
                      support.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent intent=new Intent(MainActivity.this, Support.class);
                              intent.putExtra("help",response.body().getSupport().get(0).getHelp());
                              intent.putExtra("mobile",response.body().getSupport().get(0).getMobile());
                              intent.putExtra("number",numbers);
                              startActivity(intent);
                          }
                      });
                      recyclerView.setAdapter(adapter);
                      adapter.notifyDataSetChanged();
                      recyclerView.setHasFixedSize(true);
                      recyclerView.setItemAnimator(new DefaultItemAnimator());
                      ArrayList<String> arrayList; ArrayList<String> arrayListt;
                      arrayList= new ArrayList<String>(vv);
                      arrayList.add(0,"All Product");
                      arrayListt= new ArrayList<String>(vv);
                      arrayListt.add(0,"All Sub Category");
                      for(int i=0; i<vv; i++)
                      {
                          arrayList.add(response.body().getData().get(i).getCategory());
                          arrayListt.add(response.body().getData().get(i).getSubcategory());
                      }
                      ArrayList uniqueList = (ArrayList) arrayList. stream().distinct().collect(Collectors.toList());
                      ArrayList uniqueListt = (ArrayList) arrayListt. stream().distinct().collect(Collectors.toList());

                      ArrayAdapter spinnerArrayAdapter = new ArrayAdapter
                              (MainActivity.this, android.R.layout.simple_spinner_item,
                                      uniqueList);
                      spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_deisgn);
                      spinner.setAdapter(spinnerArrayAdapter);

                      ArrayAdapter spinnerArrayAdapters = new ArrayAdapter
                              (MainActivity.this, android.R.layout.simple_spinner_item,
                                      uniqueListt);
                      spinnerArrayAdapters.setDropDownViewResource(R.layout.spinner_item_deisgn);
                      spinners.setAdapter(spinnerArrayAdapters);
                  }else
                  {
                       Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
                  }
              }else
              {
                  Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();

              }
              progressBar.setVisibility(View.GONE);
          }

          @Override
          public void onFailure(Call<DASHBOARD_RESPONSE> call, Throwable t) {
              Toast.makeText(MainActivity.this,"No Internet Connection..!",Toast.LENGTH_SHORT).show();
              progressBar.setVisibility(View.GONE);
          }
      });

        SearchView searchView=(SearchView)findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                List<Dashboard_Data> newarr = new ArrayList<>();
                newarr.clear();
                if (searchView.getQuery().length() == 0) {
                    newarr.addAll(userList);
                } else {
                    for (Dashboard_Data details : userList) {

                            if (details.getTitle().toLowerCase().indexOf(newText.toLowerCase())>=0) {
                            newarr.add(details);
                                }
                    }
                }

                adapter.set_list(newarr);

                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isDataload) {
                    isDataload = true;
                    return;
                }

                List<Dashboard_Data> newarr = new ArrayList<>();
                newarr.clear();
                if (adapterView.getSelectedItem().toString().trim().contains("All Product")) {
                    newarr.addAll(userList);
                } else {

                    for (Dashboard_Data details : userList) {
                        if (adapterView.getSelectedItem().toString().toLowerCase().contains(details.getCategory().toLowerCase())) {
                            newarr.add(details);
                        }
                    }
                }
                adapter.set_list(newarr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isDataloadd) {
                    isDataloadd = true;
                    return;
                }

                List<Dashboard_Data> newarr = new ArrayList<>();
                newarr.clear();
                if (adapterView.getSelectedItem().toString().trim().contains("All Sub Category")) {
                    newarr.addAll(userList);
                } else {

                    for (Dashboard_Data details : userList) {
                        if (adapterView.getSelectedItem().toString().toLowerCase().contains(details.getSubcategory().toLowerCase())) {
                            newarr.add(details);
                        }
                    }
                }
                adapter.set_list(newarr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    public String stripHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }
    public void category(View view) {
    }
}
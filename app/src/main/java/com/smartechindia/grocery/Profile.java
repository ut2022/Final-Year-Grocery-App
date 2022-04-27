package com.smartechindia.grocery;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartechindia.grocery.api.RetrofitClient;
import com.smartechindia.grocery.api.api;
import com.smartechindia.grocery.model.Otp_Response;
import com.smartechindia.grocery.model.Otp_SendData;
import com.smartechindia.grocery.model.register_SendData;
import com.google.android.material.textfield.TextInputEditText;

public class Profile extends AppCompatActivity {

 LinearLayout layout1,layout2,layout3;
    TextInputEditText name,number,address,otp;

    @Override
    protected void onStart() {
       
        SharedPreferences prfs = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
        String Astatus = prfs.getString("TOKEN", "");
        if(!Astatus.isEmpty()) {
            Intent intent=new Intent(Profile.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         TextView textView=findViewById(R.id.login);
         TextView textView1=findViewById(R.id.registration);
         layout1=findViewById(R.id.layout1);
         layout2=findViewById(R.id.layout2);
         layout3=findViewById(R.id.layout3);



         TextInputEditText xtt=findViewById(R.id.rnumber);
         name=findViewById(R.id.name);
         number=findViewById(R.id.number);
         address=findViewById(R.id.address);
         otp=findViewById(R.id.otp);
         Button get_otp=findViewById(R.id.get_otp);
         Button continuee=findViewById(R.id.continuee);
         Button verify_otp=findViewById(R.id.verify_otp);

         continuee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty())
                {
                    Toast.makeText(Profile.this, "Fill Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(number.getText().toString().isEmpty())
                {
                    Toast.makeText(Profile.this, "Fill Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(number.getText().toString().length()!=10)
                {
                    Toast.makeText(Profile.this, "Number is Invalid", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(address.getText().toString().isEmpty())
                {
                    Toast.makeText(Profile.this, "Fill Address Properly", Toast.LENGTH_SHORT).show();
                    return;
                }

                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                register(2);
            }
         });

         verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty())
                {
                    Toast.makeText(Profile.this, "Fill OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(number.getText().toString().isEmpty())
                {
                    get_otp(xtt.getText().toString(),otp.getText().toString());

                }else
                {
                    register(1);

                }

            }
        });

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(xtt.getText().toString().isEmpty())
                {
                    Toast.makeText(Profile.this, "Fill Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                    if(xtt.getText().toString().length()!=10)
                {
                    Toast.makeText(Profile.this, "Number is Invalid", Toast.LENGTH_SHORT).show();
                    return;

                }

                    layout1.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                get_otp(xtt.getText().toString(),"");

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                number.setText("");
                name.setText("");
                address.setText("");
                otp.setText("");


            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
                xtt.setText("");
                otp.setText("");

            }
        });
        ImageView imageView=findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }



    private void get_otp(String m,String i) {
        api api= RetrofitClient.getRetrofit().create(api.class);
        Call<Otp_Response> call;
       if(i.isEmpty())
       {
           call = api.LOGIN_RESPONSE_CALL( new Otp_SendData(m,""));

       }else
       {
            call = api.LOGIN_RESPONSE_CALL( new Otp_SendData(m,i));

       }

        call.enqueue(new Callback<Otp_Response>() {
             @Override
             public void onResponse(Call<Otp_Response> call, Response<Otp_Response> response) {
                 if(response.body().isStatus())
                 {
                     if(!i.isEmpty())
                     {
                             Toast.makeText(Profile.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                             SharedPreferences preferences = getSharedPreferences("ISLOGIN", Context.MODE_PRIVATE);
                             SharedPreferences.Editor editor = preferences.edit();
                             editor.putString("TOKEN",response.body().getToken());
                             editor.apply();
                             Intent intent=new Intent(Profile.this,MainActivity.class);
                             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(intent);
                     }
                 }else
                 {
                     Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<Otp_Response> call, Throwable t) {
                 Toast.makeText(Profile.this, "Something Went Wrong ..!", Toast.LENGTH_SHORT).show();

             }
         });

    }

    private void register(int i) {
        api api= RetrofitClient.getRetrofit().create(api.class);
        Call<Otp_Response> call;
      if(i==1)
      {
          call=api.REGISTER_RESPONSE_CALL(new register_SendData(number.getText().toString(),otp.getText().toString(),address.getText().toString(),name.getText().toString()));
      }else
      {
          call=api.OTP_RESPONSE_CALL(new Otp_SendData(number.getText().toString(),""));
      }
       call.enqueue(new Callback<Otp_Response>() {
           @Override
           public void onResponse(Call<Otp_Response> call, Response<Otp_Response> response) {
               if(response.body().isStatus())
               {
                   if(i==1)
                   {
                       Toast.makeText(Profile.this,"Successfully Registered! Login Please", Toast.LENGTH_SHORT).show();
                       layout3.setVisibility(View.GONE);
                       layout1.setVisibility(View.VISIBLE);
                       number.setText("");
                       name.setText("");
                       address.setText("");
                       otp.setText("");
                   }
               }else
               {
                   Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
           @Override
           public void onFailure(Call<Otp_Response> call, Throwable t) {
               Toast.makeText(Profile.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
           }
       });
    }
}
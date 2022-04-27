package com.smartechindia.grocery.Adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartechindia.grocery.MainActivity;
import com.smartechindia.grocery.R;

public class Support extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        String help=getIntent().getStringExtra("help");
        String mobile=getIntent().getStringExtra("mobile");
        TextView textView=findViewById(R.id.text);
        TextView button=findViewById(R.id.secc);
        textView.setText(stripHtml(help));

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Support.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable(Support.this)) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+91"+mobile+"&text=Hey, I Want Help From You"));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Support.this, "Whatsapp not installed..!", Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(Support.this, "Check Your Internet Connection !", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    public String stripHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
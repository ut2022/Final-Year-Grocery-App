package com.smartechindia.grocery.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartechindia.grocery.R;
import com.smartechindia.grocery.database.DatabaseHelper;
import com.smartechindia.grocery.model.Dashboard_Data;
import com.smartechindia.grocery.model.Myorder_Data;
import com.smartechindia.grocery.model.Myorder_Dataa;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.UserViewHolder> {
    private OrderAdapter.OnItemClickListener mListener;
    private Context mCtx;
    private List<Myorder_Dataa> userList=new ArrayList<>();
    DatabaseHelper mDatabaseHelper;
    int i=0;
    public OrderAdapter(Context mCtx, List<Myorder_Dataa> userList) {
        this.mCtx = mCtx;
        mDatabaseHelper = new DatabaseHelper(mCtx);
        this.userList = userList;
    }
    public void set_list(List<Myorder_Dataa> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onViewClick(String position,int pro);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(mCtx).inflate(R.layout.order_data,parent,false);
        return new UserViewHolder(view,mListener);

    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Myorder_Dataa data=userList.get(position);
        holder.textDate.setText(data.getDate());
        holder.TextItem.setText(data.getStatus());

        for(i=0; i<data.getData().size();i++)
        {
            TextView txt=new TextView(mCtx);
            txt.setTextColor(mCtx.getResources().getColor(R.color.black));
            if(i==0){txt.setPadding(22,22,0,0); }else{ txt.setPadding(22,12,0,0); }
            txt.setTextSize(15);
            txt.setText(data.getData().get(i).getQty()+" * "+data.getData().get(i).getName()+"  - "+data.getData().get(i).getPrice()+" INR");
            holder.layout.addView(txt);
        }
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

class UserViewHolder extends RecyclerView.ViewHolder {

    TextView textDate,TextItem;
    LinearLayout layout;

    public UserViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
        super(itemView);
        textDate=(TextView) itemView.findViewById(R.id.orderdate);
        TextItem=(TextView) itemView.findViewById(R.id.status);
        layout=itemView.findViewById(R.id.updatecart);
    }
}
}

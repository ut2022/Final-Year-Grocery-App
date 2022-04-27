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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UserViewHolder> {
    private ProductAdapter.OnItemClickListener mListener;
    private Context mCtx;
    private List<Dashboard_Data> userList=new ArrayList<>();
    DatabaseHelper mDatabaseHelper;
    int i=0;
    public ProductAdapter(Context mCtx, List<Dashboard_Data> userList) {
        this.mCtx = mCtx;
        mDatabaseHelper = new DatabaseHelper(mCtx);
        this.userList = userList;
    }
    public void set_list(List<Dashboard_Data> userList) {
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
      View view= LayoutInflater.from(mCtx).inflate(R.layout.product_data,parent,false);
        return new UserViewHolder(view,mListener);

    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Dashboard_Data data=userList.get(position);
        holder.textTitle.setText(data.getTitle());
        holder.TextPrice.setText("Price : "+data.getPrice()+" Rs.");
        if(!data.getMrp().isEmpty()){
        holder.TEXTMRP.setText(data.getMrp()+" Rs.");
        }
        holder.TEXTMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG |
                Paint.ANTI_ALIAS_FLAG);
        holder.TextCategory.setText(data.getCategory());
        holder.TextSubcategory.setText(data.getSubcategory());
        Picasso.with(mCtx).load(data.getFile()).fit().into(holder.TextImage);

        if(mDatabaseHelper.getValue(Integer.parseInt(data.getId()))!=0)
        {
            holder.btn.setVisibility(View.GONE);
            holder.layout.setVisibility(View.VISIBLE);
            holder.TextValue.setText(String.valueOf(mDatabaseHelper.getValue(Integer.parseInt(data.getId()))));
        }

        holder.TextGreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                          int pll=mDatabaseHelper.getValue(Integer.parseInt(data.getId()));
                          holder.TextValue.setText(String.valueOf(pll+1));
                          mDatabaseHelper.UpdateData(data.getId(),pll+1);
                }
        });
        holder.TextLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.getValue(Integer.parseInt(data.getId()))==1)
                {
                    holder.TextValue.setText("1");
                    holder.btn.setVisibility(View.VISIBLE);
                    holder.layout.setVisibility(View.GONE);
                    mDatabaseHelper.UpdateData(data.getId(),0);
                }else
                {
                    int pl=mDatabaseHelper.getValue(Integer.parseInt(data.getId()));
                    mDatabaseHelper.UpdateData(data.getId(),pl-1);
                    holder.TextValue.setText(String.valueOf(pl-1));

                }

            }
        });
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position != RecyclerView.NO_POSITION) {
                    holder.btn.setVisibility(View.GONE);
                    holder.layout.setVisibility(View.VISIBLE);
                    if (mListener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onViewClick(data.getId(),1);
                        }
                    }
                }
                }
        });
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

class UserViewHolder extends RecyclerView.ViewHolder {

    TextView textTitle,TextPrice,TextCategory,TextSubcategory,TextValue,TEXTMRP;
    ImageView TextImage;
    LinearLayout TextGreat,TextLess;
    LinearLayout layout;
    Button btn;

    public UserViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
        super(itemView);
        textTitle=(TextView) itemView.findViewById(R.id.title);
        TextPrice=(TextView) itemView.findViewById(R.id.price);
        TextCategory=(TextView) itemView.findViewById(R.id.category);
        TextSubcategory=(TextView) itemView.findViewById(R.id.subcategory);
        btn=itemView.findViewById(R.id.addcart);
        TextImage=(ImageView) itemView.findViewById(R.id.image);
        layout=itemView.findViewById(R.id.updatecart);
        TextGreat=itemView.findViewById(R.id.greater);
        TEXTMRP=itemView.findViewById(R.id.mrp);
        TextLess=itemView.findViewById(R.id.less);
        TextValue=itemView.findViewById(R.id.value);
    }
}
}

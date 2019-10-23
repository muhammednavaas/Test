package com.beauty.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beauty.myapplication.Interface.IRecyclerViewOnClick;
import com.beauty.myapplication.Model.BusinessList;
import com.beauty.myapplication.R;
import com.beauty.myapplication.api.Common;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchAdpter extends RecyclerView.Adapter<SearchAdpter.MyViewHolder> {

    private Context context;
    private List<BusinessList> searchBusinessList;


    public SearchAdpter(Context context, List<BusinessList> searchBusinessList) {
        this.context = context;
        this.searchBusinessList = searchBusinessList;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vew, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.businessName.setText(searchBusinessList.get(position).getBusinessName());
        holder.country.setText(searchBusinessList.get(position).getCountry());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_placeholder_sq);
        Glide.with(context).load(Common.ImagePath+searchBusinessList.get(position).getImage()+".jpeg")
                .apply(requestOptions).into(holder.imageView);
        holder.setiRecyclerViewOnClick(new IRecyclerViewOnClick() {

            @Override
            public void onClick(View view, int position) {

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("ITEM", String.valueOf(searchBusinessList.size()));
        return searchBusinessList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.businessName)
        TextView businessName;
        @BindView(R.id.country)
        TextView country;

        IRecyclerViewOnClick iRecyclerViewOnClick;
        Unbinder unbinder;

        public void setiRecyclerViewOnClick(IRecyclerViewOnClick iRecyclerViewOnClick) {
            this.iRecyclerViewOnClick = iRecyclerViewOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            imageView = itemView.findViewById(R.id.image);
            country = itemView.findViewById(R.id.country);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerViewOnClick.onClick(v, getAdapterPosition());
        }
    }
}


package com.example.foodsharingapplication;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(View itemView){
        super(itemView);

        mView = itemView;


        //Item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

    }

    public void setDetails(Context ctx, String title, ArrayList<String> image, String logo, String price, String time){
        TextView mTitleView = mView.findViewById(R.id.textTitle);
        ImageView mImageView = mView.findViewById(R.id.imageView1);
        ImageView mLogo = mView.findViewById(R.id.imageView2);
        TextView mPrice = mView.findViewById(R.id.textPrice);
        TextView mTime = mView.findViewById(R.id.textTime);

        String image1 = image.get(0);
        mTitleView.setText(title);
        mTime.setText(time);
        mPrice.setText(price);
        Picasso.get().load(logo).into(mLogo);
        Picasso.get().load(image1).into(mImageView);

    }

    private  ViewHolder.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}

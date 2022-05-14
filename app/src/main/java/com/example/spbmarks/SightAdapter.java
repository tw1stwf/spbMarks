package com.example.spbmarks;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SightAdapter extends RecyclerView.Adapter<SightAdapter.ExampleViewHolder> {
    private ArrayList<Sight> mSightList;
    private OnSightListener mOnSightListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mTextViewSightName;
        public TextView mTextViewMetro;
        public TextView mTextViewAdress;
        public ImageView mImageStar;

        OnSightListener onSightListener;

        public ExampleViewHolder(View itemView, OnSightListener onSightListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextViewSightName = itemView.findViewById(R.id.textViewSightName);
            mTextViewMetro = itemView.findViewById(R.id.textViewMetro);
            mTextViewAdress = itemView.findViewById(R.id.textViewLocation);
            mImageStar = itemView.findViewById(R.id.imageViewStar);
            this.onSightListener = onSightListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSightListener.onSightClick(getAdapterPosition());
        }
    }

    public SightAdapter(ArrayList<Sight> exampleList, OnSightListener onSightListener) {
        mSightList = exampleList;
        this.mOnSightListener = onSightListener;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sight_item,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mOnSightListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Sight currentItem = mSightList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextViewSightName.setText(currentItem.getSightName());
        holder.mTextViewMetro.setText(currentItem.getMetro());
        holder.mTextViewAdress.setText(currentItem.getLocation());

        if(currentItem.getStar() == false)
        {
            holder.mImageStar.setColorFilter(Color.argb(255, 151, 151, 151));
        }

        if(currentItem.getStar() == true)
        {
            holder.mImageStar.setColorFilter(Color.argb(255, 205, 201, 112));
        }
    }

    @Override
    public int getItemCount() {
        return mSightList.size();
    }

    public interface OnSightListener {
        void onSightClick(int position);
    }
}
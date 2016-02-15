package com.artsam.aos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artsam.aos.MainActivity;
import com.artsam.aos.R;
import com.artsam.aos.entity.Sample;

import java.util.List;

public class MyRecAdapter extends RecyclerView.Adapter<MyRecAdapter.ViewHolder> {

    private List<Sample> mSamples;

    public MyRecAdapter(List<Sample> samples) {
        this.mSamples = samples;
    }

    @Override
    public MyRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sample, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyRecAdapter.ViewHolder holder, int position) {
        holder.mSampleName.setText(mSamples.get(position).getName());
        holder.mSampleX.setText("Axis X: " + String.valueOf(mSamples.get(position).getX()));
        holder.mSampleY.setText("Axis Y: " + String.valueOf(mSamples.get(position).getY()));
        holder.mSampleZ.setText("Axis Z: " + String.valueOf(mSamples.get(position).getZ()));
    }

    public void add(Sample sample){
        mSamples.add(MainActivity.FIRST_SAMPLE_POS, sample);
        notifyItemInserted(MainActivity.FIRST_SAMPLE_POS);
    }

    @Override
    public int getItemCount() {
        return mSamples.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSampleName;
        private TextView mSampleX;
        private TextView mSampleY;
        private TextView mSampleZ;

        public ViewHolder(View itemView) {
            super(itemView);
            mSampleName = (TextView) itemView.findViewById(R.id.tv_sample_name);
            mSampleX = (TextView) itemView.findViewById(R.id.tv_sample_x);
            mSampleY = (TextView) itemView.findViewById(R.id.tv_sample_y);
            mSampleZ = (TextView) itemView.findViewById(R.id.tv_sample_z);
        }
    }
}

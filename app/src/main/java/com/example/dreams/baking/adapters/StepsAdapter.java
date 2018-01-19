package com.example.dreams.baking.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dreams.baking.R;
import com.example.dreams.baking.StepDesFragment;
import com.example.dreams.baking.StepsDetailsActivity;
import com.example.dreams.baking.models.StepsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<StepsModel> mListSteps;
    private Boolean mIsTwoPane;
    private FragmentManager mFragmentManager;

    public StepsAdapter(Context c, Boolean isTwoPane, FragmentManager fragmentManager) {
        mContext = c;
        mInflater = LayoutInflater.from(c);
        mIsTwoPane = isTwoPane;
        mFragmentManager = fragmentManager;
    }

    public void setData(ArrayList<StepsModel> StepsList) {
        mListSteps = StepsList;
        notifyDataSetChanged();
    }

    @Override
    public StepsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.steps_list_item, parent, false);
        return new StepsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepsAdapter.MyViewHolder holder, int position) {
        final int i = position;
        holder.StepId.setText(String.valueOf(mListSteps.get(i).getId()) + ".");
        holder.StepTxt.setText(mListSteps.get(i).getShortDescription());

        holder.StepClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsTwoPane) {
                    Bundle b = new Bundle();
                    b.putParcelable("step", mListSteps.get(i));
                    StepDesFragment fragment = new StepDesFragment();
                    fragment.setArguments(b);
                    mFragmentManager.beginTransaction().replace(R.id.details_fragment, fragment).commit();

                } else {

                    Intent intent = new Intent(mContext, StepsDetailsActivity.class);
                    intent.putExtra("stepId", mListSteps.get(i).getId());
                    intent.putExtra("steps", mListSteps);
                    mContext.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListSteps == null)
            return 0;
        else
            return mListSteps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stepId)
        TextView StepId;
        @BindView(R.id.stepTxt)
        TextView StepTxt;
        @BindView(R.id.step_click)
        RelativeLayout StepClick;

        MyViewHolder(final View itemView) {
            super(itemView);

            // binding view
            ButterKnife.bind(this, itemView);
        }
    }


}



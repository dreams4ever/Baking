package com.example.dreams.baking.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dreams.baking.R;
import com.example.dreams.baking.models.IngredientsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>{
private Context mContext;
private LayoutInflater mInflater;
private List<IngredientsModel> mListIngredient;


public IngredientsAdapter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(c);
        }
public void setData(List<IngredientsModel> Ingrdientlist) {
    mListIngredient=Ingrdientlist;
        notifyDataSetChanged();
        }

@Override
public IngredientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsAdapter.MyViewHolder(view);
        }

@Override
public void onBindViewHolder(MyViewHolder holder, int position) {
final int i = position;
       holder.IngredientsText.setText(mListIngredient.get(i).getIngredient());
       holder.IngredientsCount.setText("- "+mListIngredient.get(i).getQuantity()+" "+mListIngredient.get(i).getMeasure());

}

@Override
public int getItemCount() {
        if (mListIngredient == null)
        return 0;
        else
        return mListIngredient.size();
        }
class MyViewHolder extends RecyclerView.ViewHolder {
    @BindView (R.id.ingredientText) TextView IngredientsText;
    @BindView (R.id.ingredientCount) TextView IngredientsCount;

    MyViewHolder(View itemView) {
        super(itemView);
        // binding view
        ButterKnife.bind(this, itemView);

    }
}

}

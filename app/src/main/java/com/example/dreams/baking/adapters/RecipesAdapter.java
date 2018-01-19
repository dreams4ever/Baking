package com.example.dreams.baking.adapters;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dreams.baking.DetailsActivity;
import com.example.dreams.baking.R;
import com.example.dreams.baking.models.RecipesModel;
import com.example.dreams.baking.widget.WidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<RecipesModel> mList;


    public RecipesAdapter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(c);
    }

    public void setData(ArrayList<RecipesModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecipesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipes_list_item, parent, false);
        return new RecipesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int i = position;
        final SharedPreferences preferences = mContext.getSharedPreferences("DEFAULT", Context.MODE_PRIVATE);
        holder.RecipeName.setText(mList.get(i).getRecipeName());
        String json = preferences.getString("WIDGET_RECIPE_KEY", "");
        Gson gson = new Gson();
        RecipesModel recipesModel = gson.fromJson(json, RecipesModel.class);
        if (!json.isEmpty()) {
            //Active
            if (recipesModel.getRecipeId() == mList.get(i).getRecipeId())
                holder.mWidgetIcon.setImageResource(android.R.drawable.star_on);
            else
                holder.mWidgetIcon.setImageResource(android.R.drawable.star_off);
        } else {
            // inactive
            holder.mWidgetIcon.setImageResource(android.R.drawable.star_off);
        }
        holder.itemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("key", mList.get(i).getRecipeId());
                intent.putExtra("recipe_key", mList.get(i));

                mContext.startActivity(intent);
            }
        });
        holder.mWidgetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"You add this as a widget ingredients recipe ."+mList.get(i).getRecipeName(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(mContext, MainActivity.class);
//                intent.putExtra("recipe_widget", mList.get(i).getRecipeId());
                preferences.edit().clear().apply();
                Gson gson = new Gson();
                String recipesModel = gson.toJson(mList.get(i), RecipesModel.class);
                preferences.edit().putString("WIDGET_RECIPE_KEY", recipesModel).apply();
                notifyDataSetChanged();

                Context context = mContext.getApplicationContext();
                ComponentName name = new ComponentName(context, WidgetProvider.class);
                int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);

                Intent intent = new Intent(mContext, WidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                mContext.sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        else
            return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeName)
        TextView RecipeName;
        @BindView(R.id.itemClick)
        RelativeLayout itemClick;
        @BindView(R.id.widgetIcon)
        ImageView mWidgetIcon;


        MyViewHolder(View itemView) {
            super(itemView);
            // binding view
            ButterKnife.bind(this, itemView);

        }
    }
}

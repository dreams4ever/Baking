package com.example.dreams.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.dreams.baking.R;
import com.example.dreams.baking.models.IngredientsModel;
import com.example.dreams.baking.models.RecipesModel;
import com.google.gson.Gson;

import java.util.List;


public class ViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context ctxt=null;
    private int appWidgetId;
    List<IngredientsModel> items;

    public ViewsFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        SharedPreferences preferences = ctxt.getSharedPreferences("DEFAULT",Context.MODE_PRIVATE);
        String json = preferences.getString("WIDGET_RECIPE_KEY", "");
        Gson gson = new Gson();
        RecipesModel recipesModel = gson.fromJson(json, RecipesModel.class);
        items = recipesModel.getIngredientsResult();
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return(items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.widget_list_item);

        row.setTextViewText(R.id.text1, items.get(position).getIngredient());

//        Intent i=new Intent();
//        Bundle extras=new Bundle();

//      ToDo to change to key if we user DetailsActivity for recipes
//        extras.putString(WidgetProvider.EXTRA_WORD, items.get(position).getIngredient());
//        i.putExtras(extras);
//        row.setOnClickFillInIntent(R.id.text1, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }
}

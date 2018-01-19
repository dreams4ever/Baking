package com.example.dreams.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.dreams.baking.DetailsActivity;
import com.example.dreams.baking.R;
import com.example.dreams.baking.models.RecipesModel;
import com.google.gson.Gson;


public class WidgetProvider extends AppWidgetProvider {
    public static String EXTRA_WORD =
            "com.commonsware.android.appwidget.lorem.WORD";

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent svcIntent = new Intent(ctxt, WidgetService.class);

            SharedPreferences preferences = ctxt.getSharedPreferences("DEFAULT", Context.MODE_PRIVATE);
            String json = preferences.getString("WIDGET_RECIPE_KEY", "");
            Gson gson = new Gson();
            RecipesModel recipesModel = gson.fromJson(json, RecipesModel.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(), R.layout.widget);

            widget.setTextViewText(R.id.recipe_name_textview, recipesModel.getRecipeName());
            widget.setRemoteAdapter(appWidgetIds[i], R.id.appwidget_text, svcIntent);


            Intent clickIntent = new Intent(ctxt, DetailsActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(ctxt, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.appwidget_text, clickPI);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if ("PreferencesUpdated".equals(action)) {
            // update your widget here
            // my widget supports multiple instances so I needed to uniquely identify them like this
            int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
            onUpdate(context, AppWidgetManager.getInstance(context), new int[]{appWidgetId});
        }
    }
}

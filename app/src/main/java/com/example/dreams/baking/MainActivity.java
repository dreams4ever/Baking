package com.example.dreams.baking;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dreams.baking.adapters.RecipesAdapter;
import com.example.dreams.baking.models.RecipesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.RecipesList)
    RecyclerView mRecycler;
    RecipesAdapter mRecipesAdapter;
//    int mWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecycler.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
        mRecipesAdapter = new RecipesAdapter(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService mService = retrofit.create(ApiService.class);

        Call<ArrayList<RecipesModel>> recipeCall = mService.RecipeList();
        recipeCall.enqueue(new Callback<ArrayList<RecipesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipesModel>> call, Response<ArrayList<RecipesModel>> response) {
                mRecipesAdapter.setData(response.body());
                mRecipesAdapter.notifyDataSetChanged();
//                Gson gson = new Gson();
//                Intent intent=getIntent();
//                mWidgetId = intent.getIntExtra("recipe_widget", 0);
//                String widgetRecipe = gson.toJson(mWidgetId);
//                SharedPreferences sharedPreferences = getSharedPreferences("DEFAULT", MODE_PRIVATE);
//                sharedPreferences.edit().putString("WIDGET_RECIPE_KEY", widgetRecipe).apply();
            }

            @Override
            public void onFailure(Call<ArrayList<RecipesModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No Data " + t, Toast.LENGTH_LONG).show();

            }
        });
        mRecycler.setAdapter(mRecipesAdapter);


    }
}

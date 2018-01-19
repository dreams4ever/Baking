package com.example.dreams.baking;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.dreams.baking.adapters.IngredientsAdapter;
import com.example.dreams.baking.adapters.StepsAdapter;
import com.example.dreams.baking.adapters.ViewPagerAdapter;
import com.example.dreams.baking.models.IngredientsModel;
import com.example.dreams.baking.models.RecipesModel;
import com.example.dreams.baking.models.StepsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {
    int mId;
    @BindView (R.id.ingredientRec) RecyclerView mIngredientRec;
    List<IngredientsModel> mIngredientList;
    IngredientsAdapter mIngredientAdapter;

    @BindView (R.id.stepsRec) RecyclerView mStepsRec;
    ArrayList<StepsModel> mStepsList;
    StepsAdapter mStepsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);
        ButterKnife.bind(this);
        mIngredientAdapter = new IngredientsAdapter(this);

        Boolean isTwoPane = findViewById(R.id.details_fragment) != null;

        mStepsAdapter = new StepsAdapter(this, isTwoPane, getSupportFragmentManager());

        Intent intent = getIntent();
        mId = intent.getIntExtra("key", 0);
        RecipesModel recipesModel = intent.getParcelableExtra("recipe_key");

        mIngredientList = recipesModel.getIngredientsResult();
        mIngredientAdapter.setData(mIngredientList);

        mIngredientRec.setHasFixedSize(true);
        mIngredientRec.setNestedScrollingEnabled(false);
        mIngredientRec.setAdapter(mIngredientAdapter);

        mStepsList = new ArrayList<>(recipesModel.getStepsResult());
        mStepsAdapter.setData(mStepsList);

        mStepsRec.setHasFixedSize(true);
        mStepsRec.setNestedScrollingEnabled(false);
        mStepsRec.setAdapter(mStepsAdapter);

        //steps details
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitleList = new ArrayList<>();
        for (int i = 0; i < mStepsList.size(); i++) {
            Bundle b = new Bundle();
            b.putParcelable("step", mStepsList.get(i));
            StepDesFragment fragment = new StepDesFragment();
            fragment.setArguments(b);
            fragmentList.add(fragment);
            fragmentTitleList.add(String.valueOf(mStepsList.get(i).getShortDescription()));
        }
        adapter.addFragments(fragmentList, fragmentTitleList);


    }


}

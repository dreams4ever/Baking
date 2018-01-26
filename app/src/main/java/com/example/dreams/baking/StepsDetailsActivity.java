package com.example.dreams.baking;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.dreams.baking.adapters.ViewPagerAdapter;
import com.example.dreams.baking.models.StepsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsDetailsActivity extends AppCompatActivity {
    int mStepId;
    List<StepsModel> mStepsList;
    @BindBool(R.bool.two_pane_mode)
    boolean isTwoPane;

    @BindView(R.id.recipe_step_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.recipe_step_viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_details_activity);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        mStepId = intent.getIntExtra("stepId", 0);
        mStepsList = intent.getParcelableArrayListExtra("steps");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            fragmentTitleList.add("Step " + String.valueOf(i));
        }
        adapter.addFragments(fragmentList, fragmentTitleList);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(mStepId);
        if (StepsDetailsActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            tabLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

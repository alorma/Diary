package com.alorma.diary.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.alorma.diary.DiaryApplication;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.di.component.DaggerActivityComponent;
import com.alorma.diary.di.module.ActivityModule;

@SuppressLint("Registered") public class BaseActivity extends AppCompatActivity {

  private ActivityComponent activityComponent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectActivity(getActivityComponent());
  }

  protected void injectActivity(ActivityComponent activityComponent) {

  }

  private ActivityComponent getActivityComponent() {
    if (activityComponent == null) {
      activityComponent = DaggerActivityComponent.builder()
          .activityModule(new ActivityModule(this))
          .dataComponent(DiaryApplication.get(this).getDataComponent())
          .applicationComponent(DiaryApplication.get(this).getComponent())
          .build();
    }
    return activityComponent;
  }
}

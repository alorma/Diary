package com.alorma.diary.di.module;

import android.app.Activity;
import android.content.Context;
import com.alorma.diary.di.qualifiers.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

  private Activity mActivity;

  public ActivityModule(Activity activity) {
    mActivity = activity;
  }

  @Provides
  @ActivityContext
  Context provideContext() {
    return mActivity;
  }

  @Provides
  Activity provideActivity() {
    return mActivity;
  }
}
package com.alorma.diary.di.module;

import android.app.Activity;
import android.content.Context;
import com.afollestad.inquiry.Inquiry;
import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.di.qualifiers.ActivityContext;
import com.alorma.diary.di.qualifiers.DatabaseName;
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

  @Provides
  Inquiry getInquiry(@ActivityContext Context context, @DatabaseName String dbName) {
    return Inquiry.newInstance(context, dbName).instanceName(mActivity.getLocalClassName()).build();
  }

  @Provides
  ResourceLifeCycle getCleanupResource(Inquiry inquiry) {
    return inquiry::destroyInstance;
  }
}
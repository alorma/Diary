package com.alorma.diary.di.module;

import android.app.Activity;
import android.content.Context;
import com.afollestad.inquiry.Inquiry;
import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.diary.ds.SqlDiaryListDataSource;
import com.alorma.diary.di.qualifiers.ActivityContext;
import com.alorma.diary.di.qualifiers.Cache;
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
  @Cache
  DiaryListDataSource provideCacheDiaryList(@ActivityContext Context context, @DatabaseName String dbName) {
    Inquiry.newInstance(context, dbName).build();
    return new SqlDiaryListDataSource(Inquiry.get(context));
  }

  @Provides
  ResourceLifeCycle getCleanupResource(@ActivityContext Context context) {
    return () -> Inquiry.destroy(context);
  }
}
package com.alorma.diary.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.di.qualifiers.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
  private Fragment fragment;

  public FragmentModule(Fragment fragment) {
    this.fragment = fragment;
  }

  @Provides
  @ActivityContext
  Context provideContext() {
    return fragment.getContext();
  }

  /*
  @Provides
  @Cache
  DiaryListDataSource provideCacheDiaryList(@ActivityContext Context context, @DatabaseName String dbName,
      @IoScheduler Scheduler scheduler) {
    Inquiry.newInstance(context, dbName).build();
    return new SqlDiaryListDataSource(Inquiry.get(context), scheduler);
  }
  */

  @Provides
  ResourceLifeCycle getCleanupResource(@ActivityContext Context context) {
    return () -> {};
  }
}

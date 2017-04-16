package com.alorma.diary.di.module;

import android.app.Application;
import android.content.Context;
import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.diary.ds.MemoryDiaryListDataSource;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.di.qualifiers.ApplicationContext;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.DatabaseName;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

  private final Application mApplication;

  public ApplicationModule(Application app) {
    mApplication = app;
  }

  @Provides
  @ApplicationContext
  Context provideContext() {
    return mApplication;
  }

  @Provides
  Application provideApplication() {
    return mApplication;
  }

  @Provides
  ErrorTracker provideErrorTracker() {
    return Throwable::printStackTrace;
  }

  @Provides
  @Cache
  @Singleton
  DiaryListDataSource provideCacheDiaryList() {
    return new MemoryDiaryListDataSource();
  }

  @Provides
  @Singleton
  @DatabaseName
  String dbName() {
    return "diaries.db";
  }
}
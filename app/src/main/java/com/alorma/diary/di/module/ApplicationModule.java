package com.alorma.diary.di.module;

import android.app.Application;
import android.content.Context;
import com.alorma.diary.BuildConfig;
import com.alorma.diary.di.qualifiers.AppInfo;
import com.alorma.diary.di.qualifiers.ApplicationContext;
import dagger.Module;
import dagger.Provides;

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
  @AppInfo
  String provideAppPackage() {
    return BuildConfig.APPLICATION_ID;
  }

  @Provides
  @AppInfo
  int provideAppVersion() {
    return BuildConfig.VERSION_CODE;
  }

  @Provides
  @AppInfo
  boolean providesAppDebugMode() {
    return BuildConfig.DEBUG;
  }

/*
  @Provides
  @DatabaseInfo
  String provideDatabaseName() {
    return "demo-dagger.db";
  }

  @Provides
  @DatabaseInfo
  Integer provideDatabaseVersion() {
    return 2;
  }

  @Provides
  SharedPreferences provideSharedPrefs() {
    return mApplication.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
  }
  */
}
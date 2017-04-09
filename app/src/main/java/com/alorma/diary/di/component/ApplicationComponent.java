package com.alorma.diary.di.component;

import android.app.Application;
import android.content.Context;
import com.alorma.diary.DiaryApplication;
import com.alorma.diary.di.module.ApplicationModule;
import com.alorma.diary.di.qualifiers.AppInfo;
import com.alorma.diary.di.qualifiers.ApplicationContext;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(DiaryApplication demoApplication);

  @ApplicationContext
  Context getContext();

  Application getApplication();

  @AppInfo
  String appName();

  @AppInfo
  int appVersion();

  @AppInfo
  boolean appDebugMode();


/*
    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();

    DbHelper getDbHelper();
*/
}
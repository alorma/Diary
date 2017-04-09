package com.alorma.diary.di.module;

import android.app.Application;
import android.content.Context;
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
}
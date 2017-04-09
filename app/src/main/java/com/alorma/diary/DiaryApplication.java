package com.alorma.diary;

import android.app.Application;
import android.content.Context;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DaggerApplicationComponent;
import com.alorma.diary.di.module.ApplicationModule;

public class DiaryApplication extends Application {

  protected ApplicationComponent applicationComponent;

  public static DiaryApplication get(Context context) {
    return (DiaryApplication) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    applicationComponent.inject(this);
  }

  public ApplicationComponent getComponent() {
    return applicationComponent;
  }
}

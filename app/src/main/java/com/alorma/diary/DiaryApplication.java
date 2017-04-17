package com.alorma.diary;

import android.app.Application;
import android.content.Context;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DaggerApplicationComponent;
import com.alorma.diary.di.component.DaggerDataComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.module.ApplicationModule;
import com.alorma.diary.di.module.DataModule;
import com.pacoworks.rxpaper2.RxPaperBook;

public class DiaryApplication extends Application {

  protected ApplicationComponent applicationComponent;
  private DataComponent dataComponent;

  public static DiaryApplication get(Context context) {
    return (DiaryApplication) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();

    RxPaperBook.init(this);

    ApplicationModule applicationModule = new ApplicationModule(this);

    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(applicationModule)
        .build();

    dataComponent = DaggerDataComponent
        .builder()
        .applicationModule(applicationModule)
        .dataModule(new DataModule())
        .build();

    applicationComponent.inject(this);
  }

  public ApplicationComponent getComponent() {
    return applicationComponent;
  }

  public DataComponent getDataComponent() {
    return dataComponent;
  }
}

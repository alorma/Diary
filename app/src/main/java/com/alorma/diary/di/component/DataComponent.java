package com.alorma.diary.di.component;

import com.alorma.diary.MainActivity;
import com.alorma.diary.di.module.ApplicationModule;
import com.alorma.diary.di.module.DataModule;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.NetScheduler;
import dagger.Component;
import io.reactivex.Scheduler;

@Component(modules = {ApplicationModule.class, DataModule.class})
public interface DataComponent {

  void inject(MainActivity mainActivity);

  @MainScheduler
  Scheduler provideMainScheduler();

  @NetScheduler
  Scheduler provideNetScheduler();
}

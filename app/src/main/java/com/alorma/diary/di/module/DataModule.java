package com.alorma.diary.di.module;

import com.alorma.diary.di.qualifiers.ComputationScheduler;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.NetScheduler;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class DataModule {

  @Provides
  @NetScheduler
  public Scheduler provideNetScheduler() {
    return Schedulers.io();
  }

  @Provides
  @ComputationScheduler
  public Scheduler provideComputationScheduler() {
    return Schedulers.computation();
  }

  @Provides
  @MainScheduler
  public Scheduler provideMainScheduler() {
    return AndroidSchedulers.mainThread();
  }
}

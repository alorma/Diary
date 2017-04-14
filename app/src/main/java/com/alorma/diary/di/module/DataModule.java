package com.alorma.diary.di.module;

import android.content.Context;
import com.alorma.diary.data.AndroidPreferenceWrapper;
import com.alorma.diary.data.AndroidSettingsManager;
import com.alorma.diary.data.PreferenceWrapper;
import com.alorma.diary.data.SettingsManager;
import com.alorma.diary.data.Validator;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.di.qualifiers.ApplicationContext;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.NetScheduler;
import com.alorma.diary.di.qualifiers.user.UserValidator;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Completable;
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

  @Provides
  PreferenceWrapper providePreferenceWrapper(@ApplicationContext Context context) {
    return new AndroidPreferenceWrapper(context);
  }

  @Provides
  public SettingsManager getSettingsManager(PreferenceWrapper preferenceWrapper) {
    return new AndroidSettingsManager(preferenceWrapper);
  }

  @UserValidator
  @Provides
  Validator<ContactListItemModel> getUserValidator() {
    return contactListItemModel -> true;
  }
}

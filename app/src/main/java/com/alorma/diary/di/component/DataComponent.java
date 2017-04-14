package com.alorma.diary.di.component;

import com.alorma.diary.data.PreferenceWrapper;
import com.alorma.diary.data.SettingsManager;
import com.alorma.diary.data.Validator;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.di.module.ApplicationModule;
import com.alorma.diary.di.module.DataModule;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.NetScheduler;
import com.alorma.diary.di.qualifiers.user.UserValidator;
import com.alorma.diary.ui.activity.MainActivity;
import dagger.Component;
import io.reactivex.Scheduler;

@Component(modules = { ApplicationModule.class, DataModule.class })
public interface DataComponent {

  void inject(MainActivity mainActivity);

  @MainScheduler
  Scheduler provideMainScheduler();

  @NetScheduler
  Scheduler provideNetScheduler();

  @ComputationScheduler
  Scheduler provideComputationScheduler();

  SettingsManager provideSettingsManager();

  PreferenceWrapper providePreferenceWrapper();

  @UserValidator
  Validator<ContactListItemModel> userValidator();
}

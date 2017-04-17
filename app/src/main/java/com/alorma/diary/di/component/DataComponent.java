package com.alorma.diary.di.component;

import com.alorma.diary.data.PreferenceWrapper;
import com.alorma.diary.data.SettingsManager;
import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.agent.DiaryMapper;
import com.alorma.diary.data.diary.agent.EntryMapper;
import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.module.ApplicationModule;
import com.alorma.diary.di.module.DataModule;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.IoScheduler;
import com.alorma.diary.di.qualifiers.user.UserValidator;
import com.alorma.diary.ui.activity.MainActivity;
import com.pacoworks.rxpaper2.RxPaperBook;
import dagger.Component;
import io.reactivex.Scheduler;

@Component(modules = { ApplicationModule.class, DataModule.class })
public interface DataComponent {

  void inject(MainActivity mainActivity);

  @MainScheduler
  Scheduler provideMainScheduler();

  @IoScheduler
  Scheduler provideNetScheduler();

  @ComputationScheduler
  Scheduler provideComputationScheduler();

  SettingsManager provideSettingsManager();

  PreferenceWrapper providePreferenceWrapper();

  @UserValidator
  Validator<ContactItemModel> provideUserValidator();

  Validator<DiaryListItemCreator> provideDiaryValidator();

  Validator<EntryItemModel> provideEntryValidator();

  DiaryMapper provideDiaryMapper();

  EntryMapper provideEntryMapper();

  @Cache
  DiaryListDataSource provideDiaryDataSource();
}

package com.alorma.diary.di.module;

import android.content.Context;
import com.alorma.diary.data.AndroidPreferenceWrapper;
import com.alorma.diary.data.AndroidSettingsManager;
import com.alorma.diary.data.PreferenceWrapper;
import com.alorma.diary.data.SettingsManager;
import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.agent.DiaryMapper;
import com.alorma.diary.data.diary.agent.EntryMapper;
import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.diary.ds.PaperBookDiaryDataSource;
import com.alorma.diary.data.diary.validator.DiaryNewItemValidator;
import com.alorma.diary.data.diary.validator.EntryItemValidator;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.data.user.validator.UserNameMandatoryValidator;
import com.alorma.diary.di.qualifiers.ApplicationContext;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import com.alorma.diary.di.qualifiers.IoScheduler;
import com.alorma.diary.di.qualifiers.MainScheduler;
import com.alorma.diary.di.qualifiers.user.UserValidator;
import com.pacoworks.rxpaper2.RxPaperBook;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class DataModule {

  @Provides
  @IoScheduler
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
  Validator<ContactItemModel> getUserValidator(@ComputationScheduler Scheduler scheduler) {
    return new UserNameMandatoryValidator(scheduler);
  }

  @Provides
  Validator<DiaryListItemCreator> getDiaryCreatorValidator(@ComputationScheduler Scheduler scheduler) {
    return new DiaryNewItemValidator(scheduler);
  }

  @Provides
  Validator<EntryItemModel> getEntryValidator(@ComputationScheduler Scheduler scheduler) {
    return new EntryItemValidator(scheduler);
  }

  @Provides
  DiaryMapper getDiaryMapper() {
    return new DiaryMapper();
  }

  @Provides
  EntryMapper getEntryMapper() {
    return new EntryMapper();
  }

  @Provides
  RxPaperBook providesPaperBook() {
    return RxPaperBook.with(Schedulers.io());
  }

  @Provides
  @Cache DiaryListDataSource provideDataSource(RxPaperBook paperBook) {
    return new PaperBookDiaryDataSource(paperBook);
  }
}

package com.alorma.diary.data.diary.agent;

import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;

public class EntryAgent {

  private DiaryListDataSource dataSource;
  private EntryMapper entryMapper;
  private Scheduler workScheduler;

  @Inject
  public EntryAgent(@Cache DiaryListDataSource dataSource, EntryMapper entryMapper,
      @ComputationScheduler Scheduler workScheduler) {
    this.dataSource = dataSource;
    this.entryMapper = entryMapper;
    this.workScheduler = workScheduler;
  }

  public Completable addEntry(long diaryId, EntryItemModel model) {
    return Single.just(model)
        .map(entryMapper.mapEntryItemModel())
        .flatMapCompletable(entry -> dataSource.addEntry(diaryId, entry))
        .subscribeOn(workScheduler);
  }
}

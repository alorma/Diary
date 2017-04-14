package com.alorma.diary.data.diary.agent;

import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class DiariesAgent {

  private DiaryListDataSource dataSource;
  private Scheduler workScheduler;

  @Inject
  public DiariesAgent(@Cache DiaryListDataSource dataSource, @ComputationScheduler Scheduler workScheduler) {
    this.dataSource = dataSource;
    this.workScheduler = workScheduler;
  }

  public Flowable<DiaryListItemModel> getDiaries() {
    return dataSource.getDiaries().subscribeOn(workScheduler);
  }

  public Completable addDiary(DiaryListItemModel model) {
    return dataSource.addDiary(model);
  }
}
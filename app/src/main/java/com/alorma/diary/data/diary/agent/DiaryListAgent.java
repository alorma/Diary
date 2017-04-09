package com.alorma.diary.data.diary.agent;

import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.model.DiaryListItem;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class DiaryListAgent {

  private DiaryListDataSource dataSource;
  private Scheduler workScheduler;

  @Inject
  public DiaryListAgent(@Cache DiaryListDataSource dataSource, @ComputationScheduler Scheduler workScheduler) {
    this.dataSource = dataSource;
    this.workScheduler = workScheduler;
  }

  public Flowable<DiaryListItem> getDiaries() {
    return dataSource.getDiaries().subscribeOn(workScheduler);
  }
}

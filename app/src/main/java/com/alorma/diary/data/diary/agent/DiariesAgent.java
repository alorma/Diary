package com.alorma.diary.data.diary.agent;

import com.alorma.diary.data.diary.ds.DiaryListDataSource;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.di.qualifiers.Cache;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;

public class DiariesAgent {

  private DiaryListDataSource dataSource;
  private DiaryMapper diaryMapper;
  private Scheduler workScheduler;

  @Inject
  public DiariesAgent(@Cache DiaryListDataSource dataSource, DiaryMapper diaryMapper, @ComputationScheduler Scheduler workScheduler) {
    this.dataSource = dataSource;
    this.diaryMapper = diaryMapper;
    this.workScheduler = workScheduler;
  }

  public Flowable<DiaryListItemModel> getDiaries() {
    return dataSource.getDiaries().map(diaryMapper.mapDiary()).subscribeOn(workScheduler);
  }

  public Completable addDiary(DiaryListItemModel model) {
    return Single.just(model)
        .map(diaryMapper.mapDiaryItemModel())
        .flatMapCompletable(diary -> dataSource.addDiary(diary));
  }
}

package com.alorma.diary.data.diary.ds;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface DiaryListDataSource {
  Flowable<Diary> getDiaries();

  Completable addDiary(Diary model);
}

package com.alorma.diary.data.diary.ds;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DiaryListDataSource {
  Flowable<Diary> getDiaries();

  Completable addDiary(Diary model);

  Single<Diary> getDiary(int id);

  Completable addEntry(int diaryId, Entry entry);
}

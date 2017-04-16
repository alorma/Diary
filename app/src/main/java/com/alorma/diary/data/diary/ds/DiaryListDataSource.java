package com.alorma.diary.data.diary.ds;

import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.diary.dbmodel.Entry;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DiaryListDataSource {
  Flowable<Diary> getDiaries();

  Completable addDiary(Diary model);

  Single<Diary> getDiary(int id);

  Completable addEntry(long diaryId, Entry entry);
}

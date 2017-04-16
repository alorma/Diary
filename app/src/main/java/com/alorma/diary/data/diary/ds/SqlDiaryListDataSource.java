package com.alorma.diary.data.diary.ds;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.inquiry.Query;
import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.diary.dbmodel.Entry;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class SqlDiaryListDataSource implements DiaryListDataSource {
  private Inquiry inquiry;

  public SqlDiaryListDataSource(Inquiry inquiry) {
    this.inquiry = inquiry;
  }

  @Override
  public Flowable<Diary> getDiaries() {
    return Flowable.fromCallable(() -> inquiry.select(Diary.class))
        .map(Query::all)
        .flatMap(Flowable::fromArray);
  }

  @Override
  public Completable addDiary(final Diary model) {
    return Completable.defer(() -> {
      Diary[] diaries = { model };
      Long[] run = inquiry.insert(Diary.class).values(diaries).run();
      if (run != null) {
        return Completable.complete();
      }
      return Completable.error(new DiaryNotAddedException());
    });
  }

  @Override
  public Single<Diary> getDiary(int id) {
    return Single.fromCallable(() -> inquiry.select(Diary.class).atPosition(id).first());
  }

  @Override
  public Completable addEntry(long diaryId, Entry entry) {
    return Completable.error(new Exception());
  }
}

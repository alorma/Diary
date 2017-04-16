package com.alorma.diary.data.diary.ds;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.inquiry.Query;
import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.diary.dbmodel.Entry;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import com.alorma.diary.data.exception.EntryNotAddedException;
import com.alorma.diary.di.qualifiers.IoScheduler;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class SqlDiaryListDataSource implements DiaryListDataSource {
  private Inquiry inquiry;
  private Scheduler scheduler;

  public SqlDiaryListDataSource(Inquiry inquiry, @IoScheduler Scheduler scheduler) {
    this.inquiry = inquiry;
    this.scheduler = scheduler;
  }

  @Override
  public Flowable<Diary> getDiaries() {
    return Flowable.fromCallable(() -> inquiry.select(Diary.class))
        .map(Query::all)
        .flatMap(Flowable::fromArray).subscribeOn(scheduler);
  }

  @Override
  public Single<Diary> addDiary(Diary model) {
    return Single.just(model).map(this::addDiarySingle).subscribeOn(scheduler);
  }

  private Diary addDiarySingle(Diary diary) throws DiaryNotAddedException {
    Diary[] diaries = { diary };
    Long[] insertedIds = inquiry.insert(Diary.class).values(diaries).run();
    if (insertedIds == null || insertedIds.length == 0) {
      throw new DiaryNotAddedException();
    }
    return diary;
  }

  @Override
  public Single<Diary> getDiary(long id) {
    return Single.fromCallable(() -> inquiry.select(Diary.class).where("_id = ?", id).first()).subscribeOn(scheduler);
  }

  @Override
  public Completable addEntry(long diaryId, Entry entry) {
    return Completable.defer(() -> {
      entry.diaryId = diaryId;
      Entry[] entries = new Entry[] { entry };
      Long[] run = inquiry.insert(Entry.class).values(entries).run();
      if (run == null || run.length == 0) {
        return Completable.error(new EntryNotAddedException());
      }
      return Completable.complete();
    }).subscribeOn(scheduler);
  }
}

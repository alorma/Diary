package com.alorma.diary.data.diary.ds;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.inquiry.Query;
import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.diary.dbmodel.Entry;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import com.alorma.diary.di.qualifiers.IoScheduler;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.util.UUID;

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
    return Single.just(model).map(this::addDiarySingle).flatMap(this::getDiary).subscribeOn(scheduler);
  }

  private Long addDiarySingle(Diary diary) throws DiaryNotAddedException {
    diary.id = UUID.randomUUID().hashCode();
    diary.user.id = UUID.randomUUID().hashCode();
    Diary[] diaries = { diary };
    Long[] insertedIds = inquiry.insert(Diary.class).values(diaries).run();
    if (insertedIds != null) {
      return insertedIds[0];
    }
    throw new DiaryNotAddedException();
  }

  @Override
  public Single<Diary> getDiary(long id) {
    return Single.fromCallable(() -> inquiry.select(Diary.class).where("_id = ?", id).first()).subscribeOn(scheduler);
  }

  @Override
  public Completable addEntry(long diaryId, Entry entry) {
    return Completable.error(new Exception()).subscribeOn(scheduler);
  }
}

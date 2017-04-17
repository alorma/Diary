package com.alorma.diary.data.diary.ds;

import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.diary.dbmodel.Entry;
import com.pacoworks.rxpaper2.RxPaperBook;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class PaperBookDiaryDataSource implements DiaryListDataSource {

  private RxPaperBook paperBook;

  public PaperBookDiaryDataSource(RxPaperBook paperBook) {
    this.paperBook = paperBook;
  }

  @Override
  public Flowable<Diary> getDiaries() {
    return paperBook.read("diaries", new HashMap<UUID, Diary>())
        .flattenAsFlowable(HashMap::values);
  }

  @Override
  public Single<Diary> addDiary(Diary model) {
    return paperBook.read("diaries", new HashMap<UUID, Diary>()).flatMapCompletable(diaries -> {
      diaries.put(model.getId(), model);
      return paperBook.write("diaries", diaries);
    }).toSingleDefault(model);
  }

  @Override
  public Single<Diary> getDiary(UUID id) {
    return paperBook.read("diaries", new HashMap<UUID, Diary>()).map(uuidDiaryHashMap -> uuidDiaryHashMap.get(id));
  }

  @Override
  public Completable addEntry(UUID diaryId, Entry entry) {
    return getDiary(diaryId).map(diary -> {
      if (diary.entries == null) {
        diary.entries = new LinkedList<>();
      }
      diary.entries.add(entry);
      return diary;
    }).flatMap(this::addDiary)
        .toCompletable();
  }
}

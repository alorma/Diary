package com.alorma.diary.data.diary.ds;

import android.support.annotation.NonNull;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import com.alorma.diary.data.exception.DiaryNotFoundException;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class MemoryDiaryListDataSource implements DiaryListDataSource {

  private final Map<Integer, Diary> map;

  @Inject
  public MemoryDiaryListDataSource() {
    map = new LinkedHashMap<>();

    Diary Diary1 = createItem1();
    map.put(Diary1.getId(), Diary1);
  }

  @NonNull
  private Diary createItem1() {
    List<String> comments = new ArrayList<>();
    comments.add("Comment #1");
    comments.add("Comment #2");
    comments.add("Comment #3");

    Contact contact = new Contact();
    contact.setName("Bernat");
    contact.setPhone("+34672214312");

    contact.setComments(comments);

    Entry entry1 = new Entry();
    entry1.setSubject("Title of entry");
    entry1.setContent("Lorem ipsum dolor est");
    entry1.setDate(new Date(System.currentTimeMillis()));

    Entry entry2 = new Entry();
    entry2.setSubject("Title of entry 2");
    entry2.setContent("Lorem ipsum dolor est 2");
    entry2.setDate(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)));

    Diary diary = new Diary();
    diary.setId(new Random().nextInt());
    diary.setContact(contact);
    diary.setEntries(Arrays.asList(entry1, entry2));
    return diary;
  }

  @Override
  public Flowable<Diary> getDiaries() {
    return Flowable.fromIterable(map.values());
  }

  @Override
  public Completable addDiary(Diary model) {
    return Completable.defer(() -> map.put(model.getId(), model) == null
        ? Completable.complete()
        : Completable.error(new DiaryNotAddedException()));
  }

  @Override
  public Single<Diary> getDiary(int id) {
    return Single.fromCallable(() -> map.get(id));
  }

  @Override
  public Completable addEntry(int diaryId, Entry entry) {
    return Completable.defer(() -> {
      if (!map.containsKey(diaryId)) {
        return Completable.error(new DiaryNotFoundException());
      }
      Diary diary = map.get(diaryId);
      List<Entry> entries = diary.getEntries();
      if (entries == null) {
        entries = new LinkedList<>();
      }
      entries.add(entry);
      diary.setEntries(entries);
      return Completable.complete();
    });
  }
}

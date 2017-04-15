package com.alorma.diary.data.diary.ds;

import android.support.annotation.NonNull;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

    Entry entry = new Entry();
    entry.setSubject("Title of entry");
    entry.setContent("Lorem ipsum dolor est");
    entry.setDate(new Date(System.currentTimeMillis()));

    Diary diary = new Diary();
    diary.setId(new Random().nextInt());
    diary.setContact(contact);
    diary.setEntries(Collections.singletonList(entry));
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
}

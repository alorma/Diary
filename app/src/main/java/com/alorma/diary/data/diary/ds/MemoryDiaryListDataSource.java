package com.alorma.diary.data.diary.ds;

import android.support.annotation.NonNull;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;

public class MemoryDiaryListDataSource implements DiaryListDataSource {

  private final Map<Integer, DiaryListItemModel> map;

  @Inject
  public MemoryDiaryListDataSource() {
    map = new LinkedHashMap<>();

    DiaryListItemModel diaryListItemModel1 = createItem1();
    map.put(diaryListItemModel1.getId(), diaryListItemModel1);
  }

  @NonNull
  private DiaryListItemModel createItem1() {
    List<String> comments = new ArrayList<>();
    comments.add("Comment #1");
    comments.add("Comment #2");
    comments.add("Comment #3");

    ContactListItemModel contact = new ContactListItemModel();
    contact.setName("Bernat");
    contact.setPhone("+34672214312");

    contact.setComments(comments);

    EntryItemModel entry = new EntryItemModel();
    entry.setSubject("Title of entry");
    entry.setContent("Lorem ipsum dolor est");
    entry.setPostedDate(System.currentTimeMillis());

    DiaryListItemModel diaryListItemModel = new DiaryListItemModel();
    diaryListItemModel.setId(new Random().nextInt());
    diaryListItemModel.setContact(contact);
    diaryListItemModel.setLastEntry(entry);
    return diaryListItemModel;
  }

  @Override
  public Flowable<DiaryListItemModel> getDiaries() {
    return Flowable.fromIterable(map.values());
  }

  @Override
  public Completable addDiary(DiaryListItemModel model) {
    return Completable.defer(() -> map.put(model.getId(), model) == null
        ? Completable.complete()
        : Completable.error(new DiaryNotAddedException()));
  }
}

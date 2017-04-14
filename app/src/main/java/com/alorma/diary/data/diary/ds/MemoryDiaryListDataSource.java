package com.alorma.diary.data.diary.ds;

import android.support.annotation.NonNull;
import com.alorma.diary.data.exception.DiaryNotAddedException;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MemoryDiaryListDataSource implements DiaryListDataSource {

  private final List<DiaryListItemModel> list;

  @Inject
  public MemoryDiaryListDataSource() {
    list = new ArrayList<>();

    DiaryListItemModel diaryListItemModel1 = createItem1();
    list.add(diaryListItemModel1);
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
    diaryListItemModel.setContact(contact);
    diaryListItemModel.setLastEntry(entry);
    return diaryListItemModel;
  }

  @Override
  public Flowable<DiaryListItemModel> getDiaries() {
    return Flowable.fromIterable(list);
  }

  @Override
  public Completable addDiary(DiaryListItemModel model) {
    return Completable.defer(() -> list.add(model)
        ? Completable.complete()
        : Completable.error(new DiaryNotAddedException()));
  }
}

package com.alorma.diary.data.diary.ds;

import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemModel;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MemoryDiaryListDataSource implements DiaryListDataSource {

  private final List<DiaryListItemModel> list;

  @Inject
  public MemoryDiaryListDataSource() {
    list = new ArrayList<>();

    List<String> comments = new ArrayList<>();
    comments.add("Comment #1");
    comments.add("Comment #2");
    comments.add("Comment #3");

    ContactListItemModel contact = new ContactListItemModel();
    contact.setName("Bernat");
    contact.setPhone("+34672214312");

    contact.setComments(comments);

    DiaryListItemModel diaryListItemModel = new DiaryListItemModel();
    diaryListItemModel.setContact(contact);

    list.add(diaryListItemModel);
  }

  @Override
  public Flowable<DiaryListItemModel> getDiaries() {
    return Flowable.fromIterable(list);
  }
}

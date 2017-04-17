package com.alorma.diary.data.diary.agent;

import android.net.Uri;
import com.alorma.diary.data.diary.dbmodel.User;
import com.alorma.diary.data.diary.dbmodel.Diary;
import com.alorma.diary.data.diary.dbmodel.Entry;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class DiaryMapper {

  private static final Comparator<? super Entry> SORT_DATE =
      (Comparator<Entry>) (o1, o2) -> {
        if (o1.getDate() == null || o2.getDate() == null) {
          return 0;
        }
        return o2.getDate().compareTo(o1.getDate());
      };

  public Function<Diary, DiaryItemModel> mapDiary() {
    return new DiaryMap();
  }

  public Function<DiaryListItemCreator, Diary> mapDiaryItemModel() {
    return new DiaryItemModelMap();
  }

  private class DiaryMap implements Function<Diary, DiaryItemModel> {

    @Override
    public DiaryItemModel apply(Diary diary) throws Exception {
      DiaryItemModel diaryItemModel = new DiaryItemModel();
      diaryItemModel.setId(diary.getId());
      diaryItemModel.setName(diary.getName());
      mapContact(diaryItemModel, diary.getUser());
      mapEntries(diaryItemModel, diary.getEntries());
      return diaryItemModel;
    }

    private void mapContact(DiaryItemModel model, User user) {
      if (user != null) {
        ContactItemModel contactModel = new ContactItemModel();
        contactModel.setName(user.getName());
        contactModel.setPhone(user.getPhone());

        String picture = user.getPicture();
        if (picture != null) {
          contactModel.setPicture(Uri.parse(picture));
        }
        contactModel.setComments(user.getComments());
        model.setContact(contactModel);
      }
    }

    private void mapEntries(DiaryItemModel model, List<Entry> entries) {
      if (entries != null && !entries.isEmpty()) {
        List<Entry> list = new ArrayList<>(entries);
        Collections.sort(list, SORT_DATE);
        model.setLastEntry(mapEntry(list.get(0)));

        List<EntryItemModel> entryItemModels = new LinkedList<>();
        for (Entry entry : list) {
          entryItemModels.add(mapEntry(entry));
        }
        model.setEntries(entryItemModels);
      }
    }

    private EntryItemModel mapEntry(Entry entry) {
      EntryItemModel model = new EntryItemModel();
      model.setSubject(entry.getSubject());
      model.setContent(entry.getContent());
      Date date = entry.getDate();
      if (date != null) {
        model.setPostedDate(date.getTime());
      }
      return model;
    }
  }

  private class DiaryItemModelMap implements Function<DiaryListItemCreator, Diary> {

    @Override
    public Diary apply(DiaryListItemCreator creator) throws Exception {
      Diary diary = new Diary();
      if (creator.getId() == null) {
        diary.setId(UUID.randomUUID());
      }
      diary.setName(creator.getName());
      mapContact(diary, creator.getContact());
      return diary;
    }

    private void mapContact(Diary diary, ContactItemModel contactItemModel) {
      if (contactItemModel != null) {
        User user = new User();
        user.setName(contactItemModel.getName());
        contactItemModel.getPhone().ifSome(user::setPhone);
        contactItemModel.getPicture().map(Uri::toString).ifSome(user::setPicture);
        contactItemModel.getComments().ifSome(user::setComments);
        diary.setUser(user);
      }
    }
  }
}

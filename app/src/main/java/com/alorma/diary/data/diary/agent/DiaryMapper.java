package com.alorma.diary.data.diary.agent;

import android.net.Uri;
import com.alorma.diary.data.diary.ds.Contact;
import com.alorma.diary.data.diary.ds.Diary;
import com.alorma.diary.data.diary.ds.Entry;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DiaryMapper {

  private static final Comparator<? super Entry> SORT_DATE =
      (Comparator<Entry>) (o1, o2) -> {
        if (o1.getDate() == null || o2.getDate() == null) {
          return 0;
        }
        return o2.getDate().compareTo(o1.getDate());
      };

  private class DiaryMap implements Function<Diary, DiaryListItemModel> {

    @Override
    public DiaryListItemModel apply(Diary diary) throws Exception {
      DiaryListItemModel diaryListItemModel = new DiaryListItemModel();
      diaryListItemModel.setId(diary.getId());
      mapContact(diaryListItemModel, diary.getContact());
      mapEntries(diaryListItemModel, diary.getEntries());
      return diaryListItemModel;
    }

    private void mapContact(DiaryListItemModel model, Contact contact) {
      if (contact != null) {
        ContactItemModel contactModel = new ContactItemModel();
        contactModel.setName(contact.getName());
        contactModel.setPhone(contact.getPhone());

        String picture = contact.getPicture();
        if (picture != null) {
          contactModel.setPicture(Uri.parse(picture));
        }
        contactModel.setComments(contact.getComments());
        model.setContact(contactModel);
      }
    }

    private void mapEntries(DiaryListItemModel model, List<Entry> entries) {
      if (entries != null && !entries.isEmpty()) {
        List<Entry> list = new ArrayList<>(entries);
        Collections.sort(list, SORT_DATE);
        model.setLastEntry(mapEntry(list.get(0)));
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

  private class DiaryItemModelMap implements Function<DiaryListItemModel, Diary> {

    @Override
    public Diary apply(DiaryListItemModel itemModel) throws Exception {
      return new Diary();
    }
  }

  public Function<Diary, DiaryListItemModel> mapDiary() {
    return new DiaryMap();
  }

  public Function<DiaryListItemModel, Diary> mapDiaryItemModel() {
    return new DiaryItemModelMap();
  }
}

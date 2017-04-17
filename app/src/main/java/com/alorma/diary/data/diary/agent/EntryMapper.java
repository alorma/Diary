package com.alorma.diary.data.diary.agent;

import com.alorma.diary.data.diary.dbmodel.Entry;
import com.alorma.diary.data.diary.dbmodel.EntryType;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.data.model.EntryMessageItemModel;
import com.alorma.diary.data.model.EntryPhotoItemModel;
import io.reactivex.functions.Function;
import java.util.Date;

public class EntryMapper {
  public Function<EntryItemModel, Entry> mapEntryItemModel() {
    return entryItemModel -> {
      Entry entry = new Entry();
      if (entryItemModel.getEntryType() == EntryType.MESSAGE) {
        EntryMessageItemModel messageItemModel = (EntryMessageItemModel) entryItemModel;
        entry.setContent(messageItemModel.getContent());
      } else if (entryItemModel.getEntryType() == EntryType.PHOTO) {
        EntryPhotoItemModel photoItemModel = (EntryPhotoItemModel) entryItemModel;
        photoItemModel.getPhotoDescription().ifSome(entry::setPhotoDescription);
        photoItemModel.getPhotoName().ifSome(entry::setPhotoName);
        entry.setPhotoUri(photoItemModel.getUri().toString());
      }

      entry.setDate(new Date(entryItemModel.getPostedDate()));
      entryItemModel.getSubject().ifSome(entry::setSubject);
      entry.setEntryType(entryItemModel.getEntryType());
      return entry;
    };
  }
}

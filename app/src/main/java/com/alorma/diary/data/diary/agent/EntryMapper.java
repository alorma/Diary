package com.alorma.diary.data.diary.agent;

import com.alorma.diary.data.diary.dbmodel.Entry;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.functions.Function;
import java.util.Date;

public class EntryMapper {
  public Function<EntryItemModel, Entry> mapEntryItemModel() {
    return entryItemModel -> {
      Entry entry = new Entry();
      entry.setContent(entryItemModel.getContent());
      entry.setDate(new Date(entryItemModel.getPostedDate()));
      entryItemModel.getSubject().ifSome(entry::setSubject);
      return entry;
    };
  }
}

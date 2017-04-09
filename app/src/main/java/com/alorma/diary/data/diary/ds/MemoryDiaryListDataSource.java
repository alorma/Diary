package com.alorma.diary.data.diary.ds;

import com.alorma.diary.data.model.DiaryListItem;
import com.alorma.diary.di.qualifiers.Cache;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MemoryDiaryListDataSource implements DiaryListDataSource {

  private final List<DiaryListItem> list;

  @Inject
  public MemoryDiaryListDataSource() {
    list = new ArrayList<>();
    list.add(new DiaryListItem());
    list.add(new DiaryListItem());
    list.add(new DiaryListItem());
    list.add(new DiaryListItem());
  }

  @Override
  public Flowable<DiaryListItem> getDiaries() {
    return Flowable.fromIterable(list);
  }
}

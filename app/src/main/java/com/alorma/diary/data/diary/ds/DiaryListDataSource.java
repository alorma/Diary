package com.alorma.diary.data.diary.ds;

import com.alorma.diary.data.model.DiaryListItem;
import io.reactivex.Flowable;

public interface DiaryListDataSource {
  Flowable<DiaryListItem> getDiaries();
}

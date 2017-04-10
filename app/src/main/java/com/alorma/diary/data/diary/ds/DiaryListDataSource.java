package com.alorma.diary.data.diary.ds;

import com.alorma.diary.data.model.DiaryListItemModel;
import io.reactivex.Flowable;

public interface DiaryListDataSource {
  Flowable<DiaryListItemModel> getDiaries();
}

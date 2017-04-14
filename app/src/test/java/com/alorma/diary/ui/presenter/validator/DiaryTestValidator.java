package com.alorma.diary.ui.presenter.validator;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.model.DiaryListItemCreator;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class DiaryTestValidator implements Validator<DiaryListItemCreator> {
  @Override
  public Completable validate(DiaryListItemCreator creator) {
    return Completable.complete().subscribeOn(Schedulers.trampoline());
  }
}

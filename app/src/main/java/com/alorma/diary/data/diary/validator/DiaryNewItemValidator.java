package com.alorma.diary.data.diary.validator;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.exception.DiaryValidationContactException;
import com.alorma.diary.data.exception.DiaryValidationIdException;
import com.alorma.diary.data.exception.ValidationException;
import com.alorma.diary.data.model.DiaryListItemCreator;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class DiaryNewItemValidator implements Validator<DiaryListItemCreator> {
  private Scheduler scheduler;

  public DiaryNewItemValidator(Scheduler scheduler) {

    this.scheduler = scheduler;
  }

  @Override
  public Completable validate(DiaryListItemCreator creator) {
    return Completable.fromCallable(() -> {
      if (creator == null) {
        return Completable.error(new ValidationException());
      } else if (creator.getId() == null) {
        return Completable.error(new DiaryValidationIdException());
      } else if (creator.getContact() == null) {
        return Completable.error(new DiaryValidationContactException());
      }
      return Completable.complete();
    }).subscribeOn(scheduler);
  }
}

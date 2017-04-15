package com.alorma.diary.data.diary.validator;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.exception.EntryValidationContectException;
import com.alorma.diary.data.model.EntryItemModel;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class EntryItemValidator implements Validator<EntryItemModel> {
  private Scheduler scheduler;

  public EntryItemValidator(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  @Override
  public Completable validate(EntryItemModel entryItemModel) {
    return Completable.defer(() -> {
      if (entryItemModel.getContent() == null) {
        return Completable.error(new EntryValidationContectException());
      }
      return Completable.complete();
    }).subscribeOn(scheduler);
  }
}

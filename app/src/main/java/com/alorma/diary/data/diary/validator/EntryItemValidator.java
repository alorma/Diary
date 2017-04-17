package com.alorma.diary.data.diary.validator;

import com.alorma.diary.data.EntryNotValidException;
import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.dbmodel.EntryType;
import com.alorma.diary.data.exception.EntryValidationContentException;
import com.alorma.diary.data.exception.EntryValidationUriException;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.data.model.EntryMessageItemModel;
import com.alorma.diary.data.model.EntryPhotoItemModel;
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
      if (entryItemModel.getEntryType() == EntryType.MESSAGE) {
        if (((EntryMessageItemModel) entryItemModel).getContent() == null) {
          return Completable.error(new EntryValidationContentException());
        } else {
          return Completable.complete();
        }
      } else if (entryItemModel.getEntryType() == EntryType.PHOTO) {
        if (((EntryPhotoItemModel) entryItemModel).getUri() == null) {
          return Completable.error(new EntryValidationUriException());
        } else {
          return Completable.complete();
        }
      }
      return Completable.error(new EntryNotValidException());
    }).subscribeOn(scheduler);
  }
}

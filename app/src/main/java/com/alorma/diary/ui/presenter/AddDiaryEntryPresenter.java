package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.entry.AddDiaryEntryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class AddDiaryEntryPresenter {

  private AddDiaryEntryUseCase addDiaryEntryUseCase;
  private Validator<EntryItemModel> entryItemModelValidator;
  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private Screen screen;

  @Inject
  public AddDiaryEntryPresenter(AddDiaryEntryUseCase addDiaryEntryUseCase,
      Validator<EntryItemModel> entryItemModelValidator,
      @MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {
    this.addDiaryEntryUseCase = addDiaryEntryUseCase;
    this.entryItemModelValidator = entryItemModelValidator;

    this.mainScheduler = mainScheduler;
    this.errorTracker = errorTracker;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  public void addEntry(int diaryId, EntryItemModel entry) {
    validate(entry).toSingleDefault(entry)
        .flatMapCompletable(entryItemModel -> addDiaryEntryUseCase.getDiary(diaryId, entryItemModel))
        .observeOn(mainScheduler)
        .subscribe(() -> {
          getScreen().showEntryAdded();
        }, throwable -> {
          getScreen().showErrorAddEntry();
          errorTracker.trackError(throwable);
        });
  }

  private Completable validate(EntryItemModel entry) {
    return entryItemModelValidator.validate(entry);
  }

  public interface Screen {
    void showEntryAdded();

    void showErrorAddEntry();

    class Null implements Screen {

      @Override
      public void showEntryAdded() {

      }

      @Override
      public void showErrorAddEntry() {

      }
    }
  }
}

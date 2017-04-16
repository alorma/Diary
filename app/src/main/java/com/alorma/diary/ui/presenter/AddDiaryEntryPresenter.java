package com.alorma.diary.ui.presenter;

import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.entry.AddDiaryEntryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class AddDiaryEntryPresenter {

  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private AddDiaryEntryUseCase addDiaryEntryUseCase;
  private Validator<EntryItemModel> entryItemModelValidator;
  private ResourceLifeCycle resourceLifeCycle;
  private Screen screen;

  @Inject
  public AddDiaryEntryPresenter(AddDiaryEntryUseCase addDiaryEntryUseCase,
      Validator<EntryItemModel> entryItemModelValidator,
      @MainScheduler Scheduler mainScheduler,
      ResourceLifeCycle resourceLifeCycle,
      ErrorTracker errorTracker) {
    this.addDiaryEntryUseCase = addDiaryEntryUseCase;
    this.entryItemModelValidator = entryItemModelValidator;

    this.mainScheduler = mainScheduler;
    this.resourceLifeCycle = resourceLifeCycle;
    this.errorTracker = errorTracker;
  }

  public Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  public void destroy() {
    resourceLifeCycle.destroy();
  }

  public void addEntry(long diaryId, EntryItemModel entry) {
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

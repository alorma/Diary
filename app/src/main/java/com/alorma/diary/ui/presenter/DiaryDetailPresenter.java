package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.GetDiaryUseCase;
import com.alorma.diary.data.diary.entry.AddDiaryEntryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.exception.DiaryNotFoundException;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class DiaryDetailPresenter {

  private GetDiaryUseCase getDiaryUseCase;
  private AddDiaryEntryUseCase addDiaryEntryUseCase;
  private Validator<EntryItemModel> entryItemModelValidator;
  private final Scheduler mainScheduler;
  private final ErrorTracker errorTracker;
  private Screen screen;
  private DiaryItemModel diary;

  @Inject
  public DiaryDetailPresenter(GetDiaryUseCase getDiaryUseCase,
      AddDiaryEntryUseCase addDiaryEntryUseCase,
      Validator<EntryItemModel> entryItemModelValidator,
      @MainScheduler Scheduler mainScheduler,
      ErrorTracker errorTracker) {
    this.getDiaryUseCase = getDiaryUseCase;
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

  public void load(int id) {
    getDiaryUseCase.getDiary(id)
        .observeOn(mainScheduler)
        .doOnSubscribe(disposable -> getScreen().startLoading())
        .subscribe(this::onDiaryLoaded, this::loadDiaryError);
  }

  private void onDiaryLoaded(DiaryItemModel diaryItemModel) {
    diary = diaryItemModel;
    getScreen().stopLoading();
    getScreen().showDiary(diaryItemModel);
  }

  private void loadDiaryError(Throwable throwable) {
    getScreen().showError();
    errorTracker.trackError(throwable);
  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  public void addEntry(EntryItemModel entry) {
    validate(entry).toSingleDefault(entry)
        .flatMapCompletable(entryItemModel -> addDiaryEntryUseCase.getDiary(diary.getId(), entryItemModel))
        .observeOn(mainScheduler)
        .subscribe(() -> {
          getScreen().showEntryAdded();
        }, throwable -> {
          getScreen().showErrorAddEntry();
          errorTracker.trackError(throwable);
        });
  }

  private Completable validate(EntryItemModel entry) {
    Completable diary = Completable.defer(() -> {
      if (DiaryDetailPresenter.this.diary == null) {
        return Completable.error(new DiaryNotFoundException());
      }
      return Completable.complete();
    });
    Completable validate = entryItemModelValidator.validate(entry);
    return Completable.mergeArray(diary, validate);
  }

  public interface Screen {
    void startLoading();

    void showDiary(DiaryItemModel itemModel);

    void showEntryAdded();

    void stopLoading();

    void showError();

    void showErrorAddEntry();

    class Null implements Screen {

      @Override
      public void startLoading() {

      }

      @Override
      public void showDiary(DiaryItemModel itemModel) {

      }

      @Override
      public void showEntryAdded() {

      }

      @Override
      public void stopLoading() {

      }

      @Override
      public void showError() {

      }

      @Override
      public void showErrorAddEntry() {

      }
    }
  }
}

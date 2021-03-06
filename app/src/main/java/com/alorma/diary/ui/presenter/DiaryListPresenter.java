package com.alorma.diary.ui.presenter;

import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.data.diary.DiaryListUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Scheduler;
import javax.inject.Inject;
import org.reactivestreams.Subscription;

public class DiaryListPresenter {

  private DiaryListUseCase diaryListUseCase;
  private Scheduler mainScheduler;
  private ResourceLifeCycle resourceLifeCycle;
  private ErrorTracker errorTracker;
  private Screen screen;

  @Inject
  public DiaryListPresenter(DiaryListUseCase diaryListUseCase,
      @MainScheduler Scheduler mainScheduler,
      ResourceLifeCycle resourceLifeCycle,
      ErrorTracker errorTracker) {
    this.diaryListUseCase = diaryListUseCase;
    this.mainScheduler = mainScheduler;
    this.resourceLifeCycle = resourceLifeCycle;
    this.errorTracker = errorTracker;
  }

  private Screen getScreen() {
    return screen != null ? screen : new Screen.Null();
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public void load() {
    diaryListUseCase.getDiaries().doOnSubscribe(this::onStartLoading)
        .observeOn(mainScheduler)
        .subscribe(this::onItemLoaded, this::onErrorLoadingItem, this::onAllItemsLoaded);
  }

  private void onStartLoading(Subscription subscription) {
    getScreen().startRefresh();
    getScreen().clearItems();
  }

  private void onItemLoaded(DiaryItemModel diaryItemModel) {
    getScreen().addItemToScreen(diaryItemModel);
  }

  private void onErrorLoadingItem(Throwable throwable) {
    errorTracker.trackError(throwable);
    getScreen().showError();
  }

  private void onAllItemsLoaded() {
    getScreen().stopRefresh();
  }

  public void stop() {
    this.screen = new Screen.Null();
  }

  public void destroy() {
    resourceLifeCycle.destroy();
  }

  public void addNewItem() {
    getScreen().openNewAddScreen();
  }

  public interface Screen {
    void startRefresh();

    void clearItems();

    void addItemToScreen(DiaryItemModel item);

    void stopRefresh();

    void showError();

    void openNewAddScreen();

    class Null implements Screen {
      @Override
      public void startRefresh() {

      }

      @Override
      public void clearItems() {

      }

      @Override
      public void addItemToScreen(DiaryItemModel item) {

      }

      @Override
      public void stopRefresh() {

      }

      @Override
      public void showError() {

      }

      @Override
      public void openNewAddScreen() {

      }
    }
  }
}

package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.diary.DiaryListUseCase;
import com.alorma.diary.data.model.DiaryListItem;
import com.alorma.diary.di.qualifiers.MainScheduler;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class DiaryListPresenter {

  private DiaryListUseCase diaryListUseCase;
  private Scheduler mainScheduler;

  @Inject
  public DiaryListPresenter(DiaryListUseCase diaryListUseCase, @MainScheduler Scheduler mainScheduler) {
    this.diaryListUseCase = diaryListUseCase;
    this.mainScheduler = mainScheduler;
  }

  public void load() {
    diaryListUseCase.getDiaries().observeOn(mainScheduler)
        .subscribe(this::onItemLoaded, this::onErrorLoadingItem, this::onAllItemsLoaded);
  }

  private void onItemLoaded(DiaryListItem diaryListItem) {

  }

  private void onErrorLoadingItem(Throwable throwable) {

  }

  private void onAllItemsLoaded() {

  }
}

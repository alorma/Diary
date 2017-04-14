package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.diary.AddDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.DiaryListItemModel;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddDiaryPresenterTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();

  @Mock AddDiaryUseCase useCase;
  @Mock AddDiaryPresenter.Screen screen;
  @Mock ErrorTracker errorTracker;

  private AddDiaryPresenter presenter;
  private Scheduler scheduler = Schedulers.trampoline();

  @Before
  public void setUp() throws Exception {
    presenter = new AddDiaryPresenter(useCase, scheduler, errorTracker);
    presenter.setScreen(screen);
  }

  @Test
  public void should_call_screen_show_loading_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemModel.class));

    verify(screen).startLoading();
  }

  @Test
  public void should_call_screen_stop_loading_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemModel.class));

    verify(screen).stopLoading();
  }

  @Test
  public void should_call_screen_close_add_screen_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemModel.class));

    verify(screen).closeScreen();
  }

  @Test
  public void should_call_screen_show_error_when_add_item_fail() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.error(new Exception()));

    presenter.addDiary(mock(DiaryListItemModel.class));

    verify(screen).stopLoading();
    verify(screen).showError();
  }

  @Test
  public void should_call_error_tracker_when_add_item_fail() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.error(new Exception()));

    presenter.addDiary(mock(DiaryListItemModel.class));

    verify(errorTracker).trackError(any(Exception.class));
  }
}
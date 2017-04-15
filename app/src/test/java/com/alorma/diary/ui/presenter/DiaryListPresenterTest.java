package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.diary.DiaryListUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.model.DiaryItemModel;
import io.reactivex.Flowable;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DiaryListPresenterTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();

  @Mock DiaryListUseCase useCase;
  @Mock DiaryListPresenter.Screen screen;
  @Mock ErrorTracker errorTracker;

  private Scheduler scheduler = Schedulers.trampoline();
  private DiaryListPresenter presenter;

  @Before
  public void setUp() {
    presenter = new DiaryListPresenter(useCase, scheduler, errorTracker);
    presenter.setScreen(screen);
  }

  @Test
  public void should_call_screen_start_refresh_when_load() {
    given(useCase.getDiaries()).willReturn(Flowable.empty());

    presenter.load();

    verify(screen).startRefresh();
  }

  @Test
  public void should_call_screen_clear_items_when_load() {
    given(useCase.getDiaries()).willReturn(Flowable.empty());

    presenter.load();

    verify(screen).clearItems();
  }

  @Test
  public void should_call_screen_addItemToScreen_refresh_when_load() {
    given(useCase.getDiaries()).willReturn(Flowable.just(new DiaryItemModel()));

    presenter.load();

    verify(screen).addItemToScreen(any(DiaryItemModel.class));
  }

  @Test
  public void should_call_screen_addItemToScreen_N_times_refresh_when_load() {
    given(useCase.getDiaries()).willReturn(Flowable.just(new DiaryItemModel(), new DiaryItemModel(), new DiaryItemModel()));

    presenter.load();

    verify(screen, times(3)).addItemToScreen(any(DiaryItemModel.class));
  }

  @Test
  public void should_call_screen_stop_refresh_when_finish_load() {
    given(useCase.getDiaries()).willReturn(Flowable.just(new DiaryItemModel()));

    presenter.load();

    verify(screen).stopRefresh();
  }

  @Test
  public void should_call_screen_show_error_when_load_error() {
    given(useCase.getDiaries()).willReturn(Flowable.error(new Exception()));

    presenter.load();

    verify(screen).showError();
  }

  @Test
  public void should_call_error_tracker_when_load() {
    given(useCase.getDiaries()).willReturn(Flowable.error(new Exception()));

    presenter.load();

    verify(errorTracker).trackError(any(Exception.class));
  }

  @Test
  public void should_call_screen_openNewItemScreen_when_addNewItem() {
    presenter.addNewItem();

    verify(screen).openNewAddScreen();
  }
}
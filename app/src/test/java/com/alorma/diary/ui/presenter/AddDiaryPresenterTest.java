package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.diary.AddDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.exception.DiaryValidationContactException;
import com.alorma.diary.data.exception.user.validation.UserValidationNameException;
import com.alorma.diary.data.model.ContactItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.ui.presenter.validator.DiaryTestValidator;
import com.alorma.diary.ui.presenter.validator.UserTestValidator;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AddDiaryPresenterTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();

  @Mock AddDiaryUseCase useCase;
  @Mock AddDiaryPresenter.Screen screen;
  @Mock ErrorTracker errorTracker;

  UserTestValidator userValidator;
  DiaryTestValidator diaryValidator;

  private AddDiaryPresenter presenter;
  private Scheduler scheduler = Schedulers.trampoline();

  @Before
  public void setUp() throws Exception {
    userValidator = spy(new UserTestValidator());
    diaryValidator = spy(new DiaryTestValidator());

    presenter = new AddDiaryPresenter(useCase, diaryValidator, userValidator, scheduler, errorTracker);
    presenter.setScreen(screen);
  }

  @Test
  public void should_call_screen_show_loading_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemCreator.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).startLoading();
  }

  @Test
  public void should_call_screen_stop_loading_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemCreator.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).stopLoading();
  }

  @Test
  public void should_call_screen_close_add_screen_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemCreator.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).closeScreen();
  }

  @Test
  public void should_call_screen_show_error_when_add_item_fail() {
    given(useCase.addDiary(any(DiaryListItemCreator.class))).willReturn(Completable.error(new Exception()));

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).stopLoading();
    verify(screen).showError();
  }

  @Test
  public void should_call_screen_show_contact_name_error_when_add_item_fail_user_not_validate_name() {
    given(userValidator.validate(any(ContactItemModel.class)))
        .willReturn(Completable.error(new UserValidationNameException()));

    ContactItemModel contactItemModel = mock(ContactItemModel.class);
    given(contactItemModel.getName()).willReturn(null);
    DiaryListItemCreator listItemModel = mock(DiaryListItemCreator.class);
    given(listItemModel.getContact()).willReturn(contactItemModel);

    presenter.addDiary(listItemModel);

    verify(screen, never()).closeScreen();
    verify(screen).stopLoading();
    verify(screen).showInvalidName();
  }

  @Test
  public void should_call_screen_show_contact_invalid_error_when_add_item_fail_user_not_set() {
    given(diaryValidator.validate(any(DiaryListItemCreator.class)))
        .willReturn(Completable.error(new DiaryValidationContactException()));

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen, never()).closeScreen();
    verify(screen).stopLoading();
    verify(screen).showInvalidName();
  }

  @Test
  public void should_call_error_tracker_when_add_item_fail() {
    given(useCase.addDiary(any(DiaryListItemCreator.class))).willReturn(Completable.error(new Exception()));

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(errorTracker).trackError(any(Exception.class));
  }
}
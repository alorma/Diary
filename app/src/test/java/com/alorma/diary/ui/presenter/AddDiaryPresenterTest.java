package com.alorma.diary.ui.presenter;

import com.alorma.diary.data.Validator;
import com.alorma.diary.data.diary.AddDiaryUseCase;
import com.alorma.diary.data.error.ErrorTracker;
import com.alorma.diary.data.exception.ValidationException;
import com.alorma.diary.data.exception.user.validation.UserValidationNameException;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
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
  @Mock Validator<ContactListItemModel> userValidator;

  private AddDiaryPresenter presenter;
  private Scheduler scheduler = Schedulers.trampoline();

  @Before
  public void setUp() throws Exception {
    presenter = new AddDiaryPresenter(useCase, userValidator, scheduler, errorTracker);
    presenter.setScreen(screen);
  }

  @Test
  public void should_call_screen_show_loading_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).startLoading();
  }

  @Test
  public void should_call_screen_stop_loading_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).stopLoading();
  }

  @Test
  public void should_call_screen_close_add_screen_when_add_item_successfully() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.complete());

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).closeScreen();
  }

  @Test
  public void should_call_screen_show_error_when_add_item_fail() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.error(new Exception()));

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(screen).stopLoading();
    verify(screen).showError();
  }

  @Test
  public void should_call_screen_show_contact_name_error_when_add_item_fail_user_not_validate_name() throws ValidationException {
    given(userValidator.validate(any(ContactListItemModel.class)))
        .willThrow(new UserValidationNameException());

    ContactListItemModel contactListItemModel = mock(ContactListItemModel.class);
    given(contactListItemModel.getName()).willReturn(null);
    DiaryListItemCreator listItemModel = mock(DiaryListItemCreator.class);
    given(listItemModel.getContact()).willReturn(contactListItemModel);

    presenter.addDiary(listItemModel);

    verify(screen).stopLoading();
    verify(screen).showInvalidName();
  }

  @Test
  public void should_call_error_tracker_when_add_item_fail() {
    given(useCase.addDiary(any(DiaryListItemModel.class))).willReturn(Completable.error(new Exception()));

    presenter.addDiary(mock(DiaryListItemCreator.class));

    verify(errorTracker).trackError(any(Exception.class));
  }
}
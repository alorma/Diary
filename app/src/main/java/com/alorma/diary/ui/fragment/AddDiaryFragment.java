package com.alorma.diary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.component.FragmentComponent;
import com.alorma.diary.di.qualifiers.PerFragment;
import com.alorma.diary.ui.presenter.AddDiaryPresenter;
import dagger.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.inject.Inject;

public class AddDiaryFragment extends BaseFragment implements AddDiaryPresenter.Screen {

  @Inject AddDiaryPresenter presenter;

  @BindView(R.id.addItem) View addItemView;

  public static AddDiaryFragment newInstance() {
    return new AddDiaryFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_add_diary, null, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    addItemView.setOnClickListener(v -> onAddViewClick());
  }

  private void onAddViewClick() {
    presenter.addDiary(dummyItem());
  }

  private DiaryListItemModel dummyItem() {
    Random random = new Random();
    List<String> comments = new ArrayList<>();
    int commentsNum = random.nextInt(10);
    for (int i = 0; i < commentsNum; i++) {
      comments.add("Comment #" + (i + 1));
    }

    ContactListItemModel contact = new ContactListItemModel();
    contact.setName(UUID.randomUUID().toString());

    contact.setComments(comments);

    EntryItemModel entry = new EntryItemModel();
    entry.setSubject(UUID.randomUUID().toString());
    entry.setContent("Lorem ipsum dolor est");
    entry.setPostedDate(System.currentTimeMillis());

    DiaryListItemModel diaryListItemModel = new DiaryListItemModel();
    diaryListItemModel.setContact(contact);
    diaryListItemModel.setLastEntry(entry);
    return diaryListItemModel;
  }

  @Override
  protected void initInject(ApplicationComponent mainComponent, DataComponent dataComponent) {
    DaggerAddDiaryFragment_Provide
        .builder()
        .applicationComponent(mainComponent)
        .dataComponent(dataComponent)
        .build()
        .inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.setScreen(this);
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void startLoading() {

  }

  @Override
  public void stopLoading() {

  }

  @Override
  public void closeScreen() {
    getActivity().finish();
  }

  @Override
  public void showError() {

  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class })
  public interface Provide extends FragmentComponent<AddDiaryFragment> {

  }
}

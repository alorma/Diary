package com.alorma.diary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.ContactListItemModel;
import com.alorma.diary.data.model.DiaryListItemCreator;
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

  private DiaryListItemCreator dummyItem() {
    Random random = new Random();
    List<String> comments = new ArrayList<>();
    int commentsNum = random.nextInt(10);
    for (int i = 0; i < commentsNum; i++) {
      comments.add("Comment #" + (i + 1));
    }

    ContactListItemModel contact = new ContactListItemModel();
    contact.setName(UUID.randomUUID().toString().split("-")[0]);

    contact.setComments(comments);

    DiaryListItemCreator itemCreator = new DiaryListItemCreator();
    itemCreator.setContact(contact);
    itemCreator.setId(random.nextInt());
    return itemCreator;
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
    presenter.stop();
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

  @Override
  public void showInvalidName() {
    Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showContactInvalid() {
    Toast.makeText(getContext(), "Invalid contact", Toast.LENGTH_SHORT).show();
  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class })
  public interface Provide extends FragmentComponent<AddDiaryFragment> {

  }
}

package com.alorma.diary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.component.FragmentComponent;
import com.alorma.diary.di.qualifiers.PerFragment;
import com.alorma.diary.ui.presenter.AddDiaryEntryPresenter;
import dagger.Component;
import java.util.Random;
import java.util.UUID;
import javax.inject.Inject;

public class AddDiaryEntryFragment extends BaseFragment implements AddDiaryEntryPresenter.Screen {

  @Inject AddDiaryEntryPresenter presenter;

  @BindView(R.id.addItem) View addItemView;
  private int diaryId;

  public static AddDiaryEntryFragment newInstance(int id) {
    AddDiaryEntryFragment fragment = new AddDiaryEntryFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Extras.DIARY_ID, id);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_add_diary_entry, null, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    readArguments();
    addItemView.setOnClickListener(v -> onAddViewClick());
  }

  private void readArguments() {
    diaryId = getArguments().getInt(Extras.DIARY_ID);
  }

  private void onAddViewClick() {
    presenter.addEntry(diaryId, getEntry());
  }

  private EntryItemModel getEntry() {
    Random random = new Random();
    EntryItemModel model = new EntryItemModel();
    model.setSubject("Random entry: " + random.nextGaussian());
    model.setContent("Lorem ipsum generated: " + UUID.randomUUID());
    model.setPostedDate(System.currentTimeMillis());
    return model;
  }

  @Override
  protected void initInject(ApplicationComponent mainComponent, DataComponent dataComponent) {
    DaggerAddDiaryEntryFragment_Provide
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
  public void showEntryAdded() {
    getActivity().finish();
  }

  @Override
  public void showErrorAddEntry() {

  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class })
  public interface Provide extends FragmentComponent<AddDiaryEntryFragment> {

  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}

package com.alorma.diary.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.data.model.EntryMessageItemModel;
import com.alorma.diary.data.model.EntryPhotoItemModel;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.component.FragmentComponent;
import com.alorma.diary.di.module.FragmentModule;
import com.alorma.diary.di.qualifiers.PerFragment;
import com.alorma.diary.ui.presenter.AddDiaryEntryPresenter;
import dagger.Component;
import java.util.Random;
import java.util.UUID;
import javax.inject.Inject;

public class AddDiaryEntryFragment extends BaseFragment implements AddDiaryEntryPresenter.Screen {

  private static final int REQUEST_CODE_PHOTO = 121;

  @Inject AddDiaryEntryPresenter presenter;

  @BindView(R.id.addMessageItem) View addMessageItemView;
  @BindView(R.id.addPhotoItem) View addPhotoItemView;

  private UUID diaryId;

  public static AddDiaryEntryFragment newInstance(UUID id) {
    AddDiaryEntryFragment fragment = new AddDiaryEntryFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable(Extras.DIARY_ID, id);
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
    addMessageItemView.setOnClickListener(v -> onAddMessageViewClick());
    addPhotoItemView.setOnClickListener(v -> onAddPhotoViewClick());
  }

  private void readArguments() {
    diaryId = (UUID) getArguments().getSerializable(Extras.DIARY_ID);
  }

  private void onAddMessageViewClick() {
    presenter.addEntry(diaryId, getMessageEntry());
  }

  private EntryItemModel getMessageEntry() {
    Random random = new Random();
    EntryMessageItemModel model = new EntryMessageItemModel();
    model.setSubject("Random entry: " + random.nextGaussian());
    model.setContent("Lorem ipsum generated: " + UUID.randomUUID());
    model.setPostedDate(System.currentTimeMillis());
    return model;
  }

  private void onAddPhotoViewClick() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    startActivityForResult(intent, REQUEST_CODE_PHOTO);
  }

  private EntryItemModel getPhotoEntry(Uri uri) {
    EntryPhotoItemModel model = new EntryPhotoItemModel();
    model.setUri(uri);
    model.setPhotoName(uri.getLastPathSegment());
    model.setPostedDate(System.currentTimeMillis());
    return model;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
      EntryItemModel photoEntry = getPhotoEntry(data.getData());
      presenter.addEntry(diaryId, photoEntry);
    }
  }

  @Override
  protected void initInject(ApplicationComponent mainComponent, DataComponent dataComponent) {
    DaggerAddDiaryEntryFragment_Provide
        .builder()
        .applicationComponent(mainComponent)
        .dataComponent(dataComponent)
        .fragmentModule(new FragmentModule(this))
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

  @Override
  public void onDestroy() {
    presenter.destroy();
    super.onDestroy();
  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class }, modules = FragmentModule.class)
  public interface Provide extends FragmentComponent<AddDiaryEntryFragment> {

  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}

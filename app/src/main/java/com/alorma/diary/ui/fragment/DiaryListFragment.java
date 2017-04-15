package com.alorma.diary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.component.FragmentComponent;
import com.alorma.diary.di.qualifiers.PerFragment;
import com.alorma.diary.ui.activity.AddDiaryActivity;
import com.alorma.diary.ui.adapter.DiaryItemAdapter;
import com.alorma.diary.ui.presenter.DiaryListPresenter;
import dagger.Component;
import javax.inject.Inject;

public class DiaryListFragment extends BaseFragment implements DiaryListPresenter.Screen, DiaryItemAdapter.Callback {

  @Inject DiaryListPresenter presenter;
  private DiaryItemAdapter diaryItemAdapter;

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.fabAddItems) FloatingActionButton fabAddItems;

  public static DiaryListFragment newInstance() {
    return new DiaryListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createAdapter();
  }

  private void createAdapter() {
    LayoutInflater inflater = LayoutInflater.from(getContext());
    diaryItemAdapter = new DiaryItemAdapter(inflater);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_diary_list, null, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    attachAdapter(diaryItemAdapter);

    fabAddItems.setOnClickListener(v -> presenter.addNewItem());
  }

  private void attachAdapter(RecyclerView.Adapter<DiaryItemAdapter.Holder> adapter) {
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void initInject(ApplicationComponent mainComponent, DataComponent dataComponent) {
    DaggerDiaryListFragment_Provide
        .builder()
        .applicationComponent(mainComponent)
        .dataComponent(dataComponent)
        .build()
        .inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    diaryItemAdapter.setCallback(this);
    presenter.setScreen(this);
    presenter.load();
  }

  @Override
  public void startRefresh() {

  }

  @Override
  public void clearItems() {
    diaryItemAdapter.clear();
  }

  @Override
  public void addItemToScreen(DiaryListItemModel item) {
    diaryItemAdapter.add(item);
  }

  @Override
  public void stopRefresh() {

  }

  @Override
  public void showError() {

  }

  @Override
  public void openNewAddScreen() {
    Intent intent = AddDiaryActivity.createIntent(getContext());
    startActivity(intent);
  }

  @Override
  public void onStop() {
    presenter.stop();
    diaryItemAdapter.setCallback(null);
    super.onStop();
  }

  @Override
  public void onDiaryItemCLick(DiaryListItemModel itemModel) {
    Toast.makeText(getContext(), "Item: " + itemModel.getId(), Toast.LENGTH_SHORT).show();
  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class })
  public interface Provide extends FragmentComponent<DiaryListFragment> {

  }
}

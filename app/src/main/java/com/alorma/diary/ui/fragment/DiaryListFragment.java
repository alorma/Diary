package com.alorma.diary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alorma.diary.R;
import com.alorma.diary.data.model.DiaryListItem;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.component.FragmentComponent;
import com.alorma.diary.di.qualifiers.PerFragment;
import com.alorma.diary.ui.presenter.DiaryListPresenter;
import dagger.Component;
import javax.inject.Inject;

public class DiaryListFragment extends BaseFragment implements DiaryListPresenter.Screen {

  @Inject DiaryListPresenter presenter;

  public static DiaryListFragment newInstance() {
    return new DiaryListFragment();
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

    presenter.load();
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
    presenter.setScreen(this);
  }

  @Override
  public void startRefresh() {

  }

  @Override
  public void addItemToScreen(DiaryListItem item) {

  }

  @Override
  public void stopRefresh() {

  }

  @Override
  public void showError() {

  }

  @Override
  public void onStop() {
    presenter.stop();
    super.onStop();
  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class })
  public interface Provide extends FragmentComponent<DiaryListFragment> {

  }
}

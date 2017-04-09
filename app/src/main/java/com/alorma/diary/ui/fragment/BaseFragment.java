package com.alorma.diary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.alorma.diary.DiaryApplication;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;

public abstract class BaseFragment extends Fragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initInject(getMainComponent(), getDataComponent());
  }

  protected abstract void initInject(ApplicationComponent mainComponent, DataComponent dataComponent);

  protected ApplicationComponent getMainComponent() {
    return ((DiaryApplication) getActivity().getApplication()).getComponent();
  }

  protected DataComponent getDataComponent() {
    return ((DiaryApplication) getActivity().getApplication()).getDataComponent();
  }
}

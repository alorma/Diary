package com.alorma.diary.data.error;

public interface ErrorTracker {
  void trackError(Throwable throwable);
}

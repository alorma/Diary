package com.alorma.diary.di.qualifiers.user;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Named;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Named("UserValidator")
public @interface UserValidator {
}
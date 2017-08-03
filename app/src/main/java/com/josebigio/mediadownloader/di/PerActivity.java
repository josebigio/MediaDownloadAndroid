package com.josebigio.mediadownloader.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by josebigio on 8/2/17.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {}

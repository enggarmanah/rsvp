package com.infoklinik.rsvp.client.main;

import com.infoklinik.rsvp.client.main.presenter.MainPresenter;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.Loader;

@Loader(MainPresenter.class)
public interface MenuModule extends Mvp4gModule {

}

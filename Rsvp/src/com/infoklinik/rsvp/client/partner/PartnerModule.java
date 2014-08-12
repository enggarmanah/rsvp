package com.infoklinik.rsvp.client.partner;

import com.infoklinik.rsvp.client.main.presenter.MainPresenter;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.Loader;

@Loader(MainPresenter.class)
public interface PartnerModule extends Mvp4gModule {

}

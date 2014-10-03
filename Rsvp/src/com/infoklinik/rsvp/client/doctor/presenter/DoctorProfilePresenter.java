package com.infoklinik.rsvp.client.doctor.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.doctor.DoctorEventBus;
import com.infoklinik.rsvp.client.doctor.presenter.interfaces.IDoctorProfileView;
import com.infoklinik.rsvp.client.doctor.view.DoctorProfileView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.client.rpc.ScheduleServiceAsync;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = DoctorProfileView.class)
public class DoctorProfilePresenter extends LazyPresenter<IDoctorProfileView, DoctorEventBus> {
	
	@Inject
	ScheduleServiceAsync scheduleService;
	
	@Inject
	DoctorServiceAsync doctorService;
	
	DoctorBean doctor;
	
	List<ScheduleBean> schedules;
	List<InstitutionBean> institutions;
	
	@Override
	public void bindView() {
		
		initScheduleHandler();
		initLocationHandler();
		initInfoHandler();
		initBtnHandler();
	}
	
	private void loadSchedules() {
		
		ProgressDlg.show();
		
		scheduleService.getDoctorSchedules(doctor.getId(), new AsyncCallback<List<ScheduleBean>>() {
			
			@Override
			public void onSuccess(List<ScheduleBean> result) {
				
				schedules = result;
				initInstitutions(schedules);
				
				List<GenericBean<ScheduleBean>> genSchedules = new ArrayList<GenericBean<ScheduleBean>>();
				for (final ScheduleBean schedule : schedules) {
					
					HandlerManager handlerMgr = new HandlerManager();
					GenericBean<ScheduleBean> genSchedule = new GenericBean<ScheduleBean>(schedule, handlerMgr);
					handlerMgr.setShowHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							eventBus.loadReservationDoctorLv2(schedule);
						}
					});
					
					genSchedules.add(genSchedule);
				}
				
				view.setSchedules(genSchedules);
				view.showSchedule();
				
				ProgressDlg.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ProgressDlg.failure();
			}
		});
	}
	
	private void initScheduleHandler() {
		
		view.setScheduleMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (schedules == null) {
					loadSchedules();
				} else {
					view.showSchedule();
				}
			}
		});	
	}
	
	private void initLocationHandler() {
		
		view.setLocationMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.showLocation();
				
				Timer timer = new Timer() {
					
					@Override
					public void run() {
						view.setInstitutions(institutions);
					}
				};
				
				timer.schedule(Constant.FADE_TIME);
			}
		});	
	}
	
	
	private void initInfoHandler() {
		
		view.setInfoMenuClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				view.setInfo(doctor.getProfile());
				view.showInfo();
			}
		});	
	}
	
	private void initBtnHandler() {

		view.setOkBtnClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.hide();
			}
		});
	}
	
	private void initInstitutions(List<ScheduleBean> schedules) {
		
		HashMap<Long, InstitutionBean> instMap = new HashMap<Long, InstitutionBean>();
		institutions = new ArrayList<InstitutionBean>();
		
		for (ScheduleBean schedule : schedules) {
			
			Long instId = schedule.getInstitutionBean().getId();
			InstitutionBean institution = instMap.get(instId);
			
			if (institution == null) {
				
				institutions.add(schedule.getInstitutionBean());
				instMap.put(instId, schedule.getInstitutionBean());
			}
			
			schedule.setDoctor(doctor);
		}
	}
	
	public void onLoadDoctorProfile(DoctorBean doctor) {
		
		reset();
		
		this.doctor = doctor;
		
		view.setDoctor(doctor);
		view.show();
		view.showSchedule();
		
		loadSchedules();
		
		if (!ClientUtil.isAdminUser) {
			increaseViewCount();
		}
	}
	
	private void reset() {
		
		schedules = null;
		institutions = null;
	}
	
	private void increaseViewCount() {
		
		doctorService.increaseViewCount(doctor.getId(), new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {}
			
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}

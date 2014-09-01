package com.infoklinik.rsvp.client.listing.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.GenericBean;
import com.infoklinik.rsvp.client.HandlerManager;
import com.infoklinik.rsvp.client.listing.SearchResultEventBus;
import com.infoklinik.rsvp.client.listing.presenter.interfaces.ISearchResultView;
import com.infoklinik.rsvp.client.listing.view.SearchResultView;
import com.infoklinik.rsvp.shared.DoctorBean;
import com.infoklinik.rsvp.shared.InstitutionBean;
import com.infoklinik.rsvp.shared.ScheduleBean;
import com.infoklinik.rsvp.shared.ServiceBean;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Singleton
@Presenter(view = SearchResultView.class)
public class SearchResultPresenter extends LazyPresenter<ISearchResultView, SearchResultEventBus> {
	
	List<InstitutionBean> institutions;
	List<DoctorBean> doctors;
	List<ServiceBean> services;
	
	@Override
	public void bindView() {
		
		view.setSearchLocationHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadSearchLocations(institutions);
			}
		});
	}
	
	public void onLoadInstitutionSearchResult(List<InstitutionBean> institutions) {
		
		this.institutions = institutions;
					
		List<GenericBean<InstitutionBean>> list = new ArrayList<GenericBean<InstitutionBean>>();
		
		for (InstitutionBean institution : institutions) {
			
			HandlerManager handlerMgr = new HandlerManager();
			
			handlerMgr.setShowHandler(getShowHandler(institution));
			handlerMgr.setCommentHandler(getCommentHandler(institution));
			handlerMgr.setLikeHandler(getLikeHandler(institution));
			
			if (ClientUtil.isAdminUser) {
				handlerMgr.setUpdateHandler(getUpdateHandler(institution));
			}
			
			GenericBean<InstitutionBean> genericBean = new GenericBean<InstitutionBean>(institution, handlerMgr);
			list.add(genericBean);
		}
		
		view.setInstitutions(list);

		eventBus.setLeftPanel(view.asWidget());
	}
	
	public void onLoadDoctorSearchResult(List<DoctorBean> doctors) {
		
		this.doctors = doctors;
					
		List<GenericBean<DoctorBean>> list = new ArrayList<GenericBean<DoctorBean>>();
		HashMap<Long, HashMap<Long, HandlerManager>> doctorInstHandlerMgrs = new HashMap<Long, HashMap<Long, HandlerManager>>(); 
		HashMap<Long, HashMap<Long, HashMap<Long, HandlerManager>>> doctorInstScheduleHandlerMgrs = new HashMap<Long, HashMap<Long, HashMap<Long, HandlerManager>>>();
		
		HashMap<Long, InstitutionBean> uniqueInstitutionMap = new HashMap<Long, InstitutionBean>();
		
		for (DoctorBean doctor : doctors) {
			
			HandlerManager handlerMgr = new HandlerManager();
			
			handlerMgr.setShowHandler(getShowHandler(doctor));
			handlerMgr.setCommentHandler(getCommentHandler(doctor));
			handlerMgr.setLikeHandler(getLikeHandler(doctor));
			
			if (ClientUtil.isAdminUser) {
				handlerMgr.setUpdateHandler(getUpdateHandler(doctor));
			}
			
			GenericBean<DoctorBean> genericBean = new GenericBean<DoctorBean>(doctor, handlerMgr);
			list.add(genericBean);
			
			HashMap<Long, HandlerManager> institutionMap = new HashMap<Long, HandlerManager>();
			HashMap<Long, HashMap<Long, HandlerManager>> scheduleMap = new HashMap<Long, HashMap<Long, HandlerManager>>();
			
			for (ScheduleBean schedule : doctor.getSchedules()) {
				
				schedule.setDoctor(doctor);
				
				InstitutionBean institution = schedule.getInstitutionBean();
				HashMap<Long, HandlerManager> instScheduleHandlerMgrs =  scheduleMap.get(institution.getId());
				
				if (instScheduleHandlerMgrs == null) {
					instScheduleHandlerMgrs = new HashMap<Long, HandlerManager>();
				}
				
				HandlerManager instScheduleHandlerMgr = new HandlerManager();
				instScheduleHandlerMgr.setShowHandler(getShowHandler(schedule));
				
				instScheduleHandlerMgrs.put(schedule.getId(), instScheduleHandlerMgr);
				
				HandlerManager instHandlerMgr = new HandlerManager();
				instHandlerMgr.setShowHandler(getShowHandler(institution));
				
				scheduleMap.put(institution.getId(), instScheduleHandlerMgrs);
				institutionMap.put(institution.getId(), instHandlerMgr);
				
				uniqueInstitutionMap.put(institution.getId(), institution);
			}
			
			doctorInstHandlerMgrs.put(doctor.getId(), institutionMap);
			doctorInstScheduleHandlerMgrs.put(doctor.getId(), scheduleMap);
		}
		
		List<InstitutionBean> uniqueInstitutions = new ArrayList<InstitutionBean>();
		
		for (Long key : uniqueInstitutionMap.keySet()) {
			uniqueInstitutions.add(uniqueInstitutionMap.get(key));
		}
		
		this.institutions = uniqueInstitutions;
		
		view.setDoctors(list);
		view.setDoctorInstHandlerMgrs(doctorInstHandlerMgrs);
		view.setDoctorInstScheduleHandlerMgrs(doctorInstScheduleHandlerMgrs);
		
		eventBus.setLeftPanel(view.asWidget());
	}
	
	public void onLoadServiceSearchResult(List<ServiceBean> services) {
		
		this.services = services;
					
		List<GenericBean<ServiceBean>> list = new ArrayList<GenericBean<ServiceBean>>();
		HashMap<Long, InstitutionBean> uniqueInstitutionMap = new HashMap<Long, InstitutionBean>();
		
		for (ServiceBean service : services) {
			
			HandlerManager handlerMgr = new HandlerManager();
			
			handlerMgr.setShowHandler(getShowHandler(service));
			handlerMgr.setCommentHandler(getCommentHandler(service));
			handlerMgr.setLikeHandler(getLikeHandler(service));
			
			GenericBean<ServiceBean> genericBean = new GenericBean<ServiceBean>(service, handlerMgr);
			list.add(genericBean);
			
			uniqueInstitutionMap.put(service.getInstitution().getId(), service.getInstitution());
		}
		
		List<InstitutionBean> uniqueInstitutions = new ArrayList<InstitutionBean>();
		
		for (Long key : uniqueInstitutionMap.keySet()) {
			uniqueInstitutions.add(uniqueInstitutionMap.get(key));
		}
		
		this.institutions = uniqueInstitutions;
		
		view.setServices(list);

		eventBus.setLeftPanel(view.asWidget());
	}
	
	private ClickHandler getShowHandler(final InstitutionBean institution) {
		
		ClickHandler showHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadInstitutionProfile(institution);
			}
		};
		
		return showHandler;
	}
	
	private ClickHandler getUpdateHandler(final InstitutionBean institution) {
		
		ClickHandler updateHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateInstitution(institution);
			}
		};
		
		return updateHandler;
	}
	
	private ClickHandler getCommentHandler(final InstitutionBean institution) {
		
		ClickHandler commentHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadInstitutionComment(institution);
			}
		};
		
		return commentHandler;
	}
	
	private ClickHandler getLikeHandler(final InstitutionBean institution) {
		
		ClickHandler likeHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadInstitutionLike(institution);
			}
		};
		
		return likeHandler;
	}
	
	private ClickHandler getShowHandler(final DoctorBean doctor) {
		
		ClickHandler showHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadDoctorProfile(doctor);
			}
		};
		
		return showHandler;
	}
	
	private ClickHandler getUpdateHandler(final DoctorBean doctor) {
		
		ClickHandler updateHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.updateDoctor(doctor);
			}
		};
		
		return updateHandler;
	}
	
	private ClickHandler getCommentHandler(final DoctorBean doctor) {
		
		ClickHandler commentHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadDoctorComment(doctor);
			}
		};
		
		return commentHandler;
	}
	
	private ClickHandler getLikeHandler(final DoctorBean doctor) {
		
		ClickHandler likeHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadDoctorLike(doctor);
			}
		};
		
		return likeHandler;
	}
	
	private ClickHandler getShowHandler(final ScheduleBean schedule) {
		
		ClickHandler showHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadAppointment(schedule);
			}
		};
		
		return showHandler;
	}
	
	private ClickHandler getShowHandler(final ServiceBean service) {
		
		ClickHandler showHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadServiceInfo(service);
			}
		};
		
		return showHandler;
	}
	
	private ClickHandler getCommentHandler(final ServiceBean service) {
		
		ClickHandler commentHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadServiceComment(service);
			}
		};
		
		return commentHandler;
	}
	
	private ClickHandler getLikeHandler(final ServiceBean service) {
		
		ClickHandler likeHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				eventBus.loadServiceLike(service);
			}
		};
		
		return likeHandler;
	}
}

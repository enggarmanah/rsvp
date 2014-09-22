package com.infoklinik.rsvp.client.main.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.infoklinik.rsvp.client.ClientUtil;
import com.infoklinik.rsvp.client.OAuthUtil;
import com.infoklinik.rsvp.client.main.MainEventBus;
import com.infoklinik.rsvp.client.main.presenter.interfaces.IMainView;
import com.infoklinik.rsvp.client.main.view.MainView;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;
import com.infoklinik.rsvp.client.rpc.CommonServiceAsync;
import com.infoklinik.rsvp.client.rpc.DoctorServiceAsync;
import com.infoklinik.rsvp.client.rpc.InstitutionServiceAsync;
import com.infoklinik.rsvp.client.rpc.OAuthLoginServiceAsync;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.Credential;
import com.infoklinik.rsvp.shared.LocationBean;
import com.infoklinik.rsvp.shared.SocialUser;
import com.infoklinik.rsvp.shared.UserBean;
import com.mvp4g.client.Mvp4gLoader;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Singleton
@Presenter(view = MainView.class)
public class MainPresenter extends BasePresenter<IMainView, MainEventBus> implements Mvp4gLoader<MainEventBus> {
	
	@Inject
	CommonServiceAsync commonService;
	
	@Inject
	InstitutionServiceAsync institutionService;
	
	@Inject
	DoctorServiceAsync doctorService;
	
	@Inject
	OAuthLoginServiceAsync oAuthLoginService;
	
	String sessionId;
	SocialUser socialUser;
	String socialUserTask;
	
	LocationListener locationListener;
	
	public static Boolean isPartnerLoaded = false;
	public static Boolean isPromotionLoaded = false;
	
	private String callbackUrl = Constant.APP_CALLBACK_URL_PROD;
	
	@Override
	public void onSuccess(MainEventBus eventBus, final String eventName, Object[] params ) {}
	
	@Override
	public void preLoad(MainEventBus eventBus, String eventName, Object[] params, Command load ) { load.execute(); }
	
	@Override
	public void onFailure(MainEventBus eventBus, String eventName, Object[] params, Throwable err ) {}
	
	@Override
	public void bind() {}
	
	public void onErrorOnLoad(Throwable reason) {
		Window.alert(reason.getMessage());
	}

	public void onBeforeLoad() {}

	public void onAfterLoad() {}
	
	public void onSetMenuPanel(IsWidget menuPanel) {
		
		view.setMenuPanel(menuPanel.asWidget());
	}
	
	public void onSetMenuImage(String selectedMenu) {
		
		view.setMenuImage(selectedMenu);
	}
	
	public void onSetSearchPanel(IsWidget searchPanel) {
		
		view.setSearchPanel(searchPanel.asWidget());
	}
	
	public void onSetLeftPanel(IsWidget leftPanel) {
		
		view.setLeftPanel(leftPanel.asWidget());
	}
	
	public void onSetRightPanel(IsWidget rightPanel) {
		
		view.setRightPanel(rightPanel.asWidget());
	}
	
	public void onLoadChildModuleView(IsWidget widget) {}
	
	public void onSetPopUpPanel() {}
	
	public void onStart() {
				
		eventBus.loadMenu();
		eventBus.loadDoctorSearch();
		
		commonService.isProductionEnvironment(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					callbackUrl = Constant.APP_CALLBACK_URL_PROD;
				} else {
					callbackUrl = Constant.APP_CALLBACK_URL_DEV;
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {}
		});
		
		commonService.getUserInfo(new AsyncCallback<UserBean>() {
			
			@Override
			public void onSuccess(UserBean user) {
				
				if(user.isLogin()) {
					
					ClientUtil.setUser(user);
					ClientUtil.setReqGeoLocation(false);
					
					eventBus.loadStatistic();
					eventBus.loadAdmin();
				} else {
					eventBus.loadPartner();
					eventBus.loadPromotion();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	public void setLocationListener(LocationListener locationListener) {
		
		this.locationListener = locationListener;
	}
	
	public void setLocation(LocationBean location) {
		
		locationListener.setLocation(location);
	}
	
	private void getSessionId() {
		
		oAuthLoginService.getSessionId(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				
				sessionId = result;
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void executeSocialUserTask(String task) {
		
		if (Constant.TASK_INSTITUTION_COMMENT_ADD.equals(task)) {
			
			eventBus.addInstSocialUserComment(socialUser);
		
		} else if (Constant.TASK_DOCTOR_COMMENT_ADD.equals(task)) {
			
			eventBus.addDoctorSocialUserComment(socialUser);
		
		} else if (Constant.TASK_SERVICE_COMMENT_ADD.equals(task)) {
			
			eventBus.addDoctorSocialUserComment(socialUser);
		
		} else if (Constant.TASK_INSTITUTION_LIKE_ADD.equals(task)) {
			
			eventBus.addInstitutionSocialUserLike(socialUser);
		
		} else if (Constant.TASK_DOCTOR_LIKE_ADD.equals(task)) {
			
			eventBus.addDoctorSocialUserLike(socialUser);
			
		} else if (Constant.TASK_SERVICE_LIKE_ADD.equals(task)) {
			
			eventBus.addServiceSocialUserLike(socialUser);
		}
	}
	
	public void onVerifySocialUserToExecuteTask(String task) {
		
		this.socialUserTask = task;
		
		if (socialUser != null) {
			
			executeSocialUserTask(socialUserTask);
		
		} else {
		
			ProgressDlg.show();
			
			oAuthLoginService.getSocialUser(sessionId, new AsyncCallback<SocialUser>() {
				
				@Override
				public void onSuccess(SocialUser result) {
					
					ProgressDlg.hidePrompt();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					getSessionId();
					
					GWT.log(caught.getMessage(), caught);
					
					final int authProvider = OAuthUtil.getAuthProvider("Facebook");
		            getAuthorizationUrl(authProvider);
				}
			});
		}
	}

	private void getAuthorizationUrl(final int authProvider)
    {
        String authProviderName = OAuthUtil.getAuthProviderName(authProvider);
        GWT.log("Get authorization from : " + authProviderName);
        
        GWT.log("Callback url : " + callbackUrl);
        GWT.log("Getting authorization url");
        
        final Credential credential = new Credential();
        credential.setRedirectUrl(callbackUrl);
        credential.setAuthProvider(authProvider);
        
        oAuthLoginService.getAuthorizationUrl(credential, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				
				String authorizationUrl = result;
                GWT.log("Authorization url: " + authorizationUrl);
                
                OAuthUtil.clearCookies(); 
                OAuthUtil.saveAuthProvider(authProvider);
                OAuthUtil.saveRediretUrl(callbackUrl);
                OAuthUtil.redirect(authorizationUrl);
                
                getSocialUserAfterVerification();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.hidePrompt();
				
				GWT.log(caught.getMessage(), caught);
			}
		});
    }
	
	private void getSocialUserAfterVerification() {
		
		oAuthLoginService.getSocialUserAfterVerification(new AsyncCallback<SocialUser>() {
			
			@Override
			public void onSuccess(SocialUser result) {
				
				socialUser = result;
				
				executeSocialUserTask(socialUserTask);
				
				ProgressDlg.hidePrompt();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				ProgressDlg.failure("Gagal melaksanakan verifikasi pengguna.");
			}
		});
	}
}

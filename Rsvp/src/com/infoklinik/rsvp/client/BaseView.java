package com.infoklinik.rsvp.client;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.infoklinik.rsvp.shared.Constant;

public abstract class BaseView extends Composite {

	protected static String LABEL_NO = "No.";
	protected static String LABEL_NAME = "Nama";
	protected static String LABEL_CATEGORY = "Kategori";
	protected static String LABEL_MENU = "Menu";
	
	protected static String CSS_HEADING = "heading";
	protected static String CSS_CONTENT = "content";
	protected static String CSS_MENU = "option";
	protected static String CSS_NAV_PREV = "nav-arrow-left";
	protected static String CSS_NAV_NEXT = "nav-arrow-right";
	protected static String CSS_PAGE = "page";
	protected static String CSS_PAGE_CURRENT = "page-current";
	protected static String CSS_MENU_PANEL = "menuPanel";
	protected static String CSS_BTN_DETAIL = "button-detail";
	protected static String CSS_BTN_DELETE = "button-delete";
	
	protected int page = 1;
	protected int size = 0;
	protected int pageCount = 1;
	
	protected HashMap<Long, Label> pageLabelMap;
	
	// Navigation - Begin
	
	protected void clear() {}

	protected void loadHeader() {}

	public void loadContent() {}
	
	public void loadStyle() {}
	
	protected void loadEmptyResult(FlexTable flexTable) {

		int rowCount = flexTable.getRowCount();

		if (rowCount == 1) {

			int count = flexTable.getCellCount(0);

			for (int i = 0; i < count; i++) {
				String info = "";
				if (i == 1) {
					info = "Record tidak ditemukan";
				}
				flexTable.setWidget(1, i, new Label(info));
			}

			setStyleName(flexTable);
		}
	}
	
	protected void loadEmptyContent(FlexTable flexTable) {

		int count = flexTable.getCellCount(0);

		for (int i = 0; i < count; i++) {
			Label label = new Label();
			label.setStyleName("empty");
			flexTable.setWidget(1, i, label);
		}
		
		setStyleName(flexTable);
	}

	protected void loadNavigation() {

		pageLabelMap = new HashMap<Long, Label>();
		getPagePanel().clear();

		pageCount = (int) Math.ceil(1.0 * this.size / Constant.PAGE_SIZE);
		
		Label prevLabel = new Label();
		prevLabel.setStyleName(CSS_NAV_PREV);

		Label nextLabel = new Label();
		nextLabel.setStyleName(CSS_NAV_NEXT);
		
		getPagePanel().add(prevLabel);
		
		for (int i = 1; i <= pageCount; i++) {

			final int page = i;

			Label pageLabel = new Label(String.valueOf(page));
			pageLabel.setStyleName(CSS_PAGE);

			pageLabel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					setPage(page);
				}
			});

			pageLabelMap.put(Long.valueOf(page), pageLabel);
			getPagePanel().add(pageLabel);
		}
		
		if (pageCount == 0) {
			
			Label pageLabel = new Label("1");
			pageLabel.setStyleName(CSS_PAGE_CURRENT);

			pageLabelMap.put(Long.valueOf(page), pageLabel);
			getPagePanel().add(pageLabel);
		
		} else {
			
			prevLabel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					setPage(page-1);
				}
			});
			
			nextLabel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					setPage(page+1);
				}
			});
		}

		getPagePanel().add(nextLabel);	
	}

	protected FlowPanel getPagePanel() {

		return new FlowPanel();
	}

	protected int getFromIndex() {

		return (page - 1) * Constant.PAGE_SIZE;
	}

	protected int getToIndex() {

		int index = page * Constant.PAGE_SIZE;
		index = index > size ? size : index;

		return index;
	}

	protected void setPage(final int newPage) {
		
		fadeOut();
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				Window.scrollTo (0,0);
				
				Label pageLabel = pageLabelMap.get(Long.valueOf(page));

				if (pageLabel != null) {
					pageLabel.setStyleName(CSS_PAGE);
				}
				
				page = newPage;
				
				page = page > pageCount ? pageCount : page;
				page = page < 1 ? 1 : page;

				pageLabel = pageLabelMap.get(Long.valueOf(page));
				
				if (pageLabel != null) {
					pageLabel.setStyleName(CSS_PAGE_CURRENT);
				}

				clear();
				loadHeader();
				loadContent();
				
				fadeIn();
			}
		};
		
		timer.schedule(1 * 1000);
	}
	
	protected void setEmptyPage() {

		this.page = 1;

		Label pageLabel = pageLabelMap.get(Long.valueOf(this.page));
		pageLabel.setStyleName(CSS_PAGE_CURRENT);

		clear();
		loadHeader();
		loadContent();
	}
	
	// Navigation - End
	
	// Table Style - Begin

	protected void setStyleName(FlexTable flexTable) {

		int index = flexTable.getRowCount() - 1;

		if (index == 0) {

			String cellStyleName = CSS_HEADING;

			int count = flexTable.getCellCount(index);

			for (int i = 0; i < count; i++) {
				flexTable.getCellFormatter().addStyleName(index, i, cellStyleName);
			}
		} else {

			int count = flexTable.getCellCount(index);

			for (int i = 0; i < count; i++) {
				if (i != count - 1) {
					flexTable.getCellFormatter().addStyleName(index, i, CSS_CONTENT);
				} else {
					flexTable.getCellFormatter().addStyleName(index, i, CSS_MENU);
				}
			}
		}		
	}

	protected Label createUpdateLabel() {

		Label updateLbl = new Label();
		updateLbl.setStyleName(CSS_BTN_DETAIL);

		return updateLbl;
	}

	protected Label createDeleteLabel() {

		Label deleteLbl = new Label();
		deleteLbl.setStyleName(CSS_BTN_DELETE);

		return deleteLbl;
	}

	protected FlowPanel createMenuPanel() {

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(CSS_MENU_PANEL);

		return panel;
	}
	
	// Table Style - End
	
	// Display - Begin
	
	protected abstract Widget getRootWidget();
	
	public void fadeIn() {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				getRootWidget().removeStyleName(Constant.STYLE_FADE_OUT);
				getRootWidget().addStyleName(Constant.STYLE_FADE_IN);
			}
		};
		
		timer.schedule(25);
	}
	
	public void fadeOut() {
		
		getRootWidget().removeStyleName(Constant.STYLE_FADE_IN);
		getRootWidget().addStyleName(Constant.STYLE_FADE_OUT);
	}
	
	public void dlgFadeIn() {
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				asWidget().removeStyleName(Constant.STYLE_FADE_OUT);
				asWidget().addStyleName(Constant.STYLE_FADE_IN);
			}
		};
		
		timer.schedule(25);
	}
	
	public void dlgFadeOut() {
		
		asWidget().removeStyleName(Constant.STYLE_FADE_IN);
		asWidget().addStyleName(Constant.STYLE_FADE_OUT);
	}
	
	// Display - End
}
package com.infoklinik.rsvp.client;

import gwtupload.client.BaseUploadStatus;

import com.infoklinik.rsvp.client.main.view.NotificationDlg;
import com.infoklinik.rsvp.client.main.view.ProgressDlg;

public class CustomUploadStatus extends BaseUploadStatus {
	
	@Override
	public void setError(String msg) {
		setStatus(Status.ERROR);
		
		ProgressDlg.hidePrompt();
		
		if (msg.indexOf(Message.ERR_FILE_UPLOAD_EXCEED_MAX_SIZE) > -1) {
			NotificationDlg.warning(Message.ERR_FILE_UPLOAD_EXCEED_MAX_SIZE);
		} else {
			NotificationDlg.warning(Message.ERR_FILE_UPLOAD);
		}
	}
	
	@Override
	public void setProgress(long done, long total) {
		int percent =(int) (total > 0 ? done * 100 / total : 0);
		ProgressDlg.setPercentage(percent);
		super.setProgress(done, total);
	}
}
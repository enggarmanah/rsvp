package com.infoklinik.rsvp.server;

import static gwtupload.shared.UConsts.TAG_CANCELED;
import static gwtupload.shared.UConsts.TAG_ERROR;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.google.appengine.api.datastore.Blob;
import com.infoklinik.rsvp.client.Message;
import com.infoklinik.rsvp.server.dao.ImageDAO;
import com.infoklinik.rsvp.shared.Constant;
import com.infoklinik.rsvp.shared.ImageBean;

import gwtupload.server.AbstractUploadListener;
import gwtupload.server.UploadServlet;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.exceptions.UploadCanceledException;
import gwtupload.server.gae.AppEngineUploadAction;

@SuppressWarnings("serial")
public class ImageUpload extends AppEngineUploadAction {
	
	private boolean removeSessionFiles = false;
	private boolean removeData = false;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String error = null;
		String message = null;

		perThreadRequest.set(request);
		try {
			// Receive the files and form elements, updating the progress status
			error = super.parsePostRequest(request, response);
			if (error == null) {
				// Call to the user code
				message = executeAction(request, getLastReceivedFileItems(request));
				
				List<FileItem> fileItems = getLastReceivedFileItems(request);
				ImageDAO imageDao = new ImageDAO();
				for (FileItem item : fileItems) {
					
					Blob blob = new Blob(item.get());
					
					if (blob.getBytes().length <= Constant.FILE_UPLOAD_MAX_SIZE) {
						
						ImageBean imageBean = new ImageBean();
						imageBean.setBytes(blob.getBytes());
						imageBean.setUpdateBy("System");
						
						String imageId = request.getParameter("imageId");
						
						if (!ServerUtil.isEmpty(imageId)) {
							imageBean.setId(Long.valueOf(imageId));
							imageBean = imageDao.updateImage(imageBean);
						} else {
							imageBean = imageDao.addImage(imageBean);
						}
						
						message = String.valueOf(imageBean.getId());
						
					} else {
						throw new UploadActionException(Message.ERR_FILE_UPLOAD_EXCEED_MAX_SIZE);
					}
				}
			}
		} catch (UploadCanceledException e) {
			renderXmlResponse(request, response, "<" + TAG_CANCELED + ">true</" + TAG_CANCELED + ">");
			return;
		} catch (UploadActionException e) {
			logger.info("ExecuteUploadActionException when receiving a file.", e);
			error = e.getMessage();
		} catch (Exception e) {
			logger.info("Unknown Exception when receiving a file.", e);
			error = e.getMessage();
		} finally {
			perThreadRequest.set(null);
		}

		AbstractUploadListener listener = getCurrentListener(request);
		if (error != null) {
			renderXmlResponse(request, response, "<" + TAG_ERROR + ">" + error + "</" + TAG_ERROR + ">");
			if (listener != null) {
				listener.setException(new RuntimeException(error));
			}
			UploadServlet.removeSessionFileItems(request);
		} else {
			Map<String, String> stat = new HashMap<String, String>();
			getFileItemsSummary(request, stat);
			if (message != null) {
				// see issue #139
				// stat.put("message", "\n<![CDATA[\n" + message + "\n]]>\n");
				stat.put("message", "<![CDATA[" + message + "]]>");
			}
			renderXmlResponse(request, response, statusToString(stat), true);
		}

		finish(request);
		if (removeSessionFiles) {
			removeSessionFileItems(request, removeData);
		}
	}
}

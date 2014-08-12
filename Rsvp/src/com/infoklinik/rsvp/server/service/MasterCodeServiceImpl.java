package com.infoklinik.rsvp.server.service;

import java.util.List;

import com.infoklinik.rsvp.client.rpc.MasterCodeService;
import com.infoklinik.rsvp.server.dao.MasterCodeDAO;
import com.infoklinik.rsvp.shared.MasterCodeBean;

@SuppressWarnings("serial")
public class MasterCodeServiceImpl extends BaseServiceServlet implements MasterCodeService {
	
	public List<MasterCodeBean> getMasterCodes(String type) {
		
		MasterCodeDAO masterDao = new MasterCodeDAO();
		List<MasterCodeBean> masterCodeBeans = masterDao.getMasterCodes(type);
		
		return masterCodeBeans;
	}
}

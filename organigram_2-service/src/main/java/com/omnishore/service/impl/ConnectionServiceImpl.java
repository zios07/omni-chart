package com.omnishore.service.impl;

import java.io.Serializable;

import com.omnishore.dao.CompteDao;
import com.omnishore.dao.impl.CompteDaoImpl;
import com.omnishore.service.api.IConnectionService;
import com.omnishore.vo.CompteVo;

public class ConnectionServiceImpl implements IConnectionService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1173884084122720497L;
	private Converter converter = new Converter();
	private CompteDao compteDao = new CompteDaoImpl();
	
	@Override
	public boolean verifierPass(CompteVo compteVo) {
		return compteDao.verifierPass(converter.populateCompte(compteVo));
	}

	@Override
	public CompteVo trouvercompteVo(String login) {
		return converter.populateCompteVo(compteDao.trouverCompte(login));
	}

}

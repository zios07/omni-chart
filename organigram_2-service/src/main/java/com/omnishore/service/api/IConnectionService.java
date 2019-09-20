package com.omnishore.service.api;

import com.omnishore.vo.CompteVo;

public interface IConnectionService {

	boolean verifierPass(CompteVo compteVo);
	CompteVo trouvercompteVo(String login);	
	
}

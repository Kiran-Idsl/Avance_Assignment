package com.avance.service;

import java.util.List;

import com.avance.entity.AvanceUrl;

public interface IService {

	public AvanceUrl saveUrl(AvanceUrl entity);

	public List<AvanceUrl> findDuplicate(AvanceUrl entity);
	
	public AvanceUrl findByShortUrls(String url);

	public List<AvanceUrl> listUrl();
}

package com.avance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avance.entity.AvanceUrl;
import com.avance.repository.IUrlRepository;
import com.avance.service.IService;

@Service
public class ServiceImpl implements IService {

	@Autowired
	private IUrlRepository repo;

	@Override
	public AvanceUrl saveUrl(AvanceUrl entity) {
		return repo.save(entity);
	}

	@Override
	public List<AvanceUrl> listUrl() {
		return repo.findAll();
	}

	@Override
	public List<AvanceUrl> findDuplicate(AvanceUrl entity) {
		return repo.findByUrl(entity.getUrl());
	}

	@Override
	public AvanceUrl findByShortUrls(String url) {
		return repo.findByShortUrls(url);
	}

}

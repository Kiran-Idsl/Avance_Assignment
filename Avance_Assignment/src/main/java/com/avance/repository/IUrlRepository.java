package com.avance.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avance.entity.AvanceUrl;

@Repository
public interface IUrlRepository extends JpaRepository<AvanceUrl, Serializable> {

	// to check duplicate URL
	public List<AvanceUrl> findByUrl(String url);

	// to check duplicate shortUrl
	public AvanceUrl findByShortUrls(String shortUrls);

}

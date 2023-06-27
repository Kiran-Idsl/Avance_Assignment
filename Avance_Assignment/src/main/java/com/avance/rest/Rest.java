package com.avance.rest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.avance.commons.RestAPIResponse;
import com.avance.entity.AvanceUrl;
import com.avance.service.IService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/url")
public class Rest {

	@Autowired
	private IService service;

	@Value("${externalUrl}")
	String externalUrl;

	// to save the entity
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestAPIResponse> saveUrl(@RequestBody AvanceUrl entity) {
		// checking for duplicate records
		List<AvanceUrl> findDuplicate = service.findDuplicate(entity);
		if (findDuplicate.isEmpty()) {
			service.saveUrl(entity);
			return new ResponseEntity<>(new RestAPIResponse("success", "Url added Successfully"), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new RestAPIResponse("duplicate", "Record Already Exists"), HttpStatus.OK);
		}
	}

	// API to redirect desired url
	@RequestMapping(value = "/{shortUrl}", method = RequestMethod.GET, produces = "application/json")
	public void locateToExternalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
		AvanceUrl isshortUrlExists = service.findByShortUrls(shortUrl);
		if (isshortUrlExists != null) {
			// fetching url creation time
			LocalDateTime createddate = isshortUrlExists.getCreateddate();
			// getting current time
			LocalDateTime now = LocalDateTime.now();
			// calculating and storing time different
			long urlExpiryTime = ChronoUnit.MINUTES.between(createddate, now);
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			// checking if url creation time exceed more than 5 minutes
			if (urlExpiryTime > 5) {
				String content = "<html><body><h1>Url Expired</h1></body></html>";
				response.getWriter().write(content);
			} else {
				response.sendRedirect(isshortUrlExists.getUrl());
			}

		} else {
			String content = "<html><body><h1>InValid Short Url</h1><a href='http://localhost:4200/kiran'>Click Here to Register Url</a></body></html>";
			response.getWriter().write(content);
		}
	}

	// to check url by taking short url API for ANGULAR
	@RequestMapping(value = "/getUrl/{shortUrl}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestAPIResponse> getUrl(@PathVariable String shortUrl) {
		AvanceUrl saveEntity = service.findByShortUrls(shortUrl);// saveEntity.getUrl()
		return new ResponseEntity<>(new RestAPIResponse("success", "Url fetched Successfully", saveEntity.getUrl()),
				HttpStatus.OK);
	}

	// to display list of url's API for ANGULAR
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestAPIResponse> saveUrl2() {
		List<AvanceUrl> listUrl = service.listUrl();
		return new ResponseEntity<>(new RestAPIResponse("success", "fetch all Url", listUrl), HttpStatus.OK);
	}

	// to check url exists or not from front by calling API on change event of
	// angular
	@RequestMapping(value = "/duplicateCheck", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestAPIResponse> duplicateCheck(@RequestBody AvanceUrl entity) {
		List<AvanceUrl> findDuplicate = service.findDuplicate(entity);
		if (findDuplicate.isEmpty()) {
			return new ResponseEntity<>(new RestAPIResponse("success", "Not found"), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new RestAPIResponse("duplicate", "Record Already Exists"), HttpStatus.OK);
		}
	}

}

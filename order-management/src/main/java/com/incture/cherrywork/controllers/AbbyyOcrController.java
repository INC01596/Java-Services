package com.incture.cherrywork.controllers;


import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incture.cherrywork.services.AbbyOcrService;
import com.incture.cherrywork.util.AbbyServiceUtil;



@RestController
@RequestMapping("/abbyy")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1024, maxRequestSize = 1024 * 1024 * 1024)
public class AbbyyOcrController {
	@Autowired
	private AbbyOcrService abbyOcrService;

	@PostMapping(value = "/upload")
	public String upload(@RequestParam("Files") MultipartFile file, @RequestParam("Model") String skillId)
			throws IOException {
		File fileToUpload = AbbyServiceUtil.multipartToFile(file);
		if (!fileToUpload.exists()) {
			return null; 
					//"File not present";
		} else {
			return abbyOcrService.uploadDocToTransOld(fileToUpload, skillId);
		}
	}
	@GetMapping(value = "/getTranDetails/{transactionId}")
	public String getTranDetails(@PathVariable("transactionId") String transactionId) {
		return abbyOcrService.getTranDocumentDetails(transactionId);
	}
}

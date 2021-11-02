package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.PaymentTermDo;
import com.incture.cherrywork.repositories.PaymentRepo;

@RestController
public class Ptcontroller {

	@Autowired
	private PaymentRepo prepo;
	@PostMapping("/save")
	public ResponseEntity<Object>save(@RequestBody PaymentTermDo d)
	{
		return ResponseEntity.ok().body(prepo.save(d));
	}
}

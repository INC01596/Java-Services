package com.incture.cherrywork.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.InvoDto;
import com.incture.cherrywork.dtos.ObdDto;
import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.services.ExcelService;

@RestController
@RequestMapping("/excelDownload")
public class DownloadExcelController {

	@Autowired
	 private ExcelService excelService;
	
	@PostMapping("/getIn")
	public void downloadAllIn(HttpServletResponse response,@RequestBody HeaderDetailUIDto dto)  {
		
		excelService.getExcelIn(response,dto);
	}
	
	@PostMapping("/getQt")
    public void downloadAllQt(HttpServletResponse response,@RequestBody HeaderDetailUIDto dto)  {
		
		excelService.getExcelQt(response,dto);
	}
	
	@PostMapping("/getOr")
    public void downloadAllOr(HttpServletResponse response,@RequestBody HeaderDetailUIDto dto)  {
		System.err.println("helloControleer");
		System.err.println("helloControleer"+dto.getDocumentType());
		excelService.getExcelOr(response,dto);
	}
	
	@PostMapping("/getObd")
	public void downloadAllObd(HttpServletResponse response,@RequestBody ObdDto dto){
		
		excelService.getExcelObd(response,dto);
	}
	
	@PostMapping("/getInv")
	public void downloadAllInv(HttpServletResponse response,@RequestBody InvoDto dto)throws IOException {
		
		excelService.getExcelInv(response,dto);
	}
	
	@PostMapping("/getRe")
   public void downloadAllRe(HttpServletResponse response,@RequestBody ReturnFilterDto dto) throws IOException {
		
		excelService.getExcelRe(response,dto);
	}
}

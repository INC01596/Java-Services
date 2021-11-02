
package com.incture.cherrywork.services;



//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.incture.cherrywork.dtos.HeaderDetailUIDto;
//import com.incture.cherrywork.dtos.InvoDto;
//import com.incture.cherrywork.dtos.ObdDto;
//import com.incture.cherrywork.dtos.ReturnFilterDto;
//import com.incture.cherrywork.entities.ReturnRequestHeader;
//import com.incture.cherrywork.entities.SalesOrderHeader;
//import com.incture.cherrywork.repositories.IReturnRequestHeaderRepository;
//import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
//import com.incture.cherrywork.repositories.ServicesUtils;
//
//@SuppressWarnings("unused")
//@Service("ExcelService")
//@Transactional
//public class ExcelService {
//	
//	@Autowired 
//	private ISalesOrderHeaderRepository repo;
//	
//	@PersistenceContext
//	private EntityManager entityManager;
//	
//	@Autowired 
//	private IReturnRequestHeaderRepository rrepo;
//	
//	String excelFilePath = "Reviews-export.xlsx";
//	
//	public void getExcelIn(HttpServletResponse response,HeaderDetailUIDto dto) {
//		 @SuppressWarnings("resource")
//		HSSFWorkbook workbook = new HSSFWorkbook();
//         HSSFSheet sheet = workbook.createSheet("Information Sheet");
//         
//         String fileName = "userinf" + ".xls";
//         
//        
//		int rowNum = 1;
//         
//         
//         List<SalesOrderHeader>list=getManageService(dto);
//         String[] headers = {"Enquiry_Id", "Created_Date", "Customer", "CreatedBy","Amount","Status","Requested_Delivery_Date"};
//         
//         HSSFRow row = sheet.createRow(0);
//         //Add header in excel sheet
//
//    for(int i=0;i<headers.length;i++){
//    HSSFCell cell = row.createCell(i);
//    HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//    cell.setCellValue(text);
//}
//    for (SalesOrderHeader user : list) {
//        HSSFRow row1 = sheet.createRow(rowNum);
//       
//        String str=user.getDocumentProcessStatus().toString();
//        System.err.println(str);
//     
//        
//        // Convert the given date into a
//        // string using toString()method
//        
//  
//        if(!ServicesUtils.isEmpty(user.getSalesHeaderId()));{
//            row1.createCell(0).setCellValue(user.getSalesHeaderId());
//           }
//           if(!ServicesUtils.isEmpty(user.getCreatedDate())){
//        	   Date date = user.getCreatedDate();
//        	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//        	   String strDate = dateFormat.format(date); 
//            row1.createCell(1).setCellValue(strDate);
//           }
//           if(!ServicesUtils.isEmpty(user.getCustomerName()))
//         		  {
//            row1.createCell(2).setCellValue(user.getCustomerName());
//         		  }
//           if(!ServicesUtils.isEmpty(user.getCreatedBy())){
//            row1.createCell(3).setCellValue(user.getCreatedBy());
//           }
//           if(!ServicesUtils.isEmpty(user.getAmount())){
//            row1.createCell(4).setCellValue(user.getAmount());
//            }
//           if(!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())){
//            row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
//           }
//           if(!ServicesUtils.isEmpty(user.getRequestDeliveryDate()))
//           {
//        	   Date date = user.getRequestDeliveryDate();
//        	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//        	   String strDate = dateFormat.format(date);  
//        	
//        	   
//            row1.createCell(6).setCellValue(strDate);
//           }
//        rowNum++;
//    }
//
//    try {
//  	  response.setContentType("application/octet-stream");
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//        response.flushBuffer();
//        workbook.write(response.getOutputStream());
//  		
//  	} catch (IOException e) {
//  				e.printStackTrace();
//  	}
//   
//		
//	}
//	public void getExcelQt(HttpServletResponse response,HeaderDetailUIDto dto) {
//		 @SuppressWarnings("resource")
//		HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Information Sheet");
//        
//        String fileName = "userinf" + ".xls";
//        
//        
//		int rowNum = 1;
//        
//        
//        List<SalesOrderHeader>list=getManageService(dto);
//        String[] headers = {"Quotation_Id", "Created_Date", "Customer", "CreatedBy","Amount","Status","Requested_Delivery_Date"};
//        
//        HSSFRow row = sheet.createRow(0);
//        //Add header in excel sheet
//
//   for(int i=0;i<headers.length;i++){
//   HSSFCell cell = row.createCell(i);
//   HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//   cell.setCellValue(text);
//}
//   for (SalesOrderHeader user : list) {
//       HSSFRow row1 = sheet.createRow(rowNum);
//
//       if(!ServicesUtils.isEmpty(user.getSalesHeaderId()));{
//           row1.createCell(0).setCellValue(user.getSalesHeaderId());
//          }
//          if(!ServicesUtils.isEmpty(user.getCreatedDate())){
//       	   Date date = user.getCreatedDate();
//       	 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//  	   String strDate = dateFormat.format(date); 
//           row1.createCell(1).setCellValue(strDate);
//          }
//          if(!ServicesUtils.isEmpty(user.getCustomerName()))
//        		  {
//           row1.createCell(2).setCellValue(user.getCustomerName());
//        		  }
//          if(!ServicesUtils.isEmpty(user.getCreatedBy())){
//           row1.createCell(3).setCellValue(user.getCreatedBy());
//          }
//          if(!ServicesUtils.isEmpty(user.getAmount())){
//           row1.createCell(4).setCellValue(user.getAmount());
//           }
//          if(!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())){
//           row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
//          }
//          if(!ServicesUtils.isEmpty(user.getRequestDeliveryDate()))
//          {
//        	  Date date = user.getRequestDeliveryDate();
//       	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//       	   String strDate = dateFormat.format(date);  
//       	
//       	   
//           row1.createCell(6).setCellValue(strDate);
//          }
//       rowNum++;
//   }
//
//   try {
//		  response.setContentType("application/octet-stream");
//	      response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//	      response.flushBuffer();
//	      workbook.write(response.getOutputStream());
//			
//		} catch (IOException e) {
//					e.printStackTrace();
//		}
//	 
//
//		
//	}
//	public void getExcelOr(HttpServletResponse response,HeaderDetailUIDto dto) {
//		System.err.println(dto.getDocumentType());
//		
//		
//		@SuppressWarnings("resource")
//		HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Information Sheet");
//        
//        String fileName = "userinf" + ".xls";
//        
//      
//		int rowNum = 1;
//        System.err.println("Inside getExcelOr");
//        
//        List<SalesOrderHeader>list=getManageService(dto);
//        
//        System.err.println("List "+list);
//        String[] headers = {"Order_Id", "Created_Date", "Customer", "CreatedBy","Amount","Status","Requested_Delivery_Date"};
//        
//        HSSFRow row = sheet.createRow(0);
//        //Add header in excel sheet
//        System.err.println("List after");
//   for(int i=0;i<headers.length;i++){
//   HSSFCell cell = row.createCell(i);
//   HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//   cell.setCellValue(text);
//}
//   System.err.println("List after1");
//   for (SalesOrderHeader user : list) {
//       HSSFRow row1 = sheet.createRow(rowNum);
//       if(!ServicesUtils.isEmpty(user.getSalesHeaderId()));{
//           row1.createCell(0).setCellValue(user.getSalesHeaderId());
//          }
//          if(!ServicesUtils.isEmpty(user.getCreatedDate())){
//        	  Date date = user.getCreatedDate();
//            	 DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//       	   String strDate = dateFormat.format(date); 
//                row1.createCell(1).setCellValue(strDate);
//          }
//          if(!ServicesUtils.isEmpty(user.getCustomerName()))
//        		  {
//           row1.createCell(2).setCellValue(user.getCustomerName());
//        		  }
//          if(!ServicesUtils.isEmpty(user.getCreatedBy())){
//           row1.createCell(3).setCellValue(user.getCreatedBy());
//          }
//          if(!ServicesUtils.isEmpty(user.getAmount())){
//           row1.createCell(4).setCellValue(user.getAmount());
//           }
//          if(!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())){
//           row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
//          }
//          if(!ServicesUtils.isEmpty(user.getRequestDeliveryDate()))
//          {
//        	  Date date = user.getRequestDeliveryDate();
//       	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//       	   String strDate = dateFormat.format(date);  
//       	
//       	   
//           row1.createCell(6).setCellValue(strDate);
//          }
//       
//       rowNum++;
//   }
//   try {
//		  response.setContentType("application/octet-stream");
//	      response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//	      response.flushBuffer();
//	      workbook.write(response.getOutputStream());
//			
//		} catch (IOException e) {
//					e.printStackTrace();
//		}
//	 
//
//		
//	}
//	public void getExcelObd(HttpServletResponse response,ObdDto dto) {
//		 @SuppressWarnings("resource")
//		HSSFWorkbook workbook = new HSSFWorkbook();
//       HSSFSheet sheet = workbook.createSheet("Information Sheet");
//       
//       String fileName = "userinf" + ".xls";
//       
//     
//		int rowNum = 1;
//      
//       List<SalesOrderHeader>list=getManageServiceObd(dto);
//       
//       System.err.println("List "+list);
//       String[] headers = {"Obd_Id","Order_Id", "Created_Date", "Customer", "CreatedBy","Amount","Status","Requested_Delivery_Date"};
//       
//       HSSFRow row = sheet.createRow(0);
//       
//       System.err.println("List after");
//  for(int i=0;i<headers.length;i++){
//  HSSFCell cell = row.createCell(i);
//  HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//  cell.setCellValue(text);
//}
//  
//  for (SalesOrderHeader user : list) {
//      HSSFRow row1 = sheet.createRow(rowNum);
//      
//      
//      if(!ServicesUtils.isEmpty(user.getObdId()))
//      {
//      row1.createCell(0).setCellValue(user.getObdId());
//      }
//      if(!ServicesUtils.isEmpty(user.getSalesHeaderId()));{
//          row1.createCell(1).setCellValue(user.getSalesHeaderId());
//         }
//         if(!ServicesUtils.isEmpty(user.getCreatedDate())){
//        	 Date date=user.getCreatedDate();
//        	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//           	   String strDate = dateFormat.format(date);  
//          row1.createCell(2).setCellValue(strDate);
//         }
//         if(!ServicesUtils.isEmpty(user.getCustomerName()))
//       		  {
//          row1.createCell(3).setCellValue(user.getCustomerName());
//       		  }
//          if(!ServicesUtils.isEmpty(user.getCreatedBy())){
//             row1.createCell(3).setCellValue(user.getCreatedBy());
//            }
//            if(!ServicesUtils.isEmpty(user.getAmount())){
//             row1.createCell(4).setCellValue(user.getAmount());
//             }
//            if(!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())){
//             row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
//            }
//            if(!ServicesUtils.isEmpty(user.getRequestDeliveryDate()))
//            {
//         	   Date date = user.getRequestDeliveryDate();
//         	 
//       	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//          	   String strDate = dateFormat.format(date);  
//         row1.createCell(6).setCellValue(strDate);
//             
//            }
//         
//      
//      
//      rowNum++;
//  }
//  
//  try {
//	  response.setContentType("application/octet-stream");
//      response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//      response.flushBuffer();
//      workbook.write(response.getOutputStream());
//		
//	} catch (IOException e) {
//				e.printStackTrace();
//	}
// 
//
//		
//	}
//	
//	
//	public void getExcelInv(HttpServletResponse response,InvoDto dto) {
//		
//	
//	HSSFWorkbook workbook = new HSSFWorkbook();
//     HSSFSheet sheet = workbook.createSheet("Information Sheet");
//      
//      String fileName = "userinf" + ".xls";
//      
//       int rowNum = 1;
//      
//      List<SalesOrderHeader>list=getManageServiceInvo(dto);
//      
//    
//      String[] headers = {"Inv_Id","Order_Id", "Created_Date", "Customer", "CreatedBy","Amount","Status","Requested_Delivery_Date"};
//      
//      HSSFRow row = sheet.createRow(0);
//      
//     
// for(int i=0;i<headers.length;i++){
// HSSFCell cell = row.createCell(i);
// HSSFRichTextString text = new HSSFRichTextString(headers[i]);
// cell.setCellValue(text);
//}
// 
// 
// 
// for (SalesOrderHeader user : list) {
//     HSSFRow row1 = sheet.createRow(rowNum);
//     
//     
//     if(!ServicesUtils.isEmpty(user.getInvId()))
//     {
//     row1.createCell(0).setCellValue(user.getInvId());
//     }
//     if(!ServicesUtils.isEmpty(user.getSalesHeaderId()));{
//         row1.createCell(1).setCellValue(user.getSalesHeaderId());
//        }
//        if(!ServicesUtils.isEmpty(user.getCreatedDate())){
//        	
//        	
//        
//        	 Date date=user.getCreatedDate();
//      	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//         	   String strDate = dateFormat.format(date);  
//        row1.createCell(2).setCellValue(strDate);
//        }
//        if(!ServicesUtils.isEmpty(user.getCustomerName()))
//      		  {
//         row1.createCell(3).setCellValue(user.getCustomerName());
//      		  }
//        if(!ServicesUtils.isEmpty(user.getCreatedBy())){
//            row1.createCell(3).setCellValue(user.getCreatedBy());
//           }
//           if(!ServicesUtils.isEmpty(user.getAmount())){
//            row1.createCell(4).setCellValue(user.getAmount());
//            }
//           if(!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())){
//            row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
//           }
//           if(!ServicesUtils.isEmpty(user.getRequestDeliveryDate()))
//           {
//        	   Date date = user.getRequestDeliveryDate();
//           	 
//           	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//              	   String strDate = dateFormat.format(date);  
//             row1.createCell(6).setCellValue(strDate);
//           }
//        
//     
//     
//     rowNum++;
// }
// 
// try {
//	  response.setContentType("application/octet-stream");
//     response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//     response.flushBuffer();
//     workbook.write(response.getOutputStream());
//		
//	} catch (IOException e) {
//				e.printStackTrace();
//	}
//
//	}
//
//		
//	public void getExcelRe(HttpServletResponse response, ReturnFilterDto dto) {
//		HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Information Sheet");
//        
//        String fileName = "userinf" + ".xls";
//        
//        @SuppressWarnings("unused")
//		int rowNum = 1;
//        
//        
//        List<ReturnRequestHeader>list=listAllReturn(dto);
//        String[] headers = {"ReturnrequestNumber", "Created_Date", "Customer","Return Reason","CreatedBy","Amount","Status"};
//        
//        HSSFRow row = sheet.createRow(0);
//        //Add header in excel sheet
//
//   for(int i=0;i<headers.length;i++){
//   HSSFCell cell = row.createCell(i);
//   HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//   cell.setCellValue(text);
//}
//   for (ReturnRequestHeader user : list) {
//       HSSFRow row1 = sheet.createRow(rowNum);
//       if(!ServicesUtils.isEmpty(user.getReturnReqNum()))
//       {
//       row1.createCell(0).setCellValue(user.getReturnReqNum());
//       }
//       if(!ServicesUtils.isEmpty(user.getCreatedAt()))
//       {
//    	   Date date = user.getCreatedAt();
//       	 
//       	   DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//          	   String strDate = dateFormat.format(date);  
//         
//       row1.createCell(1).setCellValue(strDate);
//       }
//       if(!ServicesUtils.isEmpty(user.getSoldToParty())){
//    	   String stp=user.getSoldToParty()+"("+user.getSoldToPartyDesc()+")";
//    	   System.err.println(stp);
//       row1.createCell(2).setCellValue(user.getSoldToPartyDesc());
//       }
//       if(!ServicesUtils.isEmpty(user.getOrderReason()))
//       {
//    	   String reason=user.getOrderReason()+"("+user.getOrderReasonText()+")";
//    	   System.err.println(reason);
//       row1.createCell(3).setCellValue(reason);
//       }
//       if(!ServicesUtils.isEmpty(user.getRequestedBy()))
//       {
//       row1.createCell(4).setCellValue(user.getRequestorName());
//       }
//       if(!ServicesUtils.isEmpty(user.getTotalRoAmount()))
//       {
//    	   
//       
//       row1.createCell(5).setCellValue(user.getTotalRoAmount());
//       }
//       if(!ServicesUtils.isEmpty(user.getDocVersion()))
//       {
//    	   System.err.println(user.getDocVersion());
//       row1.createCell(6).setCellValue(user.getDocVersion());
//     
//       }
//       
//       rowNum++;
//   }
//
//   try {
//		  response.setContentType("application/octet-stream");
//	      response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//	      response.flushBuffer();
//	      workbook.write(response.getOutputStream());
//			
//		} catch (IOException e) {
//					e.printStackTrace();
//		}
//	 
//		
//	}
//public List<SalesOrderHeader> getManageServiceObd(ObdDto dto) {
//		
//		
//		String STP=null;
//		
//	     if(!ServicesUtils.isEmpty(dto.getStpId()))
//	     {
//		    STP = listToString(dto.getStpId());
//	     }
//	     
//	     
//	     String invo=null;
//			
//	     if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
//	     {
//		    invo = listToString(dto.getInvoiceStatus());
//	     }
//	     System.err.println(invo);
//		 StringBuffer str=new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");
//		   
//		   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
//		   {
//			   str.append(" and s.salesHeaderId=:id");
//		   }
//		   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom())&&!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//		   {
//			   str.append(" and s.createdDate between :stdate and :enddate");
//		   }
//		   
//		   
//		   if(!ServicesUtils.isEmpty(dto.getStpId()))
//		   {
//			   str.append(" and s.soldToParty in (" + STP + ") ");
//		   }
//		   
//		   if(!ServicesUtils.isEmpty(dto.getObdId()))
//		   {
//		   	str.append(" and s.obdId=:obdId");
//		   }
//		  
//		   
//		   if(!ServicesUtils.isEmpty(dto.getObdStatus()))
//		   {
//			   str.append(" and s.obdStatus=:obdStatus");
//		   } 
//		   
//		   
//		   if(!ServicesUtils.isEmpty(dto.getPgiStatus()))
//		   {
//			   str.append(" and s.pgiStatus=:pgiStatus");
//		   } 
//		   
//		   if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//		   {
//			   str.append(" and s.shipToParty=:shipToParty");
//		   } 
//		   
//		   
//		   
//		   
//		   if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
//		   {
//			   str.append(" and s.invoiceStatus in (" + invo + ") ");
//		   }
//		   str.append("  order by createdDate desc");
//		   Query q=entityManager.createQuery(str.toString());
//		   System.err.println(str.toString());
//		   
//		   
//		   if(!ServicesUtils.isEmpty(dto.getDocumentType()))
//		   {
//			   q.setParameter("docType",dto.getDocumentType());
//		   }
//		   if(!ServicesUtils.isEmpty(dto.getObdId()))
//		   {
//			   q.setParameter("obdId",dto.getObdId());
//		   }
//		   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
//		   {
//			   q.setParameter("id", dto.getSalesHeaderId());
//		   }
//		   
//		   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()))
//		   {
//			   q.setParameter("stdate", dto.getCreatedDateFrom());
//		   }
//		   if(!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//		   {
//			   q.setParameter("enddate", dto.getCreatedDateTo());
//		   }
//		   if(!ServicesUtils.isEmpty(dto.getObdStatus()))
//		{
//				   
//			   
//			     q.setParameter("obdStatus", dto.getObdStatus());
//		 }
//		   
//		   if(!ServicesUtils.isEmpty(dto.getPgiStatus()))
//		   {
//		   		   
//		   	   
//		   	     q.setParameter("pgiStatus", dto.getPgiStatus());
//		    }
//		   if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//		   {
//		   		   
//		   	   
//		   	     q.setParameter("shipToParty", dto.getShipToParty());
//		    }
//		   List<SalesOrderHeader> list=q.getResultList();
//		   return list;
//
//}
//
//
//public List<SalesOrderHeader> getManageServiceInvo(InvoDto dto) {
//
//	
//	String STP=null;
//	
//     if(!ServicesUtils.isEmpty(dto.getStpId()))
//     {
//	    STP = listToString(dto.getStpId());
//     }
//     
//     
//     
//	 if(!ServicesUtils.isEmpty(dto.getStpId()))
//     {
//	    STP = listToString(dto.getStpId());
//     }
//     
//	
//	 StringBuffer str=new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");
//	   
//	   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
//	   {
//		   str.append(" and s.salesHeaderId=:id");
//	   }
//	   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom())&&!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//	   {
//		   str.append(" and s.createdDate between :stdate and :enddate");
//	   }
//	   
//	   
//	   if(!ServicesUtils.isEmpty(dto.getStpId()))
//	   {
//		   str.append(" and s.soldToParty in (" + STP + ") ");
//	   }
//	   
//	   if(!ServicesUtils.isEmpty(dto.getDocumentProcessStatus()))
//	   {
//	   	str.append(" and s.documentProcessStatus=:status");
//	   }
//	  
//	   
//	   if(!ServicesUtils.isEmpty(dto.getCreatedBy()))
//			   
//	   {
//		    str.append(" and s.createdBy=:createdBy");
//			   
//	   }
//	   
//	   
//	   if(!ServicesUtils.isEmpty(dto.getObdId()))
//		   
//	   {
//		    str.append(" and s.obdId=:obdId");
//			   
//	   }
//	   
//	   if(!ServicesUtils.isEmpty(dto.getInvId()))
//	   {
//		   str.append(" and s.invId=:invId");
//	   } 
//	   
//	   
//	   if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
//		   
//	   {
//		    str.append(" and s.invoiceStatus=:invoiceStatus");
//			   
//	   }
//	   
//	   
//       if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//		   
//	   {
//		    str.append(" and s.shipToParty=:shipToParty");
//			   
//	   }
//	   str.append("  order by createdDate desc");
//	   Query q=entityManager.createQuery(str.toString());
//	   
//	   
//	   if(!ServicesUtils.isEmpty(dto.getDocumentType()))
//	   {
//		   q.setParameter("docType",dto.getDocumentType());
//	   }
//	   if(!ServicesUtils.isEmpty(dto.getDocumentProcessStatus()))
//	   {
//		   q.setParameter("status",dto.getDocumentProcessStatus());
//	   }
//	   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
//	   {
//		   q.setParameter("id", dto.getSalesHeaderId());
//	   }
//	   
//	   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()))
//	   {
//		   q.setParameter("stdate", dto.getCreatedDateFrom());
//	   }
//	   if(!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//	   {
//		   q.setParameter("enddate", dto.getCreatedDateTo());
//	   }
//	   if(!ServicesUtils.isEmpty(dto.getObdId()))
//	{
//			   
//		   
//		     q.setParameter("obdId", dto.getObdId());
//	 }
//	   if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//		{
//				   
//			   
//			     q.setParameter("shipToParty", dto.getShipToParty());
//		 }
//	   
//	   if(!ServicesUtils.isEmpty(dto.getInvId()))
//	   {
//	   		   
//	   	   
//	   	     q.setParameter("invId", dto.getInvId());
//	    }
//	   
//	   
//	   
//	   if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
//	   {
//	   		   
//	   	   
//	   	     q.setParameter("invoiceStatus", dto.getInvoiceStatus());
//	    }
//	   
//	   if(!ServicesUtils.isEmpty(dto.getCreatedBy()))
//	   {
//	   		   
//	   	   
//	   	     q.setParameter("createdBy", dto.getCreatedBy());
//	    }
//	   List<SalesOrderHeader> list=q.getResultList();
//	   return list;
//}
//
//
//public List<SalesOrderHeader> getManageService(HeaderDetailUIDto dto) {
//	
//	String strsales=dto.getSalesHeaderId();
//	if(!ServicesUtils.isEmpty(dto.getSalesHeaderId())){
//	if(dto.getSalesHeaderId().length()<10)
//	{
//		int l=dto.getSalesHeaderId().length();
//		strsales=dto.getSalesHeaderId();
//		while(l<10)
//		{
//			strsales="0"+strsales;
//			l++;
//		}
//	
//	}
//	}
//	dto.setSalesHeaderId(strsales);
//	System.err.println(dto.getCreatedDateFrom());
//	System.err.println(dto.getSalesHeaderId());
//	String DOC=null;
//	
// if(!ServicesUtils.isEmpty(dto.getStpId()))
// {
//    DOC = listToString(dto.getStpId());
// }
//
//
//StringBuffer str=new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");
//
//if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
//{
//   str.append(" and s.salesHeaderId=:id");
//}
//if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom())&&!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//{
//   str.append(" and s.createdDate between :stdate and :enddate");
//}
//
//
//if(!ServicesUtils.isEmpty(dto.getStpId()))
//{
//   str.append(" and s.soldToParty in (" + DOC + ") ");
//}
//
//if(!ServicesUtils.isEmpty(dto.getDocumentProcessStatus()))
//{
//	str.append(" and s.documentProcessStatus in :list");
//}
//
//
//if(!ServicesUtils.isEmpty(dto.getRequestDeliveryDateFrom())&&!ServicesUtils.isEmpty(dto.getRequestDeliveryDateTo()))
//{
//   str.append(" and s.requestDeliveryDate between :st1date and :end1date");
//} 
//str.append("  order by createdDate desc");
//Query q=entityManager.createQuery(str.toString());
//
//
//if(!ServicesUtils.isEmpty(dto.getDocumentType()))
//{
//   q.setParameter("docType",dto.getDocumentType());
//}
//if(!ServicesUtils.isEmpty(dto.getDocumentProcessStatus()))
//{
//   q.setParameter("list",dto.getDocumentProcessStatus());
//}
//if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
//{
//   q.setParameter("id", dto.getSalesHeaderId());
//}
//
//if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()))
//{
//   q.setParameter("stdate", dto.getCreatedDateFrom());
//}
//if(!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//{
//   q.setParameter("enddate", dto.getCreatedDateTo());
//}
//if(!ServicesUtils.isEmpty(dto.getRequestDeliveryDateFrom()))
//{
//	   
//   
//     q.setParameter("st1date", dto.getRequestDeliveryDateFrom());
//}
//
//if(!ServicesUtils.isEmpty(dto.getRequestDeliveryDateTo()))
//{
//		   
//	   
//	     q.setParameter("end1date", dto.getRequestDeliveryDateTo());
//}
//List<SalesOrderHeader> list=q.getResultList();
//return list;
//}
//
//
//public List<ReturnRequestHeader> listAllReturn(ReturnFilterDto dto) {
//	
//
//List<String> l1=new ArrayList<>();
//String s=null;
//if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//{
// s=dto.getShipToParty().substring(8);
//}
//
//System.err.println(s);
//
//
//if(!ServicesUtils.isEmpty(dto.getCustomerId()))
//{
//	
//	for(int i=0;i<dto.getCustomerId().size();i++)
//	{
//		l1.add(dto.getCustomerId().get(i).substring(8));
//	}
//	
//	
//}
//
//List<ReturnRequestHeader> list = new ArrayList<>();
//String STP = listToString(l1);
//System.err.println(STP);
//StringBuffer headerQuery = new StringBuffer("select r from ReturnRequestHeader r where Flag is null");
//
//if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
//{
//	headerQuery.append(" and r.returnReqNum=:req");
//}
//
//System.err.println(dto.getCreatedDateFrom()+" "+dto.getCreatedDateTo());
//
//if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//{
//	headerQuery.append(" and r.createdAt between :stDate and :endDate");
//}
//
//
//if(!ServicesUtils.isEmpty(dto.getOrderReason()))
//{
//	headerQuery.append(" and r.orderReason=:reason");
//}
//
//if(!ServicesUtils.isEmpty(dto.getDocVersion()))
//{
//	headerQuery.append(" and r.docVersion=:version");
//}
//
//
//
//if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//{
//	headerQuery.append(" and r.shipToParty=:STP");
//}
//
//if(!ServicesUtils.isEmpty(dto.getRequestedBy()))
//{
//	headerQuery.append(" and r.requestedBy=:request");
//}
//
//if(!ServicesUtils.isEmpty(dto.getCustomerId()))
//{
//	headerQuery.append(" and r.soldToParty in (" + STP + ") ");
//}
//
//if(!ServicesUtils.isEmpty(dto.getDistributionChannel()))
//{
//	headerQuery.append(" and r.distributionChannel=:channel");
//}
//
//if(!ServicesUtils.isEmpty(dto.getDivision()))
//{
//	headerQuery.append(" and r.division=:division");
//}
//
//if(!ServicesUtils.isEmpty(dto.getSalesOrg()))
//{
//	headerQuery.append(" and r.salesOrg=:salesOrg");
//}
//  headerQuery.append(" order by createdAt desc");
//
//Query hq = entityManager.createQuery(headerQuery.toString());
//
//if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
//{
//	hq.setParameter("req", dto.getReturnReqNumber());
//}
//
//if(!ServicesUtils.isEmpty(dto.getOrderReason()))
//{
//	hq.setParameter("reason", dto.getOrderReason());
//}
//
//if(!ServicesUtils.isEmpty(dto.getDocVersion()))
//{
//	hq.setParameter("version", dto.getDocVersion());
//}
//
//if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()))
//{
//	hq.setParameter("stDate",dto.getCreatedDateFrom());
//	
//}
//if(!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
//{
//	hq.setParameter("endDate",dto.getCreatedDateTo());
//}
//
//if(!ServicesUtils.isEmpty(dto.getShipToParty()))
//{
//	hq.setParameter("STP",s);
//}
//
//if(!ServicesUtils.isEmpty(dto.getRequestedBy()))
//{
//	hq.setParameter("request", dto.getRequestedBy());
//}
//
//if(!ServicesUtils.isEmpty(dto.getDistributionChannel()))
//{
//	hq.setParameter("channel", dto.getDistributionChannel());
//	
//}
//if(!ServicesUtils.isEmpty(dto.getDivision()))
//{
//	hq.setParameter("division", dto.getDivision());
//	
//}
//if(!ServicesUtils.isEmpty(dto.getSalesOrg()))
//{
//	hq.setParameter("salesOrg", dto.getSalesOrg());
//}
//list=hq.getResultList();
//return list;
//}
//
//public static String listToString(List<String> list) {
//	String response = "";
//	try {
//		for (String s : list) {
//			response = "'" + s + "', " + response;
//		}
//		response = response.substring(0, response.length() - 2);
//	} catch (Exception e) {
//		System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
//		e.printStackTrace();
//	}
//	return response;
//}
//
//}
//=======


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.InvoDto;
import com.incture.cherrywork.dtos.ObdDto;
import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.entities.ReturnRequestHeader;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.repositories.IReturnRequestHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ServicesUtils;

@SuppressWarnings("unused")
@Service("ExcelService")
@Transactional
public class ExcelService {

	@Autowired
	private ISalesOrderHeaderRepository repo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IReturnRequestHeaderRepository rrepo;

	String excelFilePath = "Reviews-export.xlsx";

	public void getExcelIn(HttpServletResponse response, HeaderDetailUIDto dto) {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Information Sheet");

		String fileName = "userinf" + ".xls";

		int rowNum = 1;

		List<SalesOrderHeader> list = getManageService(dto);
		String[] headers = { "Enquiry_Id", "Created_Date", "Customer", "CreatedBy", "Amount", "Status",
				"Requested_Delivery_Date" };

		HSSFRow row = sheet.createRow(0);
		// Add header in excel sheet

		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		for (SalesOrderHeader user : list) {
			HSSFRow row1 = sheet.createRow(rowNum);

			String str = user.getDocumentProcessStatus().toString();
			System.err.println(str);

			// Convert the given date into a
			// string using toString()method

			if (!ServicesUtils.isEmpty(user.getSalesHeaderId()))
				;
			{
				row1.createCell(0).setCellValue(user.getSalesHeaderId());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedDate())) {
				Date date = user.getCreatedDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(1).setCellValue(strDate);
			}
			if (!ServicesUtils.isEmpty(user.getCustomerName())) {
				row1.createCell(2).setCellValue(user.getCustomerName());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedBy())) {
				row1.createCell(3).setCellValue(user.getCreatedBy());
			}
			if (!ServicesUtils.isEmpty(user.getAmount())) {
				row1.createCell(4).setCellValue(user.getAmount());
			}
			if (!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())) {
				row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
			}
			if (!ServicesUtils.isEmpty(user.getRequestDeliveryDate())) {
				Date date = user.getRequestDeliveryDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);

				row1.createCell(6).setCellValue(strDate);
			}
			rowNum++;
		}

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.flushBuffer();
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getExcelQt(HttpServletResponse response, HeaderDetailUIDto dto) {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Information Sheet");

		String fileName = "userinf" + ".xls";

		int rowNum = 1;

		List<SalesOrderHeader> list = getManageService(dto);
		String[] headers = { "Quotation_Id", "Created_Date", "Customer", "CreatedBy", "Amount", "Status",
				"Requested_Delivery_Date" };

		HSSFRow row = sheet.createRow(0);
		// Add header in excel sheet

		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		for (SalesOrderHeader user : list) {
			HSSFRow row1 = sheet.createRow(rowNum);

			if (!ServicesUtils.isEmpty(user.getSalesHeaderId()))
				;
			{
				row1.createCell(0).setCellValue(user.getSalesHeaderId());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedDate())) {
				Date date = user.getCreatedDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(1).setCellValue(strDate);
			}
			if (!ServicesUtils.isEmpty(user.getCustomerName())) {
				row1.createCell(2).setCellValue(user.getCustomerName());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedBy())) {
				row1.createCell(3).setCellValue(user.getCreatedBy());
			}
			if (!ServicesUtils.isEmpty(user.getAmount())) {
				row1.createCell(4).setCellValue(user.getAmount());
			}
			if (!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())) {
				row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
			}
			if (!ServicesUtils.isEmpty(user.getRequestDeliveryDate())) {
				Date date = user.getRequestDeliveryDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);

				row1.createCell(6).setCellValue(strDate);
			}
			rowNum++;
		}

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.flushBuffer();
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getExcelOr(HttpServletResponse response, HeaderDetailUIDto dto) {
		System.err.println(dto.getDocumentType());

		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Information Sheet");

		String fileName = "userinf" + ".xls";

		int rowNum = 1;
		System.err.println("Inside getExcelOr");

		List<SalesOrderHeader> list = getManageService(dto);

		System.err.println("List " + list);
		String[] headers = { "Order_Id", "Created_Date", "Customer", "CreatedBy", "Amount", "Status",
				"Requested_Delivery_Date" };

		HSSFRow row = sheet.createRow(0);
		// Add header in excel sheet
		System.err.println("List after");
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		System.err.println("List after1");
		for (SalesOrderHeader user : list) {
			HSSFRow row1 = sheet.createRow(rowNum);
			if (!ServicesUtils.isEmpty(user.getSalesHeaderId()))
				;
			{
				row1.createCell(0).setCellValue(user.getSalesHeaderId());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedDate())) {
				Date date = user.getCreatedDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(1).setCellValue(strDate);
			}
			if (!ServicesUtils.isEmpty(user.getCustomerName())) {
				row1.createCell(2).setCellValue(user.getCustomerName());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedBy())) {
				row1.createCell(3).setCellValue(user.getCreatedBy());
			}
			if (!ServicesUtils.isEmpty(user.getAmount())) {
				row1.createCell(4).setCellValue(user.getAmount());
			}
			if (!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())) {
				row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
			}
			if (!ServicesUtils.isEmpty(user.getRequestDeliveryDate())) {
				Date date = user.getRequestDeliveryDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);

				row1.createCell(6).setCellValue(strDate);
			}

			rowNum++;
		}
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.flushBuffer();
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getExcelObd(HttpServletResponse response, ObdDto dto) {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Information Sheet");

		String fileName = "userinf" + ".xls";

		int rowNum = 1;

		List<SalesOrderHeader> list = getManageServiceObd(dto);

		System.err.println("List " + list);
		String[] headers = { "Obd_Id", "Order_Id", "Created_Date", "Customer", "CreatedBy", "Amount", "Status",
				"Requested_Delivery_Date" };

		HSSFRow row = sheet.createRow(0);

		System.err.println("List after");
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		for (SalesOrderHeader user : list) {
			HSSFRow row1 = sheet.createRow(rowNum);

			if (!ServicesUtils.isEmpty(user.getObdId())) {
				row1.createCell(0).setCellValue(user.getObdId());
			}
			if (!ServicesUtils.isEmpty(user.getSalesHeaderId()))
				;
			{
				row1.createCell(1).setCellValue(user.getSalesHeaderId());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedDate())) {
				Date date = user.getCreatedDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(2).setCellValue(strDate);
			}
			if (!ServicesUtils.isEmpty(user.getCustomerName())) {
				row1.createCell(3).setCellValue(user.getCustomerName());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedBy())) {
				row1.createCell(3).setCellValue(user.getCreatedBy());
			}
			if (!ServicesUtils.isEmpty(user.getAmount())) {
				row1.createCell(4).setCellValue(user.getAmount());
			}
			if (!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())) {
				row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
			}
			if (!ServicesUtils.isEmpty(user.getRequestDeliveryDate())) {
				Date date = user.getRequestDeliveryDate();

				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(6).setCellValue(strDate);

			}

			rowNum++;
		}

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.flushBuffer();
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getExcelInv(HttpServletResponse response, InvoDto dto) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Information Sheet");

		String fileName = "userinf" + ".xls";

		int rowNum = 1;

		List<SalesOrderHeader> list = getManageServiceInvo(dto);

		String[] headers = { "Inv_Id", "Order_Id", "Created_Date", "Customer", "CreatedBy", "Amount", "Status",
				"Requested_Delivery_Date" };

		HSSFRow row = sheet.createRow(0);

		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		for (SalesOrderHeader user : list) {
			HSSFRow row1 = sheet.createRow(rowNum);

			if (!ServicesUtils.isEmpty(user.getInvId())) {
				row1.createCell(0).setCellValue(user.getInvId());
			}
			if (!ServicesUtils.isEmpty(user.getSalesHeaderId()))
				;
			{
				row1.createCell(1).setCellValue(user.getSalesHeaderId());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedDate())) {

				Date date = user.getCreatedDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(2).setCellValue(strDate);
			}
			if (!ServicesUtils.isEmpty(user.getCustomerName())) {
				row1.createCell(3).setCellValue(user.getCustomerName());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedBy())) {
				row1.createCell(3).setCellValue(user.getCreatedBy());
			}
			if (!ServicesUtils.isEmpty(user.getAmount())) {
				row1.createCell(4).setCellValue(user.getAmount());
			}
			if (!ServicesUtils.isEmpty(user.getDocumentProcessStatus().getValue())) {
				row1.createCell(5).setCellValue(user.getDocumentProcessStatus().toString());
			}
			if (!ServicesUtils.isEmpty(user.getRequestDeliveryDate())) {
				Date date = user.getRequestDeliveryDate();

				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);
				row1.createCell(6).setCellValue(strDate);
			}

			rowNum++;
		}

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.flushBuffer();
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getExcelRe(HttpServletResponse response, ReturnFilterDto dto) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Information Sheet");

		String fileName = "userinf" + ".xls";

		@SuppressWarnings("unused")
		int rowNum = 1;

		List<ReturnRequestHeader> list = listAllReturn(dto);
		String[] headers = { "ReturnrequestNumber", "Created_Date", "Customer", "Return Reason", "CreatedBy", "Amount",
				"Status" };

		HSSFRow row = sheet.createRow(0);
		// Add header in excel sheet

		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		for (ReturnRequestHeader user : list) {
			HSSFRow row1 = sheet.createRow(rowNum);
			if (!ServicesUtils.isEmpty(user.getReturnReqNum())) {
				row1.createCell(0).setCellValue(user.getReturnReqNum());
			}
			if (!ServicesUtils.isEmpty(user.getCreatedAt())) {
				Date date = user.getCreatedAt();

				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String strDate = dateFormat.format(date);

				row1.createCell(1).setCellValue(strDate);
			}
			if (!ServicesUtils.isEmpty(user.getSoldToParty())) {
				String stp = user.getSoldToParty() + "(" + user.getSoldToPartyDesc() + ")";
				System.err.println(stp);
				row1.createCell(2).setCellValue(user.getSoldToPartyDesc());
			}
			if (!ServicesUtils.isEmpty(user.getOrderReason())) {
				String reason = user.getOrderReason() + "(" + user.getOrderReasonText() + ")";
				System.err.println(reason);
				row1.createCell(3).setCellValue(reason);
			}
			
			if (!ServicesUtils.isEmpty(user.getCreatedBy())) {
				String requestorName=user.getCreatedBy()+"("+user.getCreatedByDesc()+")";
				row1.createCell(4).setCellValue(requestorName);
			}
			if (!ServicesUtils.isEmpty(user.getTotalRoAmount())) {

				row1.createCell(5).setCellValue(user.getTotalRoAmount());
			}
			if (!ServicesUtils.isEmpty(user.getDocVersion())) {
				System.err.println(user.getDocVersion());
				row1.createCell(6).setCellValue(user.getDocVersion());

			}

			rowNum++;
		}

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.flushBuffer();
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<SalesOrderHeader> getManageServiceObd(ObdDto dto) {

		String STP = null;

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			STP = listToString(dto.getStpId());
		}

		String invo = null;

		if (!ServicesUtils.isEmpty(dto.getInvoiceStatus())) {
			invo = listToString(dto.getInvoiceStatus());
		}
		System.err.println(invo);
		StringBuffer str = new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");

		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			str.append(" and s.salesHeaderId=:id");
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			str.append(" and s.createdDate between :stdate and :enddate");
		}

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			str.append(" and s.soldToParty in (" + STP + ") ");
		}

		if (!ServicesUtils.isEmpty(dto.getObdId())) {
			str.append(" and s.obdId=:obdId");
		}

		if (!ServicesUtils.isEmpty(dto.getObdStatus())) {
			str.append(" and s.obdStatus=:obdStatus");
		}

		if (!ServicesUtils.isEmpty(dto.getPgiStatus())) {
			str.append(" and s.pgiStatus=:pgiStatus");
		}

		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			str.append(" and s.shipToParty=:shipToParty");
		}

		if (!ServicesUtils.isEmpty(dto.getInvoiceStatus())) {
			str.append(" and s.invoiceStatus in (" + invo + ") ");
		}
		str.append("  order by createdDate desc");
		Query q = entityManager.createQuery(str.toString());
		System.err.println(str.toString());

		if (!ServicesUtils.isEmpty(dto.getDocumentType())) {
			q.setParameter("docType", dto.getDocumentType());
		}
		if (!ServicesUtils.isEmpty(dto.getObdId())) {
			q.setParameter("obdId", dto.getObdId());
		}
		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			q.setParameter("id", dto.getSalesHeaderId());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom())) {
			q.setParameter("stdate", dto.getCreatedDateFrom());
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			q.setParameter("enddate", dto.getCreatedDateTo());
		}
		if (!ServicesUtils.isEmpty(dto.getObdStatus())) {

			q.setParameter("obdStatus", dto.getObdStatus());
		}

		if (!ServicesUtils.isEmpty(dto.getPgiStatus())) {

			q.setParameter("pgiStatus", dto.getPgiStatus());
		}
		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {

			q.setParameter("shipToParty", dto.getShipToParty());
		}
		List<SalesOrderHeader> list = q.getResultList();
		return list;

	}

	public List<SalesOrderHeader> getManageServiceInvo(InvoDto dto) {

		String STP = null;

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			STP = listToString(dto.getStpId());
		}

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			STP = listToString(dto.getStpId());
		}

		StringBuffer str = new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");

		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			str.append(" and s.salesHeaderId=:id");
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			str.append(" and s.createdDate between :stdate and :enddate");
		}

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			str.append(" and s.soldToParty in (" + STP + ") ");
		}

		if (!ServicesUtils.isEmpty(dto.getDocumentProcessStatus())) {
			str.append(" and s.documentProcessStatus=:status");
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedBy()))

		{
			str.append(" and s.createdBy=:createdBy");

		}

		if (!ServicesUtils.isEmpty(dto.getObdId()))

		{
			str.append(" and s.obdId=:obdId");

		}

		if (!ServicesUtils.isEmpty(dto.getInvId())) {
			str.append(" and s.invId=:invId");
		}

		if (!ServicesUtils.isEmpty(dto.getInvoiceStatus()))

		{
			str.append(" and s.invoiceStatus=:invoiceStatus");

		}

		if (!ServicesUtils.isEmpty(dto.getShipToParty()))

		{
			str.append(" and s.shipToParty=:shipToParty");

		}
		str.append("  order by createdDate desc");
		Query q = entityManager.createQuery(str.toString());

		if (!ServicesUtils.isEmpty(dto.getDocumentType())) {
			q.setParameter("docType", dto.getDocumentType());
		}
		if (!ServicesUtils.isEmpty(dto.getDocumentProcessStatus())) {
			q.setParameter("status", dto.getDocumentProcessStatus());
		}
		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			q.setParameter("id", dto.getSalesHeaderId());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom())) {
			q.setParameter("stdate", dto.getCreatedDateFrom());
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			q.setParameter("enddate", dto.getCreatedDateTo());
		}
		if (!ServicesUtils.isEmpty(dto.getObdId())) {

			q.setParameter("obdId", dto.getObdId());
		}
		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {

			q.setParameter("shipToParty", dto.getShipToParty());
		}

		if (!ServicesUtils.isEmpty(dto.getInvId())) {

			q.setParameter("invId", dto.getInvId());
		}

		if (!ServicesUtils.isEmpty(dto.getInvoiceStatus())) {

			q.setParameter("invoiceStatus", dto.getInvoiceStatus());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedBy())) {

			q.setParameter("createdBy", dto.getCreatedBy());
		}
		List<SalesOrderHeader> list = q.getResultList();
		return list;
	}

	public List<SalesOrderHeader> getManageService(HeaderDetailUIDto dto) {

		String strsales = dto.getSalesHeaderId();
		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			if (dto.getSalesHeaderId().length() < 10) {
				int l = dto.getSalesHeaderId().length();
				strsales = dto.getSalesHeaderId();
				while (l < 10) {
					strsales = "0" + strsales;
					l++;
				}

			}
		}
		dto.setSalesHeaderId(strsales);
		System.err.println(dto.getCreatedDateFrom());
		System.err.println(dto.getSalesHeaderId());
		String DOC = null;

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			DOC = listToString(dto.getStpId());
		}

		StringBuffer str = new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");

		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			str.append(" and s.salesHeaderId=:id");
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			str.append(" and s.createdDate between :stdate and :enddate");
		}

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			str.append(" and s.soldToParty in (" + DOC + ") ");
		}

		if (!ServicesUtils.isEmpty(dto.getDocumentProcessStatus())) {
			str.append(" and s.documentProcessStatus in :list");
		}

		if (!ServicesUtils.isEmpty(dto.getRequestDeliveryDateFrom())
				&& !ServicesUtils.isEmpty(dto.getRequestDeliveryDateTo())) {
			str.append(" and s.requestDeliveryDate between :st1date and :end1date");
		}
		str.append("  order by createdDate desc");
		Query q = entityManager.createQuery(str.toString());

		if (!ServicesUtils.isEmpty(dto.getDocumentType())) {
			q.setParameter("docType", dto.getDocumentType());
		}
		if (!ServicesUtils.isEmpty(dto.getDocumentProcessStatus())) {
			q.setParameter("list", dto.getDocumentProcessStatus());
		}
		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			q.setParameter("id", dto.getSalesHeaderId());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom())) {
			q.setParameter("stdate", dto.getCreatedDateFrom());
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			q.setParameter("enddate", dto.getCreatedDateTo());
		}
		if (!ServicesUtils.isEmpty(dto.getRequestDeliveryDateFrom())) {

			q.setParameter("st1date", dto.getRequestDeliveryDateFrom());
		}

		if (!ServicesUtils.isEmpty(dto.getRequestDeliveryDateTo())) {

			q.setParameter("end1date", dto.getRequestDeliveryDateTo());
		}
		List<SalesOrderHeader> list = q.getResultList();
		return list;
	}

	public List<ReturnRequestHeader> listAllReturn(ReturnFilterDto dto) {

		List<String> l1 = new ArrayList<>();
		String s = null;
		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			s = dto.getShipToParty().substring(8);
		}

		System.err.println(s);

		if (!ServicesUtils.isEmpty(dto.getCustomerId())) {

			for (int i = 0; i < dto.getCustomerId().size(); i++) {
				l1.add(dto.getCustomerId().get(i).substring(8));
			}

		}

		List<ReturnRequestHeader> list = new ArrayList<>();
		String STP = listToString(l1);
		System.err.println(STP);
		StringBuffer headerQuery = new StringBuffer("select r from ReturnRequestHeader r where Flag is null");

		if (!ServicesUtils.isEmpty(dto.getReturnReqNumber())) {
			headerQuery.append(" and r.returnReqNum=:req");
		}

		System.err.println(dto.getCreatedDateFrom() + " " + dto.getCreatedDateTo());

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			headerQuery.append(" and r.createdAt between :stDate and :endDate");
		}

		if (!ServicesUtils.isEmpty(dto.getOrderReason())) {
			headerQuery.append(" and r.orderReason=:reason");
		}

		if (!ServicesUtils.isEmpty(dto.getDocVersion())) {
			headerQuery.append(" and r.docVersion=:version");
		}

		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			headerQuery.append(" and r.shipToParty=:STP");
		}

		if (!ServicesUtils.isEmpty(dto.getRequestedBy())) {
			headerQuery.append(" and r.requestedBy=:request");
		}

		if (!ServicesUtils.isEmpty(dto.getCustomerId())) {
			headerQuery.append(" and r.soldToParty in (" + STP + ") ");
		}

		if (!ServicesUtils.isEmpty(dto.getDistributionChannel())) {
			headerQuery.append(" and r.distributionChannel=:channel");
		}

		if (!ServicesUtils.isEmpty(dto.getDivision())) {
			headerQuery.append(" and r.division=:division");
		}

		if (!ServicesUtils.isEmpty(dto.getSalesOrg())) {
			headerQuery.append(" and r.salesOrg=:salesOrg");
		}
		headerQuery.append(" order by createdAt desc");

		Query hq = entityManager.createQuery(headerQuery.toString());

		if (!ServicesUtils.isEmpty(dto.getReturnReqNumber())) {
			hq.setParameter("req", dto.getReturnReqNumber());
		}

		if (!ServicesUtils.isEmpty(dto.getOrderReason())) {
			hq.setParameter("reason", dto.getOrderReason());
		}

		if (!ServicesUtils.isEmpty(dto.getDocVersion())) {
			hq.setParameter("version", dto.getDocVersion());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom())) {
			hq.setParameter("stDate", dto.getCreatedDateFrom());

		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			hq.setParameter("endDate", dto.getCreatedDateTo());
		}

		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			hq.setParameter("STP", s);
		}

		if (!ServicesUtils.isEmpty(dto.getRequestedBy())) {
			hq.setParameter("request", dto.getRequestedBy());
		}

		if (!ServicesUtils.isEmpty(dto.getDistributionChannel())) {
			hq.setParameter("channel", dto.getDistributionChannel());

		}
		if (!ServicesUtils.isEmpty(dto.getDivision())) {
			hq.setParameter("division", dto.getDivision());

		}
		if (!ServicesUtils.isEmpty(dto.getSalesOrg())) {
			hq.setParameter("salesOrg", dto.getSalesOrg());
		}
		list = hq.getResultList();
		return list;
	}

	public static String listToString(List<String> list) {
		String response = "";
		try {
			for (String s : list) {
				response = "'" + s + "', " + response;
			}
			response = response.substring(0, response.length() - 2);
		} catch (Exception e) {
			System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

}


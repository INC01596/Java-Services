package com.incture.cherrywork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.dao.SalesOrderHistoryDao;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderHistoryDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;



@Service
@Transactional
public class SalesOrderHistoryServiceImpl implements SalesOrderHistoryService {

	@Autowired
	private SalesOrderHistoryDao salesOrderHistoryRepo;

	@Override
	public ResponseEntity saveSalesOrderItem(SalesOrderHistoryDto salesOrderHistoryDto) {
		try {

			if (!HelperClass.checkString(salesOrderHistoryDto.getSalesDocNum())
					&& !HelperClass.checkString(salesOrderHistoryDto.getSalesItemNum())) {
				String msg = null;
				SalesOrderHistoryDto salesOrderHistoryDtoInDb = salesOrderHistoryRepo.getSalesOrderByItemNum(
						salesOrderHistoryDto.getSalesDocNum(), salesOrderHistoryDto.getSalesItemNum());
				if (salesOrderHistoryDtoInDb != null) {
					Integer versionInDb = salesOrderHistoryDtoInDb.getVersion() + 1;

					salesOrderHistoryDto.setVersion(versionInDb);

					msg = salesOrderHistoryRepo.saveSalesOrder(salesOrderHistoryDto);

				} else {
					salesOrderHistoryDto.setVersion(0);
					msg = salesOrderHistoryRepo.saveSalesOrder(salesOrderHistoryDto);
				}

				if (msg == null) {
					return new ResponseEntity(salesOrderHistoryDto, HttpStatus.BAD_REQUEST,
							Constants.CREATION_FAILED, ResponseStatus.FAILED);
				} else {
					return new ResponseEntity(salesOrderHistoryDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity(salesOrderHistoryDto, HttpStatus.BAD_REQUEST,
						"Sales Order Header and Item num is empty, Please check !!", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesOrders() {
		try {
			List<SalesOrderHistoryDto> list = salesOrderHistoryRepo.listAllSalesOrderHistoryData();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, Constants.DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, Constants.EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listHistoryOfSalesOrderBasedOnVersion(String salesOrderHeaderNum, String salesOrderItemNum) {
		try {
			if (!HelperClass.checkString(salesOrderHeaderNum) && !HelperClass.checkString(salesOrderItemNum)) {

				List<SalesOrderHistoryDto> list = salesOrderHistoryRepo
						.getSalesOrderHistoryByItemNum(salesOrderHeaderNum, salesOrderItemNum);
				if (list != null && !list.isEmpty()) {
					return new ResponseEntity(list, HttpStatus.OK, Constants.DATA_FOUND, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, Constants.EMPTY_LIST,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Sales Order Header and Item num is empty, Please check !!", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

}

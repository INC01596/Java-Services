package com.incture.cherrywork.worflow.dao;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.SalesDocHeaderDao;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.sales.constants.DkshBlockConstant;
import com.incture.cherrywork.util.HelperClass;



@Service
@Transactional
public class BlockTypeDeterminationDaoImpl implements BlockTypeDeterminationDao {

	@Autowired
	private SalesDocHeaderDao salesDocHeaderRepo;

	@Override
	public Map<DkshBlockConstant, Object> blockTypeFilterBasedOnSoId(String salesOrderHeaderNo) throws ExecutionFault {
		try {

			Map<DkshBlockConstant, Object> map = new LinkedHashMap<>();
			SalesDocHeaderDto headerWithItems = null;
			try{
			headerWithItems = salesDocHeaderRepo.getSalesDocHeaderByIdWithOutFlag(salesOrderHeaderNo);
			}
			catch(Exception e){
				System.err.println("[BlockTypeDeterminationDaoImpl][blockTypeFilterBasedOnSoId] getSalesDocHeaderByIdWithOutFlag Exception: "+e.getMessage());
				e.printStackTrace();
			}

			System.err.println("Sales order data for " + salesOrderHeaderNo + " is : " + headerWithItems);

			if (headerWithItems != null) {
				try{

				checkingHeaderPart(map, headerWithItems);
				}
				catch(Exception e){
					System.err.println("[BlockTypeDeterminationDaompl][blockTypeFilterBasedOnSoId] checkingHeaderPart Exception: "+e.getMessage());
					e.printStackTrace();
				}
				if (headerWithItems.getSalesDocItemList() != null) {
					List<SalesDocItemDto> itemsList = headerWithItems.getSalesDocItemList();
					try{
					checkingItemPart(map, itemsList);
					}
					catch(Exception e){
						System.err.println("[BlockTypeDeterminationDaompl][blockTypeFilterBasedOnSoId] checkingItemPart Exception: "+e.getMessage());
						e.printStackTrace();
					}
				}
				System.err.println("BTD map at DAO : " + map);
				return map;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	private void checkingItemPart(Map<DkshBlockConstant, Object> map, List<SalesDocItemDto> itemsList) {

		System.err.println("Inside block type deter for sales order : " + itemsList.get(0).getSalesHeaderNo()
				+ " with item list size : " + itemsList.size());

		List<String> idb = new ArrayList<>();
		List<String> ibb = new ArrayList<>();
		List<String> itemNumList = new ArrayList<>();

		for (SalesDocItemDto item : itemsList) {
			idb.add(item.getSalesItemOrderNo());

			if (!HelperClass.checkString(item.getItemBillingBlock())) {
				ibb.add(item.getSalesItemOrderNo());
			}
			System.err.println("Reason of rejection for item " + item.getSalesItemOrderNo() + " is : "
					+ item.getReasonForRejection());
			if (item.getScheduleLineList() != null && !item.getScheduleLineList().isEmpty()
					&& HelperClass.checkString(item.getReasonForRejection())) {
				for (ScheduleLineDto scheduleLineDto : item.getScheduleLineList()) {
					if (!HelperClass.checkString(scheduleLineDto.getSchlineDeliveryBlock())) {
						if (!idb.contains(item.getSalesItemOrderNo())) {
							idb.add(item.getSalesItemOrderNo());
						}
					}
				}
			}
			itemNumList.add(item.getSalesItemOrderNo());
			
		}
		
		map.put(DkshBlockConstant.INP, itemNumList);
		map.put(DkshBlockConstant.IBB, ibb);
		map.put(DkshBlockConstant.IDB, idb);
		System.err.println("BTD map at item checking : " + map);
	}

	private void checkingHeaderPart(Map<DkshBlockConstant, Object> map, SalesDocHeaderDto headerWithItems) {

		System.err.println("Inside block type deter for sales order : " + headerWithItems.getSalesOrderNum());

		if (headerWithItems.getCreditStatus() != null) {
			map.put(DkshBlockConstant.HCB, headerWithItems.getCreditStatus());
		}
		if (headerWithItems.getHeaderBillBlockCode() != null) {
			map.put(DkshBlockConstant.HBB, headerWithItems.getHeaderBillBlockCode());
		}
		if (headerWithItems.getDeliveryBlockCode() != null) {
			map.put(DkshBlockConstant.HDB, headerWithItems.getDeliveryBlockCode());
		}
		System.err.println("BTD map at header checking : " + map);
	}

}

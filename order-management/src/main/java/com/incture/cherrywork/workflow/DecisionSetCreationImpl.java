package com.incture.cherrywork.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.dao.SalesDocItemDao;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.TH_IDB_RO_RuleInputDto;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.rules.ApproverDataOutputDto;
import com.incture.cherrywork.rules.RuleInputDto;
import com.incture.cherrywork.rules.THIDBRuleService;
import com.incture.cherrywork.rules.TH_IDBRuleInputDto;
import com.incture.cherrywork.sales.constants.StrategyConstants;
import com.incture.cherrywork.util.SequenceNumberGen;




@Transactional
@Repository
@Service
public class DecisionSetCreationImpl implements DecisionSetCreation {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Lazy
	@Autowired
	private SequenceNumberGen seqNumGenRepo;

	@Autowired
	private SalesDocItemDao salesDocItemDao;

	@Autowired
	private SessionFactory sf;

	public Session getSession() {
		try {
			return sf.getCurrentSession();
		} catch (HibernateException e) {
			return sf.openSession();
		}
	}

	private Map<String, List<ApproverDataOutputDto>> createApprovalMapFromRules(String requestId, String salesOrderId,
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,
			String country, String customerPo, String requestType, String requestCategory) {

		
		
		if( customerPo !=null  && !customerPo.isEmpty()){
		String checkTypeOfSo = customerPo.substring(0, 2);
		// if of Return Order Type and Return ExchangeType
		if (checkTypeOfSo.equals(Constants.RETURN_ORDER)||checkTypeOfSo.equals(Constants.RETURN_EXCHANGE)) {

			return generateApprovalMatFroReturnOrder(salesDocItemDtolist, strategy, distributionChannel, salesOrg,
					country, requestType, requestCategory);
		}
		}

		return generateApprovalMatForBso(salesDocItemDtolist, strategy, distributionChannel, salesOrg);
	}

	@SuppressWarnings("unchecked")
	private List<ApproverDataOutputDto> determineDsAndApprovalSet(
			Map.Entry<TH_IDBRuleInputDto, List<SalesDocItemDto>> entry) {

		THIDBRuleService rulesService = new THIDBRuleService();
		double totalAmount = 0;

		for (SalesDocItemDto itemDto : entry.getValue()) {
			if (itemDto.getNetWorth() != null)
				totalAmount = totalAmount + Double.parseDouble(itemDto.getNetWorth());
		}
		
		

		entry.getKey().setAmount(totalAmount);

		List<ApproverDataOutputDto> outputList = new ArrayList<>();

		try {
			outputList = (List<ApproverDataOutputDto>) rulesService.getResultList((RuleInputDto) entry.getKey());
		} catch (Exception e) {
			return null;
		}

		return outputList;
	}

	@SuppressWarnings("unchecked")
	private List<ApproverDataOutputDto> determineDsAndApprovalSetForRO(
			Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry) {

		THIDBRuleService rulesService = new THIDBRuleService();
		double totalAmount = 0;

		for (SalesDocItemDto itemDto : entry.getValue()) {
			if (itemDto.getNetWorth() != null)
				totalAmount = totalAmount + Double.parseDouble(itemDto.getNetWorth());
		}

		entry.getKey().setAmount(totalAmount);

		List<ApproverDataOutputDto> outputList = new ArrayList<>();

		try {
			outputList = (List<ApproverDataOutputDto>) rulesService.getResultList((RuleInputDto) entry.getKey());
		} catch (Exception e) {
			return null;
		}

		return outputList;
	}

	private String generateDecisionSetId(List<ApproverDataOutputDto> outputList,
			Map.Entry<TH_IDBRuleInputDto, List<SalesDocItemDto>> entry) {
		String decisionSetId = null;

		if (outputList != null && !outputList.isEmpty()) {
			Session session = sf.openSession();
			// if its of Blocked sales order type
			decisionSetId = seqNumGenRepo.getNextSeqNumber("DS_", 6, session);
			session.close();

			List<SalesDocItemDto> itemList = salesDocItemDao.getSalesDocItemsByDecisionSetId(decisionSetId);
			System.err.println("decision Set check " + itemList);
			if (itemList.isEmpty()) {
				if (null != decisionSetId && !decisionSetId.isEmpty()) {
					for (SalesDocItemDto itemDto : entry.getValue()) {
						try {
							itemDto.setDecisionSetId(decisionSetId);
							salesDocItemDao.saveOrUpdateSalesDocItemForDS(itemDto);
							// salesDocItemDao.updateSalesDocItemWithDecisionSet(decisionSetId,
							// itemDto.getSalesItemOrderNo(),
							// itemDto.getSalesHeaderNo());
						} catch (ExecutionFault e) {
							logger.error(e.getMessage());
						}
					}
					return decisionSetId;
				}
			}

		}

		return null;
	}

	private String generateDecisionSetIdForROType(List<ApproverDataOutputDto> outputList,
			Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry) {
		String decisionSetId = null;

		if (outputList != null && !outputList.isEmpty()) {
			Session session = sf.openSession();
			// if its of Blocked sales order type
			decisionSetId = seqNumGenRepo.getNextSeqNumber("RO_DS_", 6, session);
			session.close();

			List<SalesDocItemDto> itemList = salesDocItemDao.getSalesDocItemsByDecisionSetId(decisionSetId);
			System.err.println("decision Set check " + itemList);
			if (itemList.isEmpty()) {
				if (null != decisionSetId && !decisionSetId.isEmpty()) {
					for (SalesDocItemDto itemDto : entry.getValue()) {
						try {
							itemDto.setDecisionSetId(decisionSetId);
							salesDocItemDao.saveOrUpdateSalesDocItemForDS(itemDto);
							// salesDocItemDao.updateSalesDocItemWithDecisionSet(decisionSetId,
							// itemDto.getSalesItemOrderNo(),
							// itemDto.getSalesHeaderNo());
						} catch (ExecutionFault e) {
							logger.error(e.getMessage());
						}
					}
					return decisionSetId;
				}
			}

		}

		return null;
	}

	@Override
	public Map<String, List<ApproverDataOutputDto>> createAndReturnApprovalMap(String requestId, String salesOrderId,
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,
			String country, String customerPo, String requestType, String requestCategory) {
		return createApprovalMapFromRules(requestId, salesOrderId, salesDocItemDtolist, strategy, distributionChannel,
				salesOrg, country, customerPo, requestType, requestCategory);
	}

	// block sales order map
	private Map<String, List<ApproverDataOutputDto>> generateApprovalMatForBso(
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg) {

		Map<String, List<ApproverDataOutputDto>> outputMap = new HashMap<>();

		switch (strategy) {
		case StrategyConstants.TH_IDB_PROC:

			Map<TH_IDBRuleInputDto, List<SalesDocItemDto>> inputMap = new HashMap<>();
			Map<TH_IDBRuleInputDto, List<SalesDocItemDto>> defaultInputMap = new HashMap<>();
			String type = new String();

			for (SalesDocItemDto salesDocItemDto : salesDocItemDtolist) {

				TH_IDBRuleInputDto th_idb_proc_dto = new TH_IDBRuleInputDto();

				// forming the key

				if (salesDocItemDto.getMaterialGroup() != null && salesDocItemDto.getMaterialGroup4() != null
						&& distributionChannel != null) {
					th_idb_proc_dto.setMaterialGroup(salesDocItemDto.getMaterialGroup());
					th_idb_proc_dto.setMaterialGroup4(salesDocItemDto.getMaterialGroup4());
					th_idb_proc_dto.setDistributionChannel(distributionChannel);
					th_idb_proc_dto.setSalesOrg(salesOrg);
				} else {
					th_idb_proc_dto.setDistributionChannel(Constants.DEFAULT);
					th_idb_proc_dto.setSalesOrg(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup4(Constants.DEFAULT);
					type = Constants.DEFAULT;

				}

				if (inputMap.containsKey(th_idb_proc_dto) || defaultInputMap.containsKey(th_idb_proc_dto)) {
					if (!type.equals(Constants.DEFAULT))
						inputMap.get(th_idb_proc_dto).add(salesDocItemDto);
					else
						defaultInputMap.get(th_idb_proc_dto).add(salesDocItemDto);
				} else {
					List<SalesDocItemDto> dtoList = new ArrayList<>();
					dtoList.add(salesDocItemDto);
					if (!type.equals(Constants.DEFAULT))
						inputMap.put(th_idb_proc_dto, dtoList);
					else
						defaultInputMap.put(th_idb_proc_dto, dtoList);
				}
			}

			// Creating the output map structure
			for (Map.Entry<TH_IDBRuleInputDto, List<SalesDocItemDto>> entry : inputMap.entrySet()) {
				List<ApproverDataOutputDto> outputList = null;
				String decisionSetId = null;
				if (!entry.getKey().getDistributionChannel().equals(Constants.DEFAULT)
						&& entry.getKey().getDistributionChannel() != null
						&& !entry.getKey().getSalesOrg().equals(Constants.DEFAULT)
						&& entry.getKey().getSalesOrg() != null
						&& !entry.getKey().getMaterialGroup().equals(Constants.DEFAULT)
						&& entry.getKey().getMaterialGroup() != null
						&& !entry.getKey().getMaterialGroup4().equals(Constants.DEFAULT)
						&& entry.getKey().getMaterialGroup4() != null) {
					try {
						outputList = determineDsAndApprovalSet(entry);
						System.err.println("outputList : " + outputList);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					if (null != outputList && !outputList.isEmpty())
						decisionSetId = generateDecisionSetId(outputList, entry);
				}

				if (outputList == null || outputList.isEmpty()) {

					TH_IDBRuleInputDto th_idb_proc_dto = new TH_IDBRuleInputDto();

					th_idb_proc_dto.setDistributionChannel(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup4(Constants.DEFAULT);
					th_idb_proc_dto.setSalesOrg(Constants.DEFAULT);
					for (SalesDocItemDto salesDocItemDto : entry.getValue()) {

						if (defaultInputMap.containsKey(th_idb_proc_dto))
							defaultInputMap.get(th_idb_proc_dto).add(salesDocItemDto);

						else {
							List<SalesDocItemDto> dtoList = new ArrayList<>();
							dtoList.add(salesDocItemDto);
							defaultInputMap.put(th_idb_proc_dto, dtoList);
						}

					}
				}

				if (null != decisionSetId && !outputList.isEmpty()) {
					outputMap.put(decisionSetId, outputList); // setting key
				}
				// with
				// default
				// value and
				// values
				// with
				// output
				// from
				// rules

			}

			if (defaultInputMap != null && !defaultInputMap.isEmpty()) {
				List<ApproverDataOutputDto> outputList = new ArrayList<>();
				try {
					defaultInputMap.entrySet().iterator();
					for (Map.Entry<TH_IDBRuleInputDto, List<SalesDocItemDto>> entry : defaultInputMap.entrySet())
						outputList = determineDsAndApprovalSet(entry);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				String decisionSetId = null;
				if (outputList != null && !outputList.isEmpty()) {
					for (Map.Entry<TH_IDBRuleInputDto, List<SalesDocItemDto>> entry : defaultInputMap.entrySet())
						decisionSetId = generateDecisionSetId(outputList, entry);

				}
				if (null != decisionSetId && (null != outputMap && !outputList.isEmpty())) {
					outputMap.put(decisionSetId, outputList); // setting key
				}
			}
			break;
		default:

			break;
		}
		return outputMap;

	}

	// return Order Map
	private Map<String, List<ApproverDataOutputDto>> generateApprovalMatFroReturnOrder(
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,
			String country, String requestType, String requestCategory) {

		Map<String, List<ApproverDataOutputDto>> outputMap = new HashMap<>();

		switch (strategy) {
		case StrategyConstants.TH_IDB_PROC:

			Map<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> inputMap = new HashMap<>();
			Map<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> defaultInputMap = new HashMap<>();
			String type = new String();

			for (SalesDocItemDto salesDocItemDto : salesDocItemDtolist) {

				TH_IDB_RO_RuleInputDto th_idb_proc_dto = new TH_IDB_RO_RuleInputDto();

				// forming the key

				if (salesDocItemDto.getMaterialGroup() != null && salesDocItemDto.getMaterialGroup4() != null
						&& distributionChannel != null) {
					th_idb_proc_dto.setMaterialGroup(salesDocItemDto.getMaterialGroup());
					th_idb_proc_dto.setMaterialGroup4(salesDocItemDto.getMaterialGroup4());
					th_idb_proc_dto.setDistributionChannel(distributionChannel);
					th_idb_proc_dto.setSalesArea(salesDocItemDto.getSalesArea());
					th_idb_proc_dto.setCountry(country);
					th_idb_proc_dto.setMatrialNumber(salesDocItemDto.getSapMaterialNum());
					th_idb_proc_dto.setRequestType(requestType);
					th_idb_proc_dto.setReturnType(requestCategory);
					th_idb_proc_dto.setSalesTerritory(salesDocItemDto.getSalesTeam());
					th_idb_proc_dto.setSalesOrg(salesOrg);

				} else {
					th_idb_proc_dto.setDistributionChannel(Constants.DEFAULT);
					th_idb_proc_dto.setSalesArea(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup4(Constants.DEFAULT);
					th_idb_proc_dto.setCountry(Constants.DEFAULT);
					th_idb_proc_dto.setMatrialNumber(Constants.DEFAULT);
					th_idb_proc_dto.setRequestType(Constants.DEFAULT);
					th_idb_proc_dto.setReturnType(Constants.DEFAULT);
					th_idb_proc_dto.setSalesTerritory(Constants.DEFAULT);
					th_idb_proc_dto.setSalesOrg(Constants.DEFAULT);
					type = Constants.DEFAULT;

				}

				if (inputMap.containsKey(th_idb_proc_dto) || defaultInputMap.containsKey(th_idb_proc_dto)) {
					if (!type.equals(Constants.DEFAULT))
						inputMap.get(th_idb_proc_dto).add(salesDocItemDto);
					else
						defaultInputMap.get(th_idb_proc_dto).add(salesDocItemDto);
				} else {
					List<SalesDocItemDto> dtoList = new ArrayList<>();
					dtoList.add(salesDocItemDto);
					if (!type.equals(Constants.DEFAULT))
						inputMap.put(th_idb_proc_dto, dtoList);
					else
						defaultInputMap.put(th_idb_proc_dto, dtoList);
				}
			}

			// Creating the output map structure
			for (Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry : inputMap.entrySet()) {
				List<ApproverDataOutputDto> outputList = null;
				String decisionSetId = null;
				if (!entry.getKey().getDistributionChannel().equals(Constants.DEFAULT)
						&& entry.getKey().getDistributionChannel() != null
						&& !entry.getKey().getSalesArea().equals(Constants.DEFAULT)
						&& entry.getKey().getSalesArea() != null
						&& !entry.getKey().getSalesTerritory().equals(Constants.DEFAULT)
						&& entry.getKey().getSalesTerritory() != null
						&& !entry.getKey().getCountry().equals(Constants.DEFAULT)
						&& entry.getKey().getCountry() != null
						&& !entry.getKey().getMaterialGroup().equals(Constants.DEFAULT)
						&& entry.getKey().getMaterialGroup() != null
						&& !entry.getKey().getMaterialGroup4().equals(Constants.DEFAULT)
						&& entry.getKey().getMaterialGroup4() != null
						&& !entry.getKey().getMatrialNumber().equals(Constants.DEFAULT)
						&& entry.getKey().getMatrialNumber() != null
						&& !entry.getKey().getRequestType().equals(Constants.DEFAULT)
						&& entry.getKey().getRequestType() != null
						&& !entry.getKey().getReturnType().equals(Constants.DEFAULT)
						&& entry.getKey().getReturnType() != null
						&& !entry.getKey().getSalesOrg().equals(Constants.DEFAULT)
						&& entry.getKey().getSalesOrg() != null)

					try {
						outputList = determineDsAndApprovalSetForRO(entry);
						System.err.println("outputList : " + outputList);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				if (null != outputList && !outputList.isEmpty())
					decisionSetId = generateDecisionSetIdForROType(outputList, entry);

				if (outputList == null || outputList.isEmpty()) {

					TH_IDB_RO_RuleInputDto th_idb_proc_dto = new TH_IDB_RO_RuleInputDto();

					th_idb_proc_dto.setDistributionChannel(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup(Constants.DEFAULT);
					th_idb_proc_dto.setMaterialGroup4(Constants.DEFAULT);
					th_idb_proc_dto.setSalesArea(Constants.DEFAULT);
					th_idb_proc_dto.setSalesTerritory(Constants.DEFAULT);
					th_idb_proc_dto.setCountry(Constants.DEFAULT);
					th_idb_proc_dto.setRequestType(Constants.DEFAULT);
					th_idb_proc_dto.setReturnType(Constants.DEFAULT);
					th_idb_proc_dto.setMatrialNumber(Constants.DEFAULT);
					th_idb_proc_dto.setSalesOrg(Constants.DEFAULT);
					for (SalesDocItemDto salesDocItemDto : entry.getValue()) {

						if (defaultInputMap.containsKey(th_idb_proc_dto))
							defaultInputMap.get(th_idb_proc_dto).add(salesDocItemDto);

						else {
							List<SalesDocItemDto> dtoList = new ArrayList<>();
							dtoList.add(salesDocItemDto);
							defaultInputMap.put(th_idb_proc_dto, dtoList);
						}

					}
				}

				if (null != decisionSetId && !outputList.isEmpty()) {
					outputMap.put(decisionSetId, outputList); // setting key
				}
			}

			if (defaultInputMap != null && !defaultInputMap.isEmpty()) {
				List<ApproverDataOutputDto> outputList = new ArrayList<>();
				try {
					defaultInputMap.entrySet().iterator();
					for (Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry : defaultInputMap.entrySet())
						outputList = determineDsAndApprovalSetForRO(entry);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				String decisionSetId = null;
				if (outputList != null && !outputList.isEmpty()) {
					for (Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry : defaultInputMap.entrySet())
						decisionSetId = generateDecisionSetIdForROType(outputList, entry);

				}
				if (null != decisionSetId && (null != outputMap && !outputList.isEmpty())) {
					outputMap.put(decisionSetId, outputList); // setting key
				}
			}
			break;
		default:

			break;
		}
		return outputMap;

	}

}

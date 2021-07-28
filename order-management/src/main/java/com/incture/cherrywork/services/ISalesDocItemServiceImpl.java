package com.incture.cherrywork.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.dtos.TH_IDB_RO_RuleInputDto;
import com.incture.cherrywork.dtos.WorkflowResponseEntity;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.ScheduleLineDo;
import com.incture.cherrywork.entities.ScheduleLinePrimaryKeyDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ISalesDocHeaderRepository;
import com.incture.cherrywork.repositories.ISalesDocItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.rules.ApproverDataOutputDto;
import com.incture.cherrywork.rules.RuleInputDto;
import com.incture.cherrywork.rules.THIDBRuleService;
import com.incture.cherrywork.rules.TH_IDBRuleInputDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.sales.constants.StrategyConstants;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;
import com.incture.cherrywork.workflow.repositories.ISalesOrderLevelStatusRepository;
import com.incture.cherrywork.workflow.repositories.IScheduleLineRepository;
import com.incture.cherrywork.workflow.services.THIDROBRuleService;
import com.incture.cherrywork.workflow.services.TriggerImeDestinationService;

@Service("SalesDocItemService")
@Transactional
public class ISalesDocItemServiceImpl implements ISalesDocItemService {

	@Autowired
	private ISalesDocItemRepository salesDocItemRepository;

	@Autowired
	private ISalesDocHeaderRepository salesDocHeaderRepository;

	@Autowired
	private ISalesOrderLevelStatusRepository salesOrderLevelStatusRepository;

	@Autowired
	private IScheduleLineRepository scheduleLineRepository;
	
	@Autowired
	private TriggerImeDestinationService triggerImeService;

	@PersistenceContext
	private EntityManager entityManager;

	private SequenceNumberGen sequenceNumberGen;

	@Override
	public ResponseEntity getInputDtoDataSet(SalesOrderHeaderInput soInput) {
		System.err.println("inside getInputDtoDataSet " + soInput.toString());
		ResponseEntity responsentity = new ResponseEntity(soInput, HttpStatus.BAD_REQUEST,
				"Sales Order Item List is empty", ResponseStatus.FAILED);

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "Trigger FAILURE", ResponseStatus.FAILED,
				null, null, "");
		try {
			if (soInput.getSalesOrderItemIdList().isEmpty()) {
				return new ResponseEntity(soInput, HttpStatus.BAD_REQUEST, "Sales Order Item List is empty",
						ResponseStatus.FAILED);
			} else {
				if (soInput.getSalesOrderHeaderId() != null) {
					List<SalesDocItemDto> salesDocItemDtoList = listOfItemsFromMultiItemId(soInput);
					System.err.println("salesDocItemDtoList " + salesDocItemDtoList.toString());
					Map<String, List<ApproverDataOutputDto>> listDto = createAndReturnApprovalMap(
							soInput.getRequestId(), soInput.getSalesOrderHeaderId(), salesDocItemDtoList,
							soInput.getStrategy(), soInput.getDistributionChannel(), soInput.getSalesOrg(),
							soInput.getCountry(), soInput.getCustomerPo(), soInput.getRequestType(),
							soInput.getRequestCategory());
					System.err.println("decisionSet listDto  " + listDto.toString());

					List<ApproverDataOutputDto> listapproverDto = null;
					if (listDto.isEmpty()) {
						return new ResponseEntity(soInput, HttpStatus.NO_CONTENT, "listapproverDto is null " + soInput,
								ResponseStatus.FAILED);
					} else {
						SalesOrderLevelStatusDto salesorderlevelstatusdto = new SalesOrderLevelStatusDto();

						// Configure gson
						GsonBuilder gsonBuilder = new GsonBuilder();
						Gson gson = gsonBuilder.create();

						String LevelStatusSerialId = "";

						int countDataSetKey = 0;
						for (Map.Entry<String, List<ApproverDataOutputDto>> entry : listDto.entrySet()) {
							System.err.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
							listapproverDto = entry.getValue();
							System.err.println("listapproverDto " + listapproverDto);

							System.err.println("decisionSet listapproverDto " + listapproverDto);

							for (int listIterate = 0; listIterate < listapproverDto.size(); listIterate++) {
								ApproverDataOutputDto approverDataOutput = listapproverDto.get(listIterate);

								// setting the salesorderlevelstatusdto
								salesorderlevelstatusdto.setDecisionSetId(entry.getKey());
								salesorderlevelstatusdto.setApproverType(approverDataOutput.getApprovalType());
								salesorderlevelstatusdto.setLevel(approverDataOutput.getLevel());
								salesorderlevelstatusdto.setUserGroup(approverDataOutput.getApprover());
								salesorderlevelstatusdto.setLevelStatus(StatusConstants.LEVEL_NEW);

								// create a record in the level status table
								LevelStatusSerialId = saveOrUpdateSalesOrderLevelStatusSynchronized(
										salesorderlevelstatusdto);

								System.err.println("decisionSet levelStatusSerailId inserted succefullly  ="
										+ LevelStatusSerialId);

							}

							String approverDtoList = null;
							System.err.println("outside for after saving..");
							try {
								approverDtoList = gson.toJson(entry.getValue());
							} catch (Exception e) {
								System.err.println("exception while gson to json" + e.getMessage());
								e.printStackTrace();
							}

							System.err.println("decisionSet approverDtoList" + approverDtoList);
							// setting the decisionSetAmount
							List<SalesDocItemDto> itemStoListByDS = null;
							itemStoListByDS = getSalesDocItemsByDecisionSetId(entry.getKey());
							Double decisionSetAmount = 0.0;
							System.err.println("decisionSet 296");
							// calculating total amout and setting the final
							// value
							for (SalesDocItemDto itemDto : itemStoListByDS) {
								decisionSetAmount = decisionSetAmount + Double.parseDouble(itemDto.getNetWorth());
								System.err.println("decisionSet 301");
							}

							Double threshold = 100000.0;

							System.err.println("decisionSet 306");

							response = DecisionSetWorkflowTrigger(soInput.getSalesOrderHeaderId(),
									soInput.getRequestId(), entry.getKey(), approverDtoList, threshold,
									decisionSetAmount, soInput.getHeaderBlocReas(), soInput.getSoCreatedECC(),
									soInput.getCountry(), soInput.getCustomerPo(), soInput.getRequestType(),
									soInput.getRequestCategory(), soInput.getSalesOrderType(), soInput.getSoldToParty(),
									soInput.getShipToParty(), soInput.getDivision(), soInput.getDistributionChannel(),
									soInput.getSalesOrg(), soInput.getReturnReason());

							System.err.println("workflowtrigger.DecisionSetWorkflowTrigger =" + response);

							countDataSetKey++;
						}

						responsentity.setMessage("" + countDataSetKey);
						responsentity.setData(response);
						responsentity.setStatusCode(HttpStatus.ACCEPTED);
						responsentity.setStatus(ResponseStatus.SUCCESS);
					}

				}
			}
		} catch (Exception e) {
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
		return responsentity;

	}

	public List<SalesDocItemDto> listOfItemsFromMultiItemId(SalesOrderHeaderInput soInput) {
		List<String> salesDocItemIdList = soInput.getSalesOrderItemIdList();
		List<SalesDocItemDto> salesDocItemDtoList = new ArrayList<>();
		for (String itemId : salesDocItemIdList) {
			SalesDocItemDto item = getSalesDocItemById(itemId, soInput.getSalesOrderHeaderId());
			if (item != null) {
				salesDocItemDtoList.add(item);
			}
		}
		return salesDocItemDtoList;
	}

	public SalesDocItemDto getSalesDocItemById(String salesItemId, String salesHeaderId) {
		// Creating Sales Header Entity For inserting in Composite PK
		SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
		// Setting Sales Header Primary Key
		salesDocHeader.setSalesOrderNum(salesHeaderId);
		// return null;

		String query = "from SalesDocItemDo i where i.salesDocItemKey.salesDocHeader.salesOrderNum=:soNum and i.salesDocItemKey.salesItemOrderNo=:itemId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("soNum", salesHeaderId);
		q1.setParameter("itemId", salesItemId);

		SalesDocItemPrimaryKeyDo primaryKeyDO = new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader);
		SalesDocItemDo itemDo = (SalesDocItemDo) q1.getSingleResult();
		itemDo.setSalesDocItemKey(primaryKeyDO);
		SalesDocItemDto itemDto = ObjectMapperUtils.map(itemDo, SalesDocItemDto.class);
		itemDto.setSalesItemOrderNo(salesItemId);
		itemDto.setSalesHeaderNo(salesHeaderId);
		for (ScheduleLineDto schDto : itemDto.getScheduleLineList()) {
			schDto.setSalesItemOrderNo(salesItemId);
			schDto.setSalesHeaderNo(salesHeaderId);
			schDto.setScheduleLineNum(ServicesUtil.randomId());
		}
		String query2 = "delete from ScheduleLineDo where scheduleLineKey.soItem.salesDocItemKey.salesDocHeader.salesOrderNum=:soNum";
		Query q2 = entityManager.createQuery(query2);
		q2.setParameter("soNum", salesHeaderId);
		q2.executeUpdate();
		for(ScheduleLineDto schDto : itemDto.getScheduleLineList()){
			ScheduleLineDo sch = importDtoSch(schDto);
			scheduleLineRepository.save(sch);
		}
		return itemDto;

	}

	public Map<String, List<ApproverDataOutputDto>> createAndReturnApprovalMap(String requestId, String salesOrderId,
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,
			String country, String customerPo, String requestType, String requestCategory) {
		return createApprovalMapFromRules(requestId, salesOrderId, salesDocItemDtolist, strategy, distributionChannel,
				salesOrg, country, customerPo, requestType, requestCategory);
	}

	private Map<String, List<ApproverDataOutputDto>> createApprovalMapFromRules(String requestId, String salesOrderId,
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,
			String country, String customerPo, String requestType, String requestCategory) {

		if (customerPo != null && !customerPo.isEmpty()) {
			String checkTypeOfSo = customerPo.substring(0, 2);
			// if of Return Order Type and Return ExchangeType
			if (checkTypeOfSo.equals(Constants.RETURN_ORDER) || checkTypeOfSo.equals(Constants.RETURN_EXCHANGE)) {

				return generateApprovalMatFroReturnOrder(salesDocItemDtolist, strategy, distributionChannel, salesOrg,
						country, requestType, requestCategory);
			}
		}

		return generateApprovalMatForBso(salesDocItemDtolist, strategy, distributionChannel, salesOrg);
	}

	private Map<String, List<ApproverDataOutputDto>> generateApprovalMatFroReturnOrder(
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,
			String country, String requestType, String requestCategory) {

		Map<String, List<ApproverDataOutputDto>> outputMap = new HashMap<>();
		System.err.println(
				"[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] started with salesDocItemDtolist: "
						+ salesDocItemDtolist.toString() + " strategy: " + strategy + " distributionChannel: "
						+ distributionChannel + " salesOrg: " + salesOrg + " country:" + country + " requestType:"
						+ requestType + " requestCategory: " + requestCategory);

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
					System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check1");
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
					System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check2");
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
					if (!type.equals(Constants.DEFAULT)) {
						inputMap.get(th_idb_proc_dto).add(salesDocItemDto);
						System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check3.1");
					} else {
						defaultInputMap.get(th_idb_proc_dto).add(salesDocItemDto);
						System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check3.2");
					}
				} else {
					System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check4");
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
						&& !entry.getKey().getCountry().equals(Constants.DEFAULT) && entry.getKey().getCountry() != null
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
						System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check5");
						outputList = determineDsAndApprovalSetForRO(entry);
						System.err.println("outputList : " + outputList);
					} catch (Exception e) {
						// logger.error(e.getMessage());
						System.err.println(e.getMessage());
					}
				if (null != outputList && !outputList.isEmpty()) {

					System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check6");
					decisionSetId = generateDecisionSetIdForROType(outputList, entry);
				}

				if (outputList == null || outputList.isEmpty()) {
					System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check7");

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
					System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check8");
					outputMap.put(decisionSetId, outputList); // setting key
				}
			}

			if (defaultInputMap != null && !defaultInputMap.isEmpty()) {
				System.err.println("[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] check9");
				List<ApproverDataOutputDto> outputList = new ArrayList<>();
				try {
					defaultInputMap.entrySet().iterator();
					for (Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry : defaultInputMap.entrySet())
						outputList = determineDsAndApprovalSetForRO(entry);
				} catch (Exception e) {
					// logger.error(e.getMessage());
					System.err.println(e.getMessage());
				}
				System.err.println(
						"[DecisionSetCreationImpl][generateApprovalMatFroReturnOrder] outputList: " + outputList

				);
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
		System.err.println("[generateApprovalMatFroReturnOrder] outputMap:" + outputMap);
		return outputMap;

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
						// logger.error(e.getMessage());
						System.err.println(e.getMessage());
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
					// logger.error(e.getMessage());
					System.err.println(e.getMessage());
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

	@SuppressWarnings("unchecked")
	private List<ApproverDataOutputDto> determineDsAndApprovalSetForRO(
			Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry) {

		THIDROBRuleService rulesService = new THIDROBRuleService();
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

	private String generateDecisionSetIdForROType(List<ApproverDataOutputDto> outputList,
			Map.Entry<TH_IDB_RO_RuleInputDto, List<SalesDocItemDto>> entry) {
		String decisionSetId = null;

		if (outputList != null && !outputList.isEmpty()) {

			// if its of Blocked sales order type
			sequenceNumberGen = SequenceNumberGen.getInstance();
			Session session = entityManager.unwrap(Session.class);
			System.err.println("session : " + session);
			String tempId = sequenceNumberGen.getNextSeqNumber("RO_DS_", 6, session);
			System.err.println("returnReqNum " + tempId);
			decisionSetId = tempId;

			List<SalesDocItemDto> itemList = getSalesDocItemsByDecisionSetId(decisionSetId);
			System.err.println("decision Set check " + itemList);
			if (itemList.isEmpty()) {
				if (null != decisionSetId && !decisionSetId.isEmpty()) {
					for (SalesDocItemDto itemDto : entry.getValue()) {
						try {
							itemDto.setDecisionSetId(decisionSetId);
							System.err.println(
									"[generateDecisionSetIdForROType][in for loop] itemDto: " + itemDto.toString());

							String res = saveOrUpdateSalesDocItemForDS(itemDto);
							System.err.println("[generateDecisionSetIdForROType][in for loop] res: " + res);
							// salesDocItemDao.updateSalesDocItemWithDecisionSet(decisionSetId,
							// itemDto.getSalesItemOrderNo(),
							// itemDto.getSalesHeaderNo());
						} catch (ExecutionFault e) {
							// logger.error(e.getMessage());
							System.err.println(e.getMessage());
						}
					}
					return decisionSetId;
				}
			}

		}

		return null;
	}

	public String saveOrUpdateSalesOrderLevelStatusSynchronized(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault {
		System.err.println("salesOrderLevelStatusDto: " + salesOrderLevelStatusDto.toString());
		try {

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = null;

			salesOrderLevelStatusDo = importDtoLevel(salesOrderLevelStatusDto);
			System.err.println("salesOrderLevelStatusDo: " + salesOrderLevelStatusDo.toString());

			if (salesOrderLevelStatusDo != null) {
				try {
					salesOrderLevelStatusRepository.save(salesOrderLevelStatusDo);
				} catch (NoResultException | NullPointerException e) {
					throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
				} catch (Exception e) {
					System.err.println("exception in saveOrUpdateSalesOrderLevelStatusSynchronized while saving: "
							+ e.getMessage());
					e.printStackTrace();
				}
				return salesOrderLevelStatusDo.getLevelStatusSerialId();
			}
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId) {
		String query = "from SalesDocItemDo item where item.decisionSetId = :dsId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("dsId", decisionSetId);
		return ObjectMapperUtils.mapAll(q1.getResultList(), SalesDocItemDto.class);
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

	private String generateDecisionSetId(List<ApproverDataOutputDto> outputList,
			Map.Entry<TH_IDBRuleInputDto, List<SalesDocItemDto>> entry) {
		String decisionSetId = null;

		if (outputList != null && !outputList.isEmpty()) {

			// if its of Blocked sales order type
			sequenceNumberGen = SequenceNumberGen.getInstance();
			Session session = entityManager.unwrap(Session.class);
			System.err.println("session : " + session);
			String tempId = sequenceNumberGen.getNextSeqNumber("DS_", 6, session);
			System.err.println("returnReqNum " + tempId);

			decisionSetId = tempId;

			List<SalesDocItemDto> itemList = getSalesDocItemsByDecisionSetId(decisionSetId);
			System.err.println("decision Set check " + itemList);
			if (itemList.isEmpty()) {
				if (null != decisionSetId && !decisionSetId.isEmpty()) {
					for (SalesDocItemDto itemDto : entry.getValue()) {
						try {
							itemDto.setDecisionSetId(decisionSetId);
							saveOrUpdateSalesDocItemForDS(itemDto);
							// salesDocItemDao.updateSalesDocItemWithDecisionSet(decisionSetId,
							// itemDto.getSalesItemOrderNo(),
							// itemDto.getSalesHeaderNo());
						} catch (ExecutionFault e) {
							// logger.error(e.getMessage());
							System.err.println(e.getMessage());
						}
					}
					return decisionSetId;
				}
			}

		}

		return null;
	}

	public String saveOrUpdateSalesDocItemForDS(SalesDocItemDto salesDocItemDto) throws ExecutionFault {

		// salesDocHeaderDo.setRequestId(ServicesUtil.randomId());
		System.err.println("[saveOrUpdateSalesDocItemForDS] salesDocItemDto: " + salesDocItemDto.toString());
		SalesDocItemDo savedSalesDocItemDo = null;
		try {
			SalesDocItemDo itemDo = importDto(salesDocItemDto);
			try {
				savedSalesDocItemDo = salesDocItemRepository.save(itemDo);
			} catch (Exception e) {
				System.err.println("exception occured while saving(decision set): " + e.getMessage());
				e.printStackTrace();
			}
			System.err.println("[saveOrUpdateSalesDocItemForDS] savedSalesDocItemDo" + savedSalesDocItemDo.toString());
			System.err.println("Saved Sales Doc Item");

			// SalesDocItemDo salesDocItemDo = importDto(salesDocItemDto);
			// System.err.println("[saveOrUpdateSalesDocItemForDS]
			// salesDocItemDo: "+salesDocItemDo.toString());
			// salesDocItemRepository.save(salesDocItemDo);

			return "Sales Document Item is successfully created with =";
		} catch (NoResultException |

				NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	public SalesDocItemDo importDto(SalesDocItemDto fromDto) {
		SalesDocItemDo salesDocItemDo = null;
		if (fromDto != null) {
			salesDocItemDo = new SalesDocItemDo();

			salesDocItemDo.setSapMaterialNum(fromDto.getSapMaterialNum());
			salesDocItemDo.setBatchNum(fromDto.getBatchNum());
			salesDocItemDo.setSplPrice(fromDto.getSplPrice());
			salesDocItemDo.setMaterialGroup(fromDto.getMaterialGroup());
			salesDocItemDo.setShortText(fromDto.getShortText());
			salesDocItemDo.setItemCategory(fromDto.getItemCategory());
			salesDocItemDo.setItemType(fromDto.getItemType());
			salesDocItemDo.setSalesUnit(fromDto.getSalesUnit());
			salesDocItemDo.setItemBillingBlock(fromDto.getItemBillingBlock());
			salesDocItemDo.setItemDlvBlock(fromDto.getItemDlvBlock());
			salesDocItemDo.setPartialDlv(fromDto.getPartialDlv());
			salesDocItemDo.setRefDocNum(fromDto.getRefDocNum());
			salesDocItemDo.setRefDocItem(fromDto.getRefDocItem());
			salesDocItemDo.setPlant(fromDto.getPlant());
			salesDocItemDo.setStorageLoc(fromDto.getStorageLoc());
			salesDocItemDo.setNetPrice(Double.parseDouble(fromDto.getNetPrice()));
			salesDocItemDo.setDocCurrency(fromDto.getDocCurrency());
			salesDocItemDo.setPricingUnit(fromDto.getPricingUnit());
			salesDocItemDo.setCoudUnit(fromDto.getCoudUnit());
			salesDocItemDo.setNetWorth(Double.parseDouble(fromDto.getNetWorth()));
			salesDocItemDo.setOverallStatus(fromDto.getOverallStatus());
			salesDocItemDo.setDeliveryStatus(fromDto.getDeliveryStatus());
			salesDocItemDo.setReasonForRejection(fromDto.getReasonForRejection());
			salesDocItemDo.setReasonForRejectionText(fromDto.getReasonForRejectionText());
			salesDocItemDo.setMaterialGroupFor(fromDto.getMaterialGroup4());
			salesDocItemDo.setBaseUnit(fromDto.getBaseUnit());
			salesDocItemDo.setHigherLevelItem(fromDto.getHigherLevelItem());
			salesDocItemDo.setTaxAmount(fromDto.getTaxAmount());
			salesDocItemDo.setOldMatCode(fromDto.getOldMatCode());
			salesDocItemDo.setConvDen(fromDto.getConvDen());
			salesDocItemDo.setConvNum(fromDto.getConvNum());
			salesDocItemDo.setCuConfQtyBase(fromDto.getCuConfQtyBase());
			salesDocItemDo.setCuConfQtySales(fromDto.getCuConfQtySales());
			salesDocItemDo.setCuReqQtySales(fromDto.getCuReqQtySales());
			salesDocItemDo.setOrderedQtySales(fromDto.getOrderedQtySales());
			salesDocItemDo.setDecisionSetId(fromDto.getDecisionSetId());
			salesDocItemDo.setItemStagingStatus(fromDto.getItemStagingStatus());
			salesDocItemDo.setItemCategText(fromDto.getItemCategText());
			salesDocItemDo.setSalesArea(fromDto.getSalesArea());
			salesDocItemDo.setSalesTeam(fromDto.getSalesTeam());
			salesDocItemDo.setMatExpiryDate(fromDto.getMatExpiryDate());
			salesDocItemDo.setSerialNumber(fromDto.getSerialNumber());

			// Converting list level to entity using import List method and
			// checking the content of it
			List<ScheduleLineDo> scheduleLineList = importList(fromDto.getScheduleLineList());
			if (scheduleLineList != null && !scheduleLineList.isEmpty()) {
				salesDocItemDo.setScheduleLineList(scheduleLineList);
			}

			// Converting Date from String
			// salesDocItemDo.setFirstDeliveryDate(ConvertStringToDate(fromDto.getFirstDeliveryDate()));
			salesDocItemDo.setFirstDeliveryDate(fromDto.getFirstDeliveryDate());

			// Setting Composite Primary Key and Foreign Key
			SalesDocHeaderDo salesDocHeaderDo = new SalesDocHeaderDo();
			// Setting Foreign Key
			salesDocHeaderDo.setSalesOrderNum(fromDto.getSalesHeaderNo());
			// Setting Composite Primary Key
			salesDocItemDo
					.setSalesDocItemKey(new SalesDocItemPrimaryKeyDo(fromDto.getSalesItemOrderNo(), salesDocHeaderDo));

			// try {
			// // Setting Comment List from comment table
			// saveCommentList(fromDto.getCommentDtoList(),
			// fromDto.getReturnReqNum());
			// } catch (ExecutionFault e) {
			// return null;
			// }

		}
		return salesDocItemDo;
	}

	public List<ScheduleLineDo> importList(List<ScheduleLineDto> list) {
		if (list != null && !list.isEmpty()) {
			List<ScheduleLineDo> dtoList = new ArrayList<>();
			for (ScheduleLineDto entity : list) {

				dtoList.add(importDtoSch(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	public ScheduleLineDo importDtoSch(ScheduleLineDto dto) {
		ScheduleLineDo scheduleLineDo = null;
		if (dto != null) {
			scheduleLineDo = new ScheduleLineDo();

			scheduleLineDo.setBaseUnit(dto.getBaseUnit());
			scheduleLineDo.setConfQtySales(dto.getConfQtySales());
			scheduleLineDo.setReqQtyBase(dto.getReqQtyBase());
			scheduleLineDo.setReqQtySales(dto.getReqQtySales());
			scheduleLineDo.setSalesUnit(dto.getSalesUnit());
			scheduleLineDo.setSchlineDeliveryBlock(dto.getSchlineDeliveryBlock());
			scheduleLineDo.setRelfordelText(dto.getRelfordelText());

			// Setting Foreign Keys
			SalesDocItemDo salesDocItemDo = new SalesDocItemDo();
			SalesDocHeaderDo salesDocHeaderDo = new SalesDocHeaderDo();
			salesDocHeaderDo.setSalesOrderNum(dto.getSalesHeaderNo());
			salesDocItemDo
					.setSalesDocItemKey(new SalesDocItemPrimaryKeyDo(dto.getSalesItemOrderNo(), salesDocHeaderDo));

			scheduleLineDo.setScheduleLineKey(new ScheduleLinePrimaryKeyDo(dto.getScheduleLineNum(), salesDocItemDo));
		}
		return scheduleLineDo;
	}

	public SalesOrderLevelStatusDo importDtoLevel(SalesOrderLevelStatusDto dto) {

		SalesOrderLevelStatusDo salesOrderLevelStatusDo = null;

		if (dto != null) {
			salesOrderLevelStatusDo = new SalesOrderLevelStatusDo();

			salesOrderLevelStatusDo.setApproverType(dto.getApproverType());
			salesOrderLevelStatusDo.setDecisionSetId(dto.getDecisionSetId());
			salesOrderLevelStatusDo.setLevel(dto.getLevel());
			salesOrderLevelStatusDo.setLevelStatus(dto.getLevelStatus());
			salesOrderLevelStatusDo.setUserGroup(dto.getUserGroup());

			// Setting Primary Key
			if (dto.getLevelStatusSerialId() != null) {
				salesOrderLevelStatusDo.setLevelStatusSerialId(dto.getLevelStatusSerialId());
			}

			// Converting list level to entity using import List method and
			// checking the content of it
			// if (dto.getTaskStatusList() != null &&
			// !dto.getTaskStatusList().isEmpty()) {
			// List<SalesOrderTaskStatusDo> soTaskStatusDoList =
			// soTaskStatusDao.importList(dto.getTaskStatusList());//
			// if (soTaskStatusDoList != null && !soTaskStatusDoList.isEmpty())
			// {
			// salesOrderLevelStatusDo.setTaskStatusList(soTaskStatusDoList);
			// }
			// }
		}

		return salesOrderLevelStatusDo;
	}

	public WorkflowResponseEntity DecisionSetWorkflowTrigger(String salesOrderNo, String requestId, String keydataSet,
			String approverDtoList, double threshold, double decisionSetAmount, String headerBlocReas,
			String soCreatedECC, String country, String customerPo, String requestType, String requestCategory,
			String salesOrderType, String soldToParty, String shipToParty, String division, String distributionChannel,
			String salesOrg, String returnReason) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "Trigger FAILURE", ResponseStatus.FAILED,
				null, null, "");
		HttpURLConnection connection1 = null;
		try {
			System.err.println("decisionSet 401");

			// getting XSRF TOKEN
			String xcsrfToken = null;
			List<String> cookies = null;

			// String url =
			// "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";

			// Map<String, Object> map =
			// DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

			String url = ComConstants.WORKFLOW_REST_BASE_URL + "/v1/workflow-instances";
			System.err.println("decisionSet 416" + url);

			URL urlObj = new URL(url);
			if (jwToken != null) {
				// Triggering the Wrofklow by settign payload and XSRF token.
				System.err.println("decisionSet 430");
				connection1 = (HttpURLConnection) urlObj.openConnection();
				connection1.setRequestMethod("POST");
				System.err.println("decisionSet XSRF Token" + jwToken);
				// SET COOKIES
				connection1.setRequestProperty("Authorization", "Bearer " + jwToken);
				connection1.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				connection1.setRequestProperty("Accept", "application/json");
				connection1.setRequestProperty("DataServiceVersion", "2.0");
				connection1.setRequestProperty("X-Requested-With", "XMLHttpRequest");
				// connection1.setRequestProperty("Accept-Encoding", "gzip,
				// deflate");
				connection1.setRequestProperty("Accept-Charset", "UTF-8");
				connection1.setDoInput(true);
				connection1.setDoOutput(true);
				connection1.setUseCaches(false);
				String payload = "{\"definitionId\":\"decisionsetdetermination\",\"context\":{\"salesOrderNo\":\""
						+ salesOrderNo + "\",\"requestId\":\"" + requestId + "\",\"decisionSetId\":\"" + keydataSet
						+ "\",\"approverDtoList\":" + approverDtoList + "" + ",\"threshold\":\"" + threshold
						+ "\",\"decisionSetAmount\":\"" + decisionSetAmount + "\",\"headerBlocReas\":\""
						+ headerBlocReas + "\",\"soCreatedECC\":\"" + soCreatedECC + "\",\"country\":\"" + country
						+ "\",\"customerPo\":\"" + customerPo + "\",\"requestType\":\"" + requestType
						+ "\",\"salesOrderType\":\"" + salesOrderType + "\",\"shipToParty\":\"" + shipToParty
						+ "\",\"soldToParty\":\"" + soldToParty + "\",\"division\":\"" + division
						+ "\",\"distributionChannel\":\"" + distributionChannel + "\",\"salesOrg\":\"" + salesOrg
						+ "\",\"returnReason\":\"" + returnReason + "\"}}";
				System.err.println("decisionSet Workflow  Payload :" + payload);
				System.err.println("decisionSet 458");
				DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
				dataOutputStream.write(payload.getBytes());
				dataOutputStream.flush();
				connection1.connect();
				dataOutputStream.close();
				int responseCode = connection1.getResponseCode();
				response.setResponseStatusCode(responseCode);
				System.err.println("decisionSet 466");
				JSONObject triggerResponsMessage = null;
				try {
					String json = getDataFromStream(connection1.getInputStream());
					System.err.println("json: " + json);
					triggerResponsMessage = new JSONObject(json);
				} catch (Exception e) {
					System.err.println("Json Exception: " + e.getMessage());
					e.printStackTrace();
				}
				response.setResponseMessage(triggerResponsMessage.toMap());

				System.err.println("decisionSet 470");
				response.setMessage("Success");
				response.setStatus(ResponseStatus.SUCCESS);
				response.setResponseJson((null));
			} else {
				System.err.println("decisionSet 476 else response");
				return response;
			}

		} catch (Exception e) {

			System.err.println("Trigger FAILURE " + e.getMessage());
			System.err.println("decisionSet 483" + e.getMessage());
			response.setMessage("Trigger FAILURE EXCEPTION	" + e.getMessage());
			response.setStatus(ResponseStatus.FAILED);
			return response;

		}
		System.err.print("decisionSet 488 end response");
		return response;

	}

	private String getDataFromStream(InputStream stream) throws IOException {
		// StringBuffer dataBuffer = new StringBuffer();
		// BufferedReader inStream = new BufferedReader(new
		// InputStreamReader(stream));
		// String data = "";
		// while ((data = IOUtils.toString(inputStream, StandardCharsets.UTF_8))
		// != null) {
		// String ;
		// dataBuffer.append(data);
		// }
		// inStream.close();
		// return dataBuffer.toString();

		int bufferSize = 1024;
		char[] buffer = new char[bufferSize];
		StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
		for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0;) {
			out.append(buffer, 0, numRead);
		}
		return out.toString();
	}
	
	@Override
	public ResponseEntity triggerImePostSoDsCompletion(String salesOrderNumber, String decisionSetId) throws URISyntaxException, IOException {

		System.err.println("salesOrderNumber : " + salesOrderNumber);
		System.err.println("decisionSetId : " + decisionSetId);

		// List<SalesDocItemDto> salesDocItemList = new ArrayList<>();
		List<String> decisionSetIdList = new ArrayList<>();
		Set<Integer> levelStatus = new HashSet<>();

		ResponseEntity response = new ResponseEntity(decisionSetId, HttpStatus.ACCEPTED, decisionSetId,
				ResponseStatus.SUCCESS);
		//
		// try {
		// salesDocItemList =
		// salesDocItemRepo.listOfItemsInSalesOrder(salesOrderNumber);
		// } catch (Exception e) {
		// return new ResponseEntity(response, HttpStatus.BAD_REQUEST,
		// DkshConstants.CREATION_FAILED,
		// ResponseStatus.FAILED);
		// }

		try {
			decisionSetIdList = getDSBySalesHeaderID(salesOrderNumber);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST, ComConstants.CREATION_FAILED,
					ResponseStatus.FAILED);
		}

		System.err.println("salesDocItemList : " + decisionSetIdList.toString());

		// if (null != salesDocItemList && !salesDocItemList.isEmpty()) {
		// for (SalesDocItemDto itemDto : salesDocItemList) {
		// if (null != itemDto.getDecisionSetId()) {
		// decisionSetIdSet.add(itemDto.getDecisionSetId());
		// }
		// }
		// }

		if (!decisionSetIdList.isEmpty()) {
			for (String dsId : decisionSetIdList) {

				List<SalesOrderLevelStatusDo> salesOrderLevelStatusDoList = new ArrayList<>();
				List<SalesOrderLevelStatusDto> salesOrderLevelStatusDtoList = new ArrayList<>();
				try {
					salesOrderLevelStatusDoList = salesOrderLevelStatusRepository.getSalesOrderLevelStatusByDecisionSet(dsId);

					for(SalesOrderLevelStatusDo levelDo:salesOrderLevelStatusDoList){
						SalesOrderLevelStatusDto levelDto = ObjectMapperUtils.map(levelDo, SalesOrderLevelStatusDto.class);
						if (levelDo.getLevelStatusSerialId() != null) {
							levelDto.setLevelStatusSerialId(levelDo.getLevelStatusSerialId());
						}
						salesOrderLevelStatusDtoList.add(levelDto);
					}
					System.err.println("salesOrderLevelStatusDtoList" + salesOrderLevelStatusDtoList.toString());
				} catch (NoResultException e) {
					//logger.error(e.getMessage());
					System.err.println(e.getMessage());
					return new ResponseEntity(response, HttpStatus.BAD_REQUEST, ComConstants.CREATION_FAILED,
							ResponseStatus.FAILED);
				}

				if (null != salesOrderLevelStatusDtoList) {
					for (SalesOrderLevelStatusDto salesOrderLevelStatusDto : salesOrderLevelStatusDtoList) {
						levelStatus.add(salesOrderLevelStatusDto.getLevelStatus());
					}
				}
			}
		}
		System.err.println("level Status: "+levelStatus);

		if (((levelStatus.size() == 1) && levelStatus.contains(ComConstants.LEVEL_COMPLETE))
				|| (levelStatus.size() == 2 && levelStatus.contains(ComConstants.LEVEL_ABANDON))) {
			ResponseEntity triggerResponse = triggerImeService.triggerImePostDS(salesOrderNumber);
			if (triggerResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST))
				return new ResponseEntity(triggerResponse, HttpStatus.BAD_REQUEST, ComConstants.TRIGGER_FAILED,
						ResponseStatus.FAILED);
		} else {
			ResponseEntity triggerResponse = new ResponseEntity(decisionSetId, HttpStatus.BAD_REQUEST, decisionSetId,
					ResponseStatus.FAILED);
			return new ResponseEntity(triggerResponse, HttpStatus.BAD_REQUEST, ComConstants.PENDING_DS_LEVELS,
					ResponseStatus.FAILED);
		}

		return new ResponseEntity(response, HttpStatus.ACCEPTED, decisionSetId, ResponseStatus.SUCCESS);
	}
	
	public List<String> getDSBySalesHeaderID(String salesHeaderId) {

		return entityManager.createQuery(
				"select distinct ds.decisionSetId from SalesDocItemDo ds where ds.salesDocItemKey.salesDocHeader.salesOrderNum = :salesOrderNum and ds.decisionSetId is not null",
				String.class).setParameter("salesOrderNum", salesHeaderId).getResultList();
	}

	

}

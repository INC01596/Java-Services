package com.incture.cherrywork.services.new_workflow;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static com.incture.cherrywork.WConstants.DkshConstants.CREATION_FAILED;
import static com.incture.cherrywork.WConstants.DkshConstants.DATA_FOUND;
import static com.incture.cherrywork.WConstants.DkshConstants.EMPTY_LIST;
import static com.incture.cherrywork.WConstants.DkshConstants.EXCEPTION_FAILED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.DkshStatusConstants;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.workflow.DlvBlockReleaseMapDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.ServicesUtil;
import com.incture.cherrywork.workflow.repo.IDlvBlockReleaseMapRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderItemStatusRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderLevelStatusRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderTaskStatusRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderTaskStatusesRepository;


@SuppressWarnings("unused")
@Service("WorkFlowService")
@Transactional
public class WorkflowServices implements IWorkflowServices{

	@Autowired
	ISalesOrderTaskStatusRepository salesOrderTaskStatusRepository;
	
	@Autowired
	ISalesOrderLevelStatusRepository salesOrderLevelStatusRepository;
	
	@Autowired
	IDlvBlockReleaseMapRepository dlvBlockReleaseMapRepository;
	
	@Autowired
	private ISalesOrderItemStatusRepository salesOrderItemStatusRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public String saveOrUpdateSalesOrderTaskStatusSynchronized(SalesOrderTaskStatusDto salesOrderTaskStatusDto) {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = null;
			salesOrderTaskStatusDo = ObjectMapperUtils.map(salesOrderTaskStatusDto, SalesOrderTaskStatusDo.class);
			if (salesOrderTaskStatusDo != null) {
//				Session session = sessionfactory.openSession();
//				Transaction tx1 = session.beginTransaction();
				salesOrderTaskStatusRepository.save(salesOrderTaskStatusDo);
//				tx1.commit();
//				session.clear();
//				session.close();

				return salesOrderTaskStatusDo.getTaskStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException | IllegalStateException e) {
			throw e;
			// throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public SalesOrderTaskStatusDto getSalesOrderTaskStatusById(String salesOrderTaskStatusId) {
		
		Session session = entityManager.unwrap(Session.class);
		return ObjectMapperUtils.map(session.get(SalesOrderTaskStatusDo.class, salesOrderTaskStatusId),SalesOrderTaskStatusDto.class);
	}

	@Override
	public String saveOrUpdateSalesOrderLevelStatusSynchronized(SalesOrderLevelStatusDto salesOrderLevelStatusDto) {
		try {

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = null;

			salesOrderLevelStatusDo = ObjectMapperUtils.map(salesOrderLevelStatusDto, SalesOrderLevelStatusDo.class);
			if (salesOrderLevelStatusDo != null) {
//				Session session = sessionfactory.openSession();
//				Transaction tx1 = session.beginTransaction();
				salesOrderLevelStatusRepository.save(salesOrderLevelStatusDo);
//				tx1.commit();
//				session.clear();
//				session.close();
				return salesOrderLevelStatusDo.getLevelStatusSerialId();
			}
		} catch (NoResultException | NullPointerException e) {
			try {
				throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
			} catch (ExecutionFault e1) {
				
				e1.printStackTrace();
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	
	@Override
	public ResponseEntity<Object> getDlvBlockReleaseMapBydlvBlockCodeForDisplayOnly(String dlvBlockCode) {
		try {
			if (!HelperClass.checkString(dlvBlockCode)) {
				DlvBlockReleaseMapDto dlvBlockReleaseMapDto = dlvBlockReleaseMapRepository
						.getDlvBlockReleaseMapBydlvBlockCode(dlvBlockCode);
				if (dlvBlockReleaseMapDto != null && dlvBlockReleaseMapDto.getDisplay() != false
						&& !HelperClass.checkString(dlvBlockReleaseMapDto.getDlvBlockCode())) {
					return ResponseEntity.status(HttpStatus.OK).header("message","Success").body(dlvBlockReleaseMapDto);

				} else {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).header("message","No result found for delivery block code : ").body(dlvBlockCode);
					
				}
			} else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message","Delivery Block Code is mandatory field").body(null);
				
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			System.err.println(e.getStackTrace());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message","Failed").body(null);
			
		}

	}
	
	@Override
	public String saveOrUpdateSalesOrderItemStatusSynchronised(SalesOrderItemStatusDto salesOrderItemStatusDto) {
		try {

			SalesOrderItemStatusDo salesOrderItemStatusDo = new SalesOrderItemStatusDo();

			SalesOrderTaskStatusDo salesOrderTaskStatusDo = salesOrderTaskStatusRepository
					.getSalesOrderTaskStatusDoById(salesOrderItemStatusDto.getTaskStatusSerialId());

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = salesOrderLevelStatusRepository.getSalesOrderLevelStatusDoById(
					salesOrderTaskStatusDo.getSalesOrderLevelStatus().getLevelStatusSerialId());

			salesOrderTaskStatusDo.setSalesOrderLevelStatus(salesOrderLevelStatusDo);
			salesOrderItemStatusDo.setSalesOrderTaskStatus(salesOrderTaskStatusDo);
			salesOrderItemStatusDo.setSalesOrderItemNum(salesOrderItemStatusDto.getSalesOrderItemNum());
			salesOrderItemStatusDo.setItemStatus(salesOrderItemStatusDto.getItemStatus());
			salesOrderItemStatusDo.setVisiblity(salesOrderItemStatusDto.getVisiblity());

//			Session session = sessionfactory.openSession();
//			Transaction tx1 = session.beginTransaction();
			salesOrderItemStatusRepository.save(salesOrderItemStatusDo);
//			tx1.commit();
//			session.clear();
//			session.close();
			return salesOrderItemStatusDo.getItemStatusSerialId();

		} catch (NoResultException | NullPointerException e) {
			try {
				throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
			} catch (ExecutionFault e1) {
				
				e1.printStackTrace();
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<Object> getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(String decisionSetId,
			String levelNum) {
		try {
			if (!ServicesUtil.checkString(decisionSetId) && !ServicesUtil.checkString(levelNum)) {
				List<SalesOrderTaskStatusDto> list = salesOrderTaskStatusRepository.getAllTasksFromDecisionSetAndLevel(decisionSetId,
						levelNum);

				if (list != null && !list.isEmpty()) {
					for (SalesOrderTaskStatusDto taskDto : list) {

						List<SalesOrderItemStatusDto> itemDtoList = salesOrderItemStatusRepository
								.getItemStatusDataUsingTaskSerialId(taskDto.getTaskStatusSerialId());
						taskDto.setItemStatusList(itemDtoList);

					}

					Map<String, List<Integer>> mapToCalculate = new HashMap<>();

					for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : list) {

						for (SalesOrderItemStatusDto salesOrderItemStatusDto : salesOrderTaskStatusDto
								.getItemStatusList()) {

							if (mapToCalculate.containsKey(salesOrderItemStatusDto.getSalesOrderItemNum())) {

								List<Integer> itemStatusNumList = mapToCalculate
										.get(salesOrderItemStatusDto.getSalesOrderItemNum());
								itemStatusNumList.add(salesOrderItemStatusDto.getItemStatus());
								mapToCalculate.put(salesOrderItemStatusDto.getSalesOrderItemNum(), itemStatusNumList);
							} else {

								List<Integer> itemStatusNumList = new ArrayList<>();
								itemStatusNumList.add(salesOrderItemStatusDto.getItemStatus());

								mapToCalculate.put(salesOrderItemStatusDto.getSalesOrderItemNum(), itemStatusNumList);

							}

						}

					}

					Map<String, Integer> mapToCalculateCumulativeStatus = new HashMap<>();

					for (String itemNum : mapToCalculate.keySet()) {
						List<Integer> itemStatusNumList = mapToCalculate.get(itemNum);
						int flagToCountApproveCase = 0, flagToCountDisplayOnlyCase = 0;
						for (Integer itemStatus : itemStatusNumList) {

							if (itemStatus == DkshStatusConstants.ITEM_REJECT
									|| itemStatus == DkshStatusConstants.ITEM_INDIRECT_REJECT) {
								mapToCalculateCumulativeStatus.put(itemNum, DkshStatusConstants.ITEM_REJECT);
								break;
							} else if (itemStatus == DkshStatusConstants.ITEM_APPROVE) {
								flagToCountApproveCase++;
								if (flagToCountApproveCase == itemStatusNumList.size()) {
									mapToCalculateCumulativeStatus.put(itemNum, DkshStatusConstants.ITEM_APPROVE);
								}
							} else if (itemStatus == DkshStatusConstants.DISPLAY_ONLY_ITEM) {
								flagToCountDisplayOnlyCase++;
								if (flagToCountDisplayOnlyCase == itemStatusNumList.size()) {
									mapToCalculateCumulativeStatus.put(itemNum, DkshStatusConstants.DISPLAY_ONLY_ITEM);
								}
 
							} else if (itemStatus == DkshStatusConstants.REJECTED_FROM_ECC) {
								mapToCalculateCumulativeStatus.put(itemNum, DkshStatusConstants.REJECTED_FROM_ECC);
								break;
							} else {
								mapToCalculateCumulativeStatus.put(itemNum, DkshStatusConstants.BLOCKED);
							}

						}

					}

					return ResponseEntity.status(HttpStatus.OK).header("message","Success").body(mapToCalculateCumulativeStatus);
							
				} else {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).header("message","Failed").body("Failed");
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message","Decision set and Level Num fields are mandatory").body("Decision set and Level Num fields are mandatory");
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			System.err.println(this.getClass().getName()+e + " on " + e.getStackTrace()[1]);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message","Failed").body(null);		}
	}

	
	@Override
	public String saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto) {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = ObjectMapperUtils.map(salesOrderTaskStatusDto,SalesOrderTaskStatusDo.class);
			if (salesOrderTaskStatusDo != null) {
				salesOrderTaskStatusRepository.save(salesOrderTaskStatusDo);
//				getSession().merge(salesOrderTaskStatusDo);
//				getSession().flush();
//				getSession().clear();
				return salesOrderTaskStatusDo.getTaskStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException | IllegalStateException e) {
			throw e;
			// throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	
	@Override
	public String updateLevelStatusAndTaskStatus(String taskid, String decisionSetId, String level) {

		System.err.print(" inourdto" + taskid + decisionSetId + level);
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;
		SalesOrderTaskStatusDto salesOrderTaskStatusDto = null;

		String taskstatusid = null;
		try {
			if (taskid != null && decisionSetId != null && level != null) {

				salesOrderLevelStatusDto = salesOrderLevelStatusRepository
						.getSalesOrderLevelStatusByDecisionSetAndLevel(decisionSetId, level);
				if (salesOrderLevelStatusDto != null) {
					salesOrderLevelStatusDto.setLevelStatus(DkshStatusConstants.LEVEL_READY);
					salesOrderTaskStatusDto = salesOrderTaskStatusRepository
							.getAllTasksFromLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
					salesOrderTaskStatusDto.setLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
					salesOrderTaskStatusDto.setTaskId(taskid);
					salesOrderTaskStatusDto.setTaskStatus(DkshStatusConstants.TASK_READY);
					taskstatusid = saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);

					String msgid = saveOrUpdateSalesOrderLevelStatus(salesOrderLevelStatusDto);

					System.err.println(msgid);
				}

			}
		}

		catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			System.err.println(e);
			return e.toString();
		}

		return taskstatusid;

	}
	
	
	public String saveOrUpdateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderLevelStatusDo salesOrderLevelStatusDo = ObjectMapperUtils.map(salesOrderLevelStatusDto, SalesOrderLevelStatusDo.class);
			salesOrderLevelStatusRepository.save(salesOrderLevelStatusDo);
//			getSession().flush();
//			getSession().clear();

			return salesOrderLevelStatusDo.getLevelStatusSerialId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}
	
	

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromSapTaskId(String taskId) {

		String query = "from SalesOrderTaskStatusDo task where task.taskId = :taskId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("taskId", taskId);
		List<SalesOrderTaskStatusDo> list = q1.getResultList();
		List<SalesOrderTaskStatusDto> list1 = new ArrayList<>();
		for(SalesOrderTaskStatusDo l:list)
			list1.add(ObjectMapperUtils.map(l, SalesOrderTaskStatusDto.class));
		return list1;
	}

	@Override
	public List<String> getItemListByDataSet(String dataSet) {
		if (dataSet != null) {

			
			return entityManager.createQuery(
					"select item.salesDocItemKey.salesItemOrderNo from SalesDocItemDo item where item.decisionSetId = :decisionSetId",
					String.class).setParameter("decisionSetId", dataSet).getResultList();

		} else {
			return null;

		}

	}

	@Override
	public String saveOrUpdateSalesOrderItemStatus(SalesOrderItemStatusDto salesOrderItemStatusDto) {
		try {
			SalesOrderItemStatusDo salesOrderItemStatusDo = ObjectMapperUtils.map(salesOrderItemStatusDto,SalesOrderItemStatusDo.class);
			if (salesOrderItemStatusDo != null) {
				salesOrderItemStatusRepository.save(salesOrderItemStatusDo);
//				getSession().flush();
//				getSession().clear();

				return salesOrderItemStatusDo.getItemStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException e) {
			try {
				throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
			} catch (ExecutionFault e1) {
				
				e1.printStackTrace();
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId) {
		
		String query = "from SalesOrderItemStatusDo item where item.salesOrderTaskStatus.taskStatusSerialId = :taskSerialId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("taskSerialId", taskSerialId);
		List<SalesOrderItemStatusDo> itemStatus = q1.getResultList();
		List<SalesOrderItemStatusDto> list = new ArrayList<>();
		for(SalesOrderItemStatusDo l:itemStatus)
			list.add(ObjectMapperUtils.map(l, SalesOrderItemStatusDto.class));
		return list;
	}



}

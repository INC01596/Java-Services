package com.incture.cherrywork.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.FilterDetailDto;
import com.incture.cherrywork.dtos.ItemDataInReturnOrderDto;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderDto;
import com.incture.cherrywork.entities.ExchangeItem;
import com.incture.cherrywork.entities.ReturnItem;
import com.incture.cherrywork.entities.ReturnRequestHeader;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.util.DacMappingConstants;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.NativeSqlResultMapping;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class ReturnRequestFilterDetailsCustomGenericImpl implements ReturnRequestFilterDetailsCustomGeneric {

	private Logger logger = LoggerFactory.getLogger(ReturnRequestFilterDetailsCustomGenericImpl.class);

	private static final String AND = " AND ";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ReturnItem> filterDetails(FilterDetailDto filterDetailDto,
			Map<String, List<String>> mapForInclusionAttributes, Map<String, List<String>> mapForExclusionAttributes,
			Boolean flagForAllRights, Boolean flagForEmptyInput) {

		StringBuilder query = new StringBuilder();

		generateQueryAfterWhereClauseForParams(filterDetailDto, query, mapForInclusionAttributes,
				mapForExclusionAttributes, flagForAllRights, flagForEmptyInput);

		// If we have any item related search
		query.insert(0,
				"select * from RETURN_ITEM i join RETURN_REQUEST h on i.RETURN_REQ_NUM = h.RETURN_REQ_NUM where ");

		logger.error("Query : " + query.toString());

		return entityManager.createNativeQuery(query.toString(), ReturnItem.class).getResultList();
	}

	private void generateQueryAfterWhereClauseForParams(FilterDetailDto filterDetailDto, StringBuilder query,
			Map<String, List<String>> mapForInclusionAttributes, Map<String, List<String>> mapForExclusionAttributes,
			Boolean flagForAllRights, Boolean flagForEmptyInput) {

		// When flag for empty input is true and rights is false means
		// initially loaded
		if (!flagForAllRights && flagForEmptyInput) {
			formingQueryAndAppendingDataFromDacForSomeRights(query, mapForInclusionAttributes,
					mapForExclusionAttributes);
		} else {
			formingQueryAndAppendingDataFromFilterDetails(filterDetailDto, query);
		}
	}

	//
	private void formingQueryAndAppendingDataFromDacForSomeRights(StringBuilder query,
			Map<String, List<String>> mapForInclusionAttributes, Map<String, List<String>> mapForExclusionAttributes) {
		// When inclusion and exclusion both are there
		if (!mapForExclusionAttributes.isEmpty() && !mapForInclusionAttributes.isEmpty()) {

			checkDacForInclusionAttributes(query, mapForInclusionAttributes);

			checkDacForExclusionAttributes(query, mapForExclusionAttributes);

		}
		// When only inclusion is there
		else if (!mapForInclusionAttributes.isEmpty()) {

			checkDacForInclusionAttributes(query, mapForInclusionAttributes);

		}

		// When only exclusion is there
		else if (!mapForExclusionAttributes.isEmpty()) {

			checkDacForExclusionAttributes(query, mapForExclusionAttributes);

		}
	}

	private void checkDacForExclusionAttributes(StringBuilder query,
			Map<String, List<String>> mapForExclusionAttributes) {
		mapForExclusionAttributes.forEach((attributeId, listOfAttributeValues) -> {

			if (attributeId.equalsIgnoreCase(DacMappingConstants.SOLD_TO_PARTY) && mapForExclusionAttributes
					.get(DacMappingConstants.SOLD_TO_PARTY).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}

				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" h.SOLD_TO_PARTY NOT IN (" + inQueryData + ") ");
			}
		});
	}

	private void checkDacForInclusionAttributes(StringBuilder query,
			Map<String, List<String>> mapForInclusionAttributes) {
		mapForInclusionAttributes.forEach((attributeId, listOfAttributeValues) -> {

			if (attributeId.equalsIgnoreCase(DacMappingConstants.SALES_ORG) && mapForInclusionAttributes
					.get(DacMappingConstants.SALES_ORG).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}

				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" h.SALES_ORG IN (" + inQueryData + ") ");
			}

			if (attributeId.equalsIgnoreCase(DacMappingConstants.ORDER_TYPE) && mapForInclusionAttributes
					.get(DacMappingConstants.ORDER_TYPE).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}

				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" h.ORDER_TYPE IN (" + inQueryData + ") ");

			}

			if (attributeId.equalsIgnoreCase(DacMappingConstants.DIVISION) && mapForInclusionAttributes
					.get(DacMappingConstants.DIVISION).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}
				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" h.DIVISION IN (" + inQueryData + ") ");

			}

			if (attributeId.equalsIgnoreCase(DacMappingConstants.DISTRIBUTION_CHANNEL) && mapForInclusionAttributes
					.get(DacMappingConstants.DISTRIBUTION_CHANNEL).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}

				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" h.DISTRIBUTION_CHANNEL IN (" + inQueryData + ") ");

			}

			if (attributeId.equalsIgnoreCase(DacMappingConstants.SOLD_TO_PARTY) && mapForInclusionAttributes
					.get(DacMappingConstants.SOLD_TO_PARTY).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}
				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" h.SOLD_TO_PARTY IN (" + inQueryData + ") ");

			}

			if (attributeId.equalsIgnoreCase(DacMappingConstants.MATERIAL_GROUP) && mapForInclusionAttributes
					.get(DacMappingConstants.MATERIAL_GROUP).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}
				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" i.MATERIAL_GROUP IN (" + inQueryData + ") ");

			}

			if (attributeId.equalsIgnoreCase(DacMappingConstants.MATERIAL_GROUP_4) && mapForInclusionAttributes
					.get(DacMappingConstants.MATERIAL_GROUP_4).parallelStream().noneMatch(k -> k.contains("*"))) {

				if (query.length() != 0) {
					query.append(AND);
				}

				StringBuilder inQueryData = generateInQueryInputForDacAttributes(listOfAttributeValues);
				query.append(" i.MATERIAL_GROUP_4 IN (" + inQueryData + ") ");

			}

		});
	}

	private StringBuilder generateInQueryInputForDacAttributes(List<String> listOfAttributeValues) {
		StringBuilder inQueryData = new StringBuilder();
		AtomicInteger counter = new AtomicInteger(0);
		listOfAttributeValues.stream().forEach(data -> {
			counter.incrementAndGet();
			inQueryData.append("'" + data + "'");
			if (listOfAttributeValues.size() != counter.get()) {
				inQueryData.append(",");
			}

		});
		System.err.println("[generic impl][generateInQueryInputForDacAttributes] inQueryData: " + inQueryData);
		return inQueryData;
	}

	//
	private void formingQueryAndAppendingDataFromFilterDetails(FilterDetailDto filterDetailDto, StringBuilder query) {
		// Date will change query accordingly
		if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)) {
			query.append(" h.CREATED_AT between \'" + filterDetailDto.getCreatedAtStart() + "\' and \'"
					+ filterDetailDto.getCreatedAtEnd() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getCreatedBy())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)) {
				query.append(AND);
			}
			query.append(" h.CREATED_BY = \'" + filterDetailDto.getCreatedBy() + "\' ");
		}

		if (filterDetailDto.getDistributionChannelList() != null
				&& !filterDetailDto.getDistributionChannelList().isEmpty()) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())) {
				query.append(AND);
			}

			StringBuilder inQueryData = new StringBuilder();
			AtomicInteger counter = new AtomicInteger(0);
			filterDetailDto.getDistributionChannelList().stream().forEach(k -> {
				counter.incrementAndGet();
				inQueryData.append("'" + k + "'");
				if (filterDetailDto.getDistributionChannelList().size() != counter.get()) {
					inQueryData.append(",");
				}

			});

			query.append(" h.DISTRIBUTION_CHANNEL IN (" + inQueryData + ") ");
		}

		if (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty()) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())) {
				query.append(AND);
			}

			StringBuilder inQueryData = new StringBuilder();
			AtomicInteger counter = new AtomicInteger(0);
			filterDetailDto.getDivisionList().stream().forEach(k -> {
				counter.incrementAndGet();
				inQueryData.append("'" + k + "'");
				if (filterDetailDto.getDivisionList().size() != counter.get()) {
					inQueryData.append(",");
				}

			});

			query.append(" h.DIVISION IN (" + inQueryData + ") ");
		}

		if (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty()) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())) {
				query.append(AND);
			}

			StringBuilder inQueryData = new StringBuilder();
			AtomicInteger counter = new AtomicInteger(0);
			filterDetailDto.getSalesOrgList().stream().forEach(k -> {
				counter.incrementAndGet();
				inQueryData.append("'" + k + "'");
				if (filterDetailDto.getSalesOrgList().size() != counter.get()) {
					inQueryData.append(",");
				}

			});

			query.append(" h.SALES_ORG IN (" + inQueryData + ") ");
		}

		if (!HelperClass.checkString(filterDetailDto.getReturnReqNum())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())) {
				query.append(AND);
			}
			query.append(" h.RETURN_REQ_NUM = \'" + filterDetailDto.getReturnReqNum() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getOrderType())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())
					|| !HelperClass.checkString(filterDetailDto.getReturnReqNum())) {
				query.append(AND);
			}
			query.append(" h.ORDER_TYPE = \'" + filterDetailDto.getOrderType() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getSoldToParty())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())
					|| !HelperClass.checkString(filterDetailDto.getReturnReqNum())
					|| !HelperClass.checkString(filterDetailDto.getOrderType())) {
				query.append(AND);
			}
			query.append(" h.SOLD_TO_PARTY = \'" + filterDetailDto.getSoldToParty() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getShipToParty())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())
					|| !HelperClass.checkString(filterDetailDto.getReturnReqNum())
					|| !HelperClass.checkString(filterDetailDto.getOrderType())
					|| !HelperClass.checkString(filterDetailDto.getSoldToParty())) {
				query.append(AND);
			}
			query.append(" h.SHIP_TO_PARTY = \'" + filterDetailDto.getShipToParty() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getRefDocNum())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())
					|| !HelperClass.checkString(filterDetailDto.getReturnReqNum())
					|| !HelperClass.checkString(filterDetailDto.getOrderType())
					|| !HelperClass.checkString(filterDetailDto.getSoldToParty())
					|| !HelperClass.checkString(filterDetailDto.getShipToParty())) {
				query.append(AND);
			}
			query.append(" i.REF_DOC_NUM = \'" + filterDetailDto.getRefDocNum() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getMaterialGroup())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())
					|| !HelperClass.checkString(filterDetailDto.getReturnReqNum())
					|| !HelperClass.checkString(filterDetailDto.getOrderType())
					|| !HelperClass.checkString(filterDetailDto.getSoldToParty())
					|| !HelperClass.checkString(filterDetailDto.getShipToParty())
					|| !HelperClass.checkString(filterDetailDto.getRefDocNum())) {
				query.append(AND);
			}
			query.append(" i.MATERIAL_GROUP = \'" + filterDetailDto.getMaterialGroup() + "\' ");
		}

		if (!HelperClass.checkString(filterDetailDto.getMaterialGroup4())) {
			if ((filterDetailDto.getCreatedAtStart() != null && filterDetailDto.getCreatedAtEnd() != null)
					|| !HelperClass.checkString(filterDetailDto.getCreatedBy())
					|| (filterDetailDto.getDistributionChannelList() != null
							&& !filterDetailDto.getDistributionChannelList().isEmpty())
					|| (filterDetailDto.getDivisionList() != null && !filterDetailDto.getDivisionList().isEmpty())
					|| (filterDetailDto.getSalesOrgList() != null && !filterDetailDto.getSalesOrgList().isEmpty())
					|| !HelperClass.checkString(filterDetailDto.getReturnReqNum())
					|| !HelperClass.checkString(filterDetailDto.getOrderType())
					|| !HelperClass.checkString(filterDetailDto.getSoldToParty())
					|| !HelperClass.checkString(filterDetailDto.getShipToParty())
					|| !HelperClass.checkString(filterDetailDto.getRefDocNum())
					|| !HelperClass.checkString(filterDetailDto.getMaterialGroup())) {
				query.append(AND);
			}
			query.append(" i.MATERIAL_GROUP_4 = \'" + filterDetailDto.getMaterialGroup4() + "\' ");
		}
	}

	@Override
	public List<SalesDocHeaderDto> fetchSalesOrdersFromCustomerPoList(List<String> customerPoList) {

		try {

			System.err.println(
					"fetchSalesOrdersFromCustomerPoList starts and customerPoList size: " + customerPoList.size());
			System.err.println("fetchSalesOrdersFromCustomerPoList starts and customerPoList: " + customerPoList);
			String query = "from SalesDocHeaderDo h where h.customerPo in (:list) order by salesOrderDate desc";
			List<SalesDocHeaderDo> list1 = new ArrayList<>();
			try {
				Query q1 = entityManager.createQuery(query);
				q1.setParameter("list", customerPoList);

				list1 = q1.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			if (list1.size() > 0) {
			}
			// System.err.println("[fetchSalesOrdersFromCustomerPoList]
			// list1.get(0)" + list1.get(0).toString());
			System.err.println("[fetchSalesOrdersFromCustomerPoList] list1 size: " + list1.size());
			List<SalesDocHeaderDto> list2 = ObjectMapperUtils.mapAll(list1, SalesDocHeaderDto.class);
			list2 = list2.subList(0, Math.min(list2.size(),100));
			System.err.println("list2: " + list2);
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// @Override
	// public List<ItemDataInReturnOrderDto>
	// fetchItemDataInReturnOrderHavingTaskDtoList(String userId,
	// List<String> salesOrderNumList, Map<String, List<String>>
	// mapForInclusionAttributes,
	// Map<String, List<String>> mapForExclusionAttributes, Boolean
	// flagForAllRightsItemLevel) {
	//
	// StringBuilder query = new StringBuilder();
	//
	// if (!flagForAllRightsItemLevel) {
	//
	// // When inclusion and exclusion both are there
	// if (!mapForExclusionAttributes.isEmpty() &&
	// !mapForInclusionAttributes.isEmpty()) {
	//
	// if
	// (mapForInclusionAttributes.containsKey(DacMappingConstants.MATERIAL_GROUP)
	// &&
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP).parallelStream()
	// .noneMatch(k -> k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP));
	// query.append(" item.material_group IN (" + inQueryData + ") ");
	//
	// }
	//
	// if
	// (mapForInclusionAttributes.containsKey(DacMappingConstants.MATERIAL_GROUP_4)
	// &&
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP_4).parallelStream()
	// .noneMatch(k -> k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	//
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP_4));
	// query.append(" item.material_group_for IN (" + inQueryData + ") ");
	//
	// }
	//
	// if (mapForInclusionAttributes.containsKey(DacMappingConstants.MATERIAL)
	// && mapForInclusionAttributes
	// .get(DacMappingConstants.MATERIAL).parallelStream().noneMatch(k ->
	// k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	//
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL));
	// query.append(" item.sap_material_num IN (" + inQueryData + ") ");
	//
	// }
	//
	// if (mapForExclusionAttributes.containsKey(DacMappingConstants.MATERIAL)
	// && mapForExclusionAttributes
	// .get(DacMappingConstants.MATERIAL).parallelStream().noneMatch(k ->
	// k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	//
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForExclusionAttributes.get(DacMappingConstants.MATERIAL));
	// query.append(" item.sap_material_num NOT IN (" + inQueryData + ") ");
	// }
	//
	// }
	// // When only inclusion is there
	// else if (!mapForInclusionAttributes.isEmpty()) {
	//
	// if
	// (mapForInclusionAttributes.containsKey(DacMappingConstants.MATERIAL_GROUP)
	// &&
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP).parallelStream()
	// .noneMatch(k -> k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP));
	// query.append(" item.material_group IN (" + inQueryData + ") ");
	//
	// }
	//
	// if
	// (mapForInclusionAttributes.containsKey(DacMappingConstants.MATERIAL_GROUP_4)
	// &&
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP_4).parallelStream()
	// .noneMatch(k -> k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	//
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL_GROUP_4));
	// query.append(" item.material_group_for IN (" + inQueryData + ") ");
	//
	// }
	//
	// if (mapForInclusionAttributes.containsKey(DacMappingConstants.MATERIAL)
	// && mapForInclusionAttributes
	// .get(DacMappingConstants.MATERIAL).parallelStream().noneMatch(k ->
	// k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	//
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForInclusionAttributes.get(DacMappingConstants.MATERIAL));
	// query.append(" item.sap_material_num IN (" + inQueryData + ") ");
	//
	// }
	//
	// }
	//
	// // When only exclusion is there
	// else if (!mapForExclusionAttributes.isEmpty()
	// && mapForExclusionAttributes.containsKey(DacMappingConstants.MATERIAL) &&
	// mapForExclusionAttributes
	// .get(DacMappingConstants.MATERIAL).parallelStream().noneMatch(k ->
	// k.contains("*"))) {
	//
	// if (query.length() != 0) {
	// query.append(AND);
	// }
	//
	// StringBuilder inQueryData = generateInQueryInputForDacAttributes(
	// mapForExclusionAttributes.get(DacMappingConstants.MATERIAL));
	// query.append(" item.sap_material_num NOT IN (" + inQueryData + ") ");
	//
	// }
	//
	// query.insert(0, AND);
	//
	// }
	//
	// query.insert(0,
	// "select
	// item.sales_order_num,i.so_item_num,i.item_status_serial_id,l.level,l.decision_set_id,l.approver_type,"
	// + "t.task_id, t.task_status_serial_id,
	// i.item_status,i.visiblity,item.spl_price,item.material_expiry_date,item.serial_number,item.sap_material_num,"
	// + "item.material_group,item.short_text,
	// item.item_category,item.sales_unit,item.item_dlv_block,sl.relfordel_text,"
	// +
	// "item.ref_doc_num,item.ref_doc_item,item.plant,item.net_price,item.doc_currency,item.net_worth,"
	// +
	// "item.reason_for_rejection,item.material_group_for,item.base_unit,item.ordered_qty_sales,"
	// +
	// "item.item_categ_text,item.conv_den,item.conv_num,item.higher_level_item,item.item_staging_status,item.batch_num
	// "
	// + "from so_item_status i join so_task_status t on i.TASK_STATUS_SERIAL_ID
	// = t.TASK_STATUS_SERIAL_ID "
	// + "join so_level_status l on t.LEVEL_STATUS_SERIAL_ID =
	// l.LEVEL_STATUS_SERIAL_ID "
	// + "join sales_doc_item item on i.SO_ITEM_NUM = item.SALES_ORDER_ITEM_NUM
	// and l.decision_set_id = item.decision_set_id "
	// + "join schedule_line sl on sl.SALES_ORDER_NUM = item.SALES_ORDER_NUM and
	// sl.SALES_ORDER_ITEM_NUM = item.SALES_ORDER_ITEM_NUM "
	// + "where i.TASK_STATUS_SERIAL_ID in (select TASK_STATUS_SERIAL_ID from
	// so_task_status where approver like '%"
	// + userId
	// + "%' and LEVEL_STATUS_SERIAL_ID in (select LEVEL_STATUS_SERIAL_ID from
	// so_level_status where "
	// + "decision_set_id in (select distinct decision_set_id from
	// sales_doc_item where sales_order_num "
	// + "in (" + generateInQueryInputForDacAttributes(salesOrderNumList) +
	// ")))) ");
	//
	// logger.error("query : " + query);
	//
	// return entityManager
	// .createNativeQuery(query.toString() + " ORDER BY item.sales_order_num
	// DESC,i.so_item_num ASC",
	// NativeSqlResultMapping.ITEM_RESULT)
	// .getResultList();
	// }
	//
	@Override
	public List<ItemDataInReturnOrderDto> fetchItemDataInReturnOrderHavingTaskDtoListForNewDac(String userId,
			List<String> salesOrderNumList, Map<String, String> mapOfAttributeValues,
			Boolean flagForAllRightsItemLevel) {
		StringBuilder query = new StringBuilder();

		System.err.println("[fetchItemDataInReturnOrderHavingTaskDtoListForNewDac] starts with salesOrderNumList "+salesOrderNumList+" userId: "+userId+" mapOfAttributeValues "+mapOfAttributeValues);
		if (!flagForAllRightsItemLevel) {

			// When inclusion and exclusion both are there
			if (!mapOfAttributeValues.isEmpty()) {

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).split("@"))
									.collect(Collectors.toList()));
					query.append(" item.material_group IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP_4)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).split("@"))
									.collect(Collectors.toList()));
					query.append(" item.material_group_for IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL).split("@"))
									.collect(Collectors.toList()));
					query.append(" item.sap_material_num NOT IN (" + inQueryData + ") ");

				}
			}

			// query.insert(0, AND);
			// logger.error("query : " + query);
			//
			// Query q2 = entityManager.createNativeQuery(query.toString());
			// return q2.getResultList();

		}

		query.insert(0,
				"select item.sales_order_num,i.so_item_num,i.item_status_serial_id,l.level,l.decision_set_id,l.approver_type,"
						+ "t.task_id, t.task_status_serial_id, i.item_status,i.visiblity,item.spl_price,item.material_expiry_date,item.serial_number,item.sap_material_num,"
						+ "item.material_group,item.short_text, item.item_category,item.sales_unit,item.item_dlv_block,sl.relfordel_text,"
						+ "item.ref_doc_num,item.ref_doc_item,item.plant,item.net_price,item.doc_currency,item.net_worth,"
						+ "item.reason_for_rejection,item.material_group_for,item.base_unit,item.ordered_qty_sales,"
						+ "item.item_categ_text,item.conv_den,item.conv_num,item.higher_level_item,item.item_staging_status,item.batch_num "
						+ "from so_item_status i join so_task_status t on i.TASK_STATUS_SERIAL_ID = t.TASK_STATUS_SERIAL_ID "
						+ "join so_level_status l on t.LEVEL_STATUS_SERIAL_ID = l.LEVEL_STATUS_SERIAL_ID "
						+ "join sales_doc_item item on i.SO_ITEM_NUM = item.SALES_ORDER_ITEM_NUM and l.decision_set_id = item.decision_set_id "
						+ "join schedule_line sl on sl.SALES_ORDER_NUM = item.SALES_ORDER_NUM and sl.SALES_ORDER_ITEM_NUM = item.SALES_ORDER_ITEM_NUM "
						+ "where i.TASK_STATUS_SERIAL_ID in (select TASK_STATUS_SERIAL_ID from so_task_status where approver like '%"
						+ userId
						+ "%' and LEVEL_STATUS_SERIAL_ID in (select LEVEL_STATUS_SERIAL_ID from so_level_status where "
						+ "decision_set_id in (select distinct decision_set_id from sales_doc_item where sales_order_num "
						+ "in (" + generateInQueryInputForDacAttributes(salesOrderNumList) + ")))) "
						+ " ORDER BY item.sales_order_num DESC,i.so_item_num ASC");

		logger.error("query : " + query);
		List<ItemDataInReturnOrderDto> list = entityManager
				.createNativeQuery(query.toString(), NativeSqlResultMapping.ITEM_RESULT).getResultList();

		System.err.println("list size in [fetchItemDataInReturnOrderHavingTaskDtoListForNewDac]:" + list.size());

		return list;
	}

	@Override
	public List<SalesOrderDto> fetchSalesOrdersFromCustomerPo(String customerPo) {

		String query = "from SalesDocHeaderDo where customerPo like :customerPo";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("customerPo", "%" + customerPo + "%");
		List<SalesDocHeaderDo> l = q1.getResultList();
		List<SalesOrderDto> result = new ArrayList<>();
		for (SalesDocHeaderDo header : l) {
			result.add(new SalesOrderDto(header.getSalesOrderNum(), header.getCustomerPo()));
		}
		return result;

	}

	// @Override
	// public List<ItemStatusTrackingLevelMsgDto>
	// fetchSalesOrderItemsLevelStatusFromOnSubmit(
	// List<String> decisionSetIdList, List<String> levelIdList, List<String>
	// itemNumList) {
	//
	// return entityManager.createNativeQuery(
	// "select i.so_item_num,l.decision_set_id,l.level,l.level_status "
	// + "from so_level_status l join so_task_status t on
	// l.LEVEL_STATUS_SERIAL_ID = t.LEVEL_STATUS_SERIAL_ID "
	// + "join so_item_status i on i.TASK_STATUS_SERIAL_ID =
	// t.TASK_STATUS_SERIAL_ID "
	// + "where l.level IN (" +
	// generateInQueryInputForDacAttributes(levelIdList) + ") "
	// + "and l.decision_set_id IN (" +
	// generateInQueryInputForDacAttributes(decisionSetIdList) + ") "
	// + "and i.so_item_num IN (" +
	// generateInQueryInputForDacAttributes(itemNumList) + ")",
	// NativeSqlResultMapping.SALES_DOC_ITEM_LEVEL_STATUS_DATA).getResultList();
	// }
	//
	// @Override
	// public List<SalesOrderLevelStatusDto>
	// fetchLevelStatusDtoFromDecisionSetAndLevelList(String decisionSetId,
	// String levelId) {
	//
	// return entityManager.createNativeQuery("select * from so_level_status
	// where decision_set_id = '" + decisionSetId
	// + "' and level = '" + levelId + "' ",
	// NativeSqlResultMapping.LEVEL_STATUS_DATA).getResultList();
	// }

	@Override
	public List<SalesOrderTaskStatusDto> getAllTaskFromDecisionSetAndLevel(String decisionSet, String level) {

		return entityManager.createNativeQuery(
				"select task.* from so_task_status task join so_level_status l on "
						+ "l.LEVEL_STATUS_SERIAL_ID = task.LEVEL_STATUS_SERIAL_ID where l.decision_set_id = \'"
						+ decisionSet + "\' and l.level = \'" + level + "\'",
				NativeSqlResultMapping.SALES_DOC_TASK_STATUS_DATA).getResultList();
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId) {

		return entityManager.createNativeQuery(
				"select item.* from so_item_status item where item.TASK_STATUS_SERIAL_ID = \'" + taskSerialId + "\'",
				NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA).getResultList();
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataItemStatusAsBlockedFromTaskSerialId(String taskSerialId) {

		return entityManager.createNativeQuery(
				"select item.* from so_item_status item where item.item_status = '" + ComConstants.BLOCKED
						+ "' and item.TASK_STATUS_SERIAL_ID = '" + taskSerialId + "'",
				NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA).getResultList();
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingDecisionSetAndLevelAndItemNo(String decisionSet,
			String level, String itemNo) {

		return entityManager.createNativeQuery("select i.* "
				+ "from so_level_status l join so_task_status t on l.LEVEL_STATUS_SERIAL_ID = t.LEVEL_STATUS_SERIAL_ID "
				+ "join so_item_status i on i.TASK_STATUS_SERIAL_ID = t.TASK_STATUS_SERIAL_ID " + "where l.level = '"
				+ level + "' and l.decision_set_id = '" + decisionSet + "'and i.so_item_num = '" + itemNo + "'",
				NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA).getResultList();

	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingDecisionSetForBlockedItems(String decisionSetId) {

		return entityManager
				.createNativeQuery("select i.* "
						+ "from so_level_status l join so_task_status t on l.LEVEL_STATUS_SERIAL_ID = t.LEVEL_STATUS_SERIAL_ID "
						+ "join so_item_status i on i.TASK_STATUS_SERIAL_ID = t.TASK_STATUS_SERIAL_ID "
						+ "where l.decision_set_id = \'" + decisionSetId + "\'and i.item_status = \'"
						+ ComConstants.BLOCKED + "\'", NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA)
				.getResultList();

	}

	//
	@Override
	public List<SalesOrderTaskStatusDto> getTaskStatusDataFromTaskSerialId(String taskStatusSeriallId) {

		return entityManager.createNativeQuery(
				"select * from so_task_status where task_status != '" + ComConstants.TASK_COMPLETE
						+ "' and TASK_STATUS_SERIAL_ID = '" + taskStatusSeriallId + "'",
				NativeSqlResultMapping.SALES_DOC_TASK_STATUS_DATA).getResultList();

	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTaskFromLevelSerialId(String levelSerialId) {

		return entityManager.createNativeQuery(
				"select * from so_task_status where task_status != '" + ComConstants.TASK_COMPLETE
						+ "' and LEVEL_STATUS_SERIAL_ID = '" + levelSerialId + "'",
				NativeSqlResultMapping.SALES_DOC_TASK_STATUS_DATA).getResultList();
	}

	@Override
	public List<ReturnRequestHeader> getReturnHeaderDataFromReturnNumList(List<String> returnReqNumList) {
		return entityManager
				.createQuery(
						"from ReturnRequestHeader where returnReqNum in ("
								+ generateInQueryInputForDacAttributes(returnReqNumList) + ")",
						ReturnRequestHeader.class)
				.getResultList();
	}

	//
	@Override
	public List<ReturnRequestHeader> getFilterDetails(String query) {

		return entityManager.createNativeQuery(query.toString(), ReturnRequestHeader.class).getResultList();
	}

	@Override
	public Double findTotalAmountOnCustomerPo(String salesOrderNum) {
		System.err.println("findTotalAmountOnCustomerPo starts with salesOrderNum: " + salesOrderNum);
		String query = "select sum(i.net_worth) from sales_doc_item i " + " where i.sales_order_num = \'"
				+ salesOrderNum + "\'";
		Query q1 = entityManager.createNativeQuery(query);
		Double amount = (Double) q1.getSingleResult();
		System.err.println("[findTotalAmountOnCustomerPo] amount: " + amount);
		return amount;
	}

	@Override
	public List<ReturnRequestHeader> fetchReturnOrderForCreatReturnNewDac(String userId,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel) {
		StringBuilder query = new StringBuilder();

		if (!flagForAllRightsItemLevel) {

			// When inclusion and exclusion both are there
			if (!mapOfAttributeValues.isEmpty()) {

				if (mapOfAttributeValues.containsKey(DacMappingConstants.SALES_ORG)
						&& !mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).split("@"))
									.collect(Collectors.toList()));
					query.append(" SALES_ORG IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.DISTRIBUTION_CHANNEL)
						&& !mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).split("@"))
									.collect(Collectors.toList()));
					query.append(" DISTRIBUTION_CHANNEL IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.DIVISION)
						&& !mapOfAttributeValues.get(DacMappingConstants.DIVISION).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.DIVISION).split("@"))
									.collect(Collectors.toList()));
					query.append(" DIVISION IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.SOLD_TO_PARTY)
						&& !mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).contains("*")) {
					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).split("@"))
									.collect(Collectors.toList()));
					query.append(" SOLD_TO_PARTY NOT IN (" + inQueryData + ") ");
				}
				if (mapOfAttributeValues.containsKey(DacMappingConstants.ORDER_TYPE)
						&& !mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).contains("*")) {
					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).split("@"))
									.collect(Collectors.toList()));
					query.append(" ORDER_TYPE IN (" + inQueryData + ") ");
				}
			}
			if (query.length() != 0) {
				query.insert(0, AND);
			}

		}
		// add a query to fetch the data from return header table .
		query.insert(0, "SELECT * FROM RETURN_REQUEST  WHERE REQUESTED_BY = " + "\'" + userId + "\'");

		query.append(" ORDER BY CREATED_AT DESC");
		logger.error("query : " + query);
		return entityManager.createNativeQuery(query.toString() + "", ReturnRequestHeader.class).getResultList();

	}

	//
	@Override
	public List<ReturnRequestHeader> fetchReturnOrderForCreatReturnFilterNewDac(String userId,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel, String retunRequestNum) {
		StringBuilder query = new StringBuilder();

		if (!flagForAllRightsItemLevel) {

			// When inclusion and exclusion both are there
			if (!mapOfAttributeValues.isEmpty()) {

				if (mapOfAttributeValues.containsKey(DacMappingConstants.SALES_ORG)
						&& !mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).split("@"))
									.collect(Collectors.toList()));
					query.append(" SALES_ORG IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.DISTRIBUTION_CHANNEL)
						&& !mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).split("@"))
									.collect(Collectors.toList()));
					query.append(" DISTRIBUTION_CHANNEL IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.DIVISION)
						&& !mapOfAttributeValues.get(DacMappingConstants.DIVISION).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.DIVISION).split("@"))
									.collect(Collectors.toList()));
					query.append(" DIVISION IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.SOLD_TO_PARTY)
						&& !mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).contains("*")) {
					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).split("@"))
									.collect(Collectors.toList()));
					query.append(" SOLD_TO_PARTY NOT IN (" + inQueryData + ") ");
				}
				if (mapOfAttributeValues.containsKey(DacMappingConstants.ORDER_TYPE)
						&& !mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).contains("*")) {
					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).split("@"))
									.collect(Collectors.toList()));
					query.append(" ORDER_TYPE IN (" + inQueryData + ") ");
				}
			}
			if (query.length() != 0) {
				query.insert(0, AND);
			}

		}

		// add a query to fetch the data from return header table .
		query.insert(0, "SELECT * FROM RETURN_REQUEST  WHERE REQUESTED_BY =" + "\'" + userId + "\'"
				+ " AND RETURN_REQ_NUM =" + "\'" + retunRequestNum + "\'");

		query.append(" ORDER BY CREATED_AT DESC");
		logger.error("query : " + query);
		return entityManager.createNativeQuery(query.toString() + "", ReturnRequestHeader.class).getResultList();

	}

	@Override
	public List<ReturnItem> fetchReturnOrderForCreatReturnItemNewDac(String returnReqNum,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel) {

		StringBuilder query = new StringBuilder();

		if (!flagForAllRightsItemLevel) {
			// When inclusion and exclusion both are there
			if (!mapOfAttributeValues.isEmpty()) {

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).split("@"))
									.collect(Collectors.toList()));
					query.append(" MATERIAL_GROUP IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP_4)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).split("@"))
									.collect(Collectors.toList()));
					query.append(" MATERIAL_GROUP_4 IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL).split("@"))
									.collect(Collectors.toList()));
					query.append(" MATERIAL NOT IN (" + inQueryData + ") ");

				}
			}

			if (query.length() != 0) {
				query.insert(0, AND);
			}

		}

		// add a query to fetch the data from item table .
		query.insert(0, "SELECT * FROM RETURN_ITEM  WHERE RETURN_REQ_NUM = " + "\'" + returnReqNum + "\'");
		logger.error("query : " + query);
		return entityManager.createNativeQuery(query.toString() + "", ReturnItem.class).getResultList();

	}

	@Override
	public List<ExchangeItem> fetchReturnOrderForCreatExchangeItemNewDac(String returnReqNum,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel) {
		StringBuilder query = new StringBuilder();

		if (!flagForAllRightsItemLevel) {
			// When inclusion and exclusion both are there
			if (!mapOfAttributeValues.isEmpty()) {

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).split("@"))
									.collect(Collectors.toList()));
					query.append(" MATERIAL_GROUP IN (" + inQueryData + ") ");

				}

				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP_4)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}

					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).split("@"))
									.collect(Collectors.toList()));
					query.append(" MATERIAL_GROUP_4 IN (" + inQueryData + ") ");

				}
				if (mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL)
						&& !mapOfAttributeValues.get(DacMappingConstants.MATERIAL).contains("*")) {

					if (query.length() != 0) {
						query.append(AND);
					}
					StringBuilder inQueryData = generateInQueryInputForDacAttributes(
							Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.MATERIAL).split("@"))
									.collect(Collectors.toList()));
					query.append(" MATERIAL NOT IN (" + inQueryData + ") ");

				}
			}
			if (query.length() != 0) {
				query.insert(0, AND);
			}

		}

		// add a query to fetch the data from item table .
		query.insert(0, "SELECT * FROM EXCHANGE_ITEM  WHERE RETURN_REQ_NUM = " + "\'" + returnReqNum + "\'");
		logger.error("query : " + query);
		return entityManager.createNativeQuery(query.toString(), ExchangeItem.class).getResultList();

	}

	//
	// @Override
	// public List<SalesOrderTaskStatusDto> getTaskStatusDataFromTaskId(String
	// taskId) {
	// return entityManager.createNativeQuery("select * from so_task_status
	// where task_id = '" + taskId + "'",
	// NativeSqlResultMapping.SALES_DOC_TASK_STATUS_DATA).getResultList();
	// }
	//
	// @Override
	// public List<String> fetchSalesOrderNumsFromOrderCreationDate(String
	// startDate, String endDate) {
	// return entityManager
	// .createNativeQuery("select sales_order_num from SALES_DOC_HEADER where
	// sales_order_date between '"
	// + startDate + "' and '" + endDate + "'")
	// .getResultList();
	// }
	//
	// @Override
	// public List<String> getAllTaskIdsFromCompletedByList(List<String>
	// completedByList) {
	// return entityManager.createNativeQuery("select task_id from
	// so_task_status where completed_by IN ("
	// + generateInQueryInputForDacAttributes(completedByList) +
	// ")").getResultList();
	// }
	//
	// @Override
	// public List<SalesDocHeaderDto> fetchSalesOrdersFromSalesOrderNum(String
	// salesOrderNum) {
	//
	// return entityManager.createNativeQuery(
	// "select requested_by, order_remark, order_reason_text, order_category,
	// order_type, doc_type_text, sales_org,distribution_channel,division,"
	// +
	// "customer_po,sold_to_party,sold_to_party_text,ship_to_party,ship_to_party_text,"
	// +
	// "doc_currency,delivery_block_code,dlv_block_text,cond_group5,cond_group5_text,sales_order_date,"
	// +
	// "order_reason,orderer_na,created_by,attachment_url,approval_status,overall_status,
	// sales_org_text, distribution_channel_text,
	// division_text,payer,payer_text,bill_to_party,bill_to_party_text "
	// + "from SALES_DOC_HEADER where sales_order_num = '" + salesOrderNum +
	// "'",
	// NativeSqlResultMapping.SALES_DOC_HEADER_DATA).getResultList();
	// }
	//
//	@Override
//	 public List<SupportUserFunctionTaskItemDataDto>
//	 fetchItemDataFromItemNumList(List<String> itemNumList,
//	 String orderNum) {
//	
//	 String query = "select i.sales_order_num,
//	 i.sales_order_item_num,i.sap_material_num,i.ordered_qty_sales,"
//	 +
//	 "i.spl_price,i.sales_unit,i.net_price,i.doc_currency,i.net_worth,i.reason_for_rejection,i.reason_for_rejection_text,"
//	 + "i.material_group_for,i.base_unit, i.material_group,i.item_dlv_block,"
//	 + "sl.relfordel_text,i.ref_doc_num,i.ref_doc_item, i.short_text from
//	 sales_doc_item i "
////	 + "join schedule_line sl on sl.SALES_ORDER_NUM = i.SALES_ORDER_NUM and "
//	 + "sl.SALES_ORDER_ITEM_NUM = i.SALES_ORDER_ITEM_NUM where
//	 i.SALES_ORDER_NUM = '" + orderNum
//	 + "' and i.SALES_ORDER_ITEM_NUM IN (" +
//	 generateInQueryInputForDacAttributes(itemNumList)
//	 + ") ORDER BY i.sales_order_item_num ASC";
//	 System.err.println("query : " + query);
//	
//	 return entityManager.createNativeQuery(query,
//	 NativeSqlResultMapping.SALES_ORDER_ITEM_DATA).getResultList();
//	 }

	@Override
	public SalesOrderLevelStatusDto fetchLevelStatusDtoFromDecisionSetAndLevel(String decisionSetId, String levelId) {
		System.err.println("[fetchLevelStatusDtoFromDecisionSetAndLevel] decisionSetId: "+decisionSetId+" levelId: "+levelId);
		String query = "select * from so_level_status where decision_set_id = '" + decisionSetId
				+ "' and level = '" + levelId + "'";
		System.err.println("[fetchLevelStatusDtoFromDecisionSetAndLevel] query: "+query);
		return (SalesOrderLevelStatusDto) entityManager
				.createNativeQuery(query, NativeSqlResultMapping.LEVEL_STATUS_DATA)
				.getSingleResult();
	}

}
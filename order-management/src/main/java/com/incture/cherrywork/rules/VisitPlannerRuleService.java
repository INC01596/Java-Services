package com.incture.cherrywork.rules;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.VisitPlanDto;
import com.incture.cherrywork.entities.SalesVisit;
import com.incture.cherrywork.repositories.ISalesVisitPlannerRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;

@Service
@Transactional
public class VisitPlannerRuleService extends VisitPlannerRuleServiceDestination {

	@Autowired
	private ISalesVisitPlannerRepository salesVisitRepository;

	@Override
	public VisitPlannerRuleOutput getVisitApprover(VisitPlanRuleInputDto input) throws IOException, InterruptedException {
		TimeUnit.SECONDS.sleep(15);

		logger.info("[RuleService][getResultListTotalAmountRuleService] TotalAmountInputDto " + input);
		SalesVisit visit = null;
		visit = salesVisitRepository.findByVisitId(input.getVisitId());
		VisitPlanDto planDto = null;
		if (visit != null)
			planDto = ObjectMapperUtils.map(visit, VisitPlanDto.class);
		logger.info("[VisitPlannerRuleService][getVisitApprover] planDto: " + planDto);
		if (visit == null) {
			logger.info("[getVisitApprover] provided id has no visit.");
			return null;
		}

		String node = null;
		if (visit != null)
			node = executeGetVisitApproverRule(input, RuleConstants.Visit_Planner_Rule_Service,
					visit.getSalesRepEmail(), visit.getCustCode());

		ApproverDataOutputDto dto = new ApproverDataOutputDto();

		VisitPlannerRuleOutput output = new VisitPlannerRuleOutput();
		List<ApproverDataOutputDto> approverList = null;
		if (node != null && !node.isEmpty()) {
			approverList = dto.convertFromJSonNodeVisitPlan(node);
			output.setResult(approverList);
			output.setLevelCount(approverList.size());
			return output;
		}

		return null;
	}

}

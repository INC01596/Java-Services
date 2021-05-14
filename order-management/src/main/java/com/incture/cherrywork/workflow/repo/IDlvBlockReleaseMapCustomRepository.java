package com.incture.cherrywork.workflow.repo;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.workflow.DlvBlockReleaseMapDto;

@Repository
public interface IDlvBlockReleaseMapCustomRepository {
	DlvBlockReleaseMapDto getDlvBlockReleaseMapBydlvBlockCode(String dlvBlockCode);

}

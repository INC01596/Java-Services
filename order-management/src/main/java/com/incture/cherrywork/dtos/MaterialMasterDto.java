package com.incture.cherrywork.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;


public class MaterialMasterDto extends BaseDto {

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}

	private String materialMasterId;

	private BigDecimal barsPerBundle;

	private Boolean bendTest;

	private BigDecimal bundleWeight;

	private String catalogKey;

	private Boolean ceLogo;

	private String changedBy;

	private java.util.Date changedOn;

	private Integer clientSpecific;

	private Boolean container;

	private String createdBy;

	private String createdOn;

	private String gradePricingGroup;

	private Boolean impactTest;

	private Boolean isiLogo;

	private String key;

	private BigDecimal kgPerMeter;

	private String kgpermPricingGroup;

	private BigDecimal length;

	private String lengthMapKey;

	private String lengthPricingGroup;

	private String level2Id;

	private String material;

	private String materialDescription;

	private String plant;

	private Boolean rollingPlanFlag;

	private String searchField;

	private List<String> qualityTestList;

	private List<String> defaultQualityTestList;

	private String section;

	private String sectionGrade;

	private String sectionGradeDescription;

	private String sectionGroup;

	private String sectionPricingGroup;

	private String size;

	private BigDecimal sizeGroup;

	private String sizePricingGroup;

	private String standard;

	private String standardDescription;

	private Boolean syncStatus;

	private Boolean ultraLightTest;

	private EnUpdateIndicator updateIndicator;

	public String getMaterialMasterId() {
		return materialMasterId;
	}

	public void setMaterialMasterId(String materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	public BigDecimal getBarsPerBundle() {
		return barsPerBundle;
	}

	public void setBarsPerBundle(BigDecimal barsPerBundle) {
		this.barsPerBundle = barsPerBundle;
	}

	public Boolean getBendTest() {
		return bendTest;
	}

	public void setBendTest(Boolean bendTest) {
		this.bendTest = bendTest;
	}

	public BigDecimal getBundleWeight() {
		return bundleWeight;
	}

	public void setBundleWeight(BigDecimal bundleWeight) {
		this.bundleWeight = bundleWeight;
	}

	public String getCatalogKey() {
		return catalogKey;
	}

	public void setCatalogKey(String catalogKey) {
		this.catalogKey = catalogKey;
	}

	public Boolean getCeLogo() {
		return ceLogo;
	}

	public void setCeLogo(Boolean ceLogo) {
		this.ceLogo = ceLogo;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public java.util.Date getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(java.util.Date changedOn) {
		this.changedOn = changedOn;
	}

	public Integer getClientSpecific() {
		return clientSpecific;
	}

	public void setClientSpecific(Integer clientSpecific) {
		this.clientSpecific = clientSpecific;
	}

	public Boolean getContainer() {
		return container;
	}

	public void setContainer(Boolean container) {
		this.container = container;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getGradePricingGroup() {
		return gradePricingGroup;
	}

	public void setGradePricingGroup(String gradePricingGroup) {
		this.gradePricingGroup = gradePricingGroup;
	}

	public Boolean getImpactTest() {
		return impactTest;
	}

	public void setImpactTest(Boolean impactTest) {
		this.impactTest = impactTest;
	}

	public Boolean getIsiLogo() {
		return isiLogo;
	}

	public void setIsiLogo(Boolean isiLogo) {
		this.isiLogo = isiLogo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public BigDecimal getKgPerMeter() {
		return kgPerMeter;
	}

	public void setKgPerMeter(BigDecimal kgPerMeter) {
		this.kgPerMeter = kgPerMeter;
	}

	public String getKgpermPricingGroup() {
		return kgpermPricingGroup;
	}

	public void setKgpermPricingGroup(String kgpermPricingGroup) {
		this.kgpermPricingGroup = kgpermPricingGroup;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getLengthMapKey() {
		return lengthMapKey;
	}

	public void setLengthMapKey(String lengthMapKey) {
		this.lengthMapKey = lengthMapKey;
	}

	public String getLengthPricingGroup() {
		return lengthPricingGroup;
	}

	public void setLengthPricingGroup(String lengthPricingGroup) {
		this.lengthPricingGroup = lengthPricingGroup;
	}

	public String getLevel2Id() {
		return level2Id;
	}

	public void setLevel2Id(String level2Id) {
		this.level2Id = level2Id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public Boolean getRollingPlanFlag() {
		return rollingPlanFlag;
	}

	public void setRollingPlanFlag(Boolean rollingPlanFlag) {
		this.rollingPlanFlag = rollingPlanFlag;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public List<String> getQualityTestList() {
		return qualityTestList;
	}

	public void setQualityTestList(List<String> qualityTestList) {
		this.qualityTestList = qualityTestList;
	}

	public List<String> getDefaultQualityTestList() {
		return defaultQualityTestList;
	}

	public void setDefaultQualityTestList(List<String> defaultQualityTestList) {
		this.defaultQualityTestList = defaultQualityTestList;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSectionGrade() {
		return sectionGrade;
	}

	public void setSectionGrade(String sectionGrade) {
		this.sectionGrade = sectionGrade;
	}

	public String getSectionGradeDescription() {
		return sectionGradeDescription;
	}

	public void setSectionGradeDescription(String sectionGradeDescription) {
		this.sectionGradeDescription = sectionGradeDescription;
	}

	public String getSectionGroup() {
		return sectionGroup;
	}

	public void setSectionGroup(String sectionGroup) {
		this.sectionGroup = sectionGroup;
	}

	public String getSectionPricingGroup() {
		return sectionPricingGroup;
	}

	public void setSectionPricingGroup(String sectionPricingGroup) {
		this.sectionPricingGroup = sectionPricingGroup;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public BigDecimal getSizeGroup() {
		return sizeGroup;
	}

	public void setSizeGroup(BigDecimal sizeGroup) {
		this.sizeGroup = sizeGroup;
	}

	public String getSizePricingGroup() {
		return sizePricingGroup;
	}

	public void setSizePricingGroup(String sizePricingGroup) {
		this.sizePricingGroup = sizePricingGroup;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getStandardDescription() {
		return standardDescription;
	}

	public void setStandardDescription(String standardDescription) {
		this.standardDescription = standardDescription;
	}

	public Boolean getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Boolean syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Boolean getUltraLightTest() {
		return ultraLightTest;
	}

	public void setUltraLightTest(Boolean ultraLightTest) {
		this.ultraLightTest = ultraLightTest;
	}

	public EnUpdateIndicator getUpdateIndicator() {
		return updateIndicator;
	}

	public void setUpdateIndicator(EnUpdateIndicator updateIndicator) {
		this.updateIndicator = updateIndicator;
	}

	@Override
	public String toString() {
		return "MaterialMasterDto [materialMasterId=" + materialMasterId + ", barsPerBundle=" + barsPerBundle
				+ ", bendTest=" + bendTest + ", bundleWeight=" + bundleWeight + ", catalogKey=" + catalogKey
				+ ", ceLogo=" + ceLogo + ", changedBy=" + changedBy + ", changedOn=" + changedOn + ", clientSpecific="
				+ clientSpecific + ", container=" + container + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", gradePricingGroup=" + gradePricingGroup + ", impactTest=" + impactTest + ", isiLogo=" + isiLogo
				+ ", key=" + key + ", kgPerMeter=" + kgPerMeter + ", kgpermPricingGroup=" + kgpermPricingGroup
				+ ", length=" + length + ", lengthMapKey=" + lengthMapKey + ", lengthPricingGroup=" + lengthPricingGroup
				+ ", level2Id=" + level2Id + ", material=" + material + ", materialDescription=" + materialDescription
				+ ", plant=" + plant + ", rollingPlanFlag=" + rollingPlanFlag + ", searchField=" + searchField
				+ ", qualityTestList=" + qualityTestList + ", defaultQualityTestList=" + defaultQualityTestList
				+ ", section=" + section + ", sectionGrade=" + sectionGrade + ", sectionGradeDescription="
				+ sectionGradeDescription + ", sectionGroup=" + sectionGroup + ", sectionPricingGroup="
				+ sectionPricingGroup + ", size=" + size + ", sizeGroup=" + sizeGroup + ", sizePricingGroup="
				+ sizePricingGroup + ", standard=" + standard + ", standardDescription=" + standardDescription
				+ ", syncStatus=" + syncStatus + ", ultraLightTest=" + ultraLightTest + ", updateIndicator="
				+ updateIndicator + "]";
	}
}

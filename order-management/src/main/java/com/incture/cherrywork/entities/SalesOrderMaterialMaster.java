package com.incture.cherrywork.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.incture.cherrywork.repositories.SalesOrderEnUpdateIndicator;

@Entity
@Table(name = "MATERIAL")
public class SalesOrderMaterialMaster implements SalesOrderBase, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getPrimaryKey() {
		return materialMasterId;
	}

	@Id
	@Column(name = "MATERIAL_MASTER_ID", length = 32, nullable = false)
	private String materialMasterId;

	@Column(name = "BARS_PER_BUNDLE", precision = 6, scale = 2)
	private BigDecimal barsPerBundle;

	@Column(name = "BEND_TEST")
	private Boolean bendTest;

	@Column(name = "BUNDLE_WEIGHT", precision = 15, scale = 3)
	private BigDecimal bundleWeight;

	@Column(name = "CATALOG_KEY", length = 8)
	private String catalogKey;

	@Column(name = "CE_LOGO")
	private Boolean ceLogo;

	@Column(name = "CHANGED_BY", length = 20)
	private String changedBy;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "CHANGED_ON")
	private java.util.Date changedOn;

	@Column(name = "CLIENT_SPECIFIC")
	private Integer clientSpecific;

	@Column(name = "CONTAINER")
	private Boolean container;

	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "CREATED_ON", length = 20)
	private String createdOn;

	@Column(name = "GRADE_PRICING_GROUP", length = 4)
	private String gradePricingGroup;

	@Column(name = "IMPACT_TEST")
	private Boolean impactTest;

	@Column(name = "ISI_LOGO")
	private Boolean isiLogo;

	@Column(name = "KEY", length = 16) // catlog+Length Map Key
	private String key;

	@Column(name = "KG_PER_METER", precision = 10, scale = 4)
	private BigDecimal kgPerMeter;

	@Column(name = "KGPERM_PRICING_GROUP", length = 4)
	private String kgpermPricingGroup;

	@Column(name = "LENGTH", precision = 6, scale = 2)
	private BigDecimal length;

	@Column(name = "LENGTH_MAP_KEY", length = 8)
	private String lengthMapKey;

	@Column(name = "LENGTH_PRICING_GROUP", length = 4)
	private String lengthPricingGroup;

	@Column(name = "LEVEL2_ID", length = 70)
	private String level2Id;

	@Column(name = "MATERIAL", length = 40) // materialNumber
	private String material;

	@Column(name = "MATERIAL_DESC", length = 50) // shortTextForSOItem
	private String materialDescription;

	@Column(name = "PLANT", length = 4)
	private String plant;

	@Column(name = "ROLLING_PLAN_FLAG")
	private Boolean rollingPlanFlag;

	@Column(name = "SEARCH_FIELD", length = 180)
	private String searchField;

	@Column(name = "SECTION", length = 70)
	private String section;

	@Column(name = "SECTION_GRADE", length = 4)
	private String sectionGrade;

	@Column(name = "SECTION_GRADE_DESC", length = 30)
	private String sectionGradeDescription;

	@Column(name = "SECTION_GROUP", length = 70)
	private String sectionGroup;

	@Column(name = "SECTION_PRICING_GROUP", length = 70)
	private String sectionPricingGroup;

	@Column(name = "SIZE", length = 70)
	private String size;

	@Column(name = "SIZE_GROUP", precision = 6, scale = 2)
	private BigDecimal sizeGroup;

	@Column(name = "SIZE_PRICING_GROUP", length = 4)
	private String sizePricingGroup;

	@Column(name = "STANDARD", length = 1)
	private String standard;

	@Column(name = "STANDARD_DESC", length = 20)
	private String standardDescription;

	@Column(name = "SYNC_STAT")
	private Boolean syncStatus;

	@Column(name = "ULTRALIGHT_TEST")
	private Boolean ultraLightTest;

	@Column(name = "UPDATE_INDICATOR")
	private SalesOrderEnUpdateIndicator updateIndicator;

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

	// Discuss About Enum showing update of CRUD

	public SalesOrderEnUpdateIndicator getUpdateIndicator() {
		return updateIndicator;
	}

	public void setUpdateIndicator(SalesOrderEnUpdateIndicator updateIndicator) {
		this.updateIndicator = updateIndicator;
	}

	@Override
	public String toString() {
		return "MaterialMasterDo [materialMasterId=" + materialMasterId + ", barsPerBundle=" + barsPerBundle
				+ ", bendTest=" + bendTest + ", bundleWeight=" + bundleWeight + ", catalogKey=" + catalogKey
				+ ", ceLogo=" + ceLogo + ", changedBy=" + changedBy + ", changedOn=" + changedOn + ", clientSpecific="
				+ clientSpecific + ", container=" + container + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", gradePricingGroup=" + gradePricingGroup + ", impactTest=" + impactTest + ", isiLogo=" + isiLogo
				+ ", key=" + key + ", kgPerMeter=" + kgPerMeter + ", kgpermPricingGroup=" + kgpermPricingGroup
				+ ", length=" + length + ", lengthMapKey=" + lengthMapKey + ", lengthPricingGroup=" + lengthPricingGroup
				+ ", level2Id=" + level2Id + ", material=" + material + ", materialDescription=" + materialDescription
				+ ", plant=" + plant + ", rollingPlanFlag=" + rollingPlanFlag + ", searchField=" + searchField
				+ ", section=" + section + ", sectionGrade=" + sectionGrade + ", sectionGradeDescription="
				+ sectionGradeDescription + ", sectionGroup=" + sectionGroup + ", sectionPricingGroup="
				+ sectionPricingGroup + ", size=" + size + ", sizeGroup=" + sizeGroup + ", sizePricingGroup="
				+ sizePricingGroup + ", standard=" + standard + ", standardDescription=" + standardDescription
				+ ", syncStatus=" + syncStatus + ", ultraLightTest=" + ultraLightTest
				+ 
					  ", updateIndicator=" + updateIndicator +
					  "]";
	}
}

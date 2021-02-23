package com.incture.cherrywork.dtos;
import java.util.Date;
import java.math.BigDecimal;
public class SalesOrderItemDto{


private String salesItemId;
public String getSalesItemId(){
	return salesItemId;
}
public void setSalesItemId(String salesItemId){
	this.salesItemId = salesItemId;
}

private Integer clientSpecific;
public Integer getClientSpecific(){
	return clientSpecific;
}
public void setClientSpecific(Integer clientSpecific){
	this.clientSpecific = clientSpecific;
}

private String lineItemNumber;
public String getLineItemNumber(){
	return lineItemNumber;
}
public void setLineItemNumber(String lineItemNumber){
	this.lineItemNumber = lineItemNumber;
}

private String material;
public String getMaterial(){
	return material;
}
public void setMaterial(String material){
	this.material = material;
}

private String materialDesc;
public String getMaterialDesc(){
	return materialDesc;
}
public void setMaterialDesc(String materialDesc){
	this.materialDesc = materialDesc;
}

private String cumConfirmedQty;
public String getCumConfirmedQty(){
	return cumConfirmedQty;
}
public void setCumConfirmedQty(String cumConfirmedQty){
	this.cumConfirmedQty = cumConfirmedQty;
}

private String itemCategory;
public String getItemCategory(){
	return itemCategory;
}
public void setItemCategory(String itemCategory){
	this.itemCategory = itemCategory;
}

private String plant;
public String getPlant(){
	return plant;
}
public void setPlant(String plant){
	this.plant = plant;
}

private BigDecimal netValue;
public BigDecimal getNetValue(){
	return netValue;
}
public void setNetValue(BigDecimal netValue){
	this.netValue = netValue;
}

private BigDecimal amountB4Vat;
public BigDecimal getAmountB4Vat(){
	return amountB4Vat;
}
public void setAmountB4Vat(BigDecimal amountB4Vat){
	this.amountB4Vat = amountB4Vat;
}

private BigDecimal vatPercent;
public BigDecimal getVatPercent(){
	return vatPercent;
}
public void setVatPercent(BigDecimal vatPercent){
	this.vatPercent = vatPercent;
}

private BigDecimal vatAmount;
public BigDecimal getVatAmount(){
	return vatAmount;
}
public void setVatAmount(BigDecimal vatAmount){
	this.vatAmount = vatAmount;
}

private BigDecimal totalAmountInclVat;
public BigDecimal getTotalAmountInclVat(){
	return totalAmountInclVat;
}
public void setTotalAmountInclVat(BigDecimal totalAmountInclVat){
	this.totalAmountInclVat = totalAmountInclVat;
}

private BigDecimal deliveredQuantity;
public BigDecimal getDeliveredQuantity(){
	return deliveredQuantity;
}
public void setDeliveredQuantity(BigDecimal deliveredQuantity){
	this.deliveredQuantity = deliveredQuantity;
}

private Integer deliveredPieces;
public Integer getDeliveredPieces(){
	return deliveredPieces;
}
public void setDeliveredPieces(Integer deliveredPieces){
	this.deliveredPieces = deliveredPieces;
}

private String outstandingQuantity;
public String getOutstandingQuantity(){
	return outstandingQuantity;
}
public void setOutstandingQuantity(String outstandingQuantity){
	this.outstandingQuantity = outstandingQuantity;
}

private Integer outstandingPieces;
public Integer getOutstandingPieces(){
	return outstandingPieces;
}
public void setOutstandingPieces(Integer outstandingPieces){
	this.outstandingPieces = outstandingPieces;
}

private String availabilityStatus;
public String getAvailabilityStatus(){
	return availabilityStatus;
}
public void setAvailabilityStatus(String availabilityStatus){
	this.availabilityStatus = availabilityStatus;
}

private String paymentChqDetail;
public String getPaymentChqDetail(){
	return paymentChqDetail;
}
public void setPaymentChqDetail(String paymentChqDetail){
	this.paymentChqDetail = paymentChqDetail;
}

private String deliveryStatus;
public String getDeliveryStatus(){
	return deliveryStatus;
}
public void setDeliveryStatus(String deliveryStatus){
	this.deliveryStatus = deliveryStatus;
}

private Integer orderedPieces;
public Integer getOrderedPieces(){
	return orderedPieces;
}
public void setOrderedPieces(Integer orderedPieces){
	this.orderedPieces = orderedPieces;
}

private Integer noOfBundles;
public Integer getNoOfBundles(){
	return noOfBundles;
}
public void setNoOfBundles(Integer noOfBundles){
	this.noOfBundles = noOfBundles;
}

private BigDecimal basePrice;
public BigDecimal getBasePrice(){
	return basePrice;
}
public void setBasePrice(BigDecimal basePrice){
	this.basePrice = basePrice;
}

private BigDecimal extras;
public BigDecimal getExtras(){
	return extras;
}
public void setExtras(BigDecimal extras){
	this.extras = extras;
}

private BigDecimal qualityTestExtras;
public BigDecimal getQualityTestExtras(){
	return qualityTestExtras;
}
public void setQualityTestExtras(BigDecimal qualityTestExtras){
	this.qualityTestExtras = qualityTestExtras;
}

private BigDecimal discount1;
public BigDecimal getDiscount1(){
	return discount1;
}
public void setDiscount1(BigDecimal discount1){
	this.discount1 = discount1;
}

private BigDecimal enteredOrdQuantity;
public BigDecimal getEnteredOrdQuantity(){
	return enteredOrdQuantity;
}
public void setEnteredOrdQuantity(BigDecimal enteredOrdQuantity){
	this.enteredOrdQuantity = enteredOrdQuantity;
}

private String standard;
public String getStandard(){
	return standard;
}
public void setStandard(String standard){
	this.standard = standard;
}

private String standardDesc;
public String getStandardDesc(){
	return standardDesc;
}
public void setStandardDesc(String standardDesc){
	this.standardDesc = standardDesc;
}

private String sectionGrade;
public String getSectionGrade(){
	return sectionGrade;
}
public void setSectionGrade(String sectionGrade){
	this.sectionGrade = sectionGrade;
}

private String sectionGradeDesc;
public String getSectionGradeDesc(){
	return sectionGradeDesc;
}
public void setSectionGradeDesc(String sectionGradeDesc){
	this.sectionGradeDesc = sectionGradeDesc;
}

private String size;
public String getSize(){
	return size;
}
public void setSize(String size){
	this.size = size;
}

private BigDecimal kgPerMeter;
public BigDecimal getKgPerMeter(){
	return kgPerMeter;
}
public void setKgPerMeter(BigDecimal kgPerMeter){
	this.kgPerMeter = kgPerMeter;
}

private BigDecimal length;
public BigDecimal getLength(){
	return length;
}
public void setLength(BigDecimal length){
	this.length = length;
}

private BigDecimal barsPerBundle;
public BigDecimal getBarsPerBundle(){
	return barsPerBundle;
}
public void setBarsPerBundle(BigDecimal barsPerBundle){
	this.barsPerBundle = barsPerBundle;
}

private String sectionGroup;
public String getSectionGroup(){
	return sectionGroup;
}
public void setSectionGroup(String sectionGroup){
	this.sectionGroup = sectionGroup;
}

private String level2Id;
public String getLevel2Id(){
	return level2Id;
}
public void setLevel2Id(String level2Id){
	this.level2Id = level2Id;
}

private String ceLogo;
public String getCeLogo(){
	return ceLogo;
}
public void setCeLogo(String ceLogo){
	this.ceLogo = ceLogo;
}

private String section;
public String getSection(){
	return section;
}
public void setSection(String section){
	this.section = section;
}

private BigDecimal sizeGroup;
public BigDecimal getSizeGroup(){
	return sizeGroup;
}
public void setSizeGroup(BigDecimal sizeGroup){
	this.sizeGroup = sizeGroup;
}

private String isiLogo;
public String getIsiLogo(){
	return isiLogo;
}
public void setIsiLogo(String isiLogo){
	this.isiLogo = isiLogo;
}

private Boolean impactTest;
public Boolean getImpactTest(){
	return impactTest;
}
public void setImpactTest(Boolean impactTest){
	this.impactTest = impactTest;
}

private Boolean bendTest;
public Boolean getBendTest(){
	return bendTest;
}
public void setBendTest(Boolean bendTest){
	this.bendTest = bendTest;
}

private Boolean ultralightTest;
public Boolean getUltralightTest(){
	return ultralightTest;
}
public void setUltralightTest(Boolean ultralightTest){
	this.ultralightTest = ultralightTest;
}

private Boolean inspection;
public Boolean getInspection(){
	return inspection;
}
public void setInspection(Boolean inspection){
	this.inspection = inspection;
}

private Boolean ultrasonoicTest;
public Boolean getUltrasonoicTest(){
	return ultrasonoicTest;
}
public void setUltrasonoicTest(Boolean ultrasonoicTest){
	this.ultrasonoicTest = ultrasonoicTest;
}

private String gradePricingGroup;
public String getGradePricingGroup(){
	return gradePricingGroup;
}
public void setGradePricingGroup(String gradePricingGroup){
	this.gradePricingGroup = gradePricingGroup;
}

private BigDecimal totalNoPieces;
public BigDecimal getTotalNoPieces(){
	return totalNoPieces;
}
public void setTotalNoPieces(BigDecimal totalNoPieces){
	this.totalNoPieces = totalNoPieces;
}

private BigDecimal bundleWt;
public BigDecimal getBundleWt(){
	return bundleWt;
}
public void setBundleWt(BigDecimal bundleWt){
	this.bundleWt = bundleWt;
}

private String updateIndicator;
public String getUpdateIndicator(){
	return updateIndicator;
}
public void setUpdateIndicator(String updateIndicator){
	this.updateIndicator = updateIndicator;
}

private Date changedOn;
public Date getChangedOn(){
	return changedOn;
}
public void setChangedOn(Date changedOn){
	this.changedOn = changedOn;
}

private Date syncStatus;
public Date getSyncStatus(){
	return syncStatus;
}
public void setSyncStatus(Date syncStatus){
	this.syncStatus = syncStatus;
}
private String createdBy;
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
private Date createdOn;
public Date getCreatedOn() {
	return createdOn;
}
public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
}
private String changedBy;
public String getChangedBy() {
	return changedBy;
}
public void setChangedBy(String changedBy) {
	this.changedBy = changedBy;
}
private Date changedOn;
public Date getChangedOn() {
	return changedOn;
}
public void setChangedOn(Date changedOn) {
	this.changedOn = changedOn;
}
}
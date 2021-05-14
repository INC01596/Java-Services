package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Id;

@Entity
@Table(name = "SALES_ORDER_ITEM")
public class SalesOrderItem {

	@Id
	@Column(name = "SALES_ITEM_ID", length = 255)
	private String salesItemId;

	public String getSalesItemId() {
		return salesItemId;
	}

	public void setSalesItemId(String salesItemId) {
		this.salesItemId = salesItemId;
	}

	@Column(name = "ClientSpecific", precision = 3, scale = 0)
	private Integer clientSpecific;

	public Integer getClientSpecific() {
		return clientSpecific;
	}

	public void setClientSpecific(Integer clientSpecific) {
		this.clientSpecific = clientSpecific;
	}

	@Column(name = "LINE_ITEM_NUMBER", length = 10)
	private String lineItemNumber;

	public String getLineItemNumber() {
		return lineItemNumber;
	}

	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}

	@Column(name = "Material", length = 40)
	private String material;

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Column(name = "MaterialDesc", length = 100)
	private String materialDesc;

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	@Column(name = "CumConfirmedQty", length = 10)
	private String cumConfirmedQty;

	public String getCumConfirmedQty() {
		return cumConfirmedQty;
	}

	public void setCumConfirmedQty(String cumConfirmedQty) {
		this.cumConfirmedQty = cumConfirmedQty;
	}

	@Column(name = "ItemCategory", length = 5)
	private String itemCategory;

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	@Column(name = "Plant", length = 5)
	private String plant;

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	@Column(name = "NetValue", precision = 13, scale = 3)
	private BigDecimal netValue;

	public BigDecimal getNetValue() {
		return netValue;
	}

	public void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}

	@Column(name = "AmountB4Vat", precision = 10, scale = 3)
	private BigDecimal amountB4Vat;

	public BigDecimal getAmountB4Vat() {
		return amountB4Vat;
	}

	public void setAmountB4Vat(BigDecimal amountB4Vat) {
		this.amountB4Vat = amountB4Vat;
	}

	@Column(name = "VatPercent", precision = 10, scale = 3)
	private BigDecimal vatPercent;

	public BigDecimal getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(BigDecimal vatPercent) {
		this.vatPercent = vatPercent;
	}

	@Column(name = "VatAmount", precision = 10, scale = 3)
	private BigDecimal vatAmount;

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	@Column(name = "TotalAmountInclVat", precision = 10, scale = 3)
	private BigDecimal totalAmountInclVat;

	public BigDecimal getTotalAmountInclVat() {
		return totalAmountInclVat;
	}

	public void setTotalAmountInclVat(BigDecimal totalAmountInclVat) {
		this.totalAmountInclVat = totalAmountInclVat;
	}

	@Column(name = "DeliveredQuantity", precision = 13, scale = 3)
	private BigDecimal deliveredQuantity;

	public BigDecimal getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(BigDecimal deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	@Column(name = "DeliveredPieces", precision = 10, scale = 0)
	private Integer deliveredPieces;

	public Integer getDeliveredPieces() {
		return deliveredPieces;
	}

	public void setDeliveredPieces(Integer deliveredPieces) {
		this.deliveredPieces = deliveredPieces;
	}

	@Column(name = "OutstandingQuantity", length = 15)
	private String outstandingQuantity;

	public String getOutstandingQuantity() {
		return outstandingQuantity;
	}

	public void setOutstandingQuantity(String outstandingQuantity) {
		this.outstandingQuantity = outstandingQuantity;
	}

	@Column(name = "OutstandingPieces", precision = 10, scale = 0)
	private Integer outstandingPieces;

	public Integer getOutstandingPieces() {
		return outstandingPieces;
	}

	public void setOutstandingPieces(Integer outstandingPieces) {
		this.outstandingPieces = outstandingPieces;
	}

	@Column(name = "AvailabilityStatus", length = 2)
	private String availabilityStatus;

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	@Column(name = "PaymentChqDetail", length = 10)
	private String paymentChqDetail;

	public String getPaymentChqDetail() {
		return paymentChqDetail;
	}

	public void setPaymentChqDetail(String paymentChqDetail) {
		this.paymentChqDetail = paymentChqDetail;
	}

	@Column(name = "DeliveryStatus", length = 2)
	private String deliveryStatus;

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Column(name = "OrderedPieces", precision = 10, scale = 0)
	private Integer orderedPieces;

	public Integer getOrderedPieces() {
		return orderedPieces;
	}

	public void setOrderedPieces(Integer orderedPieces) {
		this.orderedPieces = orderedPieces;
	}

	@Column(name = "NoOfBundles", precision = 10, scale = 0)
	private Integer noOfBundles;

	public Integer getNoOfBundles() {
		return noOfBundles;
	}

	public void setNoOfBundles(Integer noOfBundles) {
		this.noOfBundles = noOfBundles;
	}

	@Column(name = "BasePrice", precision = 10, scale = 3)
	private BigDecimal basePrice;

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	@Column(name = "Extras", precision = 10, scale = 3)
	private BigDecimal extras;

	public BigDecimal getExtras() {
		return extras;
	}

	public void setExtras(BigDecimal extras) {
		this.extras = extras;
	}

	@Column(name = "QualityTestExtras", precision = 10, scale = 3)
	private BigDecimal qualityTestExtras;

	public BigDecimal getQualityTestExtras() {
		return qualityTestExtras;
	}

	public void setQualityTestExtras(BigDecimal qualityTestExtras) {
		this.qualityTestExtras = qualityTestExtras;
	}

	@Column(name = "Discount1", precision = 10, scale = 3)
	private BigDecimal discount1;

	public BigDecimal getDiscount1() {
		return discount1;
	}

	public void setDiscount1(BigDecimal discount1) {
		this.discount1 = discount1;
	}

	@Column(name = "EnteredOrdQuantity", precision = 10, scale = 2)
	private BigDecimal enteredOrdQuantity;

	public BigDecimal getEnteredOrdQuantity() {
		return enteredOrdQuantity;
	}

	public void setEnteredOrdQuantity(BigDecimal enteredOrdQuantity) {
		this.enteredOrdQuantity = enteredOrdQuantity;
	}

	@Column(name = "Standard", length = 2)
	private String standard;

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Column(name = "StandardDesc", length = 20)
	private String standardDesc;

	public String getStandardDesc() {
		return standardDesc;
	}

	public void setStandardDesc(String standardDesc) {
		this.standardDesc = standardDesc;
	}

	@Column(name = "SectionGrade", length = 15)
	private String sectionGrade;

	public String getSectionGrade() {
		return sectionGrade;
	}

	public void setSectionGrade(String sectionGrade) {
		this.sectionGrade = sectionGrade;
	}

	@Column(name = "SectionGradeDesc", length = 20)
	private String sectionGradeDesc;

	public String getSectionGradeDesc() {
		return sectionGradeDesc;
	}

	public void setSectionGradeDesc(String sectionGradeDesc) {
		this.sectionGradeDesc = sectionGradeDesc;
	}

	@Column(name = "Size", length = 20)
	private String size;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(name = "KgPerMeter", precision = 10, scale = 3)
	private BigDecimal kgPerMeter;

	public BigDecimal getKgPerMeter() {
		return kgPerMeter;
	}

	public void setKgPerMeter(BigDecimal kgPerMeter) {
		this.kgPerMeter = kgPerMeter;
	}

	@Column(name = "Length", precision = 10, scale = 3)
	private BigDecimal length;

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	@Column(name = "BarsPerBundle", precision = 10, scale = 3)
	private BigDecimal barsPerBundle;

	public BigDecimal getBarsPerBundle() {
		return barsPerBundle;
	}

	public void setBarsPerBundle(BigDecimal barsPerBundle) {
		this.barsPerBundle = barsPerBundle;
	}

	@Column(name = "SectionGroup", length = 20)
	private String sectionGroup;

	public String getSectionGroup() {
		return sectionGroup;
	}

	public void setSectionGroup(String sectionGroup) {
		this.sectionGroup = sectionGroup;
	}

	@Column(name = "Level2Id", length = 10)
	private String level2Id;

	public String getLevel2Id() {
		return level2Id;
	}

	public void setLevel2Id(String level2Id) {
		this.level2Id = level2Id;
	}

	@Column(name = "CeLogo", length = 2)
	private String ceLogo;

	public String getCeLogo() {
		return ceLogo;
	}

	public void setCeLogo(String ceLogo) {
		this.ceLogo = ceLogo;
	}

	@Column(name = "Section", length = 10)
	private String section;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Column(name = "SizeGroup", precision = 10, scale = 3)
	private BigDecimal sizeGroup;

	public BigDecimal getSizeGroup() {
		return sizeGroup;
	}

	public void setSizeGroup(BigDecimal sizeGroup) {
		this.sizeGroup = sizeGroup;
	}

	@Column(name = "IsiLogo", length = 2)
	private String isiLogo;

	public String getIsiLogo() {
		return isiLogo;
	}

	public void setIsiLogo(String isiLogo) {
		this.isiLogo = isiLogo;
	}

	@Column(name = "ImpactTest")
	private Boolean impactTest;

	public Boolean getImpactTest() {
		return impactTest;
	}

	public void setImpactTest(Boolean impactTest) {
		this.impactTest = impactTest;
	}

	@Column(name = "BendTest")
	private Boolean bendTest;

	public Boolean getBendTest() {
		return bendTest;
	}

	public void setBendTest(Boolean bendTest) {
		this.bendTest = bendTest;
	}

	@Column(name = "UltralightTest")
	private Boolean ultralightTest;

	public Boolean getUltralightTest() {
		return ultralightTest;
	}

	public void setUltralightTest(Boolean ultralightTest) {
		this.ultralightTest = ultralightTest;
	}

	@Column(name = "Inspection")
	private Boolean inspection;

	public Boolean getInspection() {
		return inspection;
	}

	public void setInspection(Boolean inspection) {
		this.inspection = inspection;
	}

	@Column(name = "UltrasonoicTest")
	private Boolean ultrasonoicTest;

	public Boolean getUltrasonoicTest() {
		return ultrasonoicTest;
	}

	public void setUltrasonoicTest(Boolean ultrasonoicTest) {
		this.ultrasonoicTest = ultrasonoicTest;
	}

	@Column(name = "GradePricingGroup", length = 20)
	private String gradePricingGroup;

	public String getGradePricingGroup() {
		return gradePricingGroup;
	}

	public void setGradePricingGroup(String gradePricingGroup) {
		this.gradePricingGroup = gradePricingGroup;
	}

	@Column(name = "TotalNoPieces", precision = 10, scale = 3)
	private BigDecimal totalNoPieces;

	public BigDecimal getTotalNoPieces() {
		return totalNoPieces;
	}

	public void setTotalNoPieces(BigDecimal totalNoPieces) {
		this.totalNoPieces = totalNoPieces;
	}

	@Column(name = "BundleWt", precision = 10, scale = 3)
	private BigDecimal bundleWt;

	public BigDecimal getBundleWt() {
		return bundleWt;
	}

	public void setBundleWt(BigDecimal bundleWt) {
		this.bundleWt = bundleWt;
	}

	@Column(name = "UpdateIndicator", length = 2)
	private String updateIndicator;

	public String getUpdateIndicator() {
		return updateIndicator;
	}

	public void setUpdateIndicator(String updateIndicator) {
		this.updateIndicator = updateIndicator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ChangedOn")
	private Date changedOn;

	public Date getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(Date changedOn) {
		this.changedOn = changedOn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SyncStatus")
	private Date syncStatus;

	public Date getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Date syncStatus) {
		this.syncStatus = syncStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // Add optional = false
	@JoinColumn(name = "s4DocumentId")
	@JsonIgnore
	private SalesOrderHeader salesOrderHeader;

	public SalesOrderHeader getSalesOrderHeader() {
		return salesOrderHeader;
	}

	public void setSalesOrderHeader(SalesOrderHeader salesOrderHeader) {
		this.salesOrderHeader = salesOrderHeader;
	}

	@Column(name = "CREATED_BY", length = 200)
	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "LAST_CHANGED_BY", length = 200)
	private String lastChangedBy;

	public String getLastChangedBy() {
		return lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CHANGED_ON")
	private Date lastChangedOn;

	public Date getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

	// Awadhesh Kumar

	@Column(name = "SALES_HEADER_ID", length = 10)
	private String salesHeaderId;

	public String getSalesHeaderId() {
		return salesHeaderId;
	}

	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}

	@Column(name="BLOCKED")
	private Boolean blocked;
	
	
	
	
	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	@Column(name = "OUT_BOUND_ORDER_ID",length=25)
	private String outBoundOrderId;

	
	
	public String getOutBoundOrderId() {
		return outBoundOrderId;
	}

	public void setOutBoundOrderId(String outBoundOrderId) {
		this.outBoundOrderId = outBoundOrderId;
	}

	@Column(name = "PGI_ID",length=25)
	private String pgiId;

	public String getPgiId() {
		return pgiId;
	}

	public void setPgiId(String pgiId) {
		this.pgiId = pgiId;
	}
	
	@Column(name = "INV_ID",length=25)
	private String invId;
	
	
	
	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	@Column(name="PICKED_QUANTITY")
	private BigDecimal pickedQuantity;

	public BigDecimal getPickedQuantity() {
		return pickedQuantity;
	}

	public void setPickedQuantity(BigDecimal pickedQuantity) {
		this.pickedQuantity = pickedQuantity;
	}

	@Column(name="SLOC")
	private String sloc;

	public String getSloc() {
		return sloc;
	}

	public void setSloc(String sloc) {
		this.sloc = sloc;
	}
	
	@Column(name="UOM")
	private String uom;

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	
	
	
	
	

		
}
package com.idorsia.research.send.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class FoodWater implements Serializable {

	private static final long serialVersionUID = -3179257759580524496L;
	private String studyId;
	private String domain;
	private String usubjId;
	private String poolId;
	private Integer fwSeq;
	private String fwGrpId;
	private String fwTestCd;
	private String fwTest;
	private BigDecimal fwOrres;
	private String fwOrresu;
	private BigDecimal fwStresc;
	private BigDecimal fwStresn;
	private String fwStresu;
	private String fwStat;
	private String fwReasNd;
	private String fwExclFl;
	private String fwReaSex;
	private Timestamp fwDtc;
	private Timestamp fwEndtc;
	private String sFwDtc;
	private String sFwEndtc;
	private Long fwDy;
	private Long fwEndy;
	private Double foodTare;
	private Double foodWeight;
	private Double foodConsumption;
	private Double waterTare;
	private Double waterWeight;
	private Double waterConsumption;
	private Integer nanimal;
	private Double averageFoodConsumption;
	private Double averageWaterConsumption;
	private Date startDate;
	private Long stratification;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsubjId() {
		return usubjId;
	}

	public void setUsubjId(String usubjId) {
		this.usubjId = usubjId;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public Integer getFwSeq() {
		return fwSeq;
	}

	public void setFwSeq(Integer fwSeq) {
		this.fwSeq = fwSeq;
	}

	public String getFwGrpId() {
		return fwGrpId;
	}

	public void setFwGrpId(String fwGrpId) {
		this.fwGrpId = fwGrpId;
	}

	public String getFwTestCd() {
		return fwTestCd;
	}

	public void setFwTestCd(String fwTestCd) {
		this.fwTestCd = fwTestCd;
	}

	public String getFwTest() {
		return fwTest;
	}

	public void setFwTest(String fwTest) {
		this.fwTest = fwTest;
	}

	public BigDecimal getFwOrres() {
		return fwOrres;
	}

	public void setFwOrres(BigDecimal fwOrres) {
		this.fwOrres = fwOrres;
	}

	public String getFwOrresu() {
		return fwOrresu;
	}

	public void setFwOrresu(String fwOrresu) {
		this.fwOrresu = fwOrresu;
	}

	public BigDecimal getFwStresc() {
		return fwStresc;
	}

	public void setFwStresc(BigDecimal fwStresc) {
		this.fwStresc = fwStresc;
	}

	public BigDecimal getFwStresn() {
		return fwStresn;
	}

	public void setFwStresn(BigDecimal fwStresn) {
		this.fwStresn = fwStresn;
	}

	public String getFwStresu() {
		return fwStresu;
	}

	public void setFwStresu(String fwStresu) {
		this.fwStresu = fwStresu;
	}

	public String getFwStat() {
		return fwStat;
	}

	public void setFwStat(String fwStat) {
		this.fwStat = fwStat;
	}

	public String getFwReasNd() {
		return fwReasNd;
	}

	public void setFwReasNd(String fwReasNd) {
		this.fwReasNd = fwReasNd;
	}

	public String getFwExclFl() {
		return fwExclFl;
	}

	public void setFwExclFl(String fwExclFl) {
		this.fwExclFl = fwExclFl;
	}

	public String getFwReaSex() {
		return fwReaSex;
	}

	public void setFwReaSex(String fwReaSex) {
		this.fwReaSex = fwReaSex;
	}

	public Timestamp getFwDtc() {
		return fwDtc;
	}

	public void setFwDtc(Timestamp fwDtc) {
		this.fwDtc = fwDtc;
	}

	public Timestamp getFwEndtc() {
		return fwEndtc;
	}

	public void setFwEndtc(Timestamp fwEndtc) {
		this.fwEndtc = fwEndtc;
	}

	public Long getFwDy() {
		return fwDy;
	}

	public void setFwDy(Long fwDy) {
		this.fwDy = fwDy;
	}

	public Long getFwEndy() {
		return fwEndy;
	}

	public void setFwEndy(Long fwEndy) {
		this.fwEndy = fwEndy;
	}

	public Double getFoodTare() {
		return foodTare;
	}

	public void setFoodTare(Double foodTare) {
		this.foodTare = foodTare;
	}

	public Double getFoodWeight() {
		return foodWeight;
	}

	public void setFoodWeight(Double foodWeight) {
		this.foodWeight = foodWeight;
	}

	public Double getFoodConsumption() {
		return foodConsumption;
	}

	public void setFoodConsumption(Double foodConsumption) {
		this.foodConsumption = foodConsumption;
	}

	public Double getWaterTare() {
		return waterTare;
	}

	public void setWaterTare(Double waterTare) {
		this.waterTare = waterTare;
	}

	public Double getWaterWeight() {
		return waterWeight;
	}

	public void setWaterWeight(Double waterWeight) {
		this.waterWeight = waterWeight;
	}

	public Double getWaterConsumption() {
		return waterConsumption;
	}

	public void setWaterConsumption(Double waterConsumption) {
		this.waterConsumption = waterConsumption;
	}

	public Integer getNanimal() {
		return nanimal;
	}

	public void setNanimal(Integer nanimal) {
		this.nanimal = nanimal;
	}

	public Double getAverageFoodConsumption() {
		return averageFoodConsumption;
	}

	public void setAverageFoodConsumption(Double averageFoodConsumption) {
		this.averageFoodConsumption = averageFoodConsumption;
	}

	public Double getAverageWaterConsumption() {
		return averageWaterConsumption;
	}

	public void setAverageWaterConsumption(Double averageWaterConsumption) {
		this.averageWaterConsumption = averageWaterConsumption;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getStratification() {
		return stratification;
	}

	public void setStratification(Long stratification) {
		this.stratification = stratification;
	}

	public String getsFwDtc() {
		return sFwDtc;
	}

	public void setsFwDtc(String sFwDtc) {
		this.sFwDtc = sFwDtc;
	}

	public String getsFwEndtc() {
		return sFwEndtc;
	}

	public void setsFwEndtc(String sFwEndtc) {
		this.sFwEndtc = sFwEndtc;
	}

}

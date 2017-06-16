package com.we.bean;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.we.tool.CustomContactDeserialize;
import com.we.tool.CustomDeliverableDeserialize;
import com.we.tool.CustomMilestoneDeserialize;
import com.we.tool.CustomPricingDeserialize;

/**
 * the parameter bean of exporting word
 * 
 * @author Hongfeng Ma
 */
public class ProjectProposalExport {

	private Integer projectProposalID;

	private String projectProposalName;

	private String situation;

	private String objective;

	private String approach;

	private List<Deliverable> deliverables;

	private Pricing pricing;

	private List<Milestone> timelineMilestones;

	private List<Contact> contact;

	private Integer status;

	private Integer version;

	private String comment;

	private Integer parentID;

	private Integer projectBriefID;

	private Long insertDate;

	private Long lastModifyDate;

	private Integer userid;

	private Integer clientID;

	private Long lastOperatorDate;

	private Integer briefUserID;

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getApproach() {
		return approach;
	}

	public void setApproach(String approach) {
		this.approach = approach;
	}

	public List<Deliverable> getDeliverables() {
		return deliverables;
	}

	@JsonDeserialize(using = CustomDeliverableDeserialize.class)
	public void setDeliverables(List<Deliverable> deliverables) {
		this.deliverables = deliverables;
	}

	public Pricing getPricing() {
		return pricing;
	}

	@JsonDeserialize(using = CustomPricingDeserialize.class)
	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	public List<Milestone> getTimelineMilestones() {
		return timelineMilestones;
	}

	@JsonDeserialize(using = CustomMilestoneDeserialize.class)
	public void setTimelineMilestones(List<Milestone> timelineMilestones) {
		this.timelineMilestones = timelineMilestones;
	}

	public List<Contact> getContact() {
		return contact;
	}

	@JsonDeserialize(using = CustomContactDeserialize.class)
	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public Integer getProjectProposalID() {
		return projectProposalID;
	}

	public void setProjectProposalID(Integer projectProposalID) {
		this.projectProposalID = projectProposalID;
	}

	public String getProjectProposalName() {
		return projectProposalName;
	}

	public void setProjectProposalName(String projectProposalName) {
		this.projectProposalName = projectProposalName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public Integer getProjectBriefID() {
		return projectBriefID;
	}

	public void setProjectBriefID(Integer projectBriefID) {
		this.projectBriefID = projectBriefID;
	}

	public Long getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Long insertDate) {
		this.insertDate = insertDate;
	}

	public Long getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Long lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getClientID() {
		return clientID;
	}

	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}

	public Long getLastOperatorDate() {
		return lastOperatorDate;
	}

	public void setLastOperatorDate(Long lastOperatorDate) {
		this.lastOperatorDate = lastOperatorDate;
	}

	public Integer getBriefUserID() {
		return briefUserID;
	}

	public void setBriefUserID(Integer briefUserID) {
		this.briefUserID = briefUserID;
	}

}
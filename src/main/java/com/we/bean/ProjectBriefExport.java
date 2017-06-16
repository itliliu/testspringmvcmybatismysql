package com.we.bean;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.we.tool.CustomListDeserialize;
import com.we.tool.CustomProjectLeadDeserialize;
import com.we.tool.CustomScheduleTimelineDeserialize;

public class ProjectBriefExport {
	private Integer projectBriefID;

	private String projectBriefName;

	private String objectGuidingPrinciple;

	private String communicationObjective;

	private String businessObjective;

	private String outcomes;

	private String context;

	private String targetAudience;

	private String createInsporation;

	private String projectType;

	private ProjectLead projectLeads;

	private String projectID;

	private String deliverable;

	private List<ScheduleTimeline> scheduleTimeline;

	private String announcementType;

	private String keyMessage;

	private String additionalContent;

	private String usOnlyOrGolbalMetrics;

	private String searchTerms;

	private String anyAdditionalData;

	private String requestedBy;

	private Integer status;

	private Integer version;

	private Integer parentID;

	private Integer userid;

	private Integer clientID;

	private Boolean isNeedProposal;

	private Integer projectProposalID;

	private Integer proposalParentID;

	private String projectProposalName;

	public Integer getProjectBriefID() {
		return projectBriefID;
	}

	public void setProjectBriefID(Integer projectBriefID) {
		this.projectBriefID = projectBriefID;
	}

	public String getProjectBriefName() {
		return projectBriefName;
	}

	public void setProjectBriefName(String projectBriefName) {
		this.projectBriefName = projectBriefName;
	}

	public String getObjectGuidingPrinciple() {
		return objectGuidingPrinciple;
	}

	public void setObjectGuidingPrinciple(String objectGuidingPrinciple) {
		this.objectGuidingPrinciple = objectGuidingPrinciple;
	}

	public String getCommunicationObjective() {
		return communicationObjective;
	}

	public void setCommunicationObjective(String communicationObjective) {
		this.communicationObjective = communicationObjective;
	}

	public String getBusinessObjective() {
		return businessObjective;
	}

	public void setBusinessObjective(String businessObjective) {
		this.businessObjective = businessObjective;
	}

	public String getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(String outcomes) {
		this.outcomes = outcomes;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public String getCreateInsporation() {
		return createInsporation;
	}

	public void setCreateInsporation(String createInsporation) {
		this.createInsporation = createInsporation;
	}

	public String getProjectType() {
		return projectType;
	}

	@JsonDeserialize(using = CustomListDeserialize.class)
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public ProjectLead getProjectLeads() {
		return projectLeads;
	}

	@JsonDeserialize(using = CustomProjectLeadDeserialize.class)
	public void setProjectLeads(ProjectLead projectLeads) {
		this.projectLeads = projectLeads;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getDeliverable() {
		return deliverable;
	}

	public void setDeliverable(String deliverable) {
		this.deliverable = deliverable;
	}

	public List<ScheduleTimeline> getScheduleTimeline() {
		return scheduleTimeline;
	}

	@JsonDeserialize(using = CustomScheduleTimelineDeserialize.class)
	public void setScheduleTimeline(List<ScheduleTimeline> scheduleTimeline) {
		this.scheduleTimeline = scheduleTimeline;
	}

	public String getAnnouncementType() {
		return announcementType;
	}

	@JsonDeserialize(using = CustomListDeserialize.class)
	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}

	public String getKeyMessage() {
		return keyMessage;
	}

	public void setKeyMessage(String keyMessage) {
		this.keyMessage = keyMessage;
	}

	public String getAdditionalContent() {
		return additionalContent;
	}

	public void setAdditionalContent(String additionalContent) {
		this.additionalContent = additionalContent;
	}

	public String getUsOnlyOrGolbalMetrics() {
		return usOnlyOrGolbalMetrics;
	}

	public void setUsOnlyOrGolbalMetrics(String usOnlyOrGolbalMetrics) {
		this.usOnlyOrGolbalMetrics = usOnlyOrGolbalMetrics;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public String getAnyAdditionalData() {
		return anyAdditionalData;
	}

	public void setAnyAdditionalData(String anyAdditionalData) {
		this.anyAdditionalData = anyAdditionalData;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
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

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
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

	public Integer getProjectProposalID() {
		return projectProposalID;
	}

	public void setProjectProposalID(Integer projectProposalID) {
		this.projectProposalID = projectProposalID;
	}

	public Integer getProposalParentID() {
		return proposalParentID;
	}

	public void setProposalParentID(Integer proposalParentID) {
		this.proposalParentID = proposalParentID;
	}

	public String getProjectProposalName() {
		return projectProposalName;
	}

	public void setProjectProposalName(String projectProposalName) {
		this.projectProposalName = projectProposalName;
	}

	public Boolean getIsNeedProposal() {
		return isNeedProposal;
	}

	public void setIsNeedProposal(Boolean isNeedProposal) {
		this.isNeedProposal = isNeedProposal;
	}
}

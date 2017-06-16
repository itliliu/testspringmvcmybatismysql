package com.we.bean;

public class ProjectBriefResult extends ProjectBrief{
	private Integer projectProposalID;

	private Integer proposalParentID;

	private String projectProposalName;	

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

}
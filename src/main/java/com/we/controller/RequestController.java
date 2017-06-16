package com.we.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.ProjectBriefResult;
import com.we.bean.ProjectParameter;
import com.we.bean.ProjectProposalResult;
import com.we.bean.Report;
import com.we.service.IClientService;
import com.we.service.IReportService;
import com.we.service.IRequestService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "request")
public class RequestController {
	private static Logger logger = Logger.getLogger(RequestController.class);

	@Resource
	private IRequestService requestService;
	@Resource
	private IReportService reportService;
	@Resource
	private IClientService clientService;

	/**
	 * Save a project brief, set status = 0
	 * 
	 * @param request
	 * @param reponse
	 * @param message:
	 *            project brief data
	 * @return
	 */
	@RequestMapping(value = "/projectbrief/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto createProjectBrief(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectBriefResult biref = null;
		int briefID = 0;
		try {
			biref = mapper.readValue(message, ProjectBriefResult.class);
			// check if client is deactivated.
			boolean isDeactivated = this.clientService.selectStatusByClientID(biref.getClientID());
			if (isDeactivated) {
				return new ResultDto(Constant.NACK, Constant.CLIENT_IS_DEACTIVATED, null);
			}
			briefID = this.requestService.saveProjectBrief(biref);
		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_BRIEF_FAILED, null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_BRIEF_FAILED, null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_BRIEF_FAILED, null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_BRIEF_FAILED, null);
		}
		if (briefID == 0) {
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_BRIEF_FAILED, null);
		}

		reponse.setStatus(Constant.HTTP_OK);
		return new ResultDto(Constant.ACK, Constant.SAVE_BRIEF_SUCCEED, briefID);
	}

	/**
	 * Submit a project brief, set status = 1
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/projectbrief/submit.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto submitProjectBrief(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectBriefResult brief = null;
		int briefID = 0;

		try {
			brief = mapper.readValue(message, ProjectBriefResult.class);
			// check if client is deactivated.
			boolean isDeactivated = this.clientService.selectStatusByClientID(brief.getClientID());
			if (isDeactivated) {
				return new ResultDto(Constant.NACK, Constant.CLIENT_IS_DEACTIVATED, null);
			}
			brief.setStatus(brief.getIsNeedProposal() ? Constant.PROPOSAL_STATUS : Constant.REQUEST_FORM_STATUS);
			briefID = this.requestService.submitProjectBrief(brief);

			if (briefID == 0) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.SUBMIT_BRIEF_FAILED, null);
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUBMIT_BRIEF_SUCCEED, briefID);
		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
		}
	}

	/**
	 * Delete a project brief, set status = -2
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/projectbrief/delete.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto deleteProjectBrief(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectBriefResult biref = null;
		int returnCode = 0;
		try {
			biref = mapper.readValue(message, ProjectBriefResult.class);
			returnCode = this.requestService.deleteProjectBrief(biref);
		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.DELETE_BRIEF_FAILED, null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.DELETE_BRIEF_FAILED, null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.DELETE_BRIEF_FAILED, null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.DELETE_BRIEF_FAILED, null);
		}

		if (returnCode == 0) {
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.DELETE_BRIEF_FAILED, null);
		}

		reponse.setStatus(Constant.HTTP_OK);
		return new ResultDto(Constant.ACK, Constant.DELETE_BRIEF_SUCCEED, returnCode);
	}

	/**
	 * Get project brief by projectBriefID.
	 * 
	 * @param userid
	 * @param projectBriefID
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/projectbrief/get.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getProjectBriefByPrimaryKeyAndUid(int userid, int projectBriefID, HttpServletRequest request,
			HttpServletResponse reponse) {

		Map<String, Object> briefMap = new HashMap<String, Object>();
		briefMap.put("briefID", projectBriefID);
		briefMap.put("userid", userid);

		List<ProjectBriefResult> recordList;
		try {
			recordList = this.requestService.getProjectBriefByPrimaryKeyUIDAndClient(briefMap);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.GET_BRIEF_FAILED, null);
		}
		return new ResultDto(Constant.ACK, Constant.GET_BRIEF_SUCCEED, recordList);
	}

	/**
	 * Save a project proposal, set status = 1
	 * 
	 * @param request
	 * @param reponse
	 * @param message:
	 *            project proposal data
	 * @return
	 */
	@RequestMapping(value = "/projectproposal/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto createProjectProposal(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectProposalResult proposal = null;
		int proposalID = 0;
		try {
			proposal = mapper.readValue(message, ProjectProposalResult.class);
			proposalID = this.requestService.saveProjectProposal(proposal);

			if (proposalID == 0) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.SAVE_PROPOSAL_FAILED, null);
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SAVE_PROPOSAL_SUCCEED, proposalID);

		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_PROPOSAL_FAILED, null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_PROPOSAL_FAILED, null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_PROPOSAL_FAILED, null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SAVE_PROPOSAL_FAILED, null);
		}
	}

	/**
	 * Submit a project proposal, set status = 2(proposal, brief)
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/projectproposal/submit.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto submitProjectProposal(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectProposalResult proposal = null;
		int proposalID = 0;
		// new or update brief info
		try {
			proposal = mapper.readValue(message, ProjectProposalResult.class);
			proposalID = this.requestService.submitProjectProposal(proposal);

			if (proposalID == 0) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.SUBMIT_PROPOSAL_FAILED, null);
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUBMIT_PROPOSAL_SUCCEED, proposalID);
		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SUBMIT_PROPOSAL_FAILED, null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SUBMIT_PROPOSAL_FAILED, null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SUBMIT_PROPOSAL_FAILED, null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SUBMIT_PROPOSAL_FAILED, null);
		}
	}

	/**
	 * Account team reject a project proposal, set status = 1(proposal, brief)
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/projectproposal/reject.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto rejectProjectProposal(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectProposalResult proposal = null;
		int proposalID = 0;
		// new or update brief info
		try {
			proposal = mapper.readValue(message, ProjectProposalResult.class);
			proposalID = this.requestService.rejectProjectProposal(proposal);
			this.requestService.rejectProjectProposalBrief(proposal.getProjectBriefID());
			if (proposalID == 0) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.REJECT_PROPOSAL_FAILED, null);
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.REJECT_PROPOSAL_SUCCEED, proposalID);
		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.REJECT_PROPOSAL_FAILED, null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.REJECT_PROPOSAL_FAILED, null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.REJECT_PROPOSAL_FAILED, null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.REJECT_PROPOSAL_FAILED, null);
		}
	}

	/**
	 * Approve a project proposal, Set status = 3(proposal, brief)
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/projectproposal/approve.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto approveProjectProposal(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectProposalResult proposal = null;
		int proposalID = 0;
		// new or update brief info
		try {
			proposal = mapper.readValue(message, ProjectProposalResult.class);
			proposalID = this.requestService.approveProjectProposal(proposal.getProjectBriefID());
			this.requestService.approveProjectProposalBrief(proposal.getProjectBriefID());
			if (proposalID == 0) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.APPROVE_PROPOSAL_FAILED, null);
			} else {
				int projectID = proposal.getProjectBriefID();
				int userid = proposal.getUserid();
				List<Report> reportList = mapper.readValue(proposal.getDeliverables(),
						new TypeReference<List<Report>>() {
						});
				for (Report temp : reportList) {
					if (temp.getDuedate() == null) {
						reponse.setStatus(Constant.SERVER_ERROR);
						return new ResultDto(Constant.NACK, Constant.DUEDATE_IS_NOT_NULL, null);
					}
					temp.setProjectid(projectID);
					temp.setUserid(userid);
					this.reportService.insertReportWhenApproveProposal(temp);
				}
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.APPROVE_PROPOSAL_SUCCEED, proposalID);
		} catch (SQLException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.APPROVE_PROPOSAL_FAILED, null);
		} catch (JsonParseException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.APPROVE_PROPOSAL_FAILED, null);
		} catch (JsonMappingException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.APPROVE_PROPOSAL_FAILED, null);
		} catch (IOException e) {
			logger.info(Constant.ERROR + e.getMessage());
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.APPROVE_PROPOSAL_FAILED, null);
		}
	}

	/**
	 * Get project proposal by projectBriefID
	 * 
	 * @param projectBriefID
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/projectproposal/get.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getProjectProposalByPrimaryKeyUidAndClient(int projectBriefID, HttpServletRequest request,
			HttpServletResponse reponse) {

		List<ProjectProposalResult> recordList;
		try {
			recordList = this.requestService.getProjectProposalByPrimaryKeyUidAndClient(projectBriefID);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.GET_PROPOSAL_FAILED, null);
		}
		return new ResultDto(Constant.ACK, Constant.GET_PROPOSAL_SUCCEED, recordList);
	}

	/**
	 * get project list, can search project list by keyword, and by total number
	 * and page size, get total pages.
	 * 
	 * @param userid
	 * @param clientID
	 * @param status:
	 *            -1-all,0-initial, 1-proposal, 2-client approve, 3-request,
	 *            4-reporting, 5-complete
	 * @param keyword
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param reponse
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getProjectListByStatusOrKeyword(int userid, int clientID, int status, String keyword,
			int pageIndex, int pageSize, HttpServletRequest request, HttpServletResponse reponse) {

		ProjectParameter param = new ProjectParameter();
		param.setClientID(clientID);
		param.setKeyword(keyword);
		param.setPageIndex(pageIndex);
		param.setPageSize(pageSize);
		param.setStatus(status);
		param.setUserid(userid);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<List<?>> recordList = this.requestService.getProjectListByStatusOrKeyword(param);
			if (recordList.size() > 0) {
				Integer pageNumber = 0;
				if (recordList.get(0).size() > 0) {
					Integer pageTotal = Integer.valueOf(String.valueOf(recordList.get(0).get(0)));
					pageNumber = pageTotal / pageSize + (pageTotal % pageSize == 0 ? 0 : 1);
				}
				List<Object> projectConsoleList = null;
				if (recordList.get(1).size() > 0) {
					projectConsoleList = (List<Object>) recordList.get(1);
				}
				returnMap.put("pageNumber", pageNumber);
				returnMap.put("projectConsole", projectConsoleList);
			}
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.SEARCH_PROJECT_LIST_FAILED, null);
		}

		reponse.setStatus(Constant.HTTP_OK);
		return new ResultDto(Constant.ACK, Constant.SEARCH_PROJECT_LIST_SUCCEED, returnMap);
	}

	/**
	 * Update LastOperatorDate when view project detail
	 * 
	 * @param userid
	 * @param parentID,
	 *            ProjectBriefID or ParentID
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/updateoperatordate.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto updateLastOperatorDate(int userid, int parentID, HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userid);
		paramMap.put("parentID", parentID);

		int returnCode = 0;
		try {
			returnCode = this.requestService.updateLastOperatorDate(paramMap);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.UPDATE_OPERATOR_DATE_FAILED, null);
		}
		reponse.setStatus(Constant.HTTP_OK);
		return new ResultDto(Constant.ACK, Constant.UPDATE_OPERATOR_DATE_SUCCEED, returnCode);
	}
}

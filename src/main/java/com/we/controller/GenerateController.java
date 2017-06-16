package com.we.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.ProjectBriefExport;
import com.we.bean.ProjectBriefResult;
import com.we.bean.ProjectProposal;
import com.we.bean.ProjectProposalExport;
import com.we.bean.VelocityUtil;
import com.we.service.IClientService;
import com.we.service.IReportService;
import com.we.service.IRequestService;
import com.we.tool.Constant;
import com.we.tool.FileConstant;
import com.we.tool.ResultDto;

/**
 * generate word controller
 * 
 * @author Hongfeng Ma
 */
@Controller
@RequestMapping(value = "generate")
public class GenerateController {
	@Resource
	private IRequestService requesrService;
	@Resource
	private IClientService clientService;
	@Resource
	private IReportService reportService;

	/**
	 * Generate word file.
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/generate111.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto generate(HttpServletRequest request, HttpServletResponse response, @RequestBody String message)
			throws SQLException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProjectProposalExport export = mapper.readValue(message, new TypeReference<ProjectProposalExport>() {
			});

			// create file name:
			// /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/Proposal_<UUID>.doc
			String fileName = createDirectoryNameByClientIDAndBriefID(export.getProjectBriefID(), export.getClientID());
			String docName = generateProposal(request, export, fileName);

			response.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.EXPORT_WORD_SUCCEED, FileConstant.exportPath + docName);
		} catch (IOException e) {
			response.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.EXPORT_WORD_FAILED, null);
		}
	}

	/**
	 * create and save brief file and proposal file and report file.
	 * 
	 * @param briefID
	 * @param request
	 * @param isNeedProposal
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "/generatebrief.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto generateBriefAndReport(int briefID, int clientID, boolean isNeedProposal,
			HttpServletRequest request, HttpServletResponse response) {
		String path;
		try {
			path = createDirectoryNameByClientIDAndBriefID(briefID, clientID);
			Integer generateBriefCode = generateBrief(briefID, path, request);
			Integer generateReportCode = generateReport(briefID, path, request);
			Integer generateProposalCode = isNeedProposal ? generateProposal(briefID, path, request) : Constant.HTTP_OK;

			if (generateBriefCode != Constant.HTTP_OK || generateReportCode != Constant.HTTP_OK
					|| generateProposalCode != Constant.HTTP_OK) {
				response.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.ACK, Constant.UPLOAD_WORD_FAILED, null);
			}

			response.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.UPLOAD_WORD_SUCCEED, null);

		} catch (SQLException e) {
			response.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.UPLOAD_WORD_FAILED, e.getMessage());
		} catch (IOException e) {
			response.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, Constant.UPLOAD_WORD_FAILED, e.getMessage());
		}
	}

	/**
	 * create and save brief file.
	 * 
	 * @param briefID
	 * @param path
	 *            : Directory name
	 *            /<clientName>_<clientID>/<projectBriefName>_<projectBirefID or
	 *            parentID>
	 * @param request
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer generateBrief(int briefID, String path, HttpServletRequest request)
			throws SQLException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// get brief info
		ProjectBriefResult brief = this.requesrService.getProjectBriefByBrirfID(briefID);
		if (brief == null) {
			return Constant.SERVER_ERROR;
		}
		String briefStr = mapper.writeValueAsString(brief);
		ProjectBriefExport export = mapper.readValue(briefStr, ProjectBriefExport.class);
		if (export == null) {
			return Constant.SERVER_ERROR;
		}
		generateBrief(request, export, path);

		return Constant.HTTP_OK;
	}

	/**
	 * get request form data and create and save request form(report) file.
	 * 
	 * @param briefID
	 *            projectBriefID or project brief parentID
	 * @param clientID
	 * @param clientName
	 * @param briefName
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer generateReport(int briefID, String path, HttpServletRequest request)
			throws SQLException, IOException {
		List<Map<String, Object>> reportList = this.reportService.getReportByProjectID(briefID);
		if (reportList == null || reportList.isEmpty()) {
			return Constant.SERVER_ERROR;
		}
		for (Map<String, Object> report : reportList) {
			report.put(Constant.REPORTURL, FileConstant.dowloadPath + report.get(Constant.REPORTURL));
			report.put(Constant.POWERBIURL, FileConstant.dowloadPath + report.get(Constant.POWERBIURL));
			report.put(Constant.REPORTFILEURL, FileConstant.dowloadPath + report.get(Constant.REPORTFILEURL));
		}
		generateReport(request, reportList, path);

		return Constant.HTTP_OK;
	}

	/**
	 * create request form file according request form data
	 * 
	 * @param request
	 * @param reportList
	 * @param fileName
	 * @throws IOException
	 */
	private void generateReport(HttpServletRequest request, List<Map<String, Object>> reportList, String fileName)
			throws IOException {
		VelocityContext context = new VelocityContext();
		context.put("reports", reportList);
		String docName = fileName + "RequestForm_" + UUID.randomUUID().toString() + Constant.WORD_FILE_SUFFIX;
		VelocityUtil util = new VelocityUtil(
				request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "vm");
		util.createDoc(Constant.REPORT_TEMPLATEFILE, context, docName);
	}

	/**
	 * Get proposal data and generate proposal file (need create directory name)
	 * Export proposal word file by proposal data.
	 * 
	 * @param briefID
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/generate.json", method = RequestMethod.GET)
	public ResponseEntity<byte[]> generateProposal(int briefID, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProjectProposal proposal = this.requesrService.getProjectPoroposalByBrirfID(briefID);
			String proposalStr = mapper.writeValueAsString(proposal);
			ProjectProposalExport export = mapper.readValue(proposalStr, ProjectProposalExport.class);
			if (export == null) {
				return null;
			}
			// create file name:
			// /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/Proposal_<UUID>.doc
			String fileName = createDirectoryNameByClientIDAndBriefID(export.getProjectBriefID(), export.getClientID());
			String docName = generateProposal(request, export, fileName);

			return download(docName);

		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Generate and save brief file by brief data.
	 * 
	 * @param request
	 * @param export
	 * @param fileName
	 * @throws IOException
	 */
	private void generateBrief(HttpServletRequest request, ProjectBriefExport export, String fileName)
			throws IOException {
		VelocityContext context = setBriefVelocityContext(export);
		String docName = fileName + "Brief_" + UUID.randomUUID().toString() + Constant.WORD_FILE_SUFFIX;
		VelocityUtil util = new VelocityUtil(
				request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "vm");
		util.createDoc(Constant.BRIEF_TEMPLATEFILE, context, docName);
	}

	/**
	 * Get proposal data, generate and save proposal file by proposal data.
	 * (Don't need create directory name) Generate and
	 * 
	 * @param briefID
	 * @param path
	 * @param request
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private Integer generateProposal(int briefID, String path, HttpServletRequest request)
			throws SQLException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ProjectProposal proposal = this.requesrService.getProjectPoroposalByBrirfID(briefID);
		if (proposal == null) {
			return Constant.SERVER_ERROR;
		}
		String proposalStr = mapper.writeValueAsString(proposal);
		ProjectProposalExport export = mapper.readValue(proposalStr, ProjectProposalExport.class);
		if (export == null) {
			return Constant.SERVER_ERROR;
		}
		generateProposal(request, export, path);
		return Constant.HTTP_OK;
	}

	/**
	 * export proposal word according proposal data.
	 * 
	 * @throws IOException
	 */
	private String generateProposal(HttpServletRequest request, ProjectProposalExport proposal, String fileName)
			throws IOException {
		VelocityContext context = setProposalVelocityContext(proposal);

		String docName = fileName + "Proposal_" + UUID.randomUUID().toString() + Constant.WORD_FILE_SUFFIX;
		VelocityUtil util = new VelocityUtil(
				request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "vm");
		util.createDoc(Constant.PROPOSAL_TEMPLATEFILE, context, docName);
		return docName;
	}

	/**
	 * Set Velocity Context according brief data.
	 * 
	 * @param export
	 *            : project brief data
	 * @return
	 */
	private VelocityContext setBriefVelocityContext(ProjectBriefExport export) {
		VelocityContext context = new VelocityContext();
		// add velocity context
		context.put("objective", export.getObjectGuidingPrinciple());
		context.put("bussinessObjective", export.getBusinessObjective());
		context.put("communicationsObjective", export.getCommunicationObjective());
		context.put("outcomes", export.getOutcomes());
		context.put("context", export.getContext());
		context.put("targetAudience", export.getTargetAudience());
		context.put("creativeInspiration", export.getCreateInsporation());
		context.put("projectLeads", export.getProjectLeads());
		context.put("projectID", export.getProjectID());
		context.put("deliverable", export.getDeliverable());
		context.put("scheduleTimelines", export.getScheduleTimeline());
		context.put("announcementType", export.getAnnouncementType());
		context.put("additionalContent", export.getAdditionalContent());
		context.put("usOnlyOrGlobalMetrics", export.getUsOnlyOrGolbalMetrics());
		context.put("searchTerms", export.getSearchTerms());
		context.put("anyAdditionalData", export.getAnyAdditionalData());
		context.put("keyMessage", export.getKeyMessage());
		context.put("projectType", export.getProjectType());

		return context;
	}

	/**
	 * Set Velocity Context according proposal data.
	 * 
	 * @param proposal
	 *            : proposal data.
	 * @return
	 */
	private VelocityContext setProposalVelocityContext(ProjectProposalExport proposal) {
		VelocityContext context = new VelocityContext();
		SimpleDateFormat format = new SimpleDateFormat("MMMMM dd, yyyy");
		Date date = new Date();
		String currentDate = format.format(date);
		// add velocity context
		context.put(Constant.PROPOSAL_NAME, proposal.getProjectProposalName());
		context.put(Constant.PROPOSAL_CREATEDATE, currentDate);
		context.put(Constant.PROPOSAL_SITUATION, proposal.getSituation());
		context.put(Constant.PROPOSAL_OBJECTIVE, proposal.getObjective());
		context.put(Constant.PROPOSAL_APPROACH, proposal.getApproach());
		context.put(Constant.PROPOSAL_DELIVERABLES, proposal.getDeliverables());
		context.put(Constant.PROPOSAL_PRICING, proposal.getPricing());
		context.put(Constant.PROPOSAL_MILESTONES, proposal.getTimelineMilestones());
		context.put(Constant.PROPOSAL_CONTACT, proposal.getContact());

		return context;
	}

	/**
	 * download file.
	 * 
	 * @param filename
	 * @return
	 */
	public ResponseEntity<byte[]> download(String filename) {
		File file = new File(filename);
		byte[] body = null;
		InputStream is = null;
		ResponseEntity<byte[]> entity = null;
		try {
			is = new FileInputStream(file);
			body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attchement;filename=" + file.getName());
			HttpStatus statusCode = HttpStatus.OK;
			entity = new ResponseEntity<byte[]>(body, headers, statusCode);

		} catch (IOException e) {
			entity = null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					entity = null;
				}
			}
		}
		return entity;
	}

	/**
	 * Create a folder name used to store the brief file or proposal file or
	 * request form file.
	 * 
	 * @param briefID
	 * @param clientID
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private String createDirectoryName(int briefID, int clientID) throws SQLException {
		// get project brief name
		ProjectBriefResult brief = this.requesrService.getProjectBriefByBrirfID(briefID);
		if (brief.getProjectBriefName() == null) {
			return null;
		}
		String briefName = brief.getProjectBriefName();
		// get client name
		Map<String, Object> clientMap = this.clientService.getClientNameByClientID(clientID);
		if (clientMap == null || clientMap.get(Constant.CLIENTNAME) == null) {
			return null;
		}
		String clientName = clientMap.get(Constant.CLIENTNAME).toString();
		// create file name:
		// /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/Proposal_<UUID>.doc
		String fileName = FileConstant.uploadFilePath + clientName + "_" + clientID + File.separator + briefName + "_"
				+ briefID + File.separator;
		return fileName;

	}

	/**
	 * Create a folder name used to store the brief file or proposal file or
	 * request form file.
	 * 
	 * @param briefID
	 * @param clientID
	 * @return
	 * @throws SQLException
	 */
	private String createDirectoryNameByClientIDAndBriefID(int briefID, int clientID) throws SQLException {
		// create file name:
		// /wefileroot/<clientID>/<briefID>/Proposal_<UUID>.doc
		return FileConstant.uploadFilePath + clientID + File.separator + +briefID + File.separator;
	}
}

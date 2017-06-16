package com.we.controller;

import java.io.File;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.ProjectBriefResult;
import com.we.bean.ReportFile;
import com.we.bean.ReportResult;
import com.we.service.IClientService;
import com.we.service.IReportFileService;
import com.we.service.IReportService;
import com.we.service.IRequestService;
import com.we.service.ITemplateService;
import com.we.tool.Constant;
import com.we.tool.FileConstant;
import com.we.tool.ResultDto;

@Controller
@RequestMapping(value = "request/report")
public class ReportController {

	private static Logger logger = Logger.getLogger(ReportController.class);

	@Resource
	private IReportService reportService;
	@Resource
	private IReportFileService reportFileService;
	@Resource
	private IRequestService requestService;
	@Resource
	private ITemplateService templateService;
	@Resource
	private IClientService clientService;

	/**
	 * Get report list info by project brief id or parent id.
	 * 
	 * @param projectID
	 *            : Project Brief Parent ID
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/get.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getReportByProjectID(Integer projectID,
			HttpServletRequest request, HttpServletResponse reponse) {
		logger.info("Call report/get.json");
		try {
			List<Map<String, Object>> reportMap = this.reportService
					.getReportByProjectID(projectID);

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, reportMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info(
					"Call report/get.json met error beacuse of database error",
					e);
			reponse.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, "get report error.", null);
		}
	}

	/**
	 * Save report(the status is request: 3).
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 *            , the report info. the value of reportid is null means this
	 *            report is an newly added report, we need to add them to
	 *            database. the value of operator is "delete" means user delete
	 *            the report, we need to delete the report. otherwise we need to
	 *            update the report.
	 * @return
	 */
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto saveReport(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {

		ObjectMapper mapper = new ObjectMapper();
		ResultDto returnDto = null;
		try {

			List<ReportResult> reportList = mapper.readValue(message,
					new TypeReference<List<ReportResult>>() {
					});
			for (ReportResult temp : reportList) {
				setFile(temp);
				if (temp.getReportid() == null) {
					this.reportService.insertReport(temp);
				} else {
					if ("delete".equals(temp.getOperator())) {
						this.reportService.deleteReport(temp.getReportid());
					} else {
						this.reportService.updateReport(temp);
					}
				}
			}
			int projectBriefID = 0;
			if (reportList.size() > 0) {
				projectBriefID = reportList.get(0).getProjectid();
			}
			this.requestService.updateProjectBriefDate(projectBriefID);
			this.requestService.updateProjectProposalDate(projectBriefID);

			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, Constant.PARAMETER_ERROR,
					null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, Constant.COMMIT_ERROR,
					null);
		}
		return returnDto;
	}

	/**
	 * Commit reports, set status = 4(Reporting)
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 *            , the report info. the value of reportid is null means this
	 *            report is an newly added report, we need to add them to
	 *            database. the value of operator is "delete" means user delete
	 *            the report, we need to delete the report. otherwise we need to
	 *            update the report.
	 * 
	 *            Then we need add the report file info to table ReportFile.
	 * @return
	 */
	@RequestMapping(value = "/commit.json", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(value = "mysql")
	public ResultDto commitReport(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {

		ObjectMapper mapper = new ObjectMapper();
		ResultDto returnDto = null;
		Integer projectID = null;
		try {
			List<ReportResult> reportList = mapper.readValue(message,
					new TypeReference<List<ReportResult>>() {
					});
			for (ReportResult temp : reportList) {
				boolean isDelete = false;
				projectID = temp.getProjectid();
				if (temp.getReportid() == null) {
					this.reportService.insertReport(temp);
				} else { 
					if ("delete".equals(temp.getOperator())) {
						this.reportService.deleteReport(temp.getReportid());
						isDelete = true;
					} else {
						this.reportService.updateReport(temp);
					}
				}

				if (!isDelete) {
					Map<String, Object> paramObject = new HashMap<String, Object>();
					paramObject.put("reportid", temp.getReportid());
					paramObject.put("tilesprotemplateid",
							temp.getTilesprotemplateid());
					paramObject.put("powerbitemplateid",
							temp.getPowerbitemplateid());
					paramObject.put("reportfileurl", temp.getReportfileurl());
					reportFileService.createReportFile(paramObject);
				}
			}

			this.reportService.updateProjectBriefStatus(projectID);
			this.reportService.updateProjectProposalStatus(projectID);
			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, Constant.PARAMETER_ERROR,
					null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, Constant.COMMIT_ERROR,
					null);
		}
		return returnDto;
	}

	/**
	 * Reject reports, set status = 3 AccountTeam doesn't agree the template,
	 * they can reject the report.
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/reject.json", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(value = "mysql")
	public ResultDto rejectReport(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {
		logger.info("Call report/reject.json");
		logger.info("parameters: " + message);
		ObjectMapper mapper = new ObjectMapper();
		ResultDto returnDto = null;

		try {
			ReportResult report = mapper.readValue(message, ReportResult.class);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("projectID", report.getProjectid());
			paramMap.put("comment", report.getComment());
			int returnCode = this.reportService.rejectReport(paramMap);
			if (returnCode == 0) {
				reponse.setStatus(Constant.SERVER_ERROR);
				returnDto = new ResultDto(Constant.NACK, "reject error", null);
			} else {
				reponse.setStatus(Constant.HTTP_OK);
				returnDto = new ResultDto(Constant.ACK, "reject succeed", null);
			}
		} catch (IOException | SQLException e) {
			logger.info("Call report/reject.json met error", e);
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, "reject error", null);
		}
		return returnDto;
	}

	/**
	 * Save report file's url to table ReportFile.
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/savereportfile.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto saveReportUrl(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {

		ObjectMapper mapper = new ObjectMapper();
		ResultDto returnDto = null;
		try {
			List<ReportFile> reportFileList = mapper.readValue(message,
					new TypeReference<List<ReportFile>>() {
					});
			for (ReportFile temp : reportFileList) {
				this.reportFileService.updateReportFile(temp);
			}

			int projectBriefID = 0;
			if (reportFileList.size() > 0) {
				projectBriefID = reportFileList.get(0).getProjectid();
			}
			this.requestService.updateProjectBriefDate(projectBriefID);
			this.requestService.updateProjectProposalDate(projectBriefID);

			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException | SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, "complete error", null);
		}

		return returnDto;
	}

	/**
	 * Complete a project, set status = 5
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/complete.json", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(value = "mysql")
	public ResultDto completeReport(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {

		ObjectMapper mapper = new ObjectMapper();
		ResultDto returnDto = null;
		try {

			List<ReportFile> reportFileList = mapper.readValue(message,
					new TypeReference<List<ReportFile>>() {
					});
			for (ReportFile temp : reportFileList) {
				this.reportFileService.updateReportFile(temp);
			}
			int projectID = reportFileList.get(0).getProjectid();
			this.reportService.completeReport(projectID);

			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException | SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			returnDto = new ResultDto(Constant.NACK, "complete error", null);
		}
		return returnDto;
	}

	/**
	 * 
	 * setFile @param @param reportResult @param @return @return ReportResult @throws
	 */
	private ReportResult setFile(ReportResult reportResult) {
		try {
			Map<String, Object> tempMap = templateService.getById(reportResult
					.getPowerbitemplateid());
			String tempUrl = (String) tempMap.get("fileurl");
			if (!tempUrl.isEmpty()) {
				File tempFile = new File(FileConstant.uploadFilePathTemp,
						tempUrl);
				String url = createDirectoryNameByClientIDAndBriefID(reportResult
						.getProjectid());
				File projectFile = new File(url);

				if (!projectFile.exists()) {
					boolean isSucceed = projectFile.mkdirs();
					if (!isSucceed) {
						return null;
					}
				}
				projectFile = new File(url, tempUrl);
				if (!projectFile.exists()) {
					boolean isSucceed = projectFile.createNewFile();
					if (!isSucceed) {
						return null;
					}
				}

				FileCopyUtils.copy(tempFile, projectFile);
				reportResult.setReportfileurl(tempUrl);
			}
		} catch (Exception e) {
			return null;
		}
		return reportResult;
	}

	/**
	 * Generate the real path The path format is:
	 * /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/
	 * 
	 * @param briefID
	 *            : project brief ID
	 * @param clientID
	 *            : client ID
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private String createDirectoryName(Integer briefID) throws SQLException {
		// get project brief name
		ProjectBriefResult brief = this.requestService
				.getProjectBriefByBrirfID(briefID);
		if (brief.getProjectBriefName() == null) {
			return null;
		}
		String briefName = brief.getProjectBriefName();
		Map<String, Object> clientMap = this.clientService
				.getClientNameByClientID(brief.getClientID());
		if (clientMap == null || clientMap.get(Constant.CLIENTNAME) == null) {
			return null;
		}
		String clientName = clientMap.get(Constant.CLIENTNAME).toString();
		// create file name:
		// /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/Proposal_<UUID>.doc
		String fileName = FileConstant.uploadFilePath + clientName + "_"
				+ brief.getClientID() + File.separator + briefName + "_"
				+ briefID + File.separator;
		return fileName;
	}

	/**
	 * Generate the real path The path format is:
	 * /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/
	 * 
	 * @param briefID
	 *            : project brief ID
	 * @param clientID
	 *            : client ID
	 * @return
	 * @throws SQLException
	 */
	private String createDirectoryNameByClientIDAndBriefID(Integer briefID)
			throws SQLException {
		ProjectBriefResult brief = this.requestService
				.getProjectBriefByBrirfID(briefID);
		if (brief.getProjectBriefName() == null) {
			return null;
		}
		// create file name:
		// /wefileroot/<clientID>/<briefID>/Proposal_<UUID>.doc
		return FileConstant.uploadFilePath + brief.getClientID()
				+ File.separator + briefID + File.separator;
	}
}

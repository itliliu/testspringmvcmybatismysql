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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.Template;
import com.we.service.IClientService;
import com.we.service.ITemplateService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "template")
public class TemplateController {
	private static Logger logger = Logger.getLogger(ClientController.class);

	@Resource
	private ITemplateService tempalteService;
	@Resource
	private IClientService clientService;

	/**
	 * Get recent 5 template.
	 * 
	 * @param userid
	 * @param clientId
	 * @param type
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/recent.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto recentTemplate(int userid, int clientId, String type, HttpServletRequest request,
			HttpServletResponse reponse) {
		try {
			List<Map<String, Object>> listMaps = tempalteService.recentTemplate(userid, clientId, type.toLowerCase());
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, listMaps);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get recentTemplate fail", null);
		}
	}

	/**
	 * Get more template, need paging.
	 * 
	 * @param title,
	 *            the name of template.
	 * @param userid
	 * @param clientid
	 * @param reportType,
	 *            "-1"=all, Citation...
	 * @param format,
	 *            "-1"=all, word...
	 * @param type,
	 *            the value is 'public' or 'owner'
	 * @param pageSize
	 * @param pageIndex
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/more.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto selectByType(String title, Integer userid, Integer clientid, Integer reportType, Integer format,
			String type, Integer pageSize, Integer pageIndex, HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Template template = new Template();
			if (!title.isEmpty()) {
				template.setTemplatename(title);
			}
			if (reportType != null) {
				template.setReporttype(reportType);
			}
			if (format != null) {
				template.setFormat(format);
			}
			map.put("template", template);
			map.put("userid", userid);
			map.put("clientid", clientid);
			map.put("type", type);
			map.put("rows", pageSize);
			map.put("page", (pageIndex - 1) * pageSize);
			Map<String, Object> returnMaps = tempalteService.selectType(map, type);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, returnMaps);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get more fail", null);
		}
	}

	/**
	 * Get detailed template information.
	 * 
	 * @param userid
	 * @param clientId
	 * @param templateId
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/detailed.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getDetailed(int templateId, HttpServletRequest request, HttpServletResponse reponse) {
		try {

			Map<String, Object> map = tempalteService.getById(templateId);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, map);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get detailed fail", null);
		}
	}

	/**
	 * delete a template
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/delete.json", method = RequestMethod.PUT)
	@ResponseBody
	public ResultDto deleteById(HttpServletRequest request, HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Template template = mapper.readValue(message, Template.class);
			
			boolean isDeactivated = this.clientService.selectClientStatusByTemplateID(template.getTemplateid());
			if (isDeactivated) {
				return new ResultDto(Constant.NACK, Constant.CLIENT_IS_DEACTIVATED_TEMP_DELETE, null);
			}

			int returnCode = tempalteService.deleteById(template.getTemplateid());
			if (returnCode == Constant.SERVER_ERROR) {
				return new ResultDto(Constant.NACK, Constant.DELETE_SQL_ERROR, null);
			} else if (returnCode == Constant.TEMPLATE_IS_USED) {
				return new ResultDto(Constant.NACK, Constant.DELETE_TEMPLATE_FAILED, null);
			}
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, Constant.DELETE_PARAM_ERROR, null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, Constant.DELETE_SQL_ERROR, null);
		}
	}

	/**
	 * 
	 * @param clientId
	 * @param reportType
	 * @param format
	 * @param templateType
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/templatenamelist.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getTemplateListForReport(int clientId, int reportType, int format, HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clientid", clientId);
		paramMap.put("reportType", reportType);
		paramMap.put("format", format);

		try {
			List<Map<String, Object>> resustTemplate = this.tempalteService.getTemplateListForReport(paramMap);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, resustTemplate);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get template failed", null);
		}
	}

	/**
	 * Save a Template
	 * 
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto save(HttpServletRequest request, HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Template template = mapper.readValue(message, Template.class);
			// check if client is deactivated.
			boolean isDeactivated = this.clientService.selectStatusByClientID(template.getClientid());
			if (isDeactivated) {
				return new ResultDto(Constant.NACK, Constant.CLIENT_IS_DEACTIVATED_TEMP, null);
			}

			int returnCode = 0;
			if (template.getTemplateid() != null) {
				returnCode = tempalteService.editById(template);
			} else {
				returnCode = tempalteService.add(template);
			}

			if (returnCode == Constant.SQL_RETURN_CODE_ERROR) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, "Save Template failed!", null);
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit parameter error ", null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit sql error ", null);
		}
	}

	@RequestMapping(value = "/getpowerbi.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getPowerBITemplateByClientid(Integer clientid, HttpServletRequest request,
			HttpServletResponse reponse) {
		try {
			List<Map<String, Object>> templates = tempalteService.getPowerBITemplateByClientid(clientid);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, templates);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get detailed fail", null);
		}
	}

	@RequestMapping(value = "/getformatereporttype.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getFormatReportType(HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Map<String, Object> map = tempalteService.getFormatReportType();
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, map);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, " getformatereporttype fail", null);
		}
	}

	/**
	 * get clientID and client parentID by clientID;
	 * 
	 * @param clientID
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/getclientinfo.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getClientIDAndParentID(int clientID, HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Map<String, Object> map = tempalteService.getClientIDAndParentID(clientID);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, map);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get client id and client parent id fail!", null);
		}
	}

}

/**
 * 
 */
package com.we.brief.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.ProjectBriefDto;
import com.we.bean.ProjectParameter;
import com.we.brief.service.IProjectBriefService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

/**
 * @author liliu
 *
 */
@Controller
@RequestMapping(value = "brief")
public class BriefController {
	private static Logger logger = Logger.getLogger(BriefController.class);
	
		@Resource
		private IProjectBriefService briefDtoService;
		
		/**
		 * Add a new ProjectBriefDto
		 * 
		 * @param request
		 * @param reponse
		 * @param message
		 * @return
		 */
		@RequestMapping(value = "/add.json", method = RequestMethod.POST)
		@ResponseBody
		public ResultDto add(HttpServletRequest request,
				HttpServletResponse reponse, @RequestBody String message) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ProjectBriefDto brief = mapper.readValue(message, ProjectBriefDto.class);
				int briefid = briefDtoService.save(brief);
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, briefid);
			} catch (IOException e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				logger.error(e.toString());
				return new ResultDto(Constant.NACK, "add parameter error ", null);
			}  catch (SQLException e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				logger.error(e.toString());
				return new ResultDto(Constant.NACK, "add sql error ", null);
			}
		}
		
		/**
		 * delete  received reports
		 * 
		 * @param request
		 * @param reponse
		 * @param message
		 * @return
		 */
		@RequestMapping(value = "/delete.json", method = RequestMethod.DELETE)
		@ResponseBody
		public ResultDto deleteById(HttpServletRequest request,
				HttpServletResponse reponse, @RequestBody String message) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ProjectBriefDto brief = mapper.readValue(message, ProjectBriefDto.class);
				if("".equals(brief.getBriefId())||brief.getBriefId()==null){
					return new ResultDto(Constant.NACK, "The brief id is null ", null);
				}
				briefDtoService.deleteReceivedReports(brief.getBriefId());
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
			} catch (IOException e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				logger.error(e.toString());
				return new ResultDto(Constant.NACK, "delete parameter error ", null);
			} catch (SQLException e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				logger.error(e.toString());
				return new ResultDto(Constant.NACK, "delete sql error", null);
			}
		}
		
		/**
		 * submit brief message
		 * 
		 * @param request
		 * @param reponse
		 * @param message
		 * @return
		 */
		@RequestMapping(value = "/submit.json", method = RequestMethod.PUT)
		@ResponseBody
		public ResultDto submit( HttpServletRequest request,
				HttpServletResponse reponse, @RequestBody String message) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ProjectBriefDto brief = mapper.readValue(message, ProjectBriefDto.class);
				if("".equals(brief.getBriefId())||brief.getBriefId()==null){
					return new ResultDto(Constant.NACK, "The brief id is null ", null);
				}
				int sub=this.briefDtoService.submit(brief);
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, sub);
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
		
		/**
		 * edit brief message
		 * 
		 * @param request
		 * @param reponse
		 * @param message
		 * @return
		 */
		@RequestMapping(value = "/edit.json", method = RequestMethod.PUT)
		@ResponseBody
		public ResultDto edit( HttpServletRequest request,
				HttpServletResponse reponse, @RequestBody String message) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ProjectBriefDto brief = mapper.readValue(message, ProjectBriefDto.class);
				if("".equals(brief.getBriefId())||brief.getBriefId()==null){
					return new ResultDto(Constant.NACK, "The brief id is null ", null);
				}
				int sub=this.briefDtoService.edit(brief);
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, sub);
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
		

		/**
		 * get project list, can search project list by keyword, and by total number
		 * and page size, get total pages.
		 * 
		 * @param userid
		 * @param clientID
		 * @param status
		 * @param keyword
		 * @param pageIndex
		 * @param pageSize
		 * @param request
		 * @param reponse
		 * @return Map String, Object 
		 * 
		 * 		returnMap.put("pageNumber", pageNumber);
				returnMap.put("projectBrief", projectBriefList);
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/search.json", method = RequestMethod.GET)
		@ResponseBody
		public ResultDto getProjectList(int userid, int clientID, int status, String keyword,
				int pageIndex, int pageSize, HttpServletRequest request, HttpServletResponse reponse) {

			if("0".equals(String.valueOf(userid))||String.valueOf(userid)==null){
				return new ResultDto(Constant.NACK, "The user id is null ", null);
			}
			if("0".equals(String.valueOf(clientID))||String.valueOf(clientID)==null){
				return new ResultDto(Constant.NACK, "The client id is null ", null);
			}
			if("0".equals(String.valueOf(pageIndex))||String.valueOf(pageIndex)==null){
				pageIndex=1;
			}
			if("0".equals(String.valueOf(pageSize))||String.valueOf(pageSize)==null){
				pageSize=10;
			}
			ProjectParameter param = new ProjectParameter();
			param.setClientID(clientID);
			param.setKeyword(keyword);
			param.setPageIndex(pageIndex);
			param.setPageSize(pageSize);
			param.setStatus(status);
			param.setUserid(userid);

			Map<String, Object> returnMap = new HashMap<String, Object>();
			try {
				List<List<?>> recordList = this.briefDtoService.listDetailInfo(param);
				if (recordList.size() > 0) {
					Integer pageNumber = 0;
					if (recordList.get(0).size() > 0) {
						Integer pageTotal = Integer.valueOf(String.valueOf(recordList.get(0).get(0)));
						pageNumber = pageTotal / pageSize + (pageTotal % pageSize == 0 ? 0 : 1);
					}
					List<Object> projectBriefList = null;
					if (recordList.get(1).size() > 0) {
						projectBriefList = (List<Object>) recordList.get(1);
					}
					returnMap.put("pageNumber", pageNumber);
					returnMap.put("projectBrief", projectBriefList);
				}
			} catch (SQLException e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, "search list fail!", null);
			}

			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, returnMap);
		}
		
		/**
		 * get brief detail by briefID;
		 * 
		 * @param briefId
		 * @param request
		 * @param reponse
		 * @return brief
		 */
		@RequestMapping(value = "/getbriefdetail.json", method = RequestMethod.GET)
		@ResponseBody
		public ResultDto getBriefDetailByProdectBriefID(int briefId, HttpServletRequest request, HttpServletResponse reponse) {
			try {
				if("0".equals(String.valueOf(briefId))||String.valueOf(briefId)==null){
					return new ResultDto(Constant.NACK, "The brief id is null ", null);
				}
				ProjectBriefDto brief = (ProjectBriefDto)this.briefDtoService.getBriefDetail(briefId);
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, brief);
			} catch (SQLException e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				logger.error(e.toString());
				return new ResultDto(Constant.NACK, "get brief by id fail!", null);
			}
		}

		
		
}

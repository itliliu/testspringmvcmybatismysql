package com.we.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.we.bean.Template;
import com.we.service.IReportFileService;
import com.we.service.ITemplateService;
import com.we.tool.Constant;
import com.we.tool.FileConstant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "preview")
public class PreviewController {

	@Resource
	private IReportFileService reportFileService;
	@Resource
	private ITemplateService templateService;

	/**
	 * Get the file's real path when preview report or template. The path format
	 * is: /wefileroot/<clientName>_<clientID>/<briefName>_<briefID>/filename
	 * 
	 * @param id
	 *            , project brief id or template id.
	 * @param type
	 *            , the value is "report" or "template" "report": get the
	 *            report's real path "template": get the template's real path
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/get.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getPreviewPath(int id, String type,
			HttpServletRequest request, HttpServletResponse reponse) {
		String path = null;
		if ("report".equals(type)) {
			try {
				Map<String, Object> resultMap = this.reportFileService
						.getReportFilePath(id);
				if (resultMap != null) {
					String clientName = String.valueOf(resultMap
							.get("ClientName"));
					Integer clientID = Integer.valueOf(String.valueOf(resultMap
							.get("ClientID")));
					String projectBriefName = String.valueOf(resultMap
							.get("ProjectBriefName"));
					Integer projectBriefID = Integer.valueOf(String
							.valueOf(resultMap.get("ProjectID")));
					String reportFileUrl = String.valueOf(resultMap
							.get("ReportFileUrl"));
					path = FileConstant.previewFilePath + clientName + "_"
							+ clientID + File.separator + projectBriefName
							+ "_" + projectBriefID + File.separator
							+ reportFileUrl;
					File file = new File(path);
					if (!file.exists()) {
						path = FileConstant.previewFilePath + clientID
								+ File.separator + projectBriefID
								+ File.separator + reportFileUrl;
					}
				}
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, path);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
			} catch (Exception e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
			}
		} else {
			try {
				Template temp = templateService.selectByPrimaryKey(id);
				path = FileConstant.previewFilePathTemp + temp.getFileurl();
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, path);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
			} catch (Exception e) {
				reponse.setStatus(Constant.SERVER_ERROR);
				return new ResultDto(Constant.NACK, Constant.ERROR + e.getMessage(), null);
			}

		}

	}

}

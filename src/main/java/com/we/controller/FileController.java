package com.we.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.we.bean.ProjectBriefResult;
import com.we.service.IClientService;
import com.we.service.IFileService;
import com.we.service.IRequestService;
import com.we.tool.Constant;
import com.we.tool.FileConstant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "file")
public class FileController {

	@Resource
	private IFileService fileService;
	@Resource
	private IRequestService requestService;
	@Resource
	private IClientService clientService;

	/**
	 * Upload file: word, excel, ppt, pbix/pbit frontend need add tag:
	 * enctype="multipart/form-data"
	 * 
	 * @param file
	 *            : the upload file
	 * @param type
	 *            : the value is "file" or "template" "file": upload report, the
	 *            format is word ppt or excel "template": upload template, the
	 *            format is pbix/pbit
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/upload.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto uploadFile(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		if(file.getSize() == 0){
			return new ResultDto(Constant.NACK, "File is empty!", null);
		}
		String path = getPathByType(request);
		String type = request.getParameter("type");
		String url = this.fileService.uploadFile(path, file, type);
		if (url == null) {
			response.setStatus(Constant.SERVER_ERROR);
			return new ResultDto(Constant.NACK, "Upload file failed!", null);
		} else {
			response.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, "Upload file succeed!", url);
		}
	}

	/**
	 * Download file
	 * 
	 * @param filename
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/download.json")
	public ResponseEntity<byte[]> download(String filename,
			HttpServletRequest request) throws SQLException {
		String path = getPathByType(request);
		if (path == null) {
			return null;
		}

		File file = new File(path + filename);
		byte[] body = null;
		InputStream is = null;
		ResponseEntity<byte[]> entity = null;
		try {
			is = new FileInputStream(file);
			body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition",
					"attchement;filename=" + file.getName());
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
	 * Get the real path when upload/download file
	 * 
	 * @param request
	 *            type: the value is "file" or "template" projectID: project
	 *            brief ID clientID: client ID
	 * @return
	 * @throws SQLException
	 */
	private String getPathByType(HttpServletRequest request)
			throws SQLException {
		String type = request.getParameter("type");
		String path = null;
		if (Constant.TYPE_TEMPLATE.equals(type)) {
			path = FileConstant.uploadFilePathTemp;
		} else if (Constant.TYPE_FILE.equals(type)) {
			Integer projectID = Integer.valueOf(request
					.getParameter("projectID"));
			Integer clientID = Integer
					.valueOf(request.getParameter("clientID"));
			path = createDirectoryNameByClientIDAndBriefID(projectID, clientID);
		}
		return path;
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
	private String createDirectoryName(Integer briefID, Integer clientID)
			throws SQLException {
		ProjectBriefResult brief = this.requestService
				.getProjectBriefByBrirfID(briefID);
		if (brief.getProjectBriefName() == null) {
			return null;
		}
		String briefName = brief.getProjectBriefName();
		Map<String, Object> clientMap = this.clientService
				.getClientNameByClientID(clientID);
		if (clientMap == null || clientMap.get(Constant.CLIENTNAME) == null) {
			return null;
		}
		String clientName = clientMap.get(Constant.CLIENTNAME).toString();
		String fileName = FileConstant.uploadFilePath + clientName + "_"
				+ clientID + File.separator + briefName + "_" + briefID
				+ File.separator;
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
	private String createDirectoryNameByClientIDAndBriefID(int briefID,
			int clientID) throws SQLException {
		// create file name:
		// /wefileroot/<clientID>/<briefID>/Proposal_<UUID>.doc
		return FileConstant.uploadFilePath + clientID + File.separator
				+ +briefID + File.separator;
	}
}

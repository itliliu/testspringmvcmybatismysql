package com.we.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.we.service.IFileService;
import com.we.tool.Constant;

@Service("fileService")
public class FileServiceImpl implements IFileService {

	/**
	 * Upload file User can only upload ppt, word, excel, pbit and pbix file
	 * 
	 * @param path:
	 *            the path to restore file
	 * @param file:
	 *            MultipartFile file
	 * @param type:
	 *            file type, the value is "file" and "template"
	 * @return
	 */
	@Override
	public String uploadFile(String path, MultipartFile file, String type) {
		if (path == null) {
			return null;
		}
		String fileName = file.getOriginalFilename();
		String newFileName = null;
		if (fileName.toLowerCase().endsWith(Constant.WORD_FILE_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.WORD_FILE_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.WORD_FILE_NEW_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.WORD_FILE_NEW_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.PPT_FILE_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.PPT_FILE_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.PPT_FILE_NEW_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.PPT_FILE_NEW_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.POWERBI_FILE_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.POWERBI_FILE_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.EXCEL_FILE_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.EXCEL_FILE_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.EXCEL_FILE_NEW_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.EXCEL_FILE_NEW_SUFFIX;
		} else if (fileName.toLowerCase().endsWith(Constant.POWERBI_TEMPLATE_SUFFIX)) {
			newFileName = java.util.UUID.randomUUID().toString() + Constant.POWERBI_TEMPLATE_SUFFIX;
		} else {
			return null;
		}
		if (Constant.TYPE_FILE.equals(type)) {
			newFileName = "Report_" + newFileName;
		}

		File targetFile = new File(path, newFileName);
		if (!targetFile.exists()) {
			boolean isSucceed = targetFile.mkdirs();
			if (!isSucceed) {
				return null;
			}
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			return null;
		}
		return newFileName;

	}

}

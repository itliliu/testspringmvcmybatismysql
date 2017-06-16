package com.we.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
	/**
	 * Upload file
	 * @param path: the path to restore file
	 * @param file: MultipartFile file
	 * @param type: file type, the value is "file" and "template"
	 * @return
	 */
	String uploadFile(String path, MultipartFile file,String type);

}

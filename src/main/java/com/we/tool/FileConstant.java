package com.we.tool;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class FileConstant {
	
	private static Logger logger = Logger.getLogger(FileConstant.class);
	
	public static String uploadFilePath;
	public static String uploadFilePathTemp;
	public static String dowloadPath;
	public static String exportPath;
	public static String previewFilePath;
	public static String previewFilePathTemp;
	
	static{
		try {
			loadProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("read file.properties error. ", e);
		}
	}
	
	private FileConstant(){};

	public static void loadProperties() throws IOException{
		ClassLoader classLoader = FileConstant.class.getClassLoader();  
		Properties prop = new Properties();
		prop.load(classLoader.getResourceAsStream("file.properties"));
		uploadFilePath = prop.getProperty("upload_file_path");
		uploadFilePathTemp = prop.getProperty("upload_file_path_temp");
		dowloadPath = prop.getProperty("download_path");
		exportPath = prop.getProperty("export_path");
		previewFilePath = prop.getProperty("preview_file_path");
		previewFilePathTemp = prop.getProperty("preview_file_path_temp");
	}
}

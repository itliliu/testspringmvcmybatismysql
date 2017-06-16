package com.we.bean;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.we.tool.Constant;

/**
 * create word document
 * 
 * @author Hongfeng Ma
 */
public class VelocityUtil {

	private VelocityEngine engine;

	public VelocityUtil(String templateFolder) {
		Properties properties = new Properties();
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templateFolder);
		engine = new VelocityEngine();
		engine.init(properties);
		engine.init();
	}

	/**
	 * create word file and response
	 * 
	 * @throws IOException
	 */
	public void createDoc(String templateName, VelocityContext context, String docFileName) throws IOException {
		File file = new File(docFileName);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			boolean isMkdir = parentFile.mkdirs();
			if (!isMkdir) {
				throw new IOException(Constant.CREATE_FOLDER_ERROR);
			}
		}
		if (!file.exists()) {
			boolean isCreate = file.createNewFile();
			if (!isCreate) {
				throw new IOException(Constant.CREATE_FILE_ERROR);
			}
		}
		// merge template
		Template template = engine.getTemplate(templateName, "UTF-8");
		PrintWriter write = new PrintWriter(file, "UTF-8");
		template.merge(context, write);

		if (write != null) {
			write.flush();
			write.close();
		}
	}

}

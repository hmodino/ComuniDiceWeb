package com.comunidice.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtils {

	private static Logger logger = LogManager.getLogger(FileUtils.class);

	private static final String UPLOAD_DIRECTORY = ConfigurationManager.getInstance().getParameter("upload.directory");
	private static final String UPLOAD_DISK = ConfigurationManager.getInstance().getParameter("upload.disk");
	private static final String FILE_EXTENSION = ConfigurationManager.getInstance().getParameter("files.extension");
	
	public static void readDocument(HttpServletResponse response, String nombre) {

		String urlBase = UPLOAD_DISK.concat(File.separator).concat(UPLOAD_DIRECTORY).concat(File.separator).concat(nombre)
				.concat(FILE_EXTENSION);
		File file = new File(urlBase);
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			response.setContentType ("application/pdf");
			response.setHeader ("Content-Disposition", "inline; filename="+nombre+".pdf");

			while (fis.read(buffer) > 0) {
				response.getOutputStream().write(buffer);
				response.flushBuffer();
			}
			fis.close();
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
	}
}
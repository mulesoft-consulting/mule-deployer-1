package com.deic;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import javax.activation.DataHandler;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.mule.message.ds.ByteArrayDataSource;
import org.mule.util.IOUtils;

/**
 * This is a utility class to generate attachments for multipart/form-data.
 * 
 * @author Diamond Edge IT Consulting Inc.
 * @version 1.0
 */
public class AttachmentHelper {
	private static Logger logger = Logger.getLogger(AttachmentHelper.class);
	
	/**
	 * Creates a DataHandler object for a String object.
	 * @param name	The name of the attachment.
	 * @param value	The value of the attachment.
	 * @return		The DataHandler object.
	 */
	public static DataHandler getStringAttachment(String name, String value) {
		byte[] bytes = value.getBytes();
		return new DataHandler(new ByteArrayDataSource(bytes, "text/plain", name));
	}
	
	/**
	 * Creates a DataHandler object for a binary object as URL.
	 * @param name	The name of the attachment.
	 * @param value	The value of the attachment.
	 * @return		The DataHandler object.
	 */
	public static DataHandler getURLAttachment(String name, String value) throws Exception {
		InputStream is = new URL(value).openStream();
		byte[] bytes = IOUtils.toByteArray(is);
		logger.info("Writing file with number of bytes: " + bytes.length);
		return new DataHandler(new ByteArrayDataSource(bytes, "application/x-zip-compressed", name));
	}
	
	/**
	 * Creates a DataHandler object for a binary object as file.
	 * @param name	The name of the attachment.
	 * @param value	The value of the attachment.
	 * @return		The DataHandler object.
	 */
	public static DataHandler getFileAttachment(String name, String value) throws Exception {
		byte[] bytes = Files.readAllBytes(new File(value).toPath());
		logger.info("Writing file with number of bytes: " + bytes.length);
		return new DataHandler(new ByteArrayDataSource(bytes, "application/x-zip-compressed", name));
	}
	
	/**
	 * Checks if value is a URL.
	 * @param value	The value of the attachment.
	 * @return	True if URL.
	 */
	public static boolean isURL(String value) {
		return new UrlValidator().isValid(value);
	}
}

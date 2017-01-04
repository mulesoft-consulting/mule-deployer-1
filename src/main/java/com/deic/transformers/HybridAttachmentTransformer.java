package com.deic.transformers;

import org.apache.commons.io.FilenameUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transport.NullPayload;

import com.deic.AttachmentHelper;

/**
 * Transformer class to create the multipart/form-data attachments for a Hybrid Deployment.
 * 
 * @author Diamond Edge IT Consulting Inc.
 * @version 1.0
 */
public class HybridAttachmentTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String baseLogger = message.getProperty("baseLogger", PropertyScope.SESSION);
		String artifactName = message.getProperty("artifactName", PropertyScope.SESSION);
		Integer targetId = message.getProperty("targetId", PropertyScope.SESSION);
		String file = message.getProperty("file", PropertyScope.SESSION);
		
		try {
			message.setPayload(NullPayload.getInstance());
			message.addOutboundAttachment("artifactName", AttachmentHelper.getStringAttachment("artifactName", artifactName));
			logger.info(baseLogger + " Added attachment: " + message.getOutboundAttachmentNames());
			message.addOutboundAttachment("targetId", AttachmentHelper.getStringAttachment("targetId", targetId.toString()));
			logger.info(baseLogger + " Added attachment: " + message.getOutboundAttachmentNames());
			
			if (AttachmentHelper.isURL(file)) {
				message.addOutboundAttachment("file", AttachmentHelper.getURLAttachment(FilenameUtils.getName(file), file));
			} else {
				message.addOutboundAttachment("file", AttachmentHelper.getFileAttachment(FilenameUtils.getName(file), file));
			}
			logger.info(baseLogger + " Added attachment: " + message.getOutboundAttachmentNames());
		} catch (Exception e) {
			logger.error(baseLogger + " Exception: " + e);
			throw new TransformerException(MessageFactory.createStaticMessage(baseLogger + " Failed to add file attachment."));
		}
		return message;
	}

}

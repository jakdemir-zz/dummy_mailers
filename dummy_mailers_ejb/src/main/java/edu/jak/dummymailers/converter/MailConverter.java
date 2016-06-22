package edu.jak.dummymailers.converter;

import java.util.ArrayList;
import java.util.List;

import edu.jak.dummymailers.model.eb.Mail;
import edu.jak.dummymailers.model.eb.MailAddress;
import edu.jak.dummymailers.model.pojo.MailResponse;

public class MailConverter {

	public static MailResponse convert(Mail mail) {
		MailResponse response = new MailResponse();
		if (mail != null) {
			// List<String> mailBCC = new ArrayList<String>();
			// if (mail.getRecipientBCC() != null) {
			// for (MailAddress mailAddress : mail.getRecipientBCC()) {
			// mailBCC.add(mailAddress.getEmail());
			// }
			// response.setMailBCC(mailBCC);
			// }
			//
			//			
			// if (mail.getRecipientCC() != null) {
			// List<String> mailCC = new ArrayList<String>();
			// for (MailAddress mailAddress : mail.getRecipientCC()) {
			// mailCC.add(mailAddress.getEmail());
			// }
			// response.setMailCC(mailCC);
			//
			// }
			//
			// if (mail.getRecipientTo() != null) {
			// List<String> mailTo = new ArrayList<String>();
			// for (MailAddress mailAddress : mail.getRecipientTo()) {
			// mailTo.add(mailAddress.getEmail());
			// }
			// response.setMailTo(mailTo);
			//
			// }

			if (mail.getAddressList() != null) {
				List<String> mailFrom = new ArrayList<String>();
				List<String> mailBCC = new ArrayList<String>();
				List<String> mailCC = new ArrayList<String>();
				List<String> mailTo = new ArrayList<String>();
				List<String> mailReplyTo = new ArrayList<String>();
				for (MailAddress mailAddress : mail.getAddressList()) {
					if (mailAddress.getType().equals("TO")) {
						mailTo.add(mailAddress.getEmail());
					} else if (mailAddress.getType().equals("CC")) {
						mailCC.add(mailAddress.getEmail());
					} else if (mailAddress.getType().equals("BCC")) {
						mailBCC.add(mailAddress.getEmail());
					}else if(mailAddress.getType().equals("FROM")){
						mailFrom.add(mailAddress.getEmail());
					}else if(mailAddress.getType().equals("REPLY_TO")){
						mailReplyTo.add(mailAddress.getEmail());
					}
				}
				response.setReplyMailTo(mailReplyTo);
				response.setMailBCC(mailBCC);
				response.setMailCC(mailCC);
				response.setMailTo(mailTo);
				response.setMailFrom(mailFrom);
			}

			response.setMailId(mail.getRemoteId());

			response.setMailSubject(mail.getSubject());

			response.setMailBody(mail.getBody());

			response.setMailSentDate(mail.getSentDate().toString());
		}

		return response;
	}

}

package edu.jak.dummymailers.sb.delegate.mail.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;

import edu.jak.dummymailers.model.eb.Mail;
import edu.jak.dummymailers.model.eb.MailAddress;
import edu.jak.dummymailers.sb.dao.crud.MailFacadeLocal;
import edu.jak.dummymailers.sb.delegate.mail.MailCatcherDelegateLocal;
import edu.jak.dummymailers.util.MailConstants;
import edu.jak.dummymailers.util.MailServerConnector;

@Stateless
public class MailCatcherDelegate implements MailCatcherDelegateLocal {

	@EJB
	private MailFacadeLocal mailFacadeLocal;

	private void pr(String s) {
		System.out.println(s);
	}

	private String bodyValue = "";

	private String getMailContentInner(Part p) throws MessagingException, IOException {
		/*
		 * Using isMimeType to determine the content type avoids fetching the
		 * actual content data until we need it.
		 */
		if (p.isMimeType("text/plain")) {
			pr("This is plain text");
			pr("---------------------------");
			return (String) p.getContent();
			// bodyValue = bodyValue + (String) p.getContent();
		} else if (p.isMimeType("multipart/*")) {
			pr("This is a Multipart");
			pr("---------------------------");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			if (count > MailConstants.DEPTH_COUNT) {
				count = MailConstants.DEPTH_COUNT;
			}
			for (int i = 0; i < count; i++) {
				bodyValue = bodyValue + getMailContentInner(mp.getBodyPart(i));
			}
		}
		// } else if (p.isMimeType("message/rfc822")) {
		// pr("This is a Nested Message");
		// pr("---------------------------");
		// return result + getMailContentInner((Part) p.getContent());
		// } else {
		//
		// Object o = p.getContent();
		// if (o instanceof String) {
		// pr("This is a string");
		// pr("---------------------------");
		// bodyValue = bodyValue + ((String) o);
		// } else {
		// pr("This is an unknown type");
		// pr("---------------------------");
		// //bodyValue = bodyValue + o.toString();
		// }
		// }

		return bodyValue;
	}

	private void setMailBody(Message m, Mail mail) throws MessagingException, IOException {
		String body = getMailContentInner(m);
		mail.setBody(body);
		bodyValue = "";
	}

	private void setMailEnvelope(Message m, Mail mail) throws MessagingException {
		pr("This is the message envelope");
		pr("---------------------------");

		// MessageID

		mail.setRemoteId(m.getHeader("MESSAGE-ID")[0]);

		/*
		 * 
		 * 
		 * RepliedTO - TO - CC - BCC --- FROM
		 */
		List<MailAddress> addressList = new ArrayList<MailAddress>();

		Address[] a;
		// FROM
		if ((a = m.getFrom()) != null) {
			List<MailAddress> mailAddressList = new ArrayList<MailAddress>();
			for (int j = 0; j < a.length; j++) {
				InternetAddress internetAddress = (InternetAddress) a[j];
				MailAddress mailAddress = new MailAddress();
				mailAddress.setType("FROM");
				mailAddress.setMail(mail);
				mailAddress.setEmail(a[j].toString());
				mailAddressList.add(mailAddress);

				pr("FROM: " + a[j].toString());
			}
			addressList.addAll(mailAddressList);
		}

		// REPLY TO
		if ((a = m.getReplyTo()) != null) {
			List<MailAddress> recipientRepliedTo = new ArrayList<MailAddress>();
			for (int j = 0; j < a.length; j++) {
				MailAddress mailAddress = new MailAddress();
				mailAddress.setType("REPLY_TO");

				mailAddress.setEmail(((InternetAddress) a[j]).getAddress());
				recipientRepliedTo.add(mailAddress);
				mailAddress.setMail(mail);
				pr("REPLY TO: " + a[j].toString());
			}
			addressList.addAll(recipientRepliedTo);
			// mail.setRecipientRepliedTo(recipientRepliedTo);
		}

		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			List<MailAddress> recipientTo = new ArrayList<MailAddress>();
			for (int j = 0; j < a.length; j++) {
				MailAddress mailAddress = new MailAddress();
				mailAddress.setType("TO");
				mailAddress.setEmail(((InternetAddress) a[j]).getAddress());
				mailAddress.setMail(mail);
				recipientTo.add(mailAddress);
				pr("TO: " + a[j].toString());
				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++) {
						MailAddress mailAddressGroup = new MailAddress();
						mailAddressGroup.setEmail(a[j].toString());
						mailAddressGroup.setType("TO");
						mailAddressGroup.setMail(mail);
						recipientTo.add(mailAddressGroup);
						pr("  GROUP: " + aa[k].toString());
					}
				}
			}
			addressList.addAll(recipientTo);
			// mail.setRecipientTo();
		}
		// CC
		if ((a = m.getRecipients(Message.RecipientType.CC)) != null) {
			List<MailAddress> recipientCC = new ArrayList<MailAddress>();
			for (int j = 0; j < a.length; j++) {
				MailAddress mailAddress = new MailAddress();
				mailAddress.setType("CC");
				mailAddress.setMail(mail);
				mailAddress.setEmail(((InternetAddress) a[j]).getAddress());
				recipientCC.add(mailAddress);
				pr("CC: " + a[j].toString());
				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++) {
						MailAddress mailAddressGroup = new MailAddress();
						mailAddressGroup.setType("CC");
						mailAddressGroup.setEmail(a[j].toString());
						mailAddressGroup.setMail(mail);
						recipientCC.add(mailAddressGroup);
						pr("  GROUP: " + aa[k].toString());
					}
				}
			}
			addressList.addAll(recipientCC);
			// mail.setRecipientCC(recipientCC);
		}
		// BCC
		if ((a = m.getRecipients(Message.RecipientType.BCC)) != null) {
			List<MailAddress> recipientBCC = new ArrayList<MailAddress>();
			for (int j = 0; j < a.length; j++) {
				MailAddress mailAddress = new MailAddress();
				mailAddress.setType("BCC");
				mailAddress.setMail(mail);
				mailAddress.setEmail(((InternetAddress) a[j]).getAddress());
				recipientBCC.add(mailAddress);
				pr("BCC: " + a[j].toString());
				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++) {
						MailAddress mailAddressGroup = new MailAddress();
						mailAddressGroup.setEmail(a[j].toString());
						mailAddressGroup.setType("BCC");
						recipientBCC.add(mailAddressGroup);
						mailAddressGroup.setMail(mail);
						pr("  GROUP: " + aa[k].toString());
					}
				}
			}
			addressList.addAll(recipientBCC);
			// mail.setRecipientBCC(recipientBCC);
		}
		//
		mail.setAddressList(addressList);
		//
		/** ************* */

		// SUBJECT
		if (m.getSubject() != null) {
			int subjectLimit = 250;
			if (m.getSubject().length() < 250) {
				subjectLimit = m.getSubject().length();
			}
			mail.setSubject(String.valueOf(m.getSubject().subSequence(0, subjectLimit)));
			pr("SUBJECT: " + m.getSubject());
		}
		// DATE
		Date d = m.getSentDate();
		mail.setSentDate(d);
		pr("SendDate: " + (d != null ? d.toString() : "UNKNOWN"));

		// X-MAILER
		String[] hdrs = m.getHeader("X-Mailer");
		if (hdrs != null) {
			mail.setHeader(hdrs[0]);
			pr("X-Mailer: " + hdrs[0]);
		} else {
			pr("X-Mailer NOT available");
		}

	}

	private Mail getMessage(Folder folder, int index, boolean isStoredOriginal) throws MessagingException, IOException {
		Mail mail = new Mail();
		System.out.println("Index : " + index);
		Message msg = null;
		try {
			msg = folder.getMessage(index);
			setMailEnvelope(msg, mail);
			setMailBody(msg, mail);
			if (isStoredOriginal) {
				msg.setFlag(Flags.Flag.SEEN, true);
			} else {
				msg.setFlag(Flags.Flag.DELETED, true);
			}
		} catch (Exception e) {
			System.out.println("Get Message FAILED: JAK");
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return mail;
	}

	private int getMailsAndInsertToDB(boolean isStoredOriginal, int messeageCount) throws Exception {
		MailServerConnector mailServerConnector = new MailServerConnector();
		Folder folder = mailServerConnector.initilizeMailBox();
		int totalMessages = folder.getMessageCount();
		;
		if (messeageCount > 0) {
			totalMessages = messeageCount;
		}
		for (int i = 1; i < totalMessages + 1; i++) {
			Mail mail = getMessage(folder, i, isStoredOriginal);
			mailFacadeLocal.saveMail(mail);
			// Folder may force to and initialize in each 1000 message
			if (i % 1000 == 0) {
				mailServerConnector.finalizeMailBox();
				folder = mailServerConnector.initilizeMailBox();
				System.out.println("After 1000 message restart connection");
			}
		}
		return totalMessages;
	}

	public int superProcess(boolean isStoredOriginal, int messageCount) {
		System.out.println("Job Started!");
		int mailCount = 0;
		try {
			mailCount = getMailsAndInsertToDB(isStoredOriginal, messageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Job Ended!");
		return mailCount;
	}

}

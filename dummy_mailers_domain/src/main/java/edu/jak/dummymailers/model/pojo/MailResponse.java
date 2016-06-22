package edu.jak.dummymailers.model.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Mail")
@XmlAccessorType(XmlAccessType.FIELD)
public class MailResponse {

	@XmlElement(name = "Id")
	private String mailId;

	@XmlElementWrapper(name="FromList")
	@XmlElement(name = "From")
	private List<String> mailFrom;

	@XmlElementWrapper(name="ReplyList")
	@XmlElement(name = "ReplyTo")
	private List<String> replyMailTo;

	@XmlElementWrapper(name="ToList")
	@XmlElement(name = "To")
	private List<String> mailTo;

	@XmlElementWrapper(name="CCList")
	@XmlElement(name = "CC")
	private List<String> mailCC;

	@XmlElementWrapper(name="BCCList")
	@XmlElement(name = "BCC")
	private List<String> mailBCC;

	@XmlElement(name = "Subject")
	private String mailSubject;

	@XmlElement(name = "Body")
	private String mailBody;

	@XmlElement(name = "SentDate")
	private String mailSentDate;

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public List<String> getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(List<String> mailFrom) {
		this.mailFrom = mailFrom;
	}

	public List<String> getMailTo() {
		return mailTo;
	}

	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}

	public List<String> getReplyMailTo() {
		return replyMailTo;
	}

	public void setReplyMailTo(List<String> replyMailTo) {
		this.replyMailTo = replyMailTo;
	}

	public List<String> getMailCC() {
		return mailCC;
	}

	public void setMailCC(List<String> mailCC) {
		this.mailCC = mailCC;
	}

	public List<String> getMailBCC() {
		return mailBCC;
	}

	public void setMailBCC(List<String> mailBCC) {
		this.mailBCC = mailBCC;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getMailSentDate() {
		return mailSentDate;
	}

	public void setMailSentDate(String mailSentDate) {
		this.mailSentDate = mailSentDate;
	}

}

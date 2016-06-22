package edu.jak.dummymailers.model.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DownloadMail")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadMailResponse implements Serializable {

	private static final long serialVersionUID = 3097722520622956286L;

	@XmlElement(name = "MailCount")
	private int mailCount;

	public int getMailCount() {
		return mailCount;
	}

	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}


}

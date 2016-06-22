package edu.jak.dummymailers.model.eb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mail", catalog = "dummy_mailers")
/*@NamedQueries({ 
	@NamedQuery(name = "mail.getByLimits", query = "select m from mail m limit :start,:end")
})*/
public class Mail implements Serializable {

	private static final long serialVersionUID = -8474083960855236325L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mailId", nullable = false, unique = true)
	private Integer id;

	@Column(name = "mailRemoteId")
	private String remoteId;
	@Column(name = "mailBody")
	private String body;
	@Column(name = "mailFileName")
	private String fileName;
	@Column(name = "mailSubject")
	private String subject;
	@Column(name = "mailSentDate")
	private Date sentDate;
	@Column(name = "mailHeader")
	private String header;

	// @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy
	// = "mail")
	@OneToMany(mappedBy = "mail", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	private List<MailAddress> addressList;

	public List<MailAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<MailAddress> addressList) {
		this.addressList = addressList;
	}

	public Mail() {
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

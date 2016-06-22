package edu.jak.dummymailers.model.eb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mail_address", catalog = "dummy_mailers")
public class MailAddress implements Serializable {

	private static final long serialVersionUID = 5278619108114509128L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mailAddressId", unique = true, nullable = false)
	private Integer id;

	@Column(name = "email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "mailId", referencedColumnName = "mailId", nullable = false)
	private Mail mail;

	@Column(name = "type")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MailAddress() {
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

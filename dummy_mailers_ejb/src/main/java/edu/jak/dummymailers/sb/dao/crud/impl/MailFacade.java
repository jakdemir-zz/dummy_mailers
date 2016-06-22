package edu.jak.dummymailers.sb.dao.crud.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import edu.jak.dummymailers.model.eb.Mail;
import edu.jak.dummymailers.sb.dao.base.impl.BaseFacade;
import edu.jak.dummymailers.sb.dao.crud.MailFacadeLocal;

@Stateless
public class MailFacade extends BaseFacade<Mail, Integer> implements MailFacadeLocal {

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMail(Integer id) {
		super.delete(id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveMail(Mail mail) {
		super.save(mail);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Mail updateMail(Mail mail) {
		return super.update(mail);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Mail getMail(Integer id) {
		return super.select(id);
	}

}

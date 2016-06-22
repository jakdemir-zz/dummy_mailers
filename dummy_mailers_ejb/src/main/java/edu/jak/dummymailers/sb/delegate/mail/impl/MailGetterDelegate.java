package edu.jak.dummymailers.sb.delegate.mail.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.jak.dummymailers.converter.MailConverter;
import edu.jak.dummymailers.model.eb.Mail;
import edu.jak.dummymailers.model.pojo.MailResponse;
import edu.jak.dummymailers.sb.dao.crud.MailFacadeLocal;
import edu.jak.dummymailers.sb.delegate.mail.MailGetterDelegateLocal;

@Stateless
public class MailGetterDelegate implements MailGetterDelegateLocal {

	@EJB
	MailFacadeLocal mailFacade;

	public MailResponse getMailsByLimits(int start, int end) {
		Mail mail = mailFacade.getMail(12986);
		return MailConverter.convert(mail);
	}

	public MailResponse getMailById(int id) {
		Mail mail = mailFacade.getMail(id);
		return MailConverter.convert(mail);
	}

}

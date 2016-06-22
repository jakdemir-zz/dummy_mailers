package edu.jak.dummymailers.sb.dao.crud;

import javax.ejb.Local;

import edu.jak.dummymailers.model.eb.Mail;
import edu.jak.dummymailers.sb.dao.base.BaseFacadeLocal;

@Local
public interface MailFacadeLocal extends BaseFacadeLocal<Mail, Integer> {
	public void saveMail(Mail mail);

	public void deleteMail(Integer id);

	public Mail updateMail(Mail mail);

	public Mail getMail(Integer id);
}

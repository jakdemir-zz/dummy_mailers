package edu.jak.dummymailers.sb.delegate.mail;

import edu.jak.dummymailers.model.pojo.MailResponse;

public interface MailGetterDelegateLocal {
	public MailResponse getMailsByLimits(int start, int end);

	public MailResponse getMailById(int id);
}

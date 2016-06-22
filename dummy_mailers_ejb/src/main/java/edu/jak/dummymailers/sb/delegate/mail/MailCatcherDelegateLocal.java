package edu.jak.dummymailers.sb.delegate.mail;

import javax.ejb.Local;

@Local
public interface MailCatcherDelegateLocal {
	public int superProcess(boolean isStoredOriginal, int messageCount);
}

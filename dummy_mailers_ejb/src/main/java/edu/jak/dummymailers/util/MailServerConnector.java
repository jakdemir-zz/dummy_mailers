package edu.jak.dummymailers.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;


public class MailServerConnector {

	Store store = null;
	Folder folder = null;

	public Folder initilizeMailBox() throws MessagingException, IOException {
		Properties properties = System.getProperties();
		Session session = Session.getInstance(properties,null);
		session.setDebug(false);
		store = session.getStore(MailConstants.IMAP);
		store.connect(MailConstants.IMAP_MAIL_SERVER, MailConstants.IMAP_MAIL_PORT, MailConstants.IMAP_MAIL_USER_NAME, MailConstants.IMAP_MAIL_USER_PASSWORD);

		Folder folder = store.getFolder(MailConstants.IMAP_MAIL_BOX);
		folder.open(Folder.READ_WRITE);
		return folder;
	}

	public void finalizeMailBox() throws MessagingException {
		if (folder != null) {
			folder.close(true);
		}
		if (store != null) {
			store.close();
		}
	}

}

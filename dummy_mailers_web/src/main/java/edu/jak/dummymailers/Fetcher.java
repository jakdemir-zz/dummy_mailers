package edu.jak.dummymailers;

import java.io.Serializable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jak.dummymailers.sb.delegate.mail.MailCatcherDelegateLocal;
import edu.jak.dummymailers.util.JndiManager;

public class Fetcher extends HttpServlet implements Serializable {

	private static final long serialVersionUID = -1388142864818249024L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		MailCatcherDelegateLocal mailCatcherDelegate = (MailCatcherDelegateLocal) JndiManager.lookup(MailCatcherDelegateLocal.class);
		mailCatcherDelegate.superProcess(false,1);
	}
}

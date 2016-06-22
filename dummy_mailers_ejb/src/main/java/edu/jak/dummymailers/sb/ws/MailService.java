package edu.jak.dummymailers.sb.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.wsf.spi.annotation.WebContext;

import edu.jak.dummymailers.model.pojo.DownloadMailResponse;
import edu.jak.dummymailers.model.pojo.MailResponse;
import edu.jak.dummymailers.sb.delegate.mail.MailCatcherDelegateLocal;
import edu.jak.dummymailers.sb.delegate.mail.MailGetterDelegateLocal;
import edu.jak.dummymailers.sb.delegate.mail.impl.MailGetterDelegate;

@Stateless
@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebService(name = "MailService", targetNamespace = "http://mail.edu.jak")
@WebContext(transportGuarantee = "NONE", contextRoot = "/dummymailers/ws")
public class MailService {
	@EJB
	private MailCatcherDelegateLocal catcherDelegate;
	@EJB
	private MailGetterDelegateLocal getterDelegate;

	@WebMethod
	public DownloadMailResponse downloadMails(@WebParam(name = "isStoredOriginal")	boolean isStoredOriginal,
											  @WebParam(name = "messageCount") int messageCount) {
		int mailCount = catcherDelegate.superProcess(isStoredOriginal,messageCount);
		DownloadMailResponse downloadMailResponse = new DownloadMailResponse();
		downloadMailResponse.setMailCount(mailCount);
		return downloadMailResponse;
	}

	@WebMethod
	public MailResponse getMails(@WebParam(name = "start")
	int start, @WebParam(name = "end")
	int end) {
		MailResponse mailsResponse = getterDelegate.getMailsByLimits(start, end);
		return mailsResponse;
	}
	
	@WebMethod
	public MailResponse getMail(@WebParam(name = "id")	int id) {
		MailResponse mailsResponse = getterDelegate.getMailById(id);
		return mailsResponse;
	}

}

package es.mdef.clientmanager.aspects;

import es.mdef.clientmanager.mail.MailService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: jonsurbe
 * Date: 27/11/14
 * Time: 16:28
 */
@Component
@Aspect
public class AuditAspect {


    @Autowired
    private MailService mailService;

    @Before("execution(* es.mdef.clientmanager.s3.*.*(..))")
    public void auditUploadAdvice(){
        System.out.print("Advice");

    }


    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}

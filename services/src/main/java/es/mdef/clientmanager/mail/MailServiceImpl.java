package es.mdef.clientmanager.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 18/11/14
 * Time: 21:38
 */
@Service("mailService")
public class MailServiceImpl implements MailService {
    @Autowired(required = false)
    private MailSender mailSender;

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    /**
     * This method will send compose and send the message
     * */
    @Override
    public void sendMail(String from, String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}

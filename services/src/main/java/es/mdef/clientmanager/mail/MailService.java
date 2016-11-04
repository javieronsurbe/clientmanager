package es.mdef.clientmanager.mail;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 18/11/14
 * Time: 23:02
 */
public interface MailService {
    void sendMail(String from, String to, String subject, String body);
}

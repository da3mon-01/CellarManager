package hu.pte.schafferg.cellarManager.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import hu.pte.schafferg.cellarManager.model.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Service class for sending e-mail
 * @author Da3mon
 *
 */
public class EmailService {
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	private static Logger logger = Logger.getLogger(EmailService.class);
	
	/**
	 * Sends out the new password to user from an admin reset.
	 * @param user
	 * @param newpass
	 * @throws MessagingException
	 */
	public void sendResetPasswordMail(User user, String newpass) throws MessagingException{
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setFrom("noreply@cellarManagerapp.com");
		helper.setTo(user.getPerson().getEmail());
		helper.setSubject("Cellar Manager password change");
		helper.setText("<html><body>" +
				"<h2>Your Cellar Manager Password has been reset!</h2>" +
				"<p>"+user.getPerson().getFirstName()+", your password has been reset. Your new one is: </p>" +
				"<p><b>"+newpass+"</b></p>"+
				"<p>Please change it after login.</p></body></html>", true);
		try {
			mailSender.send(message);
			logger.info("Reset password email was sent to: "+user.getPerson().getEmail());
		} catch (MailException e) {
			logger.info(e.getMessage()+" error while trying to send password reset email to: "+user.getPerson().getEmail());
			throw e;
		}	
		
		
	}
	
	/**
	 * Sends out a reset password the newly created user.
	 * @param user
	 * @param newpass
	 * @throws MessagingException
	 */
	public void sendCreateUserMail(User user, String newpass) throws MessagingException{
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setFrom("noreply@cellarManagerapp.com");
		helper.setTo(user.getPerson().getEmail());
		helper.setSubject("Welcome to Cellar Manager!");
		helper.setText("<html><body>" +
				"<h2>Welcome to Cellar Manager!</h2>" +
				"<p>"+user.getPerson().getFirstName()+", you have been added as a new user to Cellar Manager. Your username is "+
				user.getUsername()+" your password is: </p>" +
				"<p><b>"+newpass+"</b></p>"+
				"<p>Please change it after login.</p></body></html>", true);
		try {
			mailSender.send(message);
			logger.info("User created email was sent to: "+user.getPerson().getEmail());
		} catch (MailException e) {
			logger.info(e.getMessage()+" error while trying to user created email to: "+user.getPerson().getEmail());
			throw e;
		}	
		
		
	}

}

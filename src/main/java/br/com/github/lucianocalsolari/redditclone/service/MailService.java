package br.com.github.lucianocalsolari.redditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.github.lucianocalsolari.redditclone.exceptions.SpringRedditException;
import br.com.github.lucianocalsolari.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	private final JavaMailSender mailSender;
	private MailContentBuilder mailContentBuilder;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) throws SpringRedditException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Email de ativação enviado!!");
			
		}catch (MailException e ) {
			throw new SpringRedditException("Exception ocorreu quando tentou enviar um email para " + notificationEmail.getRecipient());
		}
		
	}
}

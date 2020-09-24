package br.com.github.lucianocalsolari.redditclone.service;

import org.thymeleaf.TemplateEngine;

public class MailContentBuilder {

	private final TemplateEngine templateEngine;
	
	String build(String message) {
		Context context = new Context();
		context.setVariable("message" message);
		return templateEngine.process("mailTemplate", context);
	}
	
	
}

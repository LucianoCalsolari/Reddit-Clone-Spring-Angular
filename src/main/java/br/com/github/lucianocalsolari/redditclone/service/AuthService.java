package br.com.github.lucianocalsolari.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.github.lucianocalsolari.redditclone.dto.RegisterRequest;
import br.com.github.lucianocalsolari.redditclone.model.User;
import br.com.github.lucianocalsolari.redditclone.model.VerificationToken;
import br.com.github.lucianocalsolari.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository; 
	
	@Transactional
	public void signup(RegisterRequest registerRequest ) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
	}
	
	private void generateVerificationToken(User user){
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
	
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
}

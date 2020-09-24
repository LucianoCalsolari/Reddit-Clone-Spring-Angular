package br.com.github.lucianocalsolari.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.github.lucianocalsolari.redditclone.dto.AuthenticationResponse;
import br.com.github.lucianocalsolari.redditclone.dto.LoginRequest;
import br.com.github.lucianocalsolari.redditclone.dto.RegisterRequest;
import br.com.github.lucianocalsolari.redditclone.exceptions.SpringRedditException;
import br.com.github.lucianocalsolari.redditclone.model.NotificationEmail;
import br.com.github.lucianocalsolari.redditclone.model.User;
import br.com.github.lucianocalsolari.redditclone.model.VerificationToken;
import br.com.github.lucianocalsolari.redditclone.repository.UserRepository;
import br.com.github.lucianocalsolari.redditclone.repository.VerificationTokenRepository;
import br.com.github.lucianocalsolari.redditclone.security.JwtProvider;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository; 
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
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
		mailService.sendMail(new NotificationEmail("Por favor ative seu email.",
				user.getEmail(),"Obrigado por se inscrever neste clone do reddit ,por favor click "
						+ "na url para ativar sua conta "
						+ "http://localhost:8080/api/auth/accountVerification/"+ token));
	}
	
	private void generateVerificationToken(User user){
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
	
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Token invalido"));
		fetchUserEnable(verificationToken.get());
	}
	
	@Transactional
	private void fetchUserEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("Usuario " + username + " não encontrado."));
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	public void login (LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest, 
				loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authentication)
	
		return new AuthenticationResponse(token, loginRequest.getUsername());
	}
}

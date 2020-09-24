package br.com.github.lucianocalsolari.redditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.github.lucianocalsolari.redditclone.dto.LoginRequest;
import br.com.github.lucianocalsolari.redditclone.dto.RegisterRequest;
import br.com.github.lucianocalsolari.redditclone.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public void signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<>("Usuario registrado com sucesso",
				HttpStatus.OK);
	}
	
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		authService.verifyAccount(token);
		return new ResponseEntity<>("Conta ativa com sucesso",HttpStatus.OK);
	}

	
	@PostMapping("/login")
	public void login(@RequestBody LoginRequest loginRequest)
		authService.login(loginRequest);
}

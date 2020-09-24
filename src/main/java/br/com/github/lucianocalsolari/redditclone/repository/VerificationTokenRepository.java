package br.com.github.lucianocalsolari.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.mail.imap.protocol.ID;

import br.com.github.lucianocalsolari.redditclone.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);
}

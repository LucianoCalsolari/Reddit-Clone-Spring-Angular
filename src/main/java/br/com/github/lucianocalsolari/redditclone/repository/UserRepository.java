package br.com.github.lucianocalsolari.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.github.lucianocalsolari.redditclone.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}

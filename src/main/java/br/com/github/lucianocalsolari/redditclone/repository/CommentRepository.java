package br.com.github.lucianocalsolari.redditclone.repository;

import org.springframework.stereotype.Repository;

import br.com.github.lucianocalsolari.redditclone.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CommentRepository extends JpaRepository<Comment ,Long> {

}

package br.com.github.lucianocalsolari.redditclone.repository;

import org.springframework.stereotype.Repository;

import br.com.github.lucianocalsolari.redditclone.model.Subreddit;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

}

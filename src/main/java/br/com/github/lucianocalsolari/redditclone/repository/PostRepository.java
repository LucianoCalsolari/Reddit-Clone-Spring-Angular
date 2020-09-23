package br.com.github.lucianocalsolari.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.github.lucianocalsolari.redditclone.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post ,Long>{

}

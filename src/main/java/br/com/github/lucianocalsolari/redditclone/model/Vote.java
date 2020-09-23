package br.com.github.lucianocalsolari.redditclone.model;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long votedId;
	private VoteType voteType;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "postId")
	private Post post;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
}

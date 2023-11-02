package com.javaMadeEasy.Entertainment.Entity;

import com.javaMadeEasy.Entertainment.authentication.user.User;
import jakarta.persistence.*;


@Entity
@Table(name="likes")
public class Like {

	@SequenceGenerator(
			name="likes_sequence",
			sequenceName = "likes_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "likes_sequence")
        private int id;

        @ManyToOne
        private Post post;

		@ManyToOne
		private User user;

        protected Like() {

        }

        public Like(Post post, User user) {
                this.post=post;
                this.user = user;
        }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}


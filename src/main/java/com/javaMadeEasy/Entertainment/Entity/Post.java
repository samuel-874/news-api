package com.javaMadeEasy.Entertainment.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

	@SequenceGenerator(
			name="post_sequence",
			sequenceName = "post_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "post_sequence")
        private Integer id;

        private String title;

        private String body;
	@JsonIgnore
	@ManyToOne
		private User user;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime dateCreated= LocalDateTime.now();


		public Post(int id, String title, String body, User user){
			this.id=id;
			this.title=title;
			this.body=body;
			this.user =user;
		}

	public Post( String myTitle, String myPostBody, User user) {
		this.title=title;
		this.body=body;
		this.user =user;
	}


	public int getId() {
			return id;
		}
		
		
		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public LocalDateTime getDateCreated() {
			return dateCreated;
		}

		public void setDateCreated(LocalDateTime dateCreated) {
			this.dateCreated = dateCreated;
		}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

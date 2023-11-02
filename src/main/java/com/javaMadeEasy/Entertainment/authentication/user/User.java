package com.javaMadeEasy.Entertainment.authentication.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {



	@SequenceGenerator(
			name="user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_sequence")
	private int id;
	private String firstName;

	private String lastName;

	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.name()));
		return authorities;
	}




	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}



}
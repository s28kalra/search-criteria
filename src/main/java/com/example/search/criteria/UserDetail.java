package com.example.search.criteria;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sagar.kalra
 */

@Getter
@Setter
@Entity
@Table(name = "user_detail")
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

	@Id
	private int id;
	private String firstName;
	private String lastName;

}

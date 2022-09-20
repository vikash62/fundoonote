package com.bridgelabz.fundoonote.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bridgelabz.fundoonote.note.model.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "User_Id")
	private Integer id;
	@Column(name = "User_Name")
	private String name;
	@Column(name = "User_Email")
	private String email;
	@Column(name = "User_Number")
	private String phoneNumber;
	@Column(name = "User_Password")
	private String password;
	@Column(name = "User_Verified")
	private Boolean isVerified = false;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Note> note = new ArrayList<Note>();
	
}
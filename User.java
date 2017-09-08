package com.csueb.bean;

import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = "CustId"),
        @UniqueConstraint(columnNames = "Email") })
public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "CustId", unique = true, nullable = false)
	    private int custID;
		@Column(name = "Firstname", unique = false, nullable = false, length = 100)
	    private String firstname;
		@Column(name = "Lastname", unique = false, nullable = false, length = 100)
	    private String lastname;
	    @Column(name = "Email", unique = true, nullable = false, length = 100)
	    private String email;
	    @Column(name = "Password", unique = false, nullable = false, length = 100)
	    private String password;
	   
	    public User() {
	    	
	    }
	 
	    public User(String firstname, String lastname, String email, String password) {
	        this.firstname = firstname;
	        this.lastname = lastname;
	        this.email = email;
	        this.password = password;
	        //this.custID = 0;
	    }

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getCustID() {
			return custID;
		}

		public void setCustID(int custID) {
			this.custID = custID;
		}
}

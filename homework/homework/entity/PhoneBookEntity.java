package homework.homework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "PhoneBookEntity")
@Table(name = "PhoneBook")
public class PhoneBookEntity {

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "SEQ", allocationSize = 1)
	@Column(name = "ID")
	Integer id;

	String name;
	
	@Column(name = "PHONE")
	String phone;

	public PhoneBookEntity() {
	}

	public PhoneBookEntity(Integer id, String name, String phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	public PhoneBookEntity(String name, String phone) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}

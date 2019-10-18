package homework.homework.dto;

public class PhoneBookDto {
	Integer id;
	String name;
	String phone;
	
	public PhoneBookDto() {
		
	}

	public PhoneBookDto(String name, String phone) {
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

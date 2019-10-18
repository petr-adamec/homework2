package homework.homework.service;

import java.util.List;

import homework.homework.dto.PhoneBookDto;

public interface PhoneBookService {
	
	public void addEntry(PhoneBookDto dto);
	public void removeEntry(PhoneBookDto dto);
	public List<PhoneBookDto> findByName(String name);
	public List<PhoneBookDto> list();
	public PhoneBookDto findByPhone(String phone);
}

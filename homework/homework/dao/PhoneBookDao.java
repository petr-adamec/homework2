package homework.homework.dao;

import java.util.List;

import homework.homework.entity.PhoneBookEntity;

public interface PhoneBookDao {
	void addEntry(PhoneBookEntity entry) throws Exception;

	void removeEntry(Integer id) throws Exception;

	List<PhoneBookEntity> findByName(String name)  throws Exception;

	List<PhoneBookEntity> list()  throws Exception;

	PhoneBookEntity findByNameAndPhone(String name, String phone) throws Exception;

	PhoneBookEntity findByPhone(String phone) throws Exception;
}

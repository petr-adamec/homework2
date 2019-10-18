package homework.homework.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import homework.homework.dao.PhoneBookDao;
import homework.homework.daoImpl.PhoneBookDaoImpl;
import homework.homework.dto.PhoneBookDto;
import homework.homework.entity.PhoneBookEntity;
import homework.homework.service.PhoneBookService;

public class PhoneBookServiceImpl implements PhoneBookService {

	protected static final Logger log = Logger.getLogger("PhoneBookServiceImpl");

	PhoneBookDao phoneBookDao = new PhoneBookDaoImpl();

	public void addEntry(PhoneBookDto dto) {
		PhoneBookEntity entity = new PhoneBookEntity();
		map(dto, entity);
		try {
			phoneBookDao.addEntry(entity);
			dto.setId(entity.getId());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error during adding an entry.", e);
		}
	}

	public void removeEntry(PhoneBookDto dto) {

		try {
			PhoneBookEntity removeMe = phoneBookDao.findByNameAndPhone(dto.getName(), dto.getPhone());
			phoneBookDao.removeEntry(removeMe.getId());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error during removal", e);
		}

	}

	public List<PhoneBookDto> findByName(String name) {
		List<PhoneBookEntity> list = null;
		try {
			list = phoneBookDao.findByName(name);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error during findByName", e);
			e.printStackTrace();
		}
		return remap(list);
	}

	public List<PhoneBookDto> list() {
		List<PhoneBookDto> ret = null;
		try {
			ret = remap(phoneBookDao.list());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error listin all ", e);
		}
		return ret;
	}

	public PhoneBookDto findByPhone(String phone) {
		PhoneBookDto ret = null;
		try {
			ret = remap(phoneBookDao.findByPhone(phone));
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error listin all ", e);
		}
		return ret;
	}

	private List<PhoneBookDto> remap(List<PhoneBookEntity> list) {
		List<PhoneBookDto> ret = new ArrayList<PhoneBookDto>();
		for (PhoneBookEntity e : list) {
			PhoneBookDto dto = new PhoneBookDto();
			map(e, dto);
			ret.add(dto);
		}
		return ret;
	}

	private PhoneBookDto remap(PhoneBookEntity findByPhone) {
		if(findByPhone == null) return null;
		PhoneBookDto dto = new PhoneBookDto();
		map(findByPhone, dto);
		return dto;
	}

	private void map(PhoneBookEntity src, PhoneBookDto dest) {
		if (dest != null && src != null) {
			dest.setId(src.getId());
			dest.setName(src.getName());
			dest.setPhone(src.getPhone());
		}
	}

	private void map(PhoneBookDto src, PhoneBookEntity dest) {
		if (dest != null && src != null) {
			dest.setId(src.getId());
			dest.setName(src.getName());
			dest.setPhone(src.getPhone());
		}
	}
}

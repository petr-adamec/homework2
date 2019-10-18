package homework.homework.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.query.Query;

import homework.homework.dao.PhoneBookDao;
import homework.homework.entity.PhoneBookEntity;

public class HibernatePhoneBbookDaoImpl implements PhoneBookDao {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("a");

	HibernateEntityManager hibernateEntityManager = (HibernateEntityManager) factory.createEntityManager();

	@Override
	public void addEntry(PhoneBookEntity entry) throws Exception {
		hibernateEntityManager.getSession().beginTransaction();
		Serializable id = hibernateEntityManager.getSession().save(entry);
		System.out.println(id);
		hibernateEntityManager.getSession().getTransaction().commit();
		//hibernateEntityManager.persist(entry);
		

	}

	@Override
	public void removeEntry(Integer id) throws Exception {
		PhoneBookEntity inst = hibernateEntityManager.find(PhoneBookEntity.class, id);
		hibernateEntityManager.remove(inst);

	}

	@Override
	public List<PhoneBookEntity> findByName(String name) throws Exception {
		Query<PhoneBookEntity> q = hibernateEntityManager
				.createQuery("from PhoneBookEntity e where e.name like :name ", PhoneBookEntity.class);
		q.setParameter("name", "%"+name+"%");
		return (List<PhoneBookEntity>) q.getResultList();
	}

	@Override
	public List<PhoneBookEntity> list() throws Exception {
		CriteriaQuery<PhoneBookEntity> crit = hibernateEntityManager.getCriteriaBuilder()
				.createQuery(PhoneBookEntity.class);
		TypedQuery<PhoneBookEntity> q = hibernateEntityManager.createQuery(crit);
		List<PhoneBookEntity> ret = q.getResultList();

		return ret;
	}

	@Override
	public PhoneBookEntity findByNameAndPhone(String name, String phone) throws Exception {
		
		Query<PhoneBookEntity> q = hibernateEntityManager.createQuery(" from PhoneBookEntity e where e.phone = :phone and e.name = :name ", PhoneBookEntity.class);
		PhoneBookEntity ret = q.getSingleResult();
		return ret;

	}

	@Override
	public PhoneBookEntity findByPhone(String phone) throws Exception {
		Session session = hibernateEntityManager.getSession();
		Criteria crit = session.createCriteria(PhoneBookEntity.class);
		crit.add(Restrictions.eq("phone", phone));
		return (PhoneBookEntity) crit.list().get(0);
	}

	public static void main(String[] args) throws Exception {
		HibernatePhoneBbookDaoImpl dao = new HibernatePhoneBbookDaoImpl();
		List<PhoneBookEntity> l = dao.findByName("p");
		for (PhoneBookEntity e : l) {
			System.out.println(e.getId() + " " + e.getName() + " " + e.getPhone());
		}
		dao.addEntry(new PhoneBookEntity("Remy","723108697"));
		
		PhoneBookEntity e = dao.findByPhone("723108697");
		assertEquals("723108697", e.getPhone());
	}

}

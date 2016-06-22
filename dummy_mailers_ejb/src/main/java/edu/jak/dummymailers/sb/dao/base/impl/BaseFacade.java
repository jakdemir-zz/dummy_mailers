package edu.jak.dummymailers.sb.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.jak.dummymailers.sb.dao.base.BaseFacadeLocal;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class BaseFacade<T, ID extends Serializable> implements BaseFacadeLocal<T, ID> {

	private Class<T> persistentClass;

	@PersistenceContext(unitName = "dummy_mailers_PROD_PU")
	protected EntityManager masterEntityManager;

	@SuppressWarnings("unchecked")
	public BaseFacade() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void save(final T t) {
		masterEntityManager.persist(t);
	}

	public void delete(ID id) {
		T t = masterEntityManager.getReference(persistentClass, id);
		masterEntityManager.remove(t);
	}

	public T update(final T t) {
		return masterEntityManager.merge(t);
	}

	public T select(ID id) {
		return masterEntityManager.getReference(persistentClass, id);
	}

}

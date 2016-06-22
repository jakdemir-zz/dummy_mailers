package edu.jak.dummymailers.sb.dao.base;

import javax.ejb.Local;


@Local
public interface BaseFacadeLocal<T, ID> {
	public void save(final T t);
	public void delete(ID id);
	public T update(T t);
}

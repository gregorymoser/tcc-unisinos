package model.dao;

import java.util.List;

import model.entities.Subunidade;

public interface SubunidadeDao {

	void insert(Subunidade obj);
	void update(Subunidade obj);
	void deleteById(Integer id);
	Subunidade findById(Integer id);
	List<Subunidade> findAll();
}

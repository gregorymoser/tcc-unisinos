package model.dao;

import java.util.List;

import model.entities.Viatura;
import model.entities.Subunidade;

public interface ViaturaDao {

	void insert(Viatura obj);
	void update(Viatura obj);
	void deleteById(Integer id);
	Viatura findById(Integer id);
	List<Viatura> findAll();
	List<Viatura> findBySubunidade(Subunidade subunidade);
}


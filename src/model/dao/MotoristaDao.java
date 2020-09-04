package model.dao;

import java.util.List;

import model.entities.Motorista;
import model.entities.Subunidade;

public interface MotoristaDao {

	void insert(Motorista obj);
	void update(Motorista obj);
	void deleteById(Integer id);
	Motorista findById(Integer id);
	List<Motorista> findAll();
	List<Motorista> findBySubunidade(Subunidade subunidade);
}
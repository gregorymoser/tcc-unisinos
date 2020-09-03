package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SubunidadeDao;
import model.entities.Subunidade;

public class SubunidadeService {
	
	private SubunidadeDao dao = DaoFactory.createSubunidadeDao();
	
	public List<Subunidade> findAll(){
		return dao.findAll();
	}
}

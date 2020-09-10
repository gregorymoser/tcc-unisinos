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
	
	public void saveOrUpdate(Subunidade obj) {
		//caso id seja nulo, significa que estou inserindo uma nova Subunidade, e não atualizando
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		//caso contrário, estou atualizando
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Subunidade obj) {
		dao.deleteById(obj.getId());
	}
}
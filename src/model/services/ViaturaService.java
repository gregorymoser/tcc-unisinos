package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ViaturaDao;
import model.entities.Viatura;

public class ViaturaService {
	
	private ViaturaDao dao = DaoFactory.createViaturaDao();
	
	public List<Viatura> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Viatura obj) {
		//caso id seja nulo, significa que estou inserindo uma nova Viatura, e não atualizando
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		//caso contrário, estou atualizando
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Viatura obj) {
		dao.deleteById(obj.getId());
	}
}
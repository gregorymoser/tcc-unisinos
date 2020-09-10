package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.MotoristaDao;
import model.entities.Motorista;

public class MotoristaService {
	
	private MotoristaDao dao = DaoFactory.createMotoristaDao();
	
	public List<Motorista> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Motorista obj) {
		//caso id seja nulo, significa que estou inserindo uma nova Motorista, e n�o atualizando
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		//caso contr�rio, estou atualizando
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Motorista obj) {
		dao.deleteById(obj.getId());
	}
}
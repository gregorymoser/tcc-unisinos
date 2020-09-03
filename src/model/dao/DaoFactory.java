package model.dao;

import db.DB;
import model.dao.impl.MotoristaDaoJDBC;
import model.dao.impl.SubunidadeDaoJDBC;
import model.dao.impl.ViaturaDaoJDBC;

public class DaoFactory {

	public static MotoristaDao createMotoristaDao() {
		return new MotoristaDaoJDBC(DB.getConnection());
	}
	
	public static ViaturaDao createViaturaDao() {
		return new ViaturaDaoJDBC(DB.getConnection());
	}
	
	public static SubunidadeDao createSubunidadeDao() {
		return new SubunidadeDaoJDBC(DB.getConnection());
	}
}

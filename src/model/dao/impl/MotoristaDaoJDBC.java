package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.MotoristaDao;
import model.entities.Motorista;
import model.entities.Subunidade;

public class MotoristaDaoJDBC implements MotoristaDao {

	private Connection conn;
	
	public MotoristaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Motorista obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO motorista "
					+ "(IdMilitar, Nome, CategoriaCNH, DataAniversario, ValidadeCNH, NumeroCNH, Situacao, SubunidadeId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getIdMilitar());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getCategoriaCNH());
			st.setDate(4, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDate(5, new java.sql.Date(obj.getValidadeCNH().getTime()));
			st.setInt(6, obj.getNumeroCNH());
			st.setString(7, obj.getSituacao());
			st.setInt(8, obj.getSubunidade().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Motorista obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE motorista "
					+ "SET IdMilitar = ?, Nome = ?, CategoriaCNH = ?, DataAniversario = ?, ValidadeCNH = ?, NumeroCNH = ?, Situacao = ?, SubunidadeId = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getIdMilitar());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getCategoriaCNH());
			st.setDate(4, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDate(5, new java.sql.Date(obj.getValidadeCNH().getTime()));
			st.setInt(6, obj.getNumeroCNH());
			st.setString(7, obj.getSituacao());
			st.setInt(8, obj.getSubunidade().getId());
			st.setInt(9, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM motorista WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Motorista findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT motorista.*,subunidade.Nome as SubNome "
					+ "FROM motorista INNER JOIN subunidade "
					+ "ON motorista.SubunidadeId = subunidade.Id "
					+ "WHERE motorista.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Subunidade sub = instantiateSubunidade(rs);
				Motorista obj = instantiateMotorista(rs, sub);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Motorista instantiateMotorista(ResultSet rs, Subunidade sub) throws SQLException {
		Motorista obj = new Motorista();
		obj.setId(rs.getInt("Id"));
		obj.setIdMilitar(rs.getInt("IdMilitar"));
		obj.setNome(rs.getString("Nome"));
		obj.setCategoriaCNH(rs.getString("CategoriaCNH"));
		obj.setDataAniversario(rs.getDate("DataAniversario"));
		obj.setValidadeCNH(rs.getDate("ValidadeCNH"));
		obj.setNumeroCNH(rs.getInt("NumeroCNH"));
		obj.setSituacao(rs.getString("Situacao"));
		obj.setSubunidade(sub);
		return obj;
	}

	private Subunidade instantiateSubunidade(ResultSet rs) throws SQLException {
		Subunidade sub = new Subunidade();
		sub.setId(rs.getInt("SubunidadeId"));
		sub.setNome(rs.getString("SubNome"));
		return sub;
	}

	@Override
	public List<Motorista> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT motorista.*,subunidade.Nome as SubNome "
					+ "FROM motorista INNER JOIN subunidade "
					+ "ON motorista.SubunidadeId = subunidade.Id "
					+ "ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Motorista> list = new ArrayList<>();
			Map<Integer, Subunidade> map = new HashMap<>();
			
			while (rs.next()) {
				
				Subunidade sub = map.get(rs.getInt("SubunidadeId"));
				
				if (sub == null) {
					sub = instantiateSubunidade(rs);
					map.put(rs.getInt("SubunidadeId"), sub);
				}
				
				Motorista obj = instantiateMotorista(rs, sub);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Motorista> findBySubunidade(Subunidade subunidade) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT motorista.*,subunidade.Nome as SubNome "
					+ "FROM motorista INNER JOIN subunidade "
					+ "ON motorista.SubunidadeId = subunidade.Id "
					+ "WHERE SubunidadeId = ? "
					+ "ORDER BY Nome");
			
			st.setInt(1, subunidade.getId());
			
			rs = st.executeQuery();
			
			List<Motorista> list = new ArrayList<>();
			Map<Integer, Subunidade> map = new HashMap<>();
			
			while (rs.next()) {
				
				Subunidade sub = map.get(rs.getInt("SubunidadeId"));
				
				if (sub == null) {
					sub = instantiateSubunidade(rs);
					map.put(rs.getInt("SubunidadeId"), sub);
				}
				
				Motorista obj = instantiateMotorista(rs, sub);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}

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
import model.dao.ViaturaDao;
import model.entities.Subunidade;
import model.entities.Viatura;

public class ViaturaDaoJDBC implements ViaturaDao {

	private Connection conn;
	
	public ViaturaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Viatura obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO viatura "
					+ "(EB, Nome, Odometro, Categoria, Ano, DataInsercao, Tipo, Situacao, SubunidadeId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getEb());
			st.setString(2, obj.getNome());
			st.setInt(3, obj.getOdometro());
			st.setString(4, obj.getCategoria());
			st.setDate(5, new java.sql.Date(obj.getAno().getTime()));
			st.setDate(6, new java.sql.Date(obj.getDataInsercao().getTime()));
			st.setString(7, obj.getTipo());
			st.setString(8, obj.getSituacao());
			st.setInt(9, obj.getSubunidade().getId());
			
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
	public void update(Viatura obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE viatura "
					+ "SET EB = ?, Nome = ?, Odometro = ?, Categoria = ?, Ano = ?, DataInsercao = ?, Tipo = ?, Situacao = ?, SubunidadeId = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getEb());
			st.setString(2, obj.getNome());
			st.setInt(3, obj.getOdometro());
			st.setString(4, obj.getCategoria());
			st.setDate(5, new java.sql.Date(obj.getAno().getTime()));
			st.setDate(6, new java.sql.Date(obj.getDataInsercao().getTime()));
			st.setString(7, obj.getTipo());
			st.setString(8, obj.getSituacao());
			st.setInt(9, obj.getSubunidade().getId());
			st.setInt(10, obj.getId());
			
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
			st = conn.prepareStatement("DELETE FROM viatura WHERE Id = ?");
			
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
	public Viatura findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT viatura.*,subunidade.Nome as SubNome "
					+ "FROM viatura INNER JOIN subunidade "
					+ "ON viatura.SubunidadeId = subunidade.Id "
					+ "WHERE viatura.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Subunidade sub = instantiateSubunidade(rs);
				Viatura obj = instantiateViatura(rs, sub);
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

	private Viatura instantiateViatura(ResultSet rs, Subunidade sub) throws SQLException {
		Viatura obj = new Viatura();
		obj.setId(rs.getInt("Id"));
		obj.setEb(rs.getInt("EB"));
		obj.setNome(rs.getString("Nome"));
		obj.setOdometro(rs.getInt("Odometro"));
		obj.setCategoria(rs.getString("Categoria"));
		obj.setAno(rs.getDate("Ano"));
		obj.setDataInsercao(rs.getDate("DataInsercao"));
		obj.setTipo(rs.getString("Tipo"));
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
	public List<Viatura> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT viatura.*,subunidade.Nome as SubNome "
					+ "FROM viatura INNER JOIN subunidade "
					+ "ON viatura.SubunidadeId = subunidade.Id "
					+ "ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Viatura> list = new ArrayList<>();
			Map<Integer, Subunidade> map = new HashMap<>();
			
			while (rs.next()) {
				
				Subunidade sub = map.get(rs.getInt("SubunidadeId"));
				
				if (sub == null) {
					sub = instantiateSubunidade(rs);
					map.put(rs.getInt("SubunidadeId"), sub);
				}
				
				Viatura obj = instantiateViatura(rs, sub);
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
	public List<Viatura> findBySubunidade(Subunidade subunidade) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT viatura.*,subunidade.Nome as SubNome "
					+ "FROM viatura INNER JOIN subunidade "
					+ "ON viatura.SubunidadeId = subunidade.Id "
					+ "WHERE SubunidadeId = ? "
					+ "ORDER BY Nome");
			
			st.setInt(1, subunidade.getId());
			
			rs = st.executeQuery();
			
			List<Viatura> list = new ArrayList<>();
			Map<Integer, Subunidade> map = new HashMap<>();
			
			while (rs.next()) {
				
				Subunidade sub = map.get(rs.getInt("SubunidadeId"));
				
				if (sub == null) {
					sub = instantiateSubunidade(rs);
					map.put(rs.getInt("SubunidadeId"), sub);
				}
				
				Viatura obj = instantiateViatura(rs, sub);
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

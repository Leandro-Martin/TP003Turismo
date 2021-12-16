package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import model.Registro;
import persistence.ItinerarioDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class ItinerarioDAOImpl implements ItinerarioDAO {

	@Override
	public List<Registro> findAll() {
		List<Registro> reg = new LinkedList<Registro>();
		try {
			String sql = "SELECT * FROM itinerario WHERE promocion = 0";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				reg.add(new Registro(resultados.getInt("usuario_id"), resultados.getInt("id"), "atraccion"));
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		try {
			String sql = "SELECT * FROM itinerario WHERE promocion = 1";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				reg.add(new Registro(resultados.getInt("usuario_id"), resultados.getInt("id"), "promocion"));
			}

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return reg;
	}

	@Override
	public Registro find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registro> getByUserId(int id) {
		List<Registro> reg = new LinkedList<Registro>();
		try {
			String sql = "SELECT * FROM itinerario WHERE (usuario_id = ?) & (promocion = 0)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				reg.add(new Registro(resultados.getInt("usuario_id"), resultados.getInt("id"), "atraccion"));
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		try {
			String sql = "SELECT * FROM itinerario WHERE (usuario_id = ?) & (promocion = 1)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				reg.add(new Registro(resultados.getInt("usuario_id"), resultados.getInt("id"), "promocion"));
			}

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return reg;
	}

	@Override
	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM itinerario";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			int total = resultados.getInt("TOTAL");

			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int insert(Registro reg) {
		try {
			String sql = "INSERT INTO itinerario (usuario_id, atraccion_id, promocion) VALUES (?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			int i = 1;
			statement.setInt(i++, reg.getUsuarioId());
			statement.setInt(i++, reg.getId());
			statement.setInt(i++, reg.esPromocion());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(Registro t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Registro t) {
		// TODO Auto-generated method stub
		return 0;
	}
}

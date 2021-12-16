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
		try {
			String sql = "SELECT * FROM itinerario";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Registro> reg = new LinkedList<Registro>();
			while (resultados.next()) {
				reg.add(new Registro(resultados.getInt("usuario_id"), resultados.getInt("atraccion_id"),
						resultados.getInt("promocion_id")));
			}

			return reg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MissingDataException(e);
		}
	}

	@Override
	public Registro find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Registro> findByUsuarioId(Integer usuarioId) {
		try {
			String sql = "SELECT * FROM itinerario WHERE usuario_id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, usuarioId);
			ResultSet resultados = statement.executeQuery();

			List<Registro> reg = new LinkedList<Registro>();
			while (resultados.next()) {
				reg.add(new Registro(resultados.getInt("usuario_id"), resultados.getInt("atraccion_id"),
						resultados.getInt("promocion_id")));
			}

			return reg;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
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
			String sql = "INSERT INTO itinerario (usuario_id, atraccion_id, promocion_id) VALUES (?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			int i = 1;
			statement.setInt(i++, reg.getUsuarioId());
			statement.setInt(i++, reg.getAtraccionId());
			statement.setInt(i++, reg.getPromocionId());
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

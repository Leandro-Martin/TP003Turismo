package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import model.Attraction;
import model.Promocion;
import persistence.PromocionDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class PromocionDAOImpl implements PromocionDAO {

	@Override
	public Promocion find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promocion> findAll() {
		List<Promocion> promociones = new LinkedList<Promocion>();
		try {
			String sql = "SELECT * FROM promocion WHERE removed = 0";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				promociones.add(new Promocion(resultados));
			}

			return promociones;
		} catch (Exception e) {
			e.printStackTrace();
			// throw new MissingDataException(e);
		}
		return promociones;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Promocion t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Promocion t) {

		for (Attraction atraccion : t.atraccionesPagas) {
			try {
				String sql = "UPDATE attractions SET capacity = ? WHERE id = ?";
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				// Resta 1 de capacidad porque no fue actualizado en otro lugar.
				statement.setInt(1, atraccion.getCapacity() - 1);
				statement.setInt(2, atraccion.getId());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new MissingDataException(e);
			}
		}
		for (Attraction atraccion : t.atraccionesGratis) {
			try {
				String sql = "UPDATE attractions SET capacity = ? WHERE id = ?";
				Connection conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				// Resta 1 de capacidad porque no fue actualizado en otro lugar.
				statement.setInt(1, atraccion.getCapacity() - 1);
				statement.setInt(2, atraccion.getId());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new MissingDataException(e);
			}
		}
		return 0;
	}

	@Override
	public int delete(Promocion promocion) {
		try {
			String sql = "UPDATE promocion SET removed = 1 WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, promocion.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}

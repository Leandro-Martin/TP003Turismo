package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class Promocion {
	int id;
	String nombre;
	double descuento = 100;
	double precio;
	double duracion;

	List<Attraction> atraccionesPagas = new LinkedList<Attraction>();
	List<Attraction> atraccionesGratis = new LinkedList<Attraction>();

	public Promocion(ResultSet promo) {
		try {
			this.id = promo.getInt("id");
			this.nombre = promo.getString("nombre");
			this.descuento -= promo.getDouble("descuento");
			this.precio = promo.getInt("precio");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.getAtracciones();
		this.setPrecio();
	}

	public void getAtracciones() {
		try {
			String sql = "SELECT attractions.*, promo_atraccion.promocion_id, promo_atraccion.gratis \r\n"
					+ "FROM promo_atraccion \r\n"
					+ "JOIN attractions ON promo_atraccion.atraccion_id = attractions.id\r\n"
					+ "WHERE promocion_id = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, this.id);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				if (resultados.getInt("gratis") == 0) {
					this.atraccionesPagas.add(new Attraction(resultados.getString("name"), resultados.getInt("cost"),
							resultados.getDouble("duration")));
				} else {
					this.atraccionesGratis
							.add(new Attraction(resultados.getString("name"), resultados.getDouble("duration")));
				}
			}

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public void setPrecio() {
		// Si el precio de la promoción está definido entonces no necesita crear un
		// precio a partir del precio de las atracciones..
		if (this.precio == 0) {
			double costoTotal = 0;
			for (Attraction attraction : atraccionesPagas) {
				costoTotal += attraction.getCost();
			}
			costoTotal *= descuento / 100;
			this.precio = costoTotal;
		}
	}

	public double getPrecio() {
		return this.precio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getId() {
		return id;
	}

	public void setDuracion() {
		// Si el precio de la promoción está definido entonces no necesita crear un
		// precio a partir del precio de las atracciones..
		double duracionTotal = 0;
		for (Attraction attraction : atraccionesPagas) {
			duracionTotal += attraction.getDuration();
		}

		for (Attraction attraction : atraccionesGratis) {
			duracionTotal += attraction.getDuration();
		}
		this.duracion = duracionTotal;

	}

	public double getDuracion() {
		return this.duracion;
	}

	public int getCupos() {
		return 0;
	}
}

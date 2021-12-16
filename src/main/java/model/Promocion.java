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
	int precio;
	double duracion;
	int capacity;

	public List<Attraction> atraccionesPagas = new LinkedList<Attraction>();
	public List<Attraction> atraccionesGratis = new LinkedList<Attraction>();

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
		this.setCupos();
		this.setDuracion();
	}
	
	public Promocion(int id) {
		this.id = id;
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
							resultados.getDouble("duration"), resultados.getInt("capacity")));
				} else {
					this.atraccionesGratis.add(new Attraction(resultados.getString("name"),
							resultados.getDouble("duration"), resultados.getInt("capacity")));
				}
			}

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public void setPrecio() {
		// Si el precio de la promoción está definido entonces no necesita crear un
		// precio a partir del precio de las atracciones.
		if (this.precio == 0) {
			double costoTotal = 0;
			for (Attraction attraction : atraccionesPagas) {
				costoTotal += attraction.getCost();
			}
			costoTotal *= descuento / 100;
			this.precio = (int) Math.ceil(costoTotal);
		}
	}

	public int getPrecio() {
		return this.precio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getId() {
		return id;
	}

	public void setDuracion() {
		// La duración de todas las atracciones a la vez.
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
		return this.capacity;
	}

	public boolean canHost(int i) {
		return this.capacity >= i;
	}

	public void setCupos() {
		// Esto se basa en que no podes comprar todas las atracciones de una promoción
		// si no hay cupos suficientes para cada una, así que la cantidad de cupos de la
		// promoción es la menor cantidad de cupos que haya entre las atracciones.
		int cupos = Integer.MAX_VALUE;
		for (Attraction attraction : this.atraccionesPagas) {
			cupos = Math.min(cupos, attraction.getCapacity());
		}

		for (Attraction attraction : this.atraccionesGratis) {
			cupos = Math.min(cupos, attraction.getCapacity());
		}
		this.capacity = cupos;
	}

	public void host(int i) {
		this.capacity -= 1;
	}

}

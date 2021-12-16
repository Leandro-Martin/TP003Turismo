package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class Registro {

	int usuarioId;
	int id;
	String tipo;
	String usuarioNombre;
	String nombre;

	public Registro(int usuarioId, int id, String tipo) {
		// TODO Auto-generated constructor stub
		this.usuarioId = usuarioId;
		this.id = id;
		this.tipo = tipo;
		setUsuarioNombre();
		if (tipo == "promocion") {
			setNombrePromocion();
		} else {
			setNombreAtraccion();
		}
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public String getUsuarioNombre() {
		return this.usuarioNombre;
	}

	public void setUsuarioNombre() {
		try {
			String sql = "SELECT username FROM users WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, this.getUsuarioId());
			ResultSet usuario = statement.executeQuery();
			if (usuario.next()) {
				this.usuarioNombre = usuario.getString("username");
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombreAtraccion() {
		try {
			String sql = "SELECT name FROM attractions WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, this.getId());
			ResultSet atraccion = statement.executeQuery();
			if (atraccion.next()) {
				this.nombre = atraccion.getString("name");
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public void setNombrePromocion() {
		try {
			String sql = "SELECT name FROM promociones WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, this.getId());
			ResultSet atraccion = statement.executeQuery();
			if (atraccion.next()) {
				this.nombre = atraccion.getString("name");
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public String getPromocionNombre() {
		return this.nombre;
	}

	public int esPromocion() {
		return this.tipo == "promocion" ? 1 : 0;
	}
}

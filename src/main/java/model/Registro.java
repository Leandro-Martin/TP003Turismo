package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class Registro {

	int usuarioId;
	int atraccionId;
	int promocionId;
	String usuarioNombre;
	String atraccionNombre;
	String promocionNombre;

	public Registro(int usuarioId, int atraccionId, int promocionId) {
		// TODO Auto-generated constructor stub
		this.usuarioId = usuarioId;
		this.atraccionId = atraccionId;
		this.promocionId = promocionId;
		setUsuarioNombre();
		setAtraccionNombre();
		setPromocionNombre();
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

	public int getAtraccionId() {
		return atraccionId;
	}

	public String getAtraccionNombre() {
		return this.atraccionNombre;
	}

	public void setAtraccionNombre() {
		try {
			String sql = "SELECT name FROM attractions WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, this.getAtraccionId());
			ResultSet atraccion = statement.executeQuery();
			if (atraccion.next()) {
				this.atraccionNombre = atraccion.getString("name");
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int getPromocionId() {
		return promocionId;
	}

	public String getPromocionNombre() {
		return null;
	}

	public void setPromocionNombre() {

	}

}

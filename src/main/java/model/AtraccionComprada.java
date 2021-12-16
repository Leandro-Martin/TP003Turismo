package model;

public class AtraccionComprada {
	private int idUsuario;
	private int idAtraccion;
	private int idPromocion;

	public AtraccionComprada(int idUsuario, int idAtraccion, int idPromocion) {
		this.idUsuario = idUsuario;
		this.idAtraccion = idAtraccion;
		this.idPromocion = idPromocion;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public int getIdPromocion() {
		return this.idPromocion;
	}

	public int getIdAtraccion() {
		return this.idAtraccion;
	}
}

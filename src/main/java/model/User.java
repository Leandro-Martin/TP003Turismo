package model;

import java.util.HashMap;
import java.util.Map;

import persistence.ItinerarioDAO;
import persistence.commons.DAOFactory;
import utils.Crypt;

public class User {

	private Integer id;
	private String username, password;
	private Boolean admin;
	private Integer coins;
	private Double time;
	private HashMap<String, String> errors;

	ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();

	public User(Integer id, String username, String password, Integer coins, Double time, Boolean admin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.coins = coins;
		this.time = time;
		this.admin = admin;
	}

	public void addToItinerary(Attraction attraction) {
		this.coins -= attraction.getCost();
		this.time -= attraction.getDuration();
		try {
			itinerarioDAO.insert(new Registro(this.getId(), attraction.getId(), "atraccion"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToItinerary(Promocion promocion) {
		this.coins -= promocion.getPrecio();
		this.time -= promocion.getDuracion();
		try {
			itinerarioDAO.insert(new Registro(this.getId(), promocion.getId(), "promocion"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean canAfford(Attraction attraction) {
		return attraction.getCost() <= this.coins;
	}

	public boolean canAfford(Promocion promocion) {
		return promocion.getPrecio() <= this.coins;
	}

	public boolean canAttend(Attraction attraction) {
		return attraction.getDuration() <= this.time;
	}

	public boolean canAttend(Promocion promocion) {
		return promocion.getDuracion() <= this.time;
	}

	public boolean checkPassword(String password) {
		// this.password en realidad es el hash del password
		return Crypt.match(password, this.password);
	}

	public Boolean getAdmin() {
		return admin;
	}

	public Integer getCoins() {
		return coins;
	}

	public Integer getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public Double getTime() {
		return time;
	}

	public String getUsername() {
		return username;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public boolean isNull() {
		return false;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = Crypt.hash(password);
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isValid() {
		validate();
		return errors.isEmpty();
	}

	public void validate() {
		errors = new HashMap<String, String>();

		if (coins < 0) {
			errors.put("coins", "No debe ser negativo");
		}
		if (time < 0) {
			errors.put("time", "No debe ser negativo");
		}
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", admin=" + admin + "]";
	}

}

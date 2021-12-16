package services;

import java.util.List;

import model.Promocion;
import persistence.commons.DAOFactory;

public class PromocionService {
	public List<Promocion> list() {
		return DAOFactory.getPromocionDAO().findAll();
	}

	public Promocion create() {
		return null;
	}

	public Promocion update(Integer id, String name, Integer cost, Double duration, Integer capacity) {
		return null;
	}

	public void delete(Integer id) {

	}

	public Promocion find(Integer id) {
		return DAOFactory.getPromocionDAO().find(id);
	}
}

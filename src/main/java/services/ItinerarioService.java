package services;

import java.util.List;

import model.Registro;
import persistence.commons.DAOFactory;

public class ItinerarioService {

	public List<Registro> list() {
		return DAOFactory.getItinerarioDAO().findAll();
	}

}

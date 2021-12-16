package persistence;

import java.util.List;

import model.Registro;
import persistence.commons.GenericDAO;

public interface ItinerarioDAO extends GenericDAO<Registro> {

	List<Registro> getByUserId(int id);

}

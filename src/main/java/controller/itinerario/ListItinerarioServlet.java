package controller.itinerario;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Registro;
import services.ItinerarioService;

/**
 * Servlet implementation class ListItinerarioServlet
 */
@WebServlet({ "/itinerario/index.do", "/users/itinerario.do" })
public class ListItinerarioServlet extends HttpServlet {
	private static final long serialVersionUID = 983234019293814018L;
	private ItinerarioService itinerarioService;

	public void init() throws ServletException {
		super.init();
		this.itinerarioService = new ItinerarioService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer usuarioId = Integer.parseInt(request.getParameter("id"));

		List<Registro> listItinerario = itinerarioService.findByUserId(usuarioId);
		request.setAttribute("listItinerario", listItinerario);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/itinerario/index.jsp");
		dispatcher.forward(request, response);
	}
}

package controller.promociones;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import persistence.commons.DAOFactory;
import services.BuyPromocionService;

@WebServlet("/promociones/buy.do")
public class ComprarPromocionServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6864228135918715712L;
	private BuyPromocionService buyPromocionService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.buyPromocionService = new BuyPromocionService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer promocionId = Integer.parseInt(req.getParameter("id"));
		User user = (User) req.getSession().getAttribute("user");
		Map<String, String> errors = buyPromocionService.buy(user.getId(), promocionId);

		User user2 = DAOFactory.getUserDAO().find(user.getId());
		req.getSession().setAttribute("user", user2);

		if (errors.isEmpty()) {
			req.setAttribute("success", "¡Gracias por comprar!");
		} else {
			req.setAttribute("errors", errors);
			req.setAttribute("flash", "No ha podido realizarse la compra");
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/promociones/index.do");
		dispatcher.forward(req, resp);
	}
}

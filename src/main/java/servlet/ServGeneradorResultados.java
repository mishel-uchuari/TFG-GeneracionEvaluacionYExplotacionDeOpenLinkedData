package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import triplestore.GraphDB;


/**
 * Servlet implementation class ServGeneradorResultados
 */
public class ServGeneradorResultados extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServGeneradorResultados() {
		super();
	}

	/**
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String textAreaValue = request.getParameter("query");
		System.out.println("llegamos" + textAreaValue);
		// AQUI TIENE QUE ESTAR EL ERROR
		GraphDB gdb = new GraphDB();
		String result = "";
		if (textAreaValue.toLowerCase().contains("construct")) {
			result = gdb.executeGraphQuery(textAreaValue);
		} else if (textAreaValue.toLowerCase().contains("select")) {
			result = gdb.executeQuery(textAreaValue);
			if (result.equals("")) {
				result = "No se han encontrado datos sobre ese recurso";
			}
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

}

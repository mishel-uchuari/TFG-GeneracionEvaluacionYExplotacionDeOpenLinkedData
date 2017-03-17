package proyectod3;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openrdf.repository.RepositoryException;


public class ServIdent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String contrasena;
	private String servidor;

    public ServIdent() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		servidor= request.getParameter("servidor");
		usuario = request.getParameter("nombre");
		contrasena = request.getParameter("contrasena");
		try {
			Stardog st= new Stardog();
			st.executeQuery("Select * where{?s ?o ?p}");
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
//		GraphDB gdb= new GraphDB();
//		gdb.executeQuery("Select * where {?s ?o ?p}");
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}

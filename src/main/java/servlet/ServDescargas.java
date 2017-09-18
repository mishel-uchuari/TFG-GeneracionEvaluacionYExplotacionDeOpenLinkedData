package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.UnsupportedRDFormatException;

import triplestore.GraphDB;

/**
 * Servlet implementation class ServDescargas
 */
public class ServDescargas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServDescargas() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		String selectedItem=request.getParameter("formato");
		String query=request.getParameter("query");
		System.out.println(selectedItem);
		System.out.println(query);
		GraphDB  gdb = new GraphDB();
		File file = null;
		try {
			file = gdb.getRDFXMLFile(query);
		} catch (RepositoryException | MalformedQueryException | RDFHandlerException | UnsupportedRDFormatException
				| QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		//File downloadFile = new File("");
		FileInputStream is = new FileInputStream(file);
		int longitud = is.available();
	    byte[] datos = new byte[longitud];
	    is.read(datos);
	    is.close();
	    
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition","attachment;filename="+"C:/"+selectedItem+".rdf");    
	    ServletOutputStream ouputStream = response.getOutputStream();
	    ouputStream.write(datos);
	    ouputStream.flush();
	    ouputStream.close();
	    System.out.println("TERMINO");
	}
}
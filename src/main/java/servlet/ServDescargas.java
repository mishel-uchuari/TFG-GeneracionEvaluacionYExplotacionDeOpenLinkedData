package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//		File downloadFile = new File("");
//		FileInputStream is = new FileInputStream(downloadFile);
//
//		ServletContext context = getServletContext();
//		String mime = context.getMimeType("");
//
//		if (mime == null) {
//			mime = "application/octet-stream";
//
//		}
//		response.setContentType(mime);
//		response.setContentLength((int) downloadFile.length());
//
//		String headerKey = "Content-Disposition";
//		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
//		response.setHeader(headerKey, headerValue);
//		OutputStream os = response.getOutputStream();
//		byte[] buffer = new byte[4096];
//		int bytesRead = -1;
//		while ((bytesRead = is.read(buffer)) != -1) {
//			os.write(buffer, 0, bytesRead);
//		}
//		is.close();
//		os.close();

	}
}

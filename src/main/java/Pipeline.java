import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.Rio;

import clojure.lang.LazySeq;
import clojure.lang.RT;

public class Pipeline extends Thread {

	private String nameSpace;
	private String rutaCsvInicial;
	private String rutaRdfFinal;
	private String aEjecutar;

	public Pipeline(String pNameSpace, String pMetodoEjecutar, String pRutaCsvInicial, String pRutaRdfFinal)
			throws IOException, RDFHandlerException {
		nameSpace = pNameSpace;
		rutaCsvInicial = pRutaCsvInicial;
		rutaRdfFinal = pRutaRdfFinal;
		aEjecutar = pMetodoEjecutar;
	}

	public void run() {
		try {
			RT.loadResourceScript("pipelines/" + nameSpace + ".clj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LazySeq lazy = (LazySeq) RT.var("pipelines." + nameSpace, aEjecutar).invoke(rutaCsvInicial);
		Iterator ite = lazy.iterator();
		Model model = new LinkedHashModel();
		while (ite.hasNext()) {
			model.add((Statement) ite.next());
		}
		/**
		 * Código para sacar el archivo RDF/XML-TURTLE
		 */
		File file = new File(rutaRdfFinal);
		FileOutputStream fileTurtle;
		try {
			fileTurtle = new FileOutputStream(file);
			Rio.write(model, fileTurtle, RDFFormat.RDFXML);
		} catch (FileNotFoundException | RDFHandlerException e) {
			e.printStackTrace();
		}

	}
}

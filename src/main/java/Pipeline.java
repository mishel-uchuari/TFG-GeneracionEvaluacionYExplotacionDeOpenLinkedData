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
import triplestore.utils.GraphDB;

public class Pipeline {

	private String nameSpace;
	private String rutaCsvInicial;
	private String aEjecutar;
	private String rutaRdfFinal;

	public Pipeline(String pNameSpace, String pMetodoEjecutar, String pRutaCsvInicial, String pRutaRdfFinal)
			throws IOException, RDFHandlerException {
		nameSpace = pNameSpace;
		rutaCsvInicial = pRutaCsvInicial;
		aEjecutar = pMetodoEjecutar;
		rutaRdfFinal = pRutaRdfFinal;
	}

	public void run() throws IOException {
		try {
			// Cargamos el pipeline de clojure
			RT.loadResourceScript("pipelines/" + nameSpace + ".clj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Ejecutamos el pipeline
		LazySeq lazy = (LazySeq) RT.var("pipelines." + nameSpace, aEjecutar).invoke(rutaCsvInicial);
		Iterator ite = lazy.iterator();
		// Lo guardamos en un model
		Model model = new LinkedHashModel();
		while (ite.hasNext()) {
			model.add((Statement) ite.next());
		}
		// Para la realización de las pruebas de calidad del RDF creamos un
		// fichero
		File file = new File(rutaRdfFinal);
		FileOutputStream fileTurtle;
		try {
			fileTurtle = new FileOutputStream(file);
			Rio.write(model, fileTurtle, RDFFormat.TURTLE);
		} catch (FileNotFoundException | RDFHandlerException e) {
			e.printStackTrace();
		}
		// Lo subimos a la triplestore
//		GraphDB gdb = new GraphDB();
//		gdb.loadRDF4JModel(model);

	}
}

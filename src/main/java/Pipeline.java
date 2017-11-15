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
import triplestore.GraphDB;

public class Pipeline  {

	private String nameSpace;
	private String rutaCsvInicial;
	private String nombreGrafo;
	private String aEjecutar;

	public Pipeline(String pNameSpace, String pMetodoEjecutar, String pRutaCsvInicial, String pNombreGrafo)
			throws IOException, RDFHandlerException {
		nameSpace = pNameSpace;
		rutaCsvInicial = pRutaCsvInicial;
		nombreGrafo = pNombreGrafo;
		aEjecutar = pMetodoEjecutar;
	}

	public void run() throws IOException {
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
		GraphDB gdb = new GraphDB();
		gdb.loadRDF4JModel(model);

	}
}

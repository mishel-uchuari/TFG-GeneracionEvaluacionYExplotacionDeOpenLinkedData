package manager;

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

public class PipelineManager {
	/**
	 * En primer lugar se le pasa el nombre del pipeline que se quiere ejecutar
	 * En segundo la ruta donde esta ubicado el CSV del que se tomaran los datos
	 * En tercero la ruta donde se almacenará el turtle con los resultados
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		try {
			// Cargamos el pipeline de clojure
			RT.loadResourceScript("pipelines/" + args[0] + ".clj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Ejecutamos el pipeline
		LazySeq lazy = (LazySeq) RT.var("pipelines." + args[0], "create-graph").invoke(args[1]);
		Iterator ite = lazy.iterator();
		// Lo guardamos en un model
		Model model = new LinkedHashModel();
		while (ite.hasNext()) {
			model.add((Statement) ite.next());
		}
		// Para la realización de las pruebas de calidad del RDF creamos un
		// fichero donde se almacenara el RDF generado en formato turtle
		File file = new File(args[2]);
		FileOutputStream fileTurtle;
		try {
			fileTurtle = new FileOutputStream(file);
			Rio.write(model, fileTurtle, RDFFormat.TURTLE);
		} catch (FileNotFoundException | RDFHandlerException e) {
			e.printStackTrace();
		}

	}
}

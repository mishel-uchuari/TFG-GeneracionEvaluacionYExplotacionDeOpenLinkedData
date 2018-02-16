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
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		if (args.length == 3 && !args[0].contains(".clj") && args[1].contains(".csv") && args[2].contains(".ttl")) {
			// Cargamos el pipeline de clojure
			RT.loadResourceScript("pipelines/" + args[0] + ".clj");
			// Ejecutamos el pipeline
			LazySeq lazy = (LazySeq) RT.var("pipelines." + args[0], "create-graph").invoke(args[1]);
			Iterator ite = lazy.iterator();
			// Lo guardamos en un model
			Model model = new LinkedHashModel();
			while (ite.hasNext()) {
				Statement statement = (Statement) ite.next();
				model.add(statement);
			}
			// Para la realización de las pruebas de calidad del RDF creamos un
			// fichero donde se almacenara el RDF generado en formato turtle
			File file = new File(args[2]);
			FileOutputStream fileTurtle;
			fileTurtle = new FileOutputStream(file);
			Rio.write(model, fileTurtle, RDFFormat.TURTLE);

		} else if (args.length != 3) {
			throw new Exception(
					"Se deben introducir tres parámetros, el nombre del pipeline a ejecutar, la ruta del CSV a convertir, y la ruta donde se almacenará el turtle con los resultados.");
		} else if (args[0].contains(".clj") || !args[1].contains(".csv") || !args[2].contains(".ttl")) {
			throw new Exception(
					"Se deben introducir tres parámetros, el nombre del pipeline a ejecutar sin la extensión '.clj', la ruta del CSV a convertir con extension '.csv', y la ruta donde se almacenará el turtle con los resultados, con extensión 'ttl'.");
		}
	}
}

package manager;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileUtils;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.spin.util.JenaUtil;

import jline.internal.InputStreamReader;
import triplestore.GraphDB;

public class ShaclManager {
	/**
	 * El primer parametro corresponderá a los datos El segundo parametro será
	 * la configuracion de SHACL sobre esos datos El tercero la ruta al fichero
	 * que contiene la query a hacerse sobre el report El cuarto a la ruta donde
	 * se almacenara el report
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 4 && args[0].contains(".ttl") && args[1].contains(".ttl") && args[2].contains(".sparql")
				&& args[3].contains(".ttl")) {
			String targetFile = args[0];
			String SHACLFile = args[1];
			String reportCheckingQueryFile = args[2];
			String reportFile = args[3];

			// Load the data to validate
			Model dataModel = JenaUtil.createMemoryModel();
			FileInputStream fiData = new FileInputStream(targetFile);
			dataModel.read(fiData, "", FileUtils.langTurtle);
			// Load the quality tests
			Model configurationModel = JenaUtil.createMemoryModel();
			fiData = new FileInputStream(SHACLFile);
			configurationModel.read(fiData, "", FileUtils.langTurtle);

			// Create a validation report (execute the tests)
			Resource report = ValidationUtil.validateModel(dataModel, configurationModel, true);

			// Write report to disk

			FileWriter out = new FileWriter(reportFile);
			report.getModel().write(out, "TURTLE");

			// Query report to check if data is conformant
			String reportCheckingQuery = FileUtils.readWholeFileAsUTF8(reportCheckingQueryFile);
			Query query = QueryFactory.create(reportCheckingQuery);
			QueryExecution qexec = QueryExecutionFactory.create(query, report.getModel());
			boolean result = qexec.execAsk();
			qexec.close();

			// Data is not conformant
			if (result) {
				throw new Exception(
						"Violacion SHACL, RDF no válido. Puedes ver los resultados en el report en : " + reportFile);
			}
			// Conformant data
			else {
				FileInputStream d = new FileInputStream(targetFile);
			    Path path = Paths.get(targetFile);
			    GraphDB gdb = new GraphDB();
				gdb.uploadFile(path.toFile());
				System.out.println("RDF válido. Puedes ver los resultados en el report en : " + reportFile);
			}
		}
		else if (args.length != 4) {
			throw new Exception("Se deben introducir 4 parámetros. \nEl primer parametro corresponderá a los datos.\n"
					+ "El segundo parametro será la configuracion de SHACL sobre esos datos\n"
					+ "El tercero la ruta al fichero que contiene la query a hacerse sobre el report\n"
					+ "El cuarto a la ruta donde se almacenara el report\n");
		} else if (!args[0].contains(".ttl") || !args[1].contains(".ttl") || !args[2].contains(".sparql")
				|| !args[3].contains(".ttl")) {
			throw new Exception(
					"Formato incorrecto de los parámetros. El primer parametro corresponderá a los datos y será de tipo .ttl\n"
							+ "El segundo parametro será la configuracion de SHACL sobre esos datos y será de tipo .ttl\n"
							+ "El tercero la ruta al fichero que contiene la query a hacerse sobre el report y sera de tipo .sparql\n"
							+ "El cuarto a la ruta donde se almacenara el report y sera de tipo .ttl\n");
		}
	}
}
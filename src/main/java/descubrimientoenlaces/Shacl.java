package descubrimientoenlaces;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileUtils;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.spin.util.JenaUtil;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;

public class Shacl {
	/**
	 * El primer parametro corresponderá a los datos
	 * El segundo parametro será la configuracion de SHACL sobre esos datos 
	 * El tercero la ruta al fichero que contiene la query a hacerse sobre el report
	 * El cuarto a la ruta donde se almacenara el report
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

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
			throw new Exception("Violacion SHACL, RDF no válido. Puedes ver los resultados en el report en : " + reportFile);
		}
		// Conformant data
		else {
			System.out.println("RDF válido. Puedes ver los resultados en el report en : " + reportFile);
		}

	}
}
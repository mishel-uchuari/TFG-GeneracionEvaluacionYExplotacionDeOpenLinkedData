
package triplestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.UnsupportedRDFormatException;

import com.healthmarketscience.jackcess.query.Query;

import tratamiento.Json;

public class GraphDB {
	private RepositoryConnection repository;

	public GraphDB() {
		try {
			HTTPRepository conn = new HTTPRepository("http://192.168.0.17:7200/repositories/ModeloGeneracionDatosEnlazados");
			//conn.setUsernameAndPassword("mishel", "mishel");
			repository = conn.getConnection();
			repository.begin();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	public void loadRDF4JModel(final Model pModel) {
		try {
			Iterable<? extends Statement> it = new Iterable<Statement>() {

				public Iterator<Statement> iterator() {
					return pModel.iterator();
				}
			};
			repository.add(it);
			repository.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String executeQuery(String pQuery) {
		System.out.println(pQuery);
		String resultados = "";
		TupleQuery query;
		try {
			query = repository.prepareTupleQuery(QueryLanguage.SPARQL, pQuery);
			TupleQueryResult statements = query.evaluate();
			while (statements.hasNext()) {
				String statement = statements.next().toString();
				statement = statement.replace("[", "");
				statement = statement.replace("]", ";");
				statement = statement.replace(";", ",");

				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#string>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#date>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#long>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#double>", "");
				statement = statement.replace(", ", ",");
				statement = statement.replace(" ", "");
				System.out.println(statement);
				resultados = resultados + statement;
			}
			Json json = new Json();
			resultados = json.parsearJSON(resultados);
			statements.close();
		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e1) {
			resultados = e1.getMessage();
			System.out.println(resultados);
		}
		return resultados;
	}

	public String executeGraphQuery(String pQuery) {
		GraphQuery query;
		String resultados = "";

		try {
			
			query = repository.prepareGraphQuery(QueryLanguage.SPARQL, pQuery);
			GraphQueryResult stataments = query.evaluate();
			while (stataments.hasNext()) {
				String statement = stataments.next().toString();
				statement = statement.replace("[null]", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#string>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#date>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#long>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#double>", "");
				statement = statement.replace(", ", ",");
				statement = statement.replace(" ", "");
				resultados = resultados + statement;
			}
			stataments.close();
			Json json = new Json();
			resultados = json.parsearJSON(resultados);
		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e1) {
			resultados = e1.getMessage();
			System.out.println(resultados);
		}
		return resultados;
	}

	public File getRDFXMLFile(String pQuery) throws IOException, RepositoryException, MalformedQueryException,
			RDFHandlerException, UnsupportedRDFormatException, QueryEvaluationException {
		GraphQuery query;
		File file = File.createTempFile("temporaryFile", null);
		FileOutputStream fo = new FileOutputStream(file);
		query = repository.prepareGraphQuery(QueryLanguage.SPARQL, pQuery);
		GraphQueryResult stataments = query.evaluate();
		System.out.println("entra");
		QueryResultIO.write(stataments, RDFFormat.RDFXML, fo);
		stataments.close();
		return file;
	}
}
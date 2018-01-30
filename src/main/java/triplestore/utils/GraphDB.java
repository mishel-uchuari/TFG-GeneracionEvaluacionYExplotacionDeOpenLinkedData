package triplestore.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

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

	public GraphDB() throws IOException {
		try {
			HTTPRepository conn = new HTTPRepository(
					"http://174.140.171.251:7200/repositories/ModeloParaLaGeneracionDeDatosEnlazados");
			conn.setUsernameAndPassword("admin", "ctxakurra");
			repository = conn.getConnection();
			repository.begin();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sube un model a la triple store
	 * 
	 * @param pModel
	 */
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
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#int>", "");
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
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#int>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#dateTime>", "");
				statement = statement.replace(") ", ")");
				statement = statement.replace(", ", ",");
				// statement = statement.replace(" ", "");
				if (!resultados.contains(statement)) {
					resultados = resultados + statement;
				}
			}
			stataments.close();
			if (!resultados.equals("")) {
				Json json = new Json();
				resultados = json.parsearJSON(resultados);
			}

		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e1) {
			resultados = e1.getMessage();
			System.out.println(resultados);
		}
		return resultados;
	}

	public String getResourceJson(String pRecurso) {
		String query = "construct{?nomRecurso ?p ?o} where{graph<http://opendata.eurohelp.es/dataset/recursos-humanos>{?nomRecurso ?p ?o}FILTER(?nomRecurso = <recursoPeticion>)}";
		String result = "";
		query = query.replace("recursoPeticion", pRecurso);
		try {
			GraphQuery tupleQuery = repository.prepareGraphQuery(QueryLanguage.SPARQL, query);
			GraphQueryResult results = tupleQuery.evaluate();
			while (results.hasNext()) {
				Statement statement = results.next();
				String e = statement.toString().replace(", ", ",");
				if (!result.contains(e)) {
					result = result + e;
				}
			}
			results.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == "") {
			result = "json vacio";
		} else {
			Json json = new Json();
			result = json.parsearJSON(result);
		}
		return result;
	}
}

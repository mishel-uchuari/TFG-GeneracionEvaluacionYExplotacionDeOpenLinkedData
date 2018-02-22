package triplestore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.lf5.util.Resource;
import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import utils.ResultAdapter;


public class GraphDB {
	private RepositoryConnection repository;

	public GraphDB() throws IOException {
		try {
			HTTPRepository conn = new HTTPRepository(
					"http://172.16.0.113:7200/repositories/pruebasMishell");
			//conn.setUsernameAndPassword("admin", "ctxakurra");
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
		String resultados = "";
		TupleQuery query;
		try {
			query = repository.prepareTupleQuery(QueryLanguage.SPARQL, pQuery);
			TupleQueryResult statements = query.evaluate();
			while (statements.hasNext()) {
				String statement = statements.next().toString();
				statement = statement.replace("[", "(");
				statement = statement.replace(";", ",");
				statement = statement.replace("]", ")");
				statement = statement.replace("s=", "");
				statement = statement.replace("p=", "");
				statement = statement.replace("o=", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#string>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#date>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#long>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#double>", "");
				statement = statement.replace("^^<http://www.w3.org/2001/XMLSchema#int>", "");
				statement = statement.replace(", ", ",");
				statement = statement.replace(" ", "");
				resultados = resultados + statement;
			}
			ResultAdapter json = new ResultAdapter();
			resultados = json.putFormat(resultados);
			statements.close();
		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e1) {
			resultados = e1.getMessage();
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
				if (!resultados.contains(statement)) {
					resultados = resultados + statement;
				}
			}
			stataments.close();
			if (!resultados.equals("")) {
				ResultAdapter json = new ResultAdapter();
				resultados = json.putFormat(resultados);
			}

		} catch (RepositoryException | MalformedQueryException | QueryEvaluationException e1) {
			resultados = e1.getMessage();
		}
		return resultados;
	}
	public void uploadFile(File pFile) throws RDFParseException, RepositoryException, IOException{
		repository.add(pFile, "", RDFFormat.TURTLE);
		repository.commit();
	}

}

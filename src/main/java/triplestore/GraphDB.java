
package triplestore;

import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import tratamiento.Json;



public class GraphDB {
	private RepositoryConnection repository;

	public GraphDB() {
		try {
			HTTPRepository conn = new HTTPRepository("http://174.140.171.251:7200/repositories/pruebasMishel");
			conn.setUsernameAndPassword("mishel", "mishel");
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

	public void executeQuery(String pQuery) {
		try {
			TupleQuery query = repository.prepareTupleQuery(QueryLanguage.SPARQL, pQuery);
			TupleQueryResult result = query.evaluate();
			while (result.hasNext()) {
				final BindingSet bs = result.next();
				System.out.println(bs);
			}
			QueryResultIO.write(result, TupleQueryResultFormat.JSON, System.out);
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String executeGraphQuery(String pQuery)
			throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		GraphQuery query = repository.prepareGraphQuery(QueryLanguage.SPARQL, pQuery);
		GraphQueryResult stataments = query.evaluate();
		String resultados = "";
		System.out.println("entra");
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
		System.out.println("Los resultados" + resultados);
		Json json = new Json();
		String e = json.parsearJSON(resultados);
		System.out.println("EL JSON" + e);

		return e;
	}
}
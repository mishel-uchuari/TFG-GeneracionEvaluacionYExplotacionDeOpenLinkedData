package triplestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.query.resultio.TupleQueryResultWriter;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

public class GraphDB {
	private RepositoryConnection repository;

	public GraphDB() {
		try {
			HTTPRepository conn = new HTTPRepository("http://174.140.171.251:7200/repositories/pruebasMishel");
			repository = conn.getConnection();
			conn.setUsernameAndPassword("mishel", "mishel");
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
		System.out.println("Vamos por graphdb");
		System.out.println(pQuery);
		String results = ""; 
		try {
			TupleQuery query = repository.prepareTupleQuery(QueryLanguage.SPARQL, pQuery);
			TupleQueryResult statements = query.evaluate();
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();

			while (statements.hasNext()) {
				final BindingSet bs = statements.next();
				//results = results+bs.toString();
				QueryResultIO.write(statements, TupleQueryResultFormat.JSON, stream);
			}
			results = new String(stream.toByteArray());
			
			System.out.println("Esta generando los resultados");
			System.out.println(results);
			statements.close();
			System.out.println("ya acabo");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
}

package proyectod3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
//import org.openrdf.repository.http.HTTPRepository;


public class GraphDB {
//	private RepositoryConnection repository;
//
//	public GraphDB() {
//		try {
//			HTTPRepository conn = new HTTPRepository("http://opendata.eurohelp.es:8888/repositories/77");
//			repository = conn.getConnection();
//			repository.begin();
//		} catch (RepositoryException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void loadRDF4JModel(final Model pModel) {
//		try {
//			Iterable<? extends Statement> it = new Iterable<Statement>() {
//				
//				public Iterator<Statement> iterator() {
//					return pModel.iterator();
//				}
//			};
//			repository.add(it);
//			repository.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void executeQuery(String pQuery) {
//		try {
//			File file = new File("./JSON/archivoJSON.json");
//			OutputStream os = new FileOutputStream(file);
//			TupleQuery query = repository.prepareTupleQuery(QueryLanguage.SPARQL, pQuery);
//			TupleQueryResult result = query.evaluate();
//			while (result.hasNext()) {
//				final BindingSet bs = result.next();
//				System.out.println(bs);
//			}
//			QueryResultIO.write(result, TupleQueryResultFormat.JSON, os);
//			result.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
}

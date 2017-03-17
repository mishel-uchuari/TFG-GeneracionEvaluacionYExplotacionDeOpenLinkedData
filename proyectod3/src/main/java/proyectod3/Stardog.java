package proyectod3;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.TupleQueryResultHandler;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.sesame.StardogRepository;

public class Stardog {
	private RepositoryConnection repository;
	private String serverURL;

	/**
	 * @throws RepositoryException
	 * 
	 */
	public Stardog() throws RepositoryException {
		serverURL = "http://ckan.eurohelp.es:5820";
		Repository stardogRepository = new StardogRepository(ConnectionConfiguration.to("LODgenAppTurismo").server(serverURL).credentials("admin", "ctxakurra"));
		stardogRepository.initialize();
		repository = stardogRepository.getConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eurohelp.opendata.aldapa.api.storage.TripleStoreService#loadRDF4JModel
	 * (org.eclipse.rdf4j.model.Model)
	 */
	public void loadRDF4JModel(final Model pModel) {
	
		System.out.println(	pModel.iterator().next());
		try {
			Iterable<? extends Statement> it = new Iterable<Statement>() {
				
				public Iterator<Statement> iterator() {
					return pModel.iterator();
				}
			};
			System.out.println(pModel.iterator().next());
				repository.add(it);
				//repository.add(statement);
		
			repository.commit();
			System.out.println("subido");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeQuery(String pQuery) {
		try {
			File file = new File("./JSON/archivoJSON.json");
			OutputStream os = new FileOutputStream(file);
			TupleQuery query = repository.prepareTupleQuery(QueryLanguage.SPARQL, pQuery);
			TupleQueryResult results = query.evaluate();
			QueryResultIO.writeTuple(results, TupleQueryResultFormat.JSON, os);
			//QueryResultIO.write(results, TupleQueryResultFormat.JSON, os);
		//	results.close();
//			PrintWriter pw = new PrintWriter(os);
//			while(results.hasNext()){
//				pw.println(results.next());
//			}
			//QueryResultIO.write(results, TupleQueryResultFormat.JSON, os);
			results.close();
			//pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}



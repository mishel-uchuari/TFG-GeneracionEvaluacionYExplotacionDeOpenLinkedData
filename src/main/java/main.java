
import java.io.IOException;

import org.openrdf.rio.RDFHandlerException;

import triplestore.GraphDB;


public class main {
	public static void main(String[] args) throws IOException, RDFHandlerException {
	
			 
//		 Pipeline pipeCalidadAire = new Pipeline("CalidadDelAire", "convertidor", "./DatosIniciales/calidadAire-VitoriaGasteiz.csv", 
//				 "./DatosConvertidos/calidadAire-VitoriaGasteiz.rdf");
//		 Pipeline pipeEstacionesMetereologicas = new Pipeline("EstacionesMetereologicas", "convertidor", "./DatosIniciales/estacionesMetereologicas-c040.csv", 
//				 "./DatosConvertidos/estacionesMetereologicas-c040.rdf");
//		 Pipeline pipeRelacionesPuestosT = new Pipeline("RelacionesPuestoTrabajo", "convertidor", "./DatosIniciales/relacionPuestosTrabajo-2017.csv", 
//				 "./DatosConvertidos/relacionPuestosTrabajo-2017.rdf");
//		 Pipeline pipeRetribNominativas = new Pipeline("RetribucionesNominativas", "convertidor", "./DatosIniciales/retribucionesNominativas-2017.csv", 
//				 "./DatosConvertidos/retribucionesNominativas-2017.rdf");
		 
		// pipeCalidadAire.start();
		// pipeEstacionesMetereologicas.start();
	//	 pipeRelacionesPuestosT.start();
		// pipeRetribNominativas.start();
		GraphDB nd = new GraphDB();
		System.out.println(nd.executeQuery("select ?s ?p ?o where { ?s ?p ?o .} limit 100"));
	}
}
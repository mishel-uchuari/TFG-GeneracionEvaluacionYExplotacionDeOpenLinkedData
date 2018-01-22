
import java.io.IOException;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandlerException;

import triplestore.utils.GraphDB;

public class main {
	public static void main(String[] args) throws IOException, RDFHandlerException, RepositoryException,
			MalformedQueryException, QueryEvaluationException {

//		 System.out.println("|||||||||||||||||||||||\n"
//		 + "|| Calidad del aire ||\n"
//		 + "|||||||||||||||||||||||\n");
//		 Pipeline pipeCalidadAire = new Pipeline("CalidadDelAire",
//		 "convertidor", "./DatosIniciales/calidadAire-VitoriaGasteiz.csv",
//		 "./DatosConvertidos/calidadAire-VitoriaGasteiz.ttl");
//		 pipeCalidadAire.run();
//		
//		 System.out.println("RDF calidad del aire creado\n");
//
//		System.out.println("||||||||||||||||||||||||||||||||\n" + "|| Estaciones Metereologicas  ||\n"
//				+ "||||||||||||||||||||||||||||||||\n");
//
//		Pipeline pipeEstacionesMetereologicas = new Pipeline("EstacionesMetereologicas", "convertidor",
//				"./DatosIniciales/estacionesMetereologicas-c040.csv",
//				"./DatosConvertidos/estacionesMetereologicas-c040.ttl");
//		pipeEstacionesMetereologicas.run();
//
//		System.out.println("RDF estaciones metereologicas creado\n");
//
//		 System.out.println("||||||||||||||||||||||||||||||||\n"
//		 + "|| Relaciones puestos trabajo ||\n"
//		 + "||||||||||||||||||||||||||||||||\n");
//
//		 Pipeline pipeRelacionesPuestosT = new
//		 Pipeline("RelacionesPuestoTrabajo", "convertidor",
//		 "./DatosIniciales/relacionPuestosTrabajo-2017.csv",
//		 "./DatosConvertidos/relacionPuestosTrabajo-2017.ttl");
//		 pipeRelacionesPuestosT.run();
//		
//		 System.out.println("RDF relaciones puestos trabajo creado\n");

		 System.out.println("|||||||||||||||||||||||||||||||\n"
		 + "|| Retribuciones Nominativas ||\n"
		 + "|||||||||||||||||||||||||||||||\n");
		
		 Pipeline pipeRetribNominativas = new
		 Pipeline("RetribucionesNominativas", "convertidor",
		 "./DatosIniciales/retribucionesNominativas-2017.csv",
		 "./DatosConvertidos/retribucionesNominativas-2017.ttl");
		 pipeRetribNominativas.run();
		
		 System.out.println("RDF retribuciones nominativas creado\n");
		
	}
}
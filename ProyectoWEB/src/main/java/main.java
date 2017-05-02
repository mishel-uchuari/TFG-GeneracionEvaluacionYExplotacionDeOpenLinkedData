import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;

import clojure.lang.LazySeq;
import clojure.lang.RT;

public class main {
public static void main(String[] args) throws IOException {
	RT.loadResourceScript("pipelines/CalidadDelAire.clj");
	//LazySeq lazy = (LazySeq) RT.var("AvenidaGasteiz.sensorLocation", "convertidor").invoke("./data/estaciones.csv");
	LazySeq lazy = (LazySeq) RT.var("pipelines.CalidadDelAire", "convertidor").invoke("./DatosIniciales/AV._GASTEIZ.csv");
	Iterator ite = lazy.iterator();
	Model model = new LinkedHashModel();
//	while (ite.hasNext()) {
//		model.add((Statement) ite.next());
//		// System.out.println(ite.next().getClass());
//		// Statement cn=(Statement) RT.var("grafterdatacube.core",
//		// "convertidor").invoke(ite.next());
//		// System.out.println(cn.getClass());
//	}
	/**
	 * Código para sacar el archivo RDF/XML-TURTLE
	 * 
	 */
	//File file = new File("./data/archivoRDFDatosLocalizacionEstaciones.rdf");
	File file = new File("./DatosConvertidos/archivoRDFAvenidaGasteiz.rdf");
	FileOutputStream fileTurtle = new FileOutputStream(file);
	Rio.write(model, fileTurtle, RDFFormat.NQUADS);
	// PruebasModel pM = new PruebasModel(model);
	// pM.testModel();
	// System.out.println(pM.testModel2());
}
}

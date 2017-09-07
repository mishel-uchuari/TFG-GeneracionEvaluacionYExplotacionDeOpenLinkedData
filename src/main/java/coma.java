
import java.io.IOException;

import org.openrdf.rio.RDFHandlerException;

import clojure.lang.RT;

public class coma {
	public static void main(String[] args) throws IOException, RDFHandlerException {
		RT.loadResourceScript("pipelines/coma.clj");
		 RT.var("pipelines.coma",
		 "quitarComas").invoke("./DatosIniciales/retribuciones_nominativas.csv");
//		 Iterator<?> ite = lazy.iterator();
//		 Model model = new LinkedHashModel();
//		 while (ite.hasNext()) {
//		 model.add((Statement) ite.next());
//		// Statement cn=(Statement) RT.var("grafterdatacube.core",
//		// "convertidor").invoke(ite.next());
//		// System.out.println(cn.getClass());
//		 }
//		 /**
//		 * Código para sacar el archivo RDF/XML-TURTLE
//		 *
//		 */
//		 File file = new File("/DatosConvertidos/archivoPuestosTrabajo.rdf");
//		 FileOutputStream fileTurtle = new FileOutputStream(file);
//		 Rio.write(model, fileTurtle, RDFFormat.NQUADS);
//		 // PruebasModel pM = new PruebasModel(model);
//		 // pM.testModel();
//		 // System.out.println(pM.testModel2());
	}
}

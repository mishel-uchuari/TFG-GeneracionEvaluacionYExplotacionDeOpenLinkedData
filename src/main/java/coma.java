
import java.io.IOException;

import org.openrdf.rio.RDFHandlerException;

import clojure.lang.RT;

public class coma {
	public static void main(String[] args) throws IOException, RDFHandlerException {
		RT.loadResourceScript("pipelines/coma.clj");
		 RT.var("pipelines.coma",
		 "quitarComas").invoke("./DatosIniciales/estacionesMetereologicas.csv");
	}
}

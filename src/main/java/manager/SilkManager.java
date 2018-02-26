package manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.jena.util.FileUtils;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;

import de.fuberlin.wiwiss.silk.Silk;
import triplestore.GraphDB;

public class SilkManager {
	public static void main(String[] args) throws Exception {
		// Se carga el archivo donde esta la configuración de SILK
		if (args.length == 2 && args[0].contains(".xml") && args[1].contains(".nt")) {
			File configFile = new File(args[0]);
			String stringConfFile = FileUtils.readWholeFileAsUTF8(args[0]);
			stringConfFile = stringConfFile.trim();
			// Si es el mismo archivo
			if (stringConfFile.contains(args[1])) {
				// Se ejecuta SILK
				Silk.executeFile(configFile, null, 8, true);
				File initialFile = new File(args[1]);
				RDFFormat format = RDFFormat.forFileName(args[1]);
				// Si se han descubierto enlaces se suben a la triple store
				if (initialFile.length() != 0) {
					GraphDB gdb = new GraphDB();
					gdb.uploadFile(initialFile, format);
				} else {
					throw new Exception("No se han encontrado enlaces según la configuración realizada");
				}
			} else {
				throw new Exception(
						"El archivo donde se almacenaran los datos debe coincidir con el fijado en el archivo de configuración de Silk");
			}
		} else if (args.length != 2) {
			throw new Exception("Se debe introducir un sólo parámetro, el xml de configuracion de Silk");
		} else if (!args[0].contains(".xml")) {
			throw new Exception("El parametro de entrada, el archivo de configuracion de Silk, debe de ser tipo .xml");
		}
	}
}

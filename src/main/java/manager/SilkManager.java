package manager;

import java.io.File;

import org.apache.jena.util.FileUtils;

import de.fuberlin.wiwiss.silk.Silk;
import triplestore.GraphDB;

public class SilkManager {
	public static void main(String[] args) throws Exception {
		// Se carga el archivo donde esta la configuración de SILK
		if (args.length == 2 && args[0].contains(".xml") && args[1].contains(".nt")) {
			File configFile = new File(args[0]);
			String stringConfFile = FileUtils.readWholeFileAsUTF8(args[0]);
			// Si es el mismo archivo
			if (stringConfFile.contains(args[1])) {
				// Se ejecuta SILK
				Silk.executeFile(configFile, null, 8, true);
				String resultsFile = FileUtils.readWholeFileAsUTF8(args[1]);
				resultsFile = resultsFile.replaceAll("  ", " ");
//				resultsFile.length();
//				int fin=resultsFile.lastIndexOf(" ");
//				resultsFile=resultsFile.substring(0, fin+);
				// Si se han descubierto enlaces se suben a la triple store
				if (resultsFile.length() != 0) {
					System.out.println(resultsFile);
					GraphDB gdb = new GraphDB();
					gdb.uploadFile(configFile);
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

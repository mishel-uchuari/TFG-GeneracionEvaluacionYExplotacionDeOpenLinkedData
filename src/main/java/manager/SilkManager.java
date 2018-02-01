package manager;

import java.io.File;

import de.fuberlin.wiwiss.silk.Silk;

public class SilkManager {
	public static void main(String[] args) {
		//Se carga el archivo donde esta la configuración de SILK
		File file = new File(args[0]);
		//Se ejecuta SILK
		Silk.executeFile(file, null, 8, true);
	}
}

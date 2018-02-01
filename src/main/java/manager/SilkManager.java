package manager;

import java.io.File;

import de.fuberlin.wiwiss.silk.Silk;

public class SilkManager {
	public static void main(String[] args) throws Exception {
		//Se carga el archivo donde esta la configuración de SILK
		if(args.length==1 && args[0].contains(".xml")){
			File file = new File(args[0]);
			//Se ejecuta SILK
			Silk.executeFile(file, null, 8, true);	
		}
		else if(args.length!=1){
			throw new Exception ("Se debe introducir un sólo parámetro, el xml de configuracion de Silk");
		}
		else if(!args[0].contains(".xml")){
			throw new Exception ("El parametro de entrada, el archivo de configuracion de Silk, debe de ser tipo .xml");
		}
	}
}

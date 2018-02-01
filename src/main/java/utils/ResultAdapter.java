package utils;

public class ResultAdapter {
	/**
	 * Devuelve las filas del resultado sparql
	 * 
	 * @param pResultados
	 * @return
	 */
	public String[] getRaws(String pResultados) {
		pResultados = pResultados.replace("(", "");
		if(pResultados.contains("\"")){
		pResultados = pResultados.replace("\"", "");
		pResultados = pResultados.replace("\"\"", "");
		}
		String[] elementos = pResultados.split("\\)");
		return elementos;
	}

	/**
	 * Convierte el resultado sparql a un formato adecuado para convertirse en
	 * json posteriormente
	 * 
	 * @param pResultados
	 * @return
	 */
	public String putFormat(String pResultados) {
		String json = "";
		String[] filas = getRaws(pResultados);
		int i = filas.length - 1;
		while (i > -1) {
			String[] porElemento = filas[i].split(",");
			if (i == 0) {
				json = json.concat("" + porElemento[0] + "," + porElemento[2] + "," + porElemento[1] + "");
			} else {
				json = json.concat("" + porElemento[0] + "," + porElemento[2] + "," + porElemento[1] + ";");
			}
			i--;
		}
		return json;
	}
	
}

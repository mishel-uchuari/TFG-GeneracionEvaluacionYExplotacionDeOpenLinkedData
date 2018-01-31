package tratamiento;

/**
 * 
 * @author dmuchuari
 * @03/01/2018
 */
public class Json {
	private String data;
	
	public Json(String pData) {
		data=pData;
	}
	/**
	 * Devuelve las filas del resultado sparql
	 * 
	 * @param data
	 * @return
	 */
	public String[] getFilas() {
		data = data.replace("(", "");
		if(data.contains("\"")){
		data = data.replace("\"", "");
		data = data.replace("\"\"", "");
		}
		String[] elementos = data.split("\\)");
		return elementos;
	}

	/**
	 * Convierte el resultado sparql a un formato adecuado para convertirse en
	 * json posteriormente
	 * 
	 * @param pResultados
	 * @return
	 */
	public String parsearJSON() {
		String json = "";
		String[] filas = getFilas();
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
		System.out.println("el json??" + json);
		return json;
	}
}
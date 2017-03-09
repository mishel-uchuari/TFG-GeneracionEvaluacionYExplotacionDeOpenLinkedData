package proyectod3;




public class Main {
	public static void main(String[] args) throws Exception {
		//InputStream is = StardogPrueba.class.getResourceAsStream("mikel.ttl");
		//final Model model = Rio.parse(is, is.toString(), RDFFormat.TURTLE);
		Stardog st = new Stardog();
		st.executeQuery("Select * where {?s ?o ?p}");
//		GraphDB gdb= new GraphDB();
//		gdb.executeQuery("Select * where {?s ?o ?p}");
		//st.loadRDF4JModel(model);
//		StardogPrueba pr = new StardogPrueba();
//		pr.conexion();
	}
}

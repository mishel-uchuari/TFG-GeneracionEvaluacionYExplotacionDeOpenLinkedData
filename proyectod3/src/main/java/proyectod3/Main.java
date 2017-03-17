package proyectod3;




public class Main {
	public static void main(String[] args) throws Exception {
		//InputStream is = StardogPrueba.class.getResourceAsStream("mikel.ttl");
		//final Model model = Rio.parse(is, is.toString(), RDFFormat.TURTLE);
		Stardog st = new Stardog();
		st.executeQuery("SELECT DISTINCT * WHERE {GRAPH<http://opendata.eurohelp.es/dataset/recursos-humanos> {?s ?p ?o}}");
//		GraphDB gdb= new GraphDB();
//		gdb.executeQuery("Select * where {?s ?o ?p}");
		//st.loadRDF4JModel(model);
//		StardogPrueba pr = new StardogPrueba();
//		pr.conexion();
	}
}

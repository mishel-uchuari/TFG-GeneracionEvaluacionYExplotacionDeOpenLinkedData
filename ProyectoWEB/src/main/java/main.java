import stardog.*;

public class main {
public static void main(String[] args) {
	Stardog st=new Stardog();
	st.executeQuery("select * where {?s ?o ?p} limit 3");
	
}
}

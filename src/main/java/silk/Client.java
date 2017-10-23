package silk;

import java.io.File;

import de.fuberlin.wiwiss.silk.Silk;

public class Client {
	public static void main(String[] args) {
		//"./data/silk-test.xml"
		File file = new File("./data/silk-test.xml");
		Silk.executeFile(file, null, 8, true);
	}
}

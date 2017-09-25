import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Peticion {

	private String url;

	public Peticion(String pUrl) throws IOException {
		HttpURLConnection connection = null;
		URL url = new URL(pUrl);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-type", "application/rdf+xml");
		connection.setUseCaches(false);
		connection.setDoOutput(true);

		DataOutputStream dOuputStream = new DataOutputStream(connection.getOutputStream());
		dOuputStream.close();

		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		br.close();
		if (connection != null) {
			connection.disconnect();
		}
	}
}

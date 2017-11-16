import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import triplestore.PropertiesManager;

public class RequestManager {
	private String uri, method, accept, responseString;
	private int status;
	private Map<String, String> params;
	private BufferedHttpEntity bfHttpEntity;


	public RequestManager(String pUri, String pMethod, String pAccept, Map<String, String> pParams) {
		uri = pUri;
		method = pMethod;
		accept = pAccept;
		params = pParams;
	}

	/**
	 * Ejecuta una peticion post
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void executeRequest() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = null;
		HttpGet httpGet = null;
		HttpResponse response = null;

		if (method.equalsIgnoreCase("POST")) {
			httpPost = new HttpPost(uri);
			httpPost.setHeader("accept", accept);
			httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			Map<String, String> parameters = params;
			Iterator<String> iterator = parameters.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				postParameters.add(new BasicNameValuePair(key, parameters.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(httpPost);
		}
		else{
			int numParam = 0;

			Map<String, String> parameters = params;

			Iterator<String> iterator = parameters.keySet().iterator();
			String completeUri=uri;
			while (iterator.hasNext()) {
				if (numParam == 0) {
					completeUri = uri + "?";
				} else {
					completeUri = completeUri + "&";
				}
				String key = iterator.next();
				completeUri = completeUri + key + "=" + parameters.get(key);
				numParam++;
			}
			bfHttpEntity = new BufferedHttpEntity(response.getEntity());
			httpGet = new HttpGet(completeUri);
			httpGet.setHeader("accept", accept);
			response = client.execute(httpGet);
			status=response.getStatusLine().getStatusCode();
			
			String resultsPath = PropertiesManager.getINSTANCE().getProperty("reportpath");
			PrintWriter pw = new PrintWriter(resultsPath);
			responseString=EntityUtils.toString(bfHttpEntity);
			pw.write(EntityUtils.toString(bfHttpEntity));
			pw.close();
			
		}
	}
	
}
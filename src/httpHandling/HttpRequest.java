package httpHandling;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {
	private URL url = null;
	private HttpsURLConnection connection = null;
    
	public HttpRequest(String address){
		
		try {
			url = new URL(address);
			this.connection = (HttpsURLConnection) url.openConnection();
			this.connection.setDoOutput(true);
			this.connection.setDoInput(true);
			
		} catch (MalformedURLException ex) {
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex){
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void startConnection(){
		try {
			connection.connect();
		} catch (IOException ex) {
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public String getHost()		{return url.getHost();	}
	public String getMethod()	{return url.getFile();	}
	public String getQuery()	{return url.getQuery();	}
	
	public String getAddress() {
		return url.getHost() + url.getFile() + url.getQuery();
	}

	public HttpsURLConnection getConnection() {
		return connection;
	}
	
	public InputStream getInputStream() throws IOException {
		return this.connection.getInputStream();
	}
	
	public int write(String message){
		try {
			connection.getOutputStream().write(message.getBytes());
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			return connection.getResponseCode();
			
		} catch (IOException ex) {
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return 0;
	}
}

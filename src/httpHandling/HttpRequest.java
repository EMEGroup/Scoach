package httpHandling;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {
	private final String protocol = "https";
	private final short port = -1;
	
	private String address = "", method = "/", query = "";
	private BufferedInputStream inputStream;
	
	private URL url;
	private HttpsURLConnection connection;
    
	public HttpRequest(String address){
		this.address = address;
		
		if(address == null)	
			this.address = "localhost";
		else if(address.isEmpty())	
			this.address = "localhost";
		else if(address.startsWith("http[s]?://"))
			this.address = address.replaceFirst("http[s]?://", "");
	}
	
    public HttpRequest(String address, String query){
		this.address = address;
		this.query = query;
		
		if(address == null)	
			this.address = "localhost";
		else if(address.isEmpty())	
			this.address = "localhost";
		else if(address.startsWith("http[s]?://"))
			this.address = address.replaceFirst("http[s]?://", "");
		
		if(query == null)	
			this.query = " ";
		else if(query.isEmpty())	
			this.query = " ";
		else if(query.charAt(0) != '?')
			this.query = "?" + query;
	}
	
	public HttpRequest(String address, String method, String query){
		this.address = address;
		this.method = method;
		this.query = query;
		
		if(address == null)	
			this.address = "localhost";
		else if(address.isEmpty())	
			this.address = "localhost";
		else if(address.startsWith("http[s]?://"))
			this.address = address.replaceFirst("http[s]?://", "");
		
		if(method == null)	
			this.method = " ";
		else if(method.isEmpty())	
			this.method = " ";
		else if(method.charAt(0) != '?')
			this.method = "?" + method;
		
		if(query == null)	
			this.query = " ";
		else if(query.isEmpty())	
			this.query = " ";
		else if(query.charAt(0) != '?')
			this.query = "?" + query;
	}

	public void openConnection(){
		
		try {
			this.url = new URL(
				protocol, this.address, port, this.method + this.query);
			this.connection = (HttpsURLConnection) url.openConnection();
			
			inputStream = new BufferedInputStream(connection.getInputStream());
			inputStream.mark(0);
		} catch (MalformedURLException ex) {
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex){
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		
		if(address == null)	
			this.address = "localhost";
		else if(address.isEmpty())	
			this.address = "localhost";
		else if(address.startsWith("http[s]?://"))
			this.address = address.replaceFirst("http[s]?://", "");
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
		
		if(query == null)	
			this.query = " ";
		else if(query.isEmpty())	
			this.query = " ";
		else if(query.charAt(0) != '?')
			this.query = "?" + query;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
		
		if(method == null)	
			this.method = " ";
		else if(method.isEmpty())	
			this.method = " ";
		else if(method.charAt(0) != '?')
			this.method = "?" + method;
	}

	public BufferedInputStream getInputStream() {
		return inputStream;
	}

	public HttpsURLConnection getConnection() {
		return connection;
	}
}

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
		
		try {
			this.url = new URL(protocol, address, port, method + query);
			this.connection = (HttpsURLConnection) url.openConnection();
			
			inputStream = new BufferedInputStream(connection.getInputStream());
			inputStream.mark(0);
		} catch (MalformedURLException ex) {
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex){
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
    public HttpRequest(String address, String query){
		this.address = address;
		this.query = query;
		
		if(query.charAt(0) != '?')
			query = "?" + query;
		
		try {
			this.url = new URL(protocol, address, port, method + query);
			this.connection = (HttpsURLConnection) url.openConnection();
			
			inputStream = new BufferedInputStream(connection.getInputStream());
			inputStream.mark(0);
		} catch (MalformedURLException ex) {
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex){
			Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public HttpRequest(String address, String method, String query){
		this.address = address;
		this.method = method;
		this.query = query;
		
		if(method.charAt(0) != '/')
			method = "/" + method;
		
		if(query.charAt(0) != '?')
			query = "?" + query;
		
		try {
			this.url = new URL(protocol, address, port, method + query);
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
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
		
		if(query.charAt(0) != '?')
			this.query = "?" + query;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
		
		if(method.charAt(0) != '?')
			this.method = "?" + query;
	}

	public BufferedInputStream getInputStream() {
		return inputStream;
	}

	public HttpsURLConnection getConnection() {
		return connection;
	}
}

package httpHandling;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpRequest {
	private final String addr;
	private URL url = null;
	private HttpURLConnection connection = null;
    
	public HttpRequest(String address) throws MalformedURLException, IOException{
		
		url = new URL(address);
		
		if( url.getProtocol().equals("HTTPS") )
			this.connection = (HttpsURLConnection) url.openConnection();
		else
			this.connection = (HttpURLConnection) url.openConnection();
		
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
		
		if( this.connection.getResponseCode() == 301 ){
			addr = this.connection.getHeaderField("Location");
		} else{
			addr = address;
		}
		
		this.connection.disconnect();

		url = new URL(addr);
		
		if( url.getProtocol().equals("HTTPS") )
			this.connection = (HttpsURLConnection) url.openConnection();
		else
			this.connection = (HttpURLConnection) url.openConnection();
		
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
	}

	public void startConnection() throws IOException{
		connection.connect();
	}
	
	public void disconnect(){
		connection.disconnect();
	}
	
	public String getHost()		{return url.getHost();	}
	public String getMethod()	{return url.getFile();	}
	public String getQuery()	{return url.getQuery();	}
	
	public String getAddress() {
		return url.getHost() + url.getFile() + url.getQuery();
	}

	public HttpURLConnection getConnection() {
		return connection;
	}
	
	public InputStream getInputStream() throws IOException {
		if(connection.getResponseCode() != 200)
			return this.connection.getErrorStream();
		else
			return this.connection.getInputStream();
	}
	
	public int write(String message) throws IOException{
		connection.getOutputStream().write(message.getBytes());
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
		return connection.getResponseCode();
	}
	
	public HttpRequest reset() throws MalformedURLException, IOException{
		return new HttpRequest(addr);
	}
}

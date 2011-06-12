import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GoogleGeocode {
	private final static String BASE_URI = "http://maps.googleapis.com/maps/api/geocode/json?";
	private final static String SENSOR = "false";
	
	public String lat;
	public String lng;
	public String addr;

	public GoogleGeocode(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public GoogleGeocode(String addr) {
		this.addr = addr;
	}
	
	public JSONObject getReverseGeo() {
		JSONObject result;
		
		String qryStr = "latlng=" + this.lat + "," + this.lng + "&sensor=" + GoogleGeocode.SENSOR;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(GoogleGeocode.BASE_URI + qryStr);
		
		InputStream is = null;
		InputStreamReader streamReader = null;
		BufferedReader rd = null;
		
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if (statusCode == 401) {
				// Unauthorized. Rarely happen, only just in case...
				return null;
			}
			
			HttpEntity myEntity = httpResponse.getEntity();
			is = myEntity.getContent();
			streamReader = new InputStreamReader(is);
			rd = new BufferedReader(streamReader);
			StringBuffer sb = new StringBuffer();
			String line;
			
			while ((line = rd.readLine()) != null) {
				//parsing data here
				sb.append(line);
			}
			
			rd.close();
			
			try {
				// assigning data
				result = new JSONObject(sb.toString()).getJSONArray("results").getJSONObject(0);
			} catch (JSONException e) {
				result = null;
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			result = null;
			e.printStackTrace();
		} catch (IOException e) {
			result = null;
			e.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
					// you can also get the specific message here
					// using: e.getMessage();
				}
			}
			if (streamReader != null) {
				try {
					streamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
					// you can also get the specific message here
					// using: e.getMessage();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					// you can also get the specific message here
					// using: e.getMessage();
				}
			}
		}
		
		return result;
	}
}

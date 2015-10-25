import java.net.*;
import java.io.*;
import org.json.*;

public class PlayWithGoogleImage {
	public static void main(String[] args) {
		try {
			URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
       	         	"v=1.0&q=barack%20obama&userip=198.137.20.133");

			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Referer", "http://visual-summary.appspot.com/");

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			// now have some fun with the results...
			System.out.println(json.get("responseData").getClass().getName());
			/*for (String a : JSONObject.getNames(json.get("responseData"))) {
				System.out.println(a);
			}*/

			JSONArray arr = json.getJSONObject("responseData").getJSONArray("results");
			for (int i=0; i<arr.length(); i++) {
				System.out.println(arr.getJSONObject(i).getString("url"));
			}
		}
		catch (MalformedURLException e) {
			System.out.println("Nooo");
		}
		catch (IOException e) {
			System.out.println("NOOOOO");
		}
	}
}
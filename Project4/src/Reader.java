import java.io.*;
import java.net.URL;
import java.util.*;
import net.sf.json.*;

public class Reader {
	Scanner scan = new Scanner(System.in);
	String code = "";
	
	public static String readURL(String webservice) throws Exception {
		URL oracle = new URL(webservice);
		BufferedReader in = new BufferedReader(
        new InputStreamReader(
        oracle.openStream()));

		String inputLine;
		String result = "";

		while ((inputLine = in.readLine()) != null)
			result = result + inputLine;

		in.close();
		
		return result;
    }
	
	public String getStats(String artist, String stats) {
		String string = "";
		if (stats.equals("All")) {
			if (bio(artist).equals("Invalid Input")) {
				string = "Invalid Input";
			}
			else {
				string = bio(artist) + "<br />" + albums(artist) + "<br />"
						+ tracks(artist) + "<br />" + listeners(artist);
			}
		}
		else if (stats.equals("Top Tracks")) {
			string = tracks(artist);
		}
		else if (stats.equals("Listeners")) {
			string = listeners(artist);
		}
		else if (stats.equals("Top Albums")) {
			string = albums(artist);
		}
		else if (stats.equals("Bio")) {
			string = bio(artist);
		}

		return string;
	}

	private String tracks(String artist) {
		String string ="<strong><u>Top Tracks:</u></strong><br />";
		try{
			String JSonString = readURL("http://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=" + artist + "&api_key=1778eaa613a8897e9b35044854b762f2&format=json");
			JSONObject x = JSONObject.fromObject(JSonString);
			JSONObject artistData =(JSONObject)(x.get("toptracks"));
			JSONArray track = (JSONArray)(artistData.getJSONArray("track"));
			for (int i=0;i<track.size(); i++) {
				JSONObject name = (JSONObject)(track.remove(i));
				string += (i+1) + ": " + (String.valueOf(name.get("name"))) + "<br />";
			}
		}
		catch (Exception e) {
			string = "<strong><u>No Tracks</u></strong><br />";
		}

		return string;
	}
	
	private String listeners(String artist) {
		String string ="";
		try{
			String JSonString = readURL("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artist + "&api_key=1778eaa613a8897e9b35044854b762f2&format=json");
			JSONObject x = JSONObject.fromObject(JSonString);
			JSONObject artistData =(JSONObject)(x.get("artist"));
			JSONObject stats = (JSONObject)(artistData.get("stats"));
			string = "<strong><u>Listeners:</u></strong> " + (String.valueOf(stats.get("listeners"))) 
					+ "<br /><strong><u>Play Count:</u></strong> "+ (String.valueOf(stats.get("playcount")));
		}
		catch (Exception e) {
			string = "<strong><u>No Listeners</u></strong><br />";
		}

		return string;
	}
	
	private String bio(String artist) {
		String string = "<strong><u>Bio:</u></strong><br />";
		try{
			String JSonString = readURL("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artist + "&api_key=1778eaa613a8897e9b35044854b762f2&format=json");
			JSONObject x = JSONObject.fromObject(JSonString);
			JSONObject artistData =(JSONObject)(x.get("artist"));
			JSONObject bio =(JSONObject)(artistData.get("bio"));
			string += (String.valueOf(bio.get("summary"))) + "<br />";
		}
		catch (Exception e) {
			string = "<strong><u>No Bio</u></strong><br />";
		}
		
		return string.replaceAll("<a href", "<br /><a href");
	}
	
	private String albums(String artist) {
		String string ="<strong><u>Top Albums:</u></strong><br />";
		try{
			String JSonString = readURL("http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" + artist + "&api_key=1778eaa613a8897e9b35044854b762f2&format=json");
			JSONObject x = JSONObject.fromObject(JSonString);
			JSONObject artistData =(JSONObject)(x.get("topalbums"));
			JSONArray album = (JSONArray)(artistData.getJSONArray("album"));
			for (int i=0;i<album.size(); i++) {
				JSONObject name = (JSONObject)(album.remove(i));
				string += (i+1) + ": " + (String.valueOf(name.get("name"))) + "<br />";
			}
		}
		catch (Exception e) {
			string = "<strong><u>No Albums</u></strong><br />";
		}

		return string;
	}
}
package com.kaonashi696.pleromamc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;

public class HTTPSPostRequest extends JavaPlugin{
	
	FileConfiguration config = getConfig();
	
	public String getString(String string) {
    	config.getString(string);
		return string;
    }  
			
    public static void sendPOST(String POST_PARAMS) throws IOException {
    	
    	HTTPSPostRequest get=new HTTPSPostRequest();
    	String oauth = get.getString("oauth");
    	String post_url = get.getString("post_url");
    	
        URL obj = new URL(post_url);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) obj.openConnection();
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setRequestProperty("Authorization","Bearer "+ oauth);

        // For POST only - START
        httpsURLConnection.setDoOutput(true);
        OutputStream os = httpsURLConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = httpsURLConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }
}


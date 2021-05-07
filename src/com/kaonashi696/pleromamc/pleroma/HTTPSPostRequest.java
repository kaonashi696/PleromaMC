package com.kaonashi696.pleromamc.pleroma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.HttpsURLConnection;

import org.bukkit.configuration.file.FileConfiguration;

import com.kaonashi696.pleromamc.PleromaMC;

import java.net.URL;

public class HTTPSPostRequest{
	
	public static void sendPOST(PleromaMC core, String post_url, String POST_PARAMS) throws IOException {
    	
		FileConfiguration config = core.getConfig();
    	String oauth = config.getString("pleromaOauth");
    	
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


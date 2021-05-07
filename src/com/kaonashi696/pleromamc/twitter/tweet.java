package com.kaonashi696.pleromamc.twitter;

import org.bukkit.configuration.file.FileConfiguration;

import com.kaonashi696.pleromamc.PleromaMC;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class tweet {

	public static void sendPOST(PleromaMC core, String status) {
		
		FileConfiguration config = core.getConfig();
    	String consumerKeyStr = config.getString("twitterConsumerKey");
    	String consumerSecretStr = config.getString("twitterConsumerSecret");
    	String accessTokenStr = config.getString("twitterAccessToken");
    	String accessTokenSecretStr = config.getString("twitterAccessTokenSecret");

		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr,
					accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			twitter.updateStatus(status);

			System.out.println("Successfully updated the status in Twitter.");
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

}


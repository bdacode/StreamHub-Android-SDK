package com.livefyre.android.core;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import android.net.Uri;
import android.net.Uri.Builder;

import com.loopj.android.http.JsonHttpResponseHandler;

public class StreamClient {
	public static String generateStreamUrl(String networkId, String collectionId, String eventId) throws MalformedURLException {
		Builder uriBuilder = new Uri.Builder();
		uriBuilder.appendPath(Config.scheme)
		.appendPath(Config.streamDomain).appendPath(".")
		.appendPath(Config.getHostname(networkId))
		.appendPath("/v3.0/collection/")
		.appendPath(collectionId)
		.appendPath("/")
        .appendPath(eventId);
		return uriBuilder.toString();
	}
	
	/**
	 * Performs a long poll request to the Livefyre's stream endpoint
	 *
	 * @param networkId The collection's network as identified by domain, i.e. livefyre.com.
	 * @param siteId The Id of the article's site.
	 * @param articleId The Id of the collection's article.
	 * @param eventId The last eventId that was returned from either stream or
	 *        bootstrap. Event time a new eventId is returned, it should be used
	 *        in the next stream request.
	 * @param handler Response handler
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	public static void pollStreamEndpoint(String networkId, String collectionId, String eventId, JsonHttpResponseHandler handler) throws IOException, JSONException {
		String streamEndpoint = generateStreamUrl(networkId, collectionId, eventId);
        HttpClient.client.get(streamEndpoint, handler);
	}
}

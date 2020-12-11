
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadSnippet;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentSnippet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;

import java.util.*;
import java.io.*;


public class VideoComments {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = "";

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static List<String> getComments(String videoId)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        int page=0;
        String pageToken = "";
		YouTube youtubeService = getService();
        YouTube.CommentThreads.List request = youtubeService.commentThreads().list("snippet,replies");
        ArrayList<String> cmts  = new ArrayList<String>();
        while(page==0 || !(pageToken==null || pageToken.matches(""))){
            CommentThreadListResponse response = null;
            if(page==0){
                response = request.setKey(DEVELOPER_KEY).setVideoId(videoId).execute();
            }
            else{
                response = request.setKey(DEVELOPER_KEY).setPageToken(pageToken).setVideoId(videoId).execute();
            }
            if(response==null){
                System.out.println("BREAK : reponse is null.");
                break;
            }
            List<CommentThread> all_items = response.getItems();
            for(CommentThread item:all_items){
                CommentThreadSnippet cmtSnip = item.getSnippet();
                Comment cmt = cmtSnip.getTopLevelComment();
                CommentSnippet snip = cmt.getSnippet();
                String text = snip.getTextOriginal();
                System.out.println((cmts.size()+1)+"  "+text);
                cmts.add(text);
            }
            page++;
            pageToken = response.getNextPageToken();
        }
        System.out.println("Total page count:"+page);
        return cmts;

    }
}
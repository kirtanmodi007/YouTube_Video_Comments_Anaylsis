/**
 * Sample Java code for youtube.commentThreads.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoGetRatingResponse;
import com.google.api.services.youtube.model.VideoRating;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;

import java.util.*;
import java.net.*;
import java.io.*;

public class ApiExample {

    // static ServerSocket socket;
    // static{
    //     ServerSocket temp =null; 
    //     try{
    //         temp = new ServerSocket(6789); 
    //     }catch(Exception e){
    //         System.out.println("Error while Connecting to socker: "+e);
    //     }
    //     socket = temp;
    // }
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String CLIENT_SECRETS= "client_secret.json";
    private static final Collection<String> SCOPES =
        Arrays.asList("https://www.googleapis.com/auth/youtube");

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
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = ApiExample.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .build();
        Credential credential =
            new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        // YouTube youtubeService = getService();
        // Define and execute the API request
        ServerSocket socket = null;
        try{
            socket = new ServerSocket(6789);    
            // Chnage the hostname according to your Machins env variable
        } catch(Exception e){
            System.out.println("Error occured in Socket Server Creation: "+e);
        }
        BufferedReader br = null;
        DataOutputStream dos = null;
        while(true){

            Socket s = null;
            String str = "";
            System.out.println("Will accept the socket server:");
            try{ 
                // System.out.println("Socket status: "+s.isClosed());
                s = socket.accept(); 
                System.out.println("Socket status: "+s.isClosed());
                br = new BufferedReader(new InputStreamReader(s.getInputStream())); 
                str=br.readLine();
                System.out.println("Received URL: "+str);
            } catch(Exception e){
                System.out.println("Error while catching the URL send from Spring application: "+e);
            }

            int index = str.indexOf('=');
            String videoId = str.substring(index+1);
            System.out.println("VideoId : "+videoId);
            // Part for extracting comments from API
            VideoComments vc = new VideoComments();
            List<String> cmts = vc.getComments(videoId);


            /*  
                Below code gives ratting provided by authenticated user only, not anyone else: So no use
                But code can be used if OAuth 2.0 is used for some API call
            */
            // Video Ratting
            // System.out.println("retreiving the ratting of the video"+videoId);

            // YouTube.Videos.GetRating req_rate = youtubeService.videos().getRating(videoId);
            // VideoGetRatingResponse res_rate = req_rate.execute();
            // System.out.println(res_rate);
            // List<VideoRating> vr_item = res_rate.getItems();
            // dos.writeBytes(vr_item.get(0).getRating()+"\n");
            // System.out.println("send data: "+vr_item.get(0).getRating());


            try{
                dos = new DataOutputStream(s.getOutputStream());
                for(String x:cmts){
                    dos.writeBytes(x+"\n");
                }
                dos.writeBytes("done\n");
            }catch(Exception e){
                System.out.println("Error while sending fecthed data of youtube video : "+videoId+"\n"+e);
            }

        }
    }
}
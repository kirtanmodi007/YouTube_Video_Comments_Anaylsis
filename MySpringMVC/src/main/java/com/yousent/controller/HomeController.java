package com.yousent.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
 
import java.net.*;
import java.util.*;
import java.io.*;

import com.yousent.controller.ScriptPython;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}

	@RequestMapping(value="/trial")
	public String test_1(HttpServletResponse response) throws IOException{
		return "Nothing JUST Trial";
	}

	@RequestMapping(value="/output", method = RequestMethod.POST)
    public ModelAndView visitOutput(HttpServletRequest request) {
    	String url = request.getParameter("search");
	    
	    String comments = "";
        Socket s = null;
    	try{
        	s = new Socket("DESKTOP-S9QFA0K", 6789); 
        	// Chnage the hostname according to your Machins env variable
    	} catch(Exception e){
    		System.out.println("Error occured in Socket acceptance: "+e);
    	}
    	DataOutputStream dos = null;
    	try{
	        dos = new DataOutputStream(s.getOutputStream()); 
	        dos.writeBytes(url+"\n"); 
	    } catch(Exception e){
	    	int index = url.indexOf('=');
            String videoId = url.substring(index+1);
    		System.out.println("Error occured in Sending the URL: videoId:"+videoId+"\n"+e);
    	}
    	BufferedReader br = null;
    	try{
    		
	        br = new BufferedReader(new InputStreamReader(s.getInputStream())); 
	        	
	        System.out.println("Socket status: "+s.isClosed());
			String str = "";
			int index=0;
			while(!(str=br.readLine()).matches("done")){
				comments = comments + str + "\n";
				System.out.println((++index)+ ": "+str);
			}
	    }catch(Exception e){
	    	System.out.println("Error while reading comments from socket"+e);
	    }
	    /*
	    	Running Python script:
	    */
		ScriptPython scriptPython = new ScriptPython();
        String analysisOutput = scriptPython.runScript(comments);

        ModelAndView model = new ModelAndView("output");
        System.out.println();
        model.addObject("url", url);
        model.addObject("comments", analysisOutput);
        try{
	        dos.close();
		    br.close();
		    s.close();
		}catch(Exception e){
			System.out.println("Error while closing socket: "+e);
		}
        return model;
    }

}

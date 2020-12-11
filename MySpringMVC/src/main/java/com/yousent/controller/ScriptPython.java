package com.yousent.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScriptPython {

  public String runScript(String comments){
    Process process = null;
    String output = "Comment Analysis:<br>";
    try{
      process = Runtime.getRuntime().exec("python script_python.py "+comments);
      System.out.println("Exit value of Python script: "+process.exitValue());
    }catch(Exception e) {
      System.out.println("Exception Raised" + e.toString());
    }
    InputStream stdout = process.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

    InputStream stderr = process.getErrorStream();
    BufferedReader reader_err = new BufferedReader(new InputStreamReader(stderr));

    String line;
    try{
      System.out.println("OUTPUT:\n");
      while((line = reader.readLine()) != null){
        System.out.println("stdout: "+ line);
        output += line+"<br>";
      }
      System.out.println("ERROR:\n");
      while((line = reader_err.readLine()) != null){
        System.out.println("stderr: "+ line);
      }
    }catch(IOException e){
      System.out.println("Exception in reading output"+ e.toString());
    }
    return output;
  }
}
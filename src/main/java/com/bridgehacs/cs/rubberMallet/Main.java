package com.bridgehacs.cs.rubberMallet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.bridgehacs.cs.rubberMallet.testing.TopicModeler;
import com.bridgehacs.cs.rubberMallet.testing.Topic;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Main method, which executes all the functionality of the program.
 * @author cdluo
 */

public final class Main {

  public static void main(String[] args) throws IOException {
    new Main(args).run();
  }

  private String[] args;
  private static final Gson GSON = new Gson();
  private TopicModeler tm;
  

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException {
    runSparkServer();
  }

  ///////////////
  // Front End //
  ///////////////
  
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Runs the spark Server with 3 paths: 1 homepage, and 2 results pages.
   */
  private void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    FreeMarkerEngine freeMarker = createEngine();
    Spark.setPort(4567);

    // Setup Spark Routes
    Spark.get("/home", new FrontHandler(), freeMarker);
    Spark.post("/topics", new TopicHandler());
  }

  /**
   * Handles the homepage.
   * @return a modelandview object representing the homepage.
   * @author cdluo
   */
  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
          "rubberMallet");
      return new ModelAndView(variables, "query.ftl");
    }
  }
  
  /**
   * Handles sending the topics to the front end.
   * @author ChrisLuo
   */
  private class TopicHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      
      String fileJSON = qm.value("file");
      File file = new File(fileJSON);
      System.out.println(fileJSON + " exists? " + file.exists());
      tm = new TopicModeler(file);
      
      int numTops = Integer.parseInt(qm.value("num"));
      int accuracy = Integer.parseInt(qm.value("acu"));
      
      Object toReturn = null;
      try {
        toReturn = GSON.toJson(tm.getTopics(numTops, accuracy));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println(toReturn);
      return toReturn;
    }
  }
}

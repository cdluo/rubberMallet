package com.bridgehacs.cs.rubberMallet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.bridgehacs.cs.rubberMallet.testing.Testing;
import com.bridgehacs.cs.rubberMallet.testing.Topic;
import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
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

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException {
    File file = new File(args[0]);
    System.out.println(file.exists());
    
    Testing t = new Testing(args);
    ArrayList<Topic> topics = new ArrayList<Topic>();
    topics = t.getTopics(10,1);
    
    //Topics is an arraylist of Topics, which just have 5 fields for the strings.
    for(Topic top: topics){
      System.out.println(top.toString());
    }
    
//    runSparkServer();
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
}

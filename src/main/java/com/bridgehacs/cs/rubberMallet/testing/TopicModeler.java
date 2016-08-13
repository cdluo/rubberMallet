package com.bridgehacs.cs.rubberMallet.testing;

import cc.mallet.types.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class TopicModeler {

  private File file;
  
  public TopicModeler(File file1){
    file=file1;
  }
  
  /*
   * accuracy: 1: min, 2: med, 3: max
   */
  public ArrayList<Topic> getTopics(int numTopics, int accuracy) throws IOException{
    ///////////////////////////////////////////////////////////////
    // Example from: http://mallet.cs.umass.edu/topics-devel.php //
    ///////////////////////////////////////////////////////////////
    
    // Additional Notes by Chris Luo
    
    // The Pipes we'll be using to parse the data.
    ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

    // Pipes: lowercase, tokenize, remove stopwords, map to features
    pipeList.add( new CharSequenceLowercase() );
    pipeList.add( new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")) );
    pipeList.add( new TokenSequenceRemoveStopwords(new File("stoplists/en.txt"), "UTF-8", false, false, false) );
    pipeList.add( new TokenSequence2FeatureSequence() );

    // An Instance is the building block of MALLET. Used for training and testing ML algorithms.
    // Each Instance has 4 type generic fields:
    //    -Source: Human readable version of the instance
    //    -Data: Machine readable version of the instance
    //    -Name: Name of the instance
    //    -Target: Label associated with instance
    InstanceList instances = new InstanceList (new SerialPipes(pipeList));

    // Read in file (first argument after ./run)
    Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
    instances.addThruPipe(new CsvIterator (fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
                                           3, 2, 1)); // data, label, name fields

    // Create a model with 100 topics, alpha_t = 0.01, beta_w = 0.01
    //  Note that the first parameter is passed as the sum over topics, while
    //  the second is the parameter for a single dimension of the Dirichlet prior.
    
    // Basically ML algorithms. Read up on what they do so we can at least explain to judges.
    ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);

    // Once you've declared the type of model, you can add instances.
    model.addInstances(instances);

    //  Use two parallel samplers, which each look at one half the corpus and combine
    //  statistics after every iteration.
    model.setNumThreads(2);

    // Run the model for X iterations (Recommended 1000 iterations)
    
    // Accuracy Code added in (for speed, since 1000 iterations takes a long time.)
    int numIterations;
    if(accuracy == 1){
      numIterations = 100;
    }else if(accuracy == 2){
      numIterations = 500;
    }else{
      numIterations = 1000;
    }
    
    model.setNumIterations(numIterations);
    model.estimate();

    // Show the words and topics in the first instance

    // The data alphabet maps the data field from the instances to integers.
    Alphabet dataAlphabet = instances.getDataAlphabet();
    
    // A sequence that ensures that every object inside has the same class.
    FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
    LabelSequence topics = model.getData().get(0).topicSequence;
    
    //Printing the tokens and topics.
    Formatter out = new Formatter(new StringBuilder(), Locale.US);
    for (int position = 0; position < tokens.getLength(); position++) {
        out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
    }
    
//    System.out.println(out);
    
    // Estimate the topic distribution of the first instance, 
    //  given the current Gibbs state.
    double[] topicDistribution = model.getTopicProbabilities(0);

    // Get an array of sorted sets of word ID/count pairs
    ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
    
    // ArrayList of Topics to pass in
    ArrayList<Topic> topicList = new ArrayList<Topic>();
    String[] topicBuffer = new String[5];
    
    // Show top 5 words in topics with proportions for the first document
    for (int topic = 0; topic < numTopics; topic++) {
        Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
        
        out = new Formatter(new StringBuilder(), Locale.US);
        out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
            
            topicBuffer[rank] = dataAlphabet.lookupObject(idCountPair.getID()).toString();
//            System.out.println(idCountPair.getWeight());
            rank++;
        }
        Topic t = new Topic(topicBuffer[0],topicBuffer[1],topicBuffer[2],topicBuffer[3],topicBuffer[4]);
        topicList.add(t);
//        System.out.println(out);
    }
    
    ////////////////////////////
    // Topics are now loaded. //
    ////////////////////////////
    
    // Create a new instance with high probability of topic 0
    StringBuilder topicZeroText = new StringBuilder();
    Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();

    int rank = 0;
    while (iterator.hasNext() && rank < 5) {
        IDSorter idCountPair = iterator.next();
        topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
        rank++;
    }

    // Create a new instance named "test instance" with empty target and source fields.
    InstanceList testing = new InstanceList(instances.getPipe());
    testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

    TopicInferencer inferencer = model.getInferencer();
    double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
//    System.out.println("0\t" + testProbabilities[0]);
    
    return topicList;
  }

}


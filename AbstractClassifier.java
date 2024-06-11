package com.ML;


import weka.core.Attribute;
import weka.core.BatchPredictor;
import weka.core.Capabilities;
import weka.core.CapabilitiesHandler;
import weka.core.CapabilitiesIgnorer;
import weka.core.CommandlineRunnable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.RevisionHandler;
import weka.core.RevisionUtils;
import weka.core.SerializedObject;
import weka.core.Utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;


public abstract class AbstractClassifier implements Classifier, BatchPredictor,
  Cloneable, Serializable, OptionHandler, CapabilitiesHandler, RevisionHandler,
  CapabilitiesIgnorer, CommandlineRunnable {

 
  private static final long serialVersionUID = 6502780192411755341L;
  protected boolean m_Debug = false;
  protected boolean m_DoNotCheckCapabilities = false;
  public static int NUM_DECIMAL_PLACES_DEFAULT = 2;
  protected int m_numDecimalPlaces = NUM_DECIMAL_PLACES_DEFAULT;
  public static String BATCH_SIZE_DEFAULT = "100";
  protected String m_BatchSize = BATCH_SIZE_DEFAULT;

  public static Classifier forName(String classifierName, String[] options)
    throws Exception {

    return ((AbstractClassifier) Utils.forName(Classifier.class, classifierName,
      options));
  }

  public static Classifier makeCopy(Classifier model) throws Exception {

    return (Classifier) new SerializedObject(model).getObject();
  }

  public static Classifier[] makeCopies(Classifier model, int num)
    throws Exception {

    if (model == null) {
      throw new Exception("No model classifier set");
    }
    Classifier[] classifiers = new Classifier[num];
    SerializedObject so = new SerializedObject(model);
    for (int i = 0; i < classifiers.length; i++) {
      classifiers[i] = (Classifier) so.getObject();
    }
    return classifiers;
  }


  public static void runClassifier(Classifier classifier, String[] options) {
    try {
      if (classifier instanceof CommandlineRunnable) {
        ((CommandlineRunnable)classifier).preExecution();
      }
      System.out.println(Evaluation.evaluateModel(classifier, options));
    } catch (Exception e) {
      if (((e.getMessage() != null)
        && (e.getMessage().indexOf("General options") == -1))
        || (e.getMessage() == null)) {
        e.printStackTrace();
      } else {
        System.err.println(e.getMessage());
      }
    }
    if (classifier instanceof CommandlineRunnable) {
      try {
        ((CommandlineRunnable) classifier).postExecution();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }


  
  public double classifyInstance(Instance instance) throws Exception {

    double[] dist = distributionForInstance(instance);
    if (dist == null) {
      throw new Exception("Null distribution predicted");
    }
    switch (instance.classAttribute().type()) {
    case Attribute.NOMINAL:
      double max = 0;
      int maxIndex = 0;

      for (int i = 0; i < dist.length; i++) {
        if (dist[i] > max) {
          maxIndex = i;
          max = dist[i];
        }
      }
      if (max > 0) {
        return maxIndex;
      } else {
        return Utils.missingValue();
      }
    case Attribute.NUMERIC:
    case Attribute.DATE:
      return dist[0];
    default:
      return Utils.missingValue();
    }
  }



  public double[] distributionForInstance(Instance instance) throws Exception {

    double[] dist = new double[instance.numClasses()];
    switch (instance.classAttribute().type()) {
    case Attribute.NOMINAL:
      double classification = classifyInstance(instance);
      if (Utils.isMissingValue(classification)) {
        return dist;
      } else {
        dist[(int) classification] = classifyInstance(instance);
      }
      return dist;
    case Attribute.NUMERIC:
    case Attribute.DATE:
      dist[0] = classifyInstance(instance);
      return dist;
    default:
      return dist;
    }
  }


  @Override
  public Enumeration<Option> listOptions() {

    Vector<Option> newVector = Option
      .listOptionsForClassHierarchy(this.getClass(), AbstractClassifier.class);

    return newVector.elements();
  }

  /**
   * Gets the current settings of the Classifier.
   * 
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {

    Vector<String> options = new Vector<String>();
    for (String s : Option.getOptionsForHierarchy(this,
      AbstractClassifier.class)) {
      options.add(s);
    }

   
    return options.toArray(new String[0]);
  }

 
  @Override
  public void setOptions(String[] options) throws Exception {

    Option.setOptionsForHierarchy(options, this, AbstractClassifier.class);
    setDebug(Utils.getFlag("output-debug-info", options));
    setDoNotCheckCapabilities(
      Utils.getFlag("do-not-check-capabilities", options));

    String optionString = Utils.getOption("num-decimal-places", options);
    if (optionString.length() != 0) {
      setNumDecimalPlaces((new Integer(optionString)).intValue());
    }
    optionString = Utils.getOption("batch-size", options);
    if (optionString.length() != 0) {
      setBatchSize(optionString);
    }
    Utils.checkForRemainingOptions(options);
  }


  public boolean getDebug() {

    return m_Debug;
  }


  public void setDebug(boolean debug) {

    m_Debug = debug;
  }


  public String debugTipText() {
    return "If set to true, classifier may output additional info to "
      + "the console.";
  }


  @Override
  public boolean getDoNotCheckCapabilities() {

    return m_DoNotCheckCapabilities;
  }


  @Override
  public void setDoNotCheckCapabilities(boolean doNotCheckCapabilities) {

    m_DoNotCheckCapabilities = doNotCheckCapabilities;
  }


  public String doNotCheckCapabilitiesTipText() {
    return "If set, classifier capabilities are not checked before classifier is built"
      + " (Use with caution to reduce runtime).";
  }


  public String numDecimalPlacesTipText() {
    return "The number of decimal places to be used for the output of numbers in "
      + "the model.";
  }

 
  public int getNumDecimalPlaces() {
    return m_numDecimalPlaces;
  }


  public void setNumDecimalPlaces(int num) {
    m_numDecimalPlaces = num;
  }


  public String batchSizeTipText() {
    return "error";
  }


  @Override
  public void setBatchSize(String size) {
    m_BatchSize = size;
  }


  @Override
  public String getBatchSize() {
    return m_BatchSize;
  }


  @Override
  public boolean implementsMoreEfficientBatchPrediction() {
    return false;
  }


  @Override
  public double[][] distributionsForInstances(Instances batch)
    throws Exception {
    double[][] batchPreds = new double[batch.numInstances()][];
    for (int i = 0; i < batch.numInstances(); i++) {
      batchPreds[i] = distributionForInstance(batch.instance(i));
    }

    return batchPreds;
  }


  @Override
  public Capabilities getCapabilities() {
    Capabilities result = new Capabilities(this);
    result.enableAll();

    return result;
  }


  @Override
  public String getRevision() {
    return RevisionUtils.extract("$Revision$");
  }


  @Override
  public void preExecution() throws Exception {
  }


  @Override
  public void run(Object toRun, String[] options) throws Exception {
    if (!(toRun instanceof Classifier)) {
      throw new IllegalArgumentException("Object to run is not a Classifier!");
    }
    runClassifier((Classifier) toRun, options);
  }


  @Override
  public void postExecution() throws Exception {
  }
}

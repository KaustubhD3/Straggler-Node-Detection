package com.ML;
import com.connection.Dbconn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.MultiClassClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader;
import weka.core.WekaPackageManager;

public class ClassifierMLC {

    public int Execute(String train, String test) throws FileNotFoundException, IOException, Exception {
        // TODO code application logic hereInstances trainset=new Instances(new BufferedReader(new FileReader("C:/project/traindataset.arff")));

        int rval = 0;
        boolean T = false;
        try {
            Dbconn db = new Dbconn();
            Connection conn = (Connection) db.conn();
            rval = 1;
            if (true) {
                int correctlyPredicted[] = new int[11];
                ArffLoader al = new ArffLoader();
                al.setFile(new File(train));
                Instances inst = al.getDataSet();
                inst.setClassIndex(inst.numAttributes() - 1);

                ArffLoader alTest = new ArffLoader();
                alTest.setFile(new File(test));

                Instances instTest = alTest.getDataSet();
                instTest.setClassIndex(instTest.numAttributes() - 1);
                //instTest.setClassMissing();

                MultilayerPerceptron nn = null;

              //Load weka path
                              WekaPackageManager.loadPackages(false, true, false);
                              AbstractClassifier classifier = (AbstractClassifier) Class.forName(
                                      "weka.classifiers.functions.LibSVM").newInstance();

                              nn = new MultilayerPerceptron();
//                              nn.setLearningRate(0.2);
//                              nn.setMomentum(0.2);
//                              nn.setTrainingTime(2000);
////                              nn.setHiddenLayers("3");
//               nn.setHiddenLayers("2");
//                              L = Learning Rate
                              //M = Momentum 
//                              N = Training Time or Epochs 
                              //H = Hidden Layers etc
                                              nn.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a"));
                              nn.buildClassifier(instTest);
                T = false;
                Evaluation evaluation = new Evaluation(instTest);
                evaluation.crossValidateModel(classifier, instTest, 3, new Random(1));
                System.out.println(evaluation.toSummaryString());

                double correctClass = 0;
                double records = 0;
                int i = 1;
                //attributes.remove();
                for (i = 0; i < instTest.numInstances(); i++) {
                    records++;
                    double pred = 0, pred1 = 0, pred2 = 0, pred3 = 0, pred4 = 0, pred5 = 0, pred6 = 0, pred7 = 0;
                    int classArray[] = new int[11];

                    pred = nn.classifyInstance(instTest.instance(i));
                    classArray[(int) pred]++;

                    int maxClass = 0;
                    for (int k = 1; k < classArray.length; k++) {
                        if (classArray[k] > classArray[maxClass]) {
                            maxClass = k;
                        }
                    }

                    String actualClass = instTest.classAttribute().value((int) instTest.instance(i).classValue());
                    String predictedClass = instTest.classAttribute().value((int) pred);
                    System.out.println("Actual Class is :" + actualClass);
                    System.out.println("Predicted Class is :" + predictedClass);
                    Statement st = conn.createStatement();
                  //  st.executeUpdate("update testinglogdata set MLCclass='" + predictedClass + "' where id ='" + (records) + "'");
                    if (actualClass.equalsIgnoreCase(predictedClass)) {
                        correctlyPredicted[maxClass]++;
                        correctClass++;
                    }
                }

//                printMeasures(evaluation, T);
                System.out.println("No of records : " + records);
                System.out.println("Correctly classified : " + correctClass);
                System.out.println("Incorrectly classified : " + (records - correctClass));
                System.out.println("Accuracy : " + (correctClass / records) * 100);
                double ANNclass = (correctClass / (records) * 100);
               
                Statement st1 = conn.createStatement();
                st1.executeUpdate("update straggler_node_detection_accuracydata set accuracydata='" + ANNclass + "' where Classlabel='ANN'");

            } else {
                rval = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rval;
    }

}

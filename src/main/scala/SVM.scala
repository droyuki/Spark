/**
 * Created by droyuki on 2014/11/4.
 */


import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkContext, SparkConf}
/** Computes an approximation to pi */
object SVM {
  def main(args: Array[String]) {
    println("hi")
      val config = new SparkConf().setAppName("Test").setMaster("local[4]")
      val sc = new SparkContext(config)

      val svmData = MLUtils.loadLibSVMFile(sc, "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\sample_libsvm_data.txt").cache()

      // Train a DecisionTree model.
      //  Empty categoricalFeaturesInfo indicates all features are continuous.
      val numClasses = 2
      val categoricalFeaturesInfo = Map[Int, Int]()
      val impurity = "gini"
      val maxDepth = 5
      val maxBins = 100

      val model = DecisionTree.trainClassifier(svmData, numClasses, categoricalFeaturesInfo, impurity,
          maxDepth, maxBins)

      // Evaluate model on training instances and compute training error
      val labelAndPreds = svmData.map { point =>
          val prediction = model.predict(point.features)
          (point.label, prediction)
      }
//      val trainErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / svmData.count
//      println("Training Error = " + trainErr)
      println("Learned classification tree model:\n" + model)

  }
}

/*
// Split data into training (60%) and test (40%).
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)
    // Run training algorithm to build the model
    val numIterations = 100
    val model = SVMWithSGD.train(training, numIterations).setThreshold(1.0)
    // Clear the default threshold.
    //    model.clearThreshold()
    //    // Compute raw scores on the test set.
    val scoreAndLabels = test.map { point =>
      val score = model.predict(point.features)
      // println(score)
      (score, point.label)
    }
    scoreAndLabels.collect().foreach(println _)
      var predictZero = 0
      var predictOne = 0

      scoreAndLabels.collect().foreach{ t =>
          if (t._1 == 0.0){
              predictZero = predictZero + 1
          }
          if (t._1 == 1.0){
              predictOne = predictOne + 1
          }
      }

      print("0:")
      println(predictZero)
      print("1:")
      println(predictOne)
    context.stop()

*/
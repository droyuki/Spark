//import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.mllib.tree.DecisionTree
//import org.apache.spark.mllib.util.MLUtils
//import org.apache.spark.SparkContext
//import org.apache.spark.mllib.tree.DecisionTree
//import org.apache.spark.mllib.regression.LabeledPoint
//import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.tree.configuration.Algo._
//import org.apache.spark.mllib.tree.impurity.Gini
//
//object Test2{
//  def main(args: Array[String]): Unit = {
//    // Load and parse the data file.
//    // Cache the data since we will use it again to compute training error.
//    val conf = new SparkConf().setAppName("Test").setMaster("local[4]")
//    val context = new SparkContext(conf)
//    // Load and parse the data file
//
//    //val data = sc.textFile("C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\Data.csv")
//    val dataFiles = List("C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\Data_1.csv",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\Data_2.csv",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\Data_3.csv",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\Data_4.csv",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\Data_5.csv")
//
//
//    val svmModelsWithTestingData = for {
//      item <- dataFiles
//    } yield {
//      val data = context.textFile(item)
//      val parsedData = data.map { line =>
//        val parts = line.split(',').map(_.toDouble)
//        LabeledPoint(parts(0), Vectors.dense(parts.tail))
//      }
//      val maxDepth = 5
//      val model = DecisionTree.train(parsedData, Classification, Gini, maxDepth)
//       model
//    }
//
//
//    /*
//
//    val svmModelsWithTestingData = for {
//      item <- dataFiles
//    } yield {
//      val numClasses = 2
//      val categoricalFeaturesInfo = Map[Int, Int]()
//      val impurity = "gini"
//      val maxDepth = 5
//      val maxBins = 100
//
//      val model = DecisionTree.trainClassifier(data, numClasses, categoricalFeaturesInfo, impurity,
//        maxDepth, maxBins)
//      model
//
//    }
//
//    val modelWithTestingData = for {
//      item <- dataFiles
//    } yield {
//      val data = MLUtils.loadLibSVMFile(context, item)
////      val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
////      val training = splits(0).cache()
//      val test = splits(1)
//      val maxDepth = 5
//      val numIterations = 100
//
//      val parsedData = data.map { line =>
//        val parts = line.split(',').map(_.toDouble)
//        LabeledPoint(parts(0), Vectors.dense(parts.tail))
//      }
//
//      val model = DecisionTree.train(parsedData, Classification, Gini, maxDepth)
//      (model, test)
//    }
//
//    */
////    val parsedData = data.map { line =>
////      val parts = line.split(',').map(_.toDouble)
////      LabeledPoint(parts(0), Vectors.dense(parts.tail))
////    }
////
////    // Run training algorithm to build the model
////    val maxDepth = 5
////    val model = DecisionTree.train(parsedData, Classification, Gini, maxDepth)
//////    val model2 = LogisticRegressionModel
////
////    // Evaluate model on training examples and compute training error
////    val labelAndPreds = parsedData.map { point =>
////      val prediction = model.predict(point.features)
////      (point.label, prediction)
////    }
////    val trainErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / parsedData.count
////    println("Training Error = " + trainErr)
//  }
//}
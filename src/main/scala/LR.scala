///**
//* Created by droyuki on 2014/11/4.
//*/
//
//import org.apache.spark.{SparkContext, _}
//import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
//import org.apache.spark.mllib.util.MLUtils
//object LR {
//  def main(args: Array[String]) {
//    println("hi~LR")
//    val conf = new SparkConf().setAppName("LR").setMaster("local[4]")
//    val sc = new SparkContext(conf)
//    val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\svmtest.txt")
////    // Split data into training (60%) and test (40%).
////    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
////    val training = splits(0).cache()
////    val test = splits(1)
////
////    // Run training algorithm to build the model
////    val numIterations = 100
////    val model = LogisticRegressionWithSGD.train(training, numIterations)
////    println(model)
////    // Clear the default threshold.
////    //model.clearThreshold()
//
//    val svmFiles = List(
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\SVM1.txt",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\SVM2.txt",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\SVM3.txt",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\SVM4.txt",
//      "C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\SVM5.txt"
//    )
//
//
//    val modelsWithTestingData = for {
//      item <- svmFiles
//    } yield {
//      val data = MLUtils.loadLibSVMFile(sc, item)
//      //val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
//      val training = item.cache()
//      val test = splits(1)
//      val numIterations = 100
//      val model = LogisticRegressionWithSGD.train(training, numIterations)
//     // print(model)
//      (model, test)
//    }
//
//    //val calculateSvmModelWithContext = getSVMModels(sc) _
//    //val svmModelsWithTestingData = svmFiles.map(calculateSvmModelWithContext)
//    val (model, test) = modelsWithTestingData(0)
//    println("QQQ")
//    val m = modelsWithTestingData(0)._1
//    println(m)
//    val scoreAndLabels = test.map { point =>
//      val score = model.predict(point.features)
//      //println(score)
//      (score, point.label)
//    }.collect()
//    //print(model)
//    //println(scoreAndLabels)
//
//
///*
//    // Compute raw scores on the test set.
//    val scoreAndLabels = test.map { point =>
//      val score = model.predict(point.features)
//      (score, point.label)
//    }
//
//    // Get evaluation metrics.
//    val metrics = new BinaryClassificationMetrics(scoreAndLabels)
//    val auROC = metrics.areaUnderROC()
//
//    println("Area under ROC = " + auROC)
//    */
//  }
//}
//

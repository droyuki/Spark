import java.io._

import com.google.gson.Gson
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithSGD, SVMWithSGD}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}
object LRModel {
    def main(args: Array[String]): Unit = {
        val config = new SparkConf()
            .setMaster("local[4]")
            .setAppName("SparkPi")
        val context = new SparkContext(config)
        val models = createModels(context)

//        val jsonFiles = modelsToJson(models)
//
//        val svmModels = jsonToModels(jsonFiles)
        val testData = "C:\\Users\\droyuki\\Desktop\\DATA\\predict.txt"
        predictData(models, testData, context)
    }
    //create LR Model
    def createModels(context:SparkContext): List[LogisticRegressionModel] =  {
//        var configMap: Map[String, String] = Map()
//        configMap += ("minerID" -> "103356011")
//        configMap += ("k" -> "10") //unused
//        configMap += ("d" -> "10") //unused
//        configMap += ("percentage" -> "5")
//        configMap += ("region" -> "taiwan")
//        configMap += ("market" -> "future")
//        configMap += ("symbolID" -> "TXF5")
//        configMap += ("timeGranularity" -> "1s")
//        configMap += ("stateTag" -> "1005/2005/3005/4005/5005/")
//        configMap += ("beginDate" -> "20150106122041")
//        configMap += ("endDate" -> "20150106125958")
//        configMap += ("ratio" -> "11003")
//        //        configMap += ("group" -> "") unused
//        configMap += ("preProcType" -> "RatioRanSubSet") //RandomSubSet, RatioSubSet, RatioRanSubSet
//        configMap += ("MiningType" -> "SVM")
//        //        configMap += ("miningConfig" -> "") unused
//
//        var minerConfig = new MinerConfig(configMap)
//
//        val dataSet = PreProcManager.excute(minerConfig)
        val dataSet = List( "C:\\Users\\droyuki\\Desktop\\DATA\\1.txt",
    "C:\\Users\\droyuki\\Desktop\\DATA\\2.txt",
    "C:\\Users\\droyuki\\Desktop\\DATA\\3.txt",
    "C:\\Users\\droyuki\\Desktop\\DATA\\4.txt",
    "C:\\Users\\droyuki\\Desktop\\DATA\\5.txt",
    "C:\\Users\\droyuki\\Desktop\\DATA\\6.txt",
    "C:\\Users\\droyuki\\Desktop\\DATA\\7.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\8.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\9.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\10.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\11.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\12.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\13.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\14.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\15.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\16.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\17.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\18.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\19.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\20.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\21.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\22.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\23.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\24.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\25.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\26.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\27.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\28.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\29.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\30.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\31.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\32.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\33.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\34.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\35.txt")
        dataSet.foreach(t=>println(t))
        var i = 0
        val LRModels = for { item <- dataSet } yield {
            val data = MLUtils.loadLibSVMFile(context, item).cache()
            val numIterations = 100
            val model = LogisticRegressionWithSGD.train(data, numIterations)
            val ss = SVMWithSGD
            model.clearThreshold()
            val scoreAndLabels = data.map { point =>
                val score = model.predict(point.features)
                println("SCORE: "+score+" Label: "+point.label)
                (score, point.label)
            }
            val predictMaxScore = scoreAndLabels.max()
            val predictMinScore = scoreAndLabels.min()
//            model.setThreshold(predictMinScore._1+(predictMaxScore._1+predictMinScore._1)*0.9)
            println("Weight: "+model.weights+"Intercept: "+model.intercept)
//            val jsonLRModel = new JsonLRModelConvertor()
//            jsonLRModel.setIntercept(model.intercept)
//            jsonLRModel.setWeights(model.weights.toArray)
//            jsonLRModel.setMax(predictMaxScore._1)
//            jsonLRModel.setMin(predictMinScore._1)
//            i = i +1
//            jsonLRModel
            model
        }
        LRModels
    }
    //translate LR model to json file
    def modelsToJson(jsonLRModel:List[JsonLRModelConvertor]): List[String] = {
        val gSon = new Gson()
        var i = 0
        val fileLocations = for { item <- jsonLRModel } yield {
            val jSon = gSon.toJson(item)
            val fileLocation= "C:\\Users\\droyuki\\Desktop\\models\\LR_Json"+ i +".txt"
            val writer = new PrintWriter(new File(fileLocation))
            writer.write(jSon)
            writer.close()
            i = i + 1
            fileLocation
        }
        fileLocations
    }
    // json to model

    def jsonToModels(jsonFile:List[String]): List[LogisticRegressionModel] ={
        val gSon = new Gson()
        val LRModels = for {item <- jsonFile} yield {
            val fr = new FileReader(item)
            val br = new BufferedReader(fr)
            val json = br.readLine()
            val LRModel = gSon.fromJson(json, classOf[JsonLRModelConvertor])
            LRModel.getModelFromCurrentData
        }
        for(i <- LRModels){
            println("I!!: "+i.intercept)
            println("W: "+i.weights)
        }
        LRModels
    }

    def predictData(LRModels:List[LogisticRegressionModel], testData:String, context:SparkContext): Unit ={
        val data = MLUtils.loadLibSVMFile(context, testData).cache()
        val scoreAndLabels = data.map { point =>
            val predicts = for { model <- LRModels } yield {
                val score = model.predict(point.features)
                score
            }

            var result = -1
            var oo = predicts.filter(t => t == 1.0).size
            var zz = predicts.filter(t => t == 0.0).size
            var mo = predicts.filter(t => t != 0.0 && t != 1.0).size
            if(oo > predicts.length * 0.6)
                result = 1
            else if( zz > predicts.length * 0.6)
                result = 0
//            if(predicts.forall(t => t == 0.0)){
//                result = 0
//            }else if(predicts.forall(t=> t == 1.0)){
//                result = 1
//            }else{
//                result = -1
//            }
            //      var size = predicts.filter(t=> t==1.0).size
            //
            //      if(size >= 4){
            //        result = 1
            //      }else{
            //        result = 0
            //      }
            println((result, point.label, predicts))
            //預測結果, 原本
            (result, point.label, predicts)
        }
        var tp = 0
        var fp = 0
        var tn = 0
        var fn = 0
        var minusOne=0
        var zero = 0
        var one = 0
        scoreAndLabels.collect().foreach{ t =>
            if (t._1 == 1 && t._2 == 1){
                tp = tp + 1
            }
            if (t._1 == 1 && t._2 == 0) {
                fp = fp + 1
            }
            if (t._1 == 0 && t._2 == 0){
                tn = tn + 1
            }
            if (t._1 == 0 && t._2 == 1){
                fn = fn + 1
            }
            if (t._1 == -1){
                minusOne = minusOne + 1
            }
            if(t._1 == 0){
                zero = zero+1
            }
            if(t._1 ==1){
                one = one +1
            }
        }
        //    scoreAndLabels.foreach(t=>println(t._1, t._2, t._3))
        println("tp:"+tp)
        println("fp:"+fp)
        println("tn:"+tn)
        println("fn:"+fn)
        println("zero:"+zero)
        println("one:"+one)
        println("minusOne:"+minusOne)
    }
}
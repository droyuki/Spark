package mypackage
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.rdd.RDD

/**
 * Created by WeiChen on 2014/11/4.
 */

object Classification_Test {
    def main(args: Array[String]): Unit = {
        val model = new Models
        val result = model.predict()
        //Array[Double, List(Double)]
        printResult(result)

        val conf = new SparkConf().setAppName("Test").setMaster("local[4]")
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        conf.set("spark.kryo.registrator", "mypackage.MyRegistrator")
        val sc = new SparkContext(conf)
    }
    def vote(a:Any)= a match{
        case List(1,1,1,1,1) => 1
        case List(0,0,0,0,0) => 0
        case _ => -1
    }
    def printResult(result:RDD[(Double, List[Double])]): Unit ={
        val trainPredict = result.collect().map{
            m => (m._1.toDouble,m._2)
                (m._1.toDouble,m._2)
        }
        //        result
        //Array[List(Double)]
        val voteList = trainPredict.map(x => x._2)
        val retMe = voteList.map{ l =>
            val res = vote(l)
            res
        }
        val lastResult = trainPredict.map{ x=>
            (x._1,x._2,retMe(trainPredict.indexOf(x)))
        }
        lastResult.foreach(l => println(l))
        val fp = lastResult.filter(t=> t._1==0 && t._3==1).size
        val tp = lastResult.filter(f => f._1 == 1 && f._3 == 1).size
        val tf = lastResult.filter(tf => tf._1 == 1 && tf._3 == 0).size
        println("TP:" + tp)
        println("FP: " + fp)
        println("TF " +tf)
    }
}


















/*
val config = new SparkConf()
  .setMaster("local[4]")
  .setAppName("SparkPi")
//                 .setJars(Array("/Users/heisenberg/Program/HelloSpark/target/scala-2.10/hellospark_2.10-1.0.jar"))
val context = new SparkContext(config)
val svmFiles = List(
  "/Users/heisenberg/Program/HelloSpark/src/main/resources/raw",
  "/Users/heisenberg/Program/HelloSpark/src/main/resources/SVM1.txt",
  "/Users/heisenberg/Program/HelloSpark/src/main/resources/SVM2.txt",
  "/Users/heisenberg/Program/HelloSpark/src/main/resources/SVM3.txt",
  "/Users/heisenberg/Program/HelloSpark/src/main/resources/SVM4.txt",
  "/Users/heisenberg/Program/HelloSpark/src/main/resources/SVM5.txt"
)

val calculateSvmModelWithContext = getSVMModels(context) _
val svmModelsWithTestingData = svmFiles.map(calculateSvmModelWithContext)
val (model, test) = svmModelsWithTestingData(0)
val scoreAndLabels = test.map { point =>
  val score = model.predict(point.features)
  println(score)
  (score, point.label)
}.collect()
*/


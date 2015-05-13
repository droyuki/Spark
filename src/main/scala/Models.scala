package mypackage

import java.io.ByteArrayOutputStream

import com.twitter.chill.{Input, Output, ScalaKryoInstantiator}
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}
/**
 * Created by WeiChen on 2014/11/11.
 */
class Models {
    val conf = new SparkConf().setAppName("Test").setMaster("local[4]")
//    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//    conf.set("spark.kryo.registrator", "mypackage.MyRegistrator")
    val sc = new SparkContext(conf)
    val dataSet = List( //"C:\\Users\\droyuki\\Desktop\\DATA\\1.txt",
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
        "C:\\Users\\droyuki\\Desktop\\DATA\\35.txt",
        "C:\\Users\\droyuki\\Desktop\\DATA\\predict.txt")

    def modelGenerator()={
        val instantiator = new ScalaKryoInstantiator
        instantiator.setRegistrationRequired(false)
        val kryo = instantiator.newKryo()
        val baos = new ByteArrayOutputStream
        val output = new Output(baos, 4096)
        var i = 0

        val models = for {
            item <- dataSet
        } yield {
            val parsedData = MLUtils.loadLibSVMFile(sc, item).cache()
//            val data = sc.textFile(item)
//            val parsedData = data.map { line =>
//                val parts = line.split(',').map(_.toDouble)
//                LabeledPoint(parts(0), Vectors.dense(parts.tail))
//            }
            val numClasses = 2
            val categoricalFeaturesInfo = Map[Int, Int]()
            val impurity = "gini"
            val maxDepth = 10
            val maxBins = 100
            val model = DecisionTree.trainClassifier(parsedData, numClasses, categoricalFeaturesInfo, impurity,
                maxDepth, maxBins)
            //val model = DecisionTree.train(parsedData, Classification,Gini, maxDepth)
            println(item)
            println(model.toString())
            kryo.writeObject(output, model)
            println(kryo)
            val out = new java.io.FileWriter("C:\\Users\\droyuki\\Desktop\\models\\result"+ i +".txt")
            out.write(baos.toString)
            i = i +1
            val input = new Input(baos.toByteArray)
            val deser = kryo.readObject(input, classOf[List[Models]])
//            assert(deser.size == 3000)
//            print("This is MMMMOOOODDDEELLLL!!!!!"+deser)

            model
        }
        models
    }
    def predict()={
        println("Start predict")
        val models = this.modelGenerator()
        val parsedData = MLUtils.loadLibSVMFile(sc, "C:\\Users\\droyuki\\Desktop\\DATA\\predict.txt").cache()
//        val data = sc.textFile("C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\run.csv")
//        val parsedData = data.map { line =>
//            val parts = line.split(',').map(_.toDouble)
//            LabeledPoint(parts(0), Vectors.dense(parts.tail))
//        }

        //[lable point,List[predicts]]
//        val result = parsedData.map { point =>
//                val prediction:List[Double] = models.map { model =>
//                    model.predict(point.features)
//                }
//            (point.label, prediction)
//        }
        val result = parsedData.map{ point =>
            val prediction:List[Double] = models.map{ model=>
                model.predict(point.features)
            }
            (point.label,prediction)
        }

        //        //List[List[predicts]]
        //        val vote = result.map(r => r._2.toList)
        //
        //        //
        //        //        voteRes.p
        //        val voteRes = voteM(vote)
        //        println(voteRes)
        result
    }
}










/*
def modelGenerator()={
        // Load and parse the data file
        val models = for {
            item <- dataSet
        } yield {
            val data = sc.textFile(item)
            val parsedData = data.map { line =>
                val parts = line.split(',').map(_.toDouble)
                LabeledPoint(parts(0), Vectors.dense(parts.tail))
            }
            val maxDepth = 5
            val model = DecisionTree.train(parsedData, Classification, Gini, maxDepth)
            model
        }
        models
    }
*/

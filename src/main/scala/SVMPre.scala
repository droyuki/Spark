package mypackage
import java.io.{File, FileWriter}

import scala.io.Source
object SVMPre {
    def main(args: Array[String]) {
        val SVM = "SVMrun"
        val fw = new FileWriter(new File("C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\" + SVM + ".txt"))
        val f = Source.fromFile("C:\\Users\\droyuki\\Desktop\\Spark_Example\\src\\main\\resources\\run.csv").getLines()
        f.foreach(f => {
            val rawData = f.split(",")
            //10
            val result = rawData(4)
            val data = rawData.slice(10, rawData.size)
            fw.write(result + " ")
            data.zipWithIndex.toList.foreach(g =>{
                val (c,i) = g
                val t = i + 1
                fw.write(t + ":" + c + " ")
            })
            fw.write("\n")
        })
    }
}

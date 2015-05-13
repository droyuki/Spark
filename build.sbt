
name := "Spark_Example"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
    "com.twitter" %% "chill" % "0.3.6"withSources() withJavadoc(),
    "org.apache.spark" %% "spark-core" % "1.1.0"withSources() withJavadoc(),
    "org.apache.hadoop" % "hadoop-client" % "2.4.1",
    "org.apache.spark" % "spark-mllib_2.10" % "1.1.0"withSources() withJavadoc()
)

//baseAssemblySettings

//assemblyMergeStrategy in assembly := {
//    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
//    case x =>
//        val oldStrategy = (assemblyMergeStrategy in assembly).value
//        oldStrategy(x)
//}
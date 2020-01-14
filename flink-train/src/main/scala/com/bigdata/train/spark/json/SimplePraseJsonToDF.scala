package com.bigdata.train.spark.json

import com.google.gson.Gson
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * Description  简洁方式解析json
  * Created with guoshuai 
  * date 2020/1/14 13:33
  **/

case class User(id: String, name: String, pic: String, tdate: String)

object SimplePraseJsonToDF {

    final val jsonStr = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""

    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
          .builder()
          .appName("SimplePraseJsonToDF")
          .enableHiveSupport()
          .getOrCreate()

        process(spark)
        spark.stop()
    }


    def process(spark: SparkSession): Unit = {
        val gson = new Gson()
        val users: User = gson.fromJson(jsonStr, classOf[User])

        //实现1
        val df1 = spark.createDataFrame(Seq(users))
        df1.printSchema()
        df1.show(1)

        //实现2
        val arrayBuffer = new ArrayBuffer[Row]()
        val rddRow = Row(users.id, users.name, users.pic, users.tdate)
        arrayBuffer+= rddRow

        //序列化 parallelize(seq,list)
        val rdd: RDD[Row] = spark.sparkContext.parallelize(arrayBuffer)
        val colSchema = StructType(
            Seq(
                StructField("id", StringType, true),
                StructField("name", StringType, true),
                StructField("pic", StringType, true),
                StructField("tdate", StringType, true)
            )
        )
        val df2 = spark.createDataFrame(rdd, colSchema)
        df2.printSchema()
        df2.show()
    }
}

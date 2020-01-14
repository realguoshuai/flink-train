package com.bigdata.train.spark.json

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * 将json格式的数据解析成dateframe
  */
object ggg {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession
          .builder()
          .appName("test")
          .enableHiveSupport()
          .getOrCreate()

        import spark.implicits._
        val str: String = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""
        val jsonStr = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""
        val jsonStr1 = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""
        val jsonStr2 = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""
        val jsonStr3 = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""
        val jsonStr4 = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""

        val ab = new ArrayBuffer[Row]()
        ab += Row(str) += Row(jsonStr) += Row(jsonStr1) += Row(jsonStr2) += Row(jsonStr3) += Row(jsonStr4)
        val rddRow: RDD[Row] = spark.sparkContext.parallelize(ab)
        val schema = StructType(
            Seq(
                StructField("text", StringType, true)
            )
        )

        val colSchema = StructType(
            Seq(
                StructField("id", StringType, true),
                StructField("name", StringType, true),
                StructField("pic", StringType, true),
                StructField("tdate", StringType, true)
            )
        )
        val df: DataFrame = spark.createDataFrame(rddRow, schema)
        val aaa = df.withColumn("spalitJson", from_json(col("text"), colSchema))
          .drop("text")
          .map(m => {
              val spalitStr = m.get(0).toString.split("\\[|\\]")(1).split(",")
              (spalitStr(0), spalitStr(1), spalitStr(2), spalitStr(3))
          }).toDF(colSchema.fieldNames(0), colSchema.fieldNames(1), colSchema.fieldNames(2), colSchema.fieldNames(3))

        println("**************" + colSchema.fieldNames.mkString(","))
        aaa.show(10, false)
    }
}

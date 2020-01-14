package com.bigdata.train.spark.json

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
/**
  * Description  将json数据解析成DataFrame
  *     since Spark 2.0
  *
  * Created with guoshuai
  * date 2020/1/14 9:10
  **/
object ParseJsonToDf {

    final val jsonStr = """{"id" : "10001", "name" : "张三","pic" : "aaa","tdate" : "2020-01-01"}"""

    def main(args: Array[String]): Unit = {
        val spark: SparkSession = SparkSession
          .builder()
          .appName("ParseJsonToDf")
          .enableHiveSupport()
          .getOrCreate()

        process(spark)
        spark.stop()
    }

    def process(spark: SparkSession): Unit = {
        //获取数据
        val strings: Seq[String] = Seq(jsonStr)
        //先转成rdd
        val rddStr: RDD[String] = spark.sparkContext.parallelize(strings)
        //转成rdd row()
        val rowRow: RDD[Row] = rddStr.map { x => Row(x) }

        //通过StructType为字段添加Schema
        val schema = StructType(Seq(StructField("overview", DataTypes.StringType, true)))

        //通过StructType为字段添加Schema
        val colSchema = StructType(Seq(StructField("id", DataTypes.StringType, true)
            , StructField("name", DataTypes.StringType, true)
            , StructField("pic", DataTypes.StringType, true)
            , StructField("tdate", DataTypes.StringType, true)
        ))
        //创建Schema
        val df: DataFrame = spark.createDataFrame(rowRow, schema)
        df.printSchema()
        df.show(1)
        /*root
        |-- overview: string (nullable = true)
        +--------------------+
        |            overview|
        +--------------------+
        |{"id" : "10001", ...|
        +--------------------+*/

        // 解析schema中 一行的json
        val dataFrame = df.withColumn("splitJson", from_json(col("overview"), colSchema))
        dataFrame.printSchema()
        dataFrame.show(1)
        /*root
        |-- overview: string (nullable = true)
        |-- splitJson: struct (nullable = true)
        |    |-- id: string (nullable = true)
        |    |-- name: string (nullable = true)
        |    |-- pic: string (nullable = true)
        |    |-- tdate: string (nullable = true)

        +--------------------+--------------------+
        |            overview|           splitJson|
        +--------------------+--------------------+
        |{"id" : "10001", ...|[10001,张三,aaa,202..|
        +--------------------+--------------------+*/

        //DataFrame 转成 DataSet 对数据进行处理
        val dfToTuple: Dataset[(String, String, String, String)] = dataFrame.map {
            x => {
                // | 或,先将数据拆分到数组中 split("\\[|\\]")(1)就是取[]中间的数据
                val splits: Array[String] = x.get(1).toString.split("\\[|\\]")(1).split(",")
                (splits(0), splits(1), splits(2), splits(3))
            }
        }
        println(dfToTuple)
        /*
            [_1: string, _2: string, _3: string, _4: string]
        */

        //DataSet转成最终的DataFrame
        val result:DataFrame = dfToTuple.toDF(colSchema.fieldNames(0)
            ,colSchema.fieldNames(1),colSchema.fieldNames(2),colSchema.fieldNames(3))
        result.printSchema()
        result.show(1)
        /*
        +-----+----+---+----------+
        |id   |name|pic|   tdate  |
        +-----+----+---+----------+
        |10001| 张三|aaa|2020-01-01|
        +-----+----+---+----------+
        * */
    }

}

package com.zhiyou.bd20

import org.apache.spark.sql.SparkSession
case class Student(sno:Int,name:String,age:Int,address:String)
object DfDs {
  val spark = SparkSession.builder().master("local[*]")
    .appName("datafram dataset").config("spark.sql.warehouse.dir","/spark/warehouse").getOrCreate()


  import spark.sql
  import spark.implicits._
  def createDataFrame()={
    val list = List(("张三",23,"河南"),("李四",23,"河北"),("王五",23,"北京"),("赵六",23,"上海"))
    val rdd= spark.sparkContext.parallelize(list)
    val df = rdd.toDF("name","age","address")
    df.createOrReplaceTempView("person")
    val result = sql(
//      select name from person
      """
         |select _2 from person
      """.stripMargin
    )
    result.show()
    df.printSchema()
    spark.stop()
  }
  def createDataset()= {
    val list = List(("张三", 23, "河南"), ("李四", 23, "河北"), ("王五", 23, "北京"), ("赵六", 23, "上海"))
    val rdd = spark.sparkContext.parallelize(list)
    val ds = rdd.toDS()
    ds.printSchema()
  }
  def createDateset1()={
    val list=List(Student(1,"jim",22,"usa"),Student(2,"tom",22,"landon"),Student(3,"cater",22,"china"))
    val rdd = spark.sparkContext.parallelize(list)
    val ds = rdd.toDS()
    ds.printSchema()
  }

  def main(args: Array[String]): Unit = {
//    createDataFrame()
    createDataset()
    createDateset1()
  }
}

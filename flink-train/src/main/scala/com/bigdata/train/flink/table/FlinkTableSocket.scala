package com.bigdata.train.flink.table

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.TableEnvironment
import org.apache.flink.table.api.scala._
import scala.collection.mutable
import org.apache.flink.api.scala.createTypeInformation
/**
  * Description 接入Socket 模拟数据输出 实现wordcount
  *     since 1.7.0
  * Created with guoshuai
  * date 2020/01/09 14:40
  * */
object FlinkTableSocket {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tableEnv = TableEnvironment.getTableEnvironment(env)

        val tData = Seq("Flink", "Solr", "ES", "Hadoop", "Spark", "Flink", "Spark")

        // val dataSource = env.fromCollection(tData).toTable(tableEnv, 'word) //toTable 需要添加org.apache.flink.table.api.scala._ 包

        // 接入实时的有问题
        val dataSource = env.socketTextStream("localhost",8001).toTable(tableEnv,'word)

        //统计wordcount 核心代码 没有水印/窗口  很简洁
        val result = dataSource
          .groupBy('word)
          .select('word, 'word.count)

        //自定义sink
        val sink = new RetractSink
        result.toRetractStream[(String,Long)].addSink(sink)

        env.execute("flink table train demo")
    }

    //自定义sink 对数据进行初里
    class RetractSink extends RichSinkFunction[(Boolean, (String, Long))] {
        private var resultSet:mutable.Set[(String,Long)] = _ //_ 代表某种类型的初始值
        override def open(parameters: Configuration): Unit = {
            //初始化内存存储结构
            resultSet = new mutable.HashSet[(String, Long)]
        }
        //调用
        override def invoke(value: (Boolean, (String, Long)), context: SinkFunction.Context[_]): Unit = {
            if(value._1){
                println("add: "+value._1+"  "+value._2.toString())
                //计算数据
                resultSet.add(value._2)
            } else{
                //撤回数据 ???
                println("remove: "+value._1+" "+value._2.toString())
                resultSet.remove(value._2)
            }
        }

        override def close(): Unit = {
            //打印 结果
            resultSet.foreach(println)
        }

    }

}

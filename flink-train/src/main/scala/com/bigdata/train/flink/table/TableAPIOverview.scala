package com.bigdata.train.flink.table

import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.runtime.state.StateBackend
import org.apache.flink.runtime.state.memory.MemoryStateBackend
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{Table, TableEnvironment}
import org.apache.flink.types.Row
import org.junit.rules.TemporaryFolder
import org.junit.{Rule, Test}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Description 
  * Created with guoshuai 
  * date 2020/1/10 10:02
  **/
class TableAPIOverview {

    // 客户表数据 customer_tab (客户id，客户姓名和客户描述信息)
    val customer_data = new mutable.MutableList[(String, String, String)]
    customer_data.+=(("c_001", "Kevin", "from JinLin"))
    customer_data.+=(("c_002", "Sunny", "from JinLin"))
    customer_data.+=(("c_003", "JinCheng", "from HeBei"))


    // 订单表数据 order_tab (订单id，客户id,订单时间和订单描述信息)
    val order_data = new mutable.MutableList[(String, String, String, String)]
    order_data.+=(("o_001", "c_002", "2018-11-05 10:01:01", "iphone"))
    order_data.+=(("o_002", "c_001", "2018-11-05 10:01:55", "ipad"))
    order_data.+=(("o_003", "c_001", "2018-11-05 10:03:44", "flink book"))

    // 商品销售表数据 Item_tab (商品id，商品类型，出售时间，价格)
    val item_data = Seq(
        Left((1510365660000L, (1510365660000L, 20, "ITEM001", "Electronic"))),
        Right((1510365660000L)),
        Left((1510365720000L, (1510365720000L, 50, "ITEM002", "Electronic"))),
        Right((1510365720000L)),
        Left((1510365780000L, (1510365780000L, 30, "ITEM003", "Electronic"))),
        Left((1510365780000L, (1510365780000L, 60, "ITEM004", "Electronic"))),
        Right((1510365780000L)),
        Left((1510365900000L, (1510365900000L, 40, "ITEM005", "Electronic"))),
        Right((1510365900000L)),
        Left((1510365960000L, (1510365960000L, 20, "ITEM006", "Electronic"))),
        Right((1510365960000L)),
        Left((1510366020000L, (1510366020000L, 70, "ITEM007", "Electronic"))),
        Right((1510366020000L)),
        Left((1510366080000L, (1510366080000L, 20, "ITEM008", "Clothes"))),
        Right((151036608000L)))

    // 页面访问表数据 PageAccess_tab (用户ID，访问时间，用户所在地域)
    val pageAccess_data = Seq(
        Left((1510365660000L, (1510365660000L, "ShangHai", "U0010"))),
        Right((1510365660000L)),
        Left((1510365660000L, (1510365660000L, "BeiJing", "U1001"))),
        Right((1510365660000L)),
        Left((1510366200000L, (1510366200000L, "BeiJing", "U2032"))),
        Right((1510366200000L)),
        Left((1510366260000L, (1510366260000L, "BeiJing", "U1100"))),
        Right((1510366260000L)),
        Left((1510373400000L, (1510373400000L, "ShangHai", "U0011"))),
        Right((1510373400000L)))

    // 页面访问量表数据2 PageAccessCount_tab (访问量，访问时间，用户所在地)
    val pageAccessCount_data = Seq(
        Left((1510365660000L, (1510365660000L, "ShangHai", 100))),
        Right((1510365660000L)),
        Left((1510365660000L, (1510365660000L, "BeiJing", 86))),
        Right((1510365660000L)),
        Left((1510365960000L, (1510365960000L, "BeiJing", 210))),
        Right((1510366200000L)),
        Left((1510366200000L, (1510366200000L, "BeiJing", 33))),
        Right((1510366200000L)),
        Left((1510373400000L, (1510373400000L, "ShangHai", 129))),
        Right((1510373400000L)))

    // 页面访问表数据3 PageAccessSession_tab (访问量，访问时间，用户所在地域信息)
    val pageAccessSession_data = Seq(
        Left((1510365660000L, (1510365660000L, "ShangHai", "U0011"))),
        Right((1510365660000L)),
        Left((1510365720000L, (1510365720000L, "ShangHai", "U0012"))),
        Right((1510365720000L)),
        Left((1510365720000L, (1510365720000L, "ShangHai", "U0013"))),
        Right((1510365720000L)),
        Left((1510365900000L, (1510365900000L, "ShangHai", "U0015"))),
        Right((1510365900000L)),
        Left((1510366200000L, (1510366200000L, "ShangHai", "U0011"))),
        Right((1510366200000L)),
        Left((1510366200000L, (1510366200000L, "BeiJing", "U2010"))),
        Right((1510366200000L)),
        Left((1510366260000L, (1510366260000L, "ShangHai", "U0011"))),
        Right((1510366260000L)),
        Left((1510373760000L, (1510373760000L, "ShangHai", "U0410"))),
        Right((1510373760000L)))

    //构建Junit
    val _tempFolder = new TemporaryFolder

    // 构造Streaming 开发环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = TableEnvironment.getTableEnvironment(env)
    env.setParallelism(1)

    //设置 状态的后端存储
    env.setStateBackend(getStateBackend)

    def getProcTimeTables(): (Table, Table) = {
        // 将order_tab, customer_tab 注册到catalog
        val customer = env.fromCollection(customer_data).toTable(tEnv).as('c_id, 'c_name, 'c_desc)
        val order = env.fromCollection(order_data).toTable(tEnv).as('o_id, 'c_id, 'o_time, 'o_desc)
        (customer, order)
    }


    def getEventTimeTables(): (Table, Table, Table, Table) = {
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        // 将item_tab, pageAccess_tab 注册到catalog: catalog提供元数据
        // catalog可以是临时表之类的临时元数据，也可以是针对表环境注册的UDF函数。或者永久的元数据
        // 提供了一个统一的API来管理元数据,并可以通过表API和SQL查询
        val item =
        env.addSource(new EventTimeSourceFunction[(Long, Int, String, String)](item_data))
          .toTable(tEnv, 'onSellTime, 'price, 'itemID, 'itemType, 'rowtime.rowtime)

        val pageAccess =
            env.addSource(new EventTimeSourceFunction[(Long, String, String)](pageAccess_data))
              .toTable(tEnv, 'accessTime, 'region, 'userId, 'rowtime.rowtime)

        val pageAccessCount =
            env.addSource(new EventTimeSourceFunction[(Long, String, Int)](pageAccessCount_data))
              .toTable(tEnv, 'accessTime, 'region, 'accessCount, 'rowtime.rowtime)

        val pageAccessSession =
            env.addSource(new EventTimeSourceFunction[(Long, String, String)](pageAccessSession_data))
              .toTable(tEnv, 'accessTime, 'region, 'userId, 'rowtime.rowtime)

        (item, pageAccess, pageAccessCount, pageAccessSession)
    }


    /**
      * Description @Rule 构造测试用具（fixture）时初始化方法和清理方法的替代和补充
      *
      * return org.junit.rules.TemporaryFolder
      **/
    @Rule
    def tempFolder: TemporaryFolder = _tempFolder


    /**
      * 设置状态的后端存储
      *
      * @return
      */
    def getStateBackend: StateBackend = {
        new MemoryStateBackend() //state数据保存在java堆内存中,执行checkpoint的时候,会把state的快照数据保存到jobmanager的内存中,
        // 基于内存的state backend  一半做测试使用

        /*new FsStateBackend("")*/
        //state数据保存在taskmanager的内存中,执行checkpoint的时候，会把state的快照数据保存到配置的文件系统中
        // 可以使用hdfs等分布式文件系统

        /*new RocksDBStateBackend()*/
        // 需要添加第三方依赖 flink-statebackend-rocksdb_2.11
        //RocksDB跟上面的都略有不同,它会在本地文件系统中维护状态,state会直接写入本地rocksdb中。
        // 同时它需要配置一个远端的filesystem uri（一般是HDFS）,在做checkpoint的时候,会把本地的数据直接复制到filesystem中
        // fail over的时候从filesystem中恢复到本地.RocksDB克服了state受内存限制的缺点,同时又能够持久化到远端文件系统中,
        // 比较适合在生产中使用


    }

    /**
      * 自定义输出 toRetractStream 缩进模式  返回true或false 表示数据追加或撤回
      * @param result
      */
    def procTimePrint(result: Table): Unit = {
        val sink = new RetractingSink
        result.toRetractStream[Row].addSink(sink)
        env.execute()
    }

    def rowTimePrint(result: Table): Unit = {
        val sink = new RetractingSink
        result.toRetractStream[Row].addSink(sink)
        env.execute()
    }

    @Test
    def testProc(): Unit = {
        val (customer, order) = getProcTimeTables()
        //TODO  使用Table  API 实现下列功能

        //SELECT 从customer_tab     选择用户姓名,并用内置的CONCAT函数拼接用户信息
        val result = customer
          .select('c_name,concat_ws('c_name," come ",'c_desc))
        //DISTINCT 使用分组+查询实现   在订单表查询 所有的客户id , 需要去重
        /*val result = order.groupBy('c_id).select('c_id)*/
        //WHERE   在customer_tab查询客户id为c_001和c_003的客户信息
        /*val result = customer.where("c_id='c_001' || c_id = 'c_003'")
                            .select('c_id,'c_name,'c_desc)*/
        //.select("c_id","c_name","c_desc") // 使用双引号 会原样输出
        //IN/NOT IN 在Table API 中对应 intersect 和 minus
        /*val distinct_cids=order.groupBy('c_id)
          .select('c_id as 'o_c_id)*/

        //IN Stream 模式下可以使用双流Join实现
        /*val result = customer
          .join(distinct_cids,'c_id === 'o_c_id)
          .select('c_id,'c_name,'c_desc)*/
        //NOT IN 使用双流Join实现
        /*val result = customer
          .leftOuterJoin(distinct_cids,'c_id === 'o_c_id)
          .where('o_c_id isNull)
          .select('c_id,'c_name,'c_desc)*/

        //groupBy   将order_tab信息按c_id分组统计订单数量
        /*val result = order.groupBy('c_id).select('c_id,'o_id.count)*/
        //按时间进行分组，查询每分钟的订单数量
        /*val result = order.select('o_id, 'c_id, 'o_time.substring(1, 16) as 'o_time_min)
          .groupBy("o_time_min")
          .select('o_time_min, 'o_id.count)*/

        //UNION ALL 将两个表合并起来,要求两个表的字段完全一致(字段类型 字段顺序)   不进行去重
        /*val result = customer.unionAll(customer)*/

        //UNION  将两个流给合并起来 对数据去重 1.7.2不支持
        /*val result = customer.union(customer)*/
        //JOIN 用于把来自两个表的行联合起来形成一个宽表
        //INNER JOIN 只选择满足ON条件的记录
        /*val result = customer
            .join(order.select('o_id,'c_id as 'o_c_id,'o_time,'o_desc)
                    ,'c_id==='o_c_id)*/
        //LEFT JOIN  区别是当右表没有与左边相JOIN的数据时候
        //右边对应的字段补NULL输出
        /*SELECT ColA, ColB, T2.ColC, ColE FROM TI LEFT JOIN T2 ON T1.ColC = T2.ColC ;*/
        /*val result = customer.leftOuterJoin(order.select(
            'o_id, 'c_id as 'o_c_id, 'o_time, 'o_desc
        ), 'c_id === 'o_c_id)*/


        //打印输出
        procTimePrint(result)
    }

    /*@Test
    def testEvent(): Unit = {
        val (item, pageAccess, pageAccessCount, pageAccessSession) = getEventTimeTables()
        val result = ??? // 测试的查询逻辑
        procTimePrint(result)
    }
*/

}

// 自定义Sink
final class RetractingSink extends RichSinkFunction[(Boolean, Row)] {
    var retractedResults: ArrayBuffer[String] = null


    override def open(parameters: Configuration): Unit = {
        super.open(parameters)
        retractedResults = mutable.ArrayBuffer.empty[String]
    }

    def invoke(v: (Boolean, Row)) {
        retractedResults.synchronized {
            val value = v._2.toString
            if (v._1) {
                retractedResults += value
            } else {
                val idx = retractedResults.indexOf(value)
                if (idx >= 0) {
                    retractedResults.remove(idx)
                } else {
                    throw new RuntimeException("Tried to retract a value that wasn't added first. " +
                      "This is probably an incorrectly implemented test. " +
                      "Try to set the parallelism of the sink to 1.")
                }
            }
        }

    }

    override def close(): Unit = {
        super.close()
        retractedResults.sorted.foreach(println(_))
    }
}

// Water mark 生成器

/**
  * 重写sourceFunction  给数据添加水印
  * @param dataWithTimestampList
  * @tparam T
  */
class EventTimeSourceFunction[T](dataWithTimestampList: Seq[Either[(Long, T), Long]]) extends SourceFunction[T] {
    override def run(ctx: SourceContext[T]): Unit = {
        dataWithTimestampList.foreach {
            case Left(t) => ctx.collectWithTimestamp(t._2, t._1) //
            case Right(w) => ctx.emitWatermark(new Watermark(w)) //水印
        }
    }

    override def cancel(): Unit = ???
}

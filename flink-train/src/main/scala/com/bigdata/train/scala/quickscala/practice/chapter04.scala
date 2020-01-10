package com.bigdata.train.scala.quickscala.practice

import java.io.File
import java.util.{Scanner, StringTokenizer}

/**
  * Created with IDEA  
  * author 郭帅 
  * date 14:31 2019/1/3
  * 映射Map 元组Tuple
  **/
object chapter04 {
    /*
    * 4.1
    * 设置一个映射, 其中包含你想要的一些装备,以及它们的价格,
    * 然后构建另一个映射, 采用同一组键, 但在价格上打9折
    */
    def  answer01(A:Map[String,Double]): Unit ={
        val map = for ((k, v) <- A) yield (k,v * 0.9)
        for(elem <-map) println(elem)
    }

    /*
     * 4.2
     * 编写一段程序,从文件中读取单词,用一个可变映射(mutable.map)来统计每一个单次出现的频率,
     * 读取这些单次的操作可以使用java.util.Scanner,
     * val in = new java.util.Scanner(new java.io.File("code.txt:)),
     * while(in.hasNext()) 处理 in.next() 最后, 打印出所有单次和它们出现的次数
     */
    def answer02(path:String): Unit ={
        import scala.collection.mutable.Map
        val map = Map[String,Int]()
        val into = new Scanner(new File(path))
        while(into.hasNext()){
            val  st = new StringTokenizer(into.next())
            while(st.hasMoreTokens){
                val key = st.nextToken()
                map(key) =map.getOrElse(key,0) + 1
            }
        }
        for(elem<-map) println(elem)
    }

    /*
     * 4.3
     * 这次用不可变的映射,实现读取文件,实现单词计数
     * 区别在于:不可变+追加的形成一个新的映射Map
     * StringTokenizer : Java 字符串分隔解析类型
     * 默认按照空格、“制表符（‘\t’）”、“换行符(‘\n’）”、“回车符（‘\r’）”
     */
    def answer03(path:String): Unit ={
        var map = Map[String,Int]()
        val into =new Scanner(new File(path))
        while(into.hasNext()){
            val st = new StringTokenizer(into.next())
            while(st.hasMoreTokens){
                val key = st.nextToken()
                map += (key ->(map.getOrElse(key,0)+1))
            }
        }
        for(elem<-map) println(elem)
    }

    /*
     * 4.4
     * 重复前一个练习, 这次用已排序的映射(SortedMap),一遍单次可以按顺序打印出来
     */

    /*
     * 4.5
     * 重复前一个练习, 这次用java.util.TreeMap并使之适用于ScalaAPI
     */

    /*
     * 4.6
     * 定义一个链式哈希映射, 将"Monday"映射到java.util.Calendar.MONDAY,
     * 以此类推假如其他日期, 展示元素是以插入的顺序被访问的
     */
    def answer06(): Map[String,Int]={
        var map = Map[String, Int]()
        val array = classOf[java.util.Calendar].getFields
        array foreach (x => map += (x.getName -> x.getInt(null)))
        map foreach (x => println(x._1+" "+x._2))
        map
    }
    /*
     * 4.7
     * 打印出所有Java系统属性的表格, 类似这样:
     * java.vm.info                  | mixed mode
     * java.version                  | 1.7.0_67
     * sun.cpu.endian                | little
     * sun.desktop                   | windows
     * sun.cpu.isalist               | amd64
     */
    def answer07(): Unit ={
        import scala.collection.JavaConverters._
        val  props = System.getProperties().asScala  //props 类型  scala.collection.mutable.Map[String,String]
        //print(props)
        //println(props.map(m=>m._1.length).max)
        val len = props.map(m=>m._1.length).max
        println(props)
        props foreach{x=>println(x._1 + " " * (len - x._1.length+1) +"| " +x._2)}
    }
    /*
     * 4.8
     * 编写一个函数minmax(values: Array[Int]), 返回数组中最小值和最大值的对偶
     * Tuple2 元组中放两个数据   Tuple3 三个   最大Tuple22
    */
    def minmax(values:Array[Int]): Tuple2[Int,Int] ={
        println(values.max,values.min)
        (values.max,values.min)
    }
    /*
    * 4.9
    * 编写一个函数lteqgt(values: Array[Int], v: Int),
    * 返回数组中小于v 等于v 和 大于v的数量, 要求三个值一起返回
    */
    def lteqgt(values: Array[Int], v: Int): Unit ={
        println(values.count(_>v),values.count(_==v),values.count(_<v))
    }
    /*
    * 4.10
    * 当你将两个字符串拉链在一起, 比如"Hello" zip "World", 回事什么结果?
    * 想处一个讲的同的用例
    */
    def answer10(): Unit ={
        println("hello".zip("world"))  //Vector((h,w), (e,o), (l,r), (l,l), (o,d))
    }


    def main(args: Array[String]): Unit = {
        //answer01(Map("A" -> 0.1, "B" -> 1, "C" -> 10, "D" -> 100))
        //lteqgt(Array(0,1,2,3,4,5,6,7),5)
        //minmax(Array(1,2,3,4,5,6,7))
        //answer07
        //answer10
        answer06
    }
}

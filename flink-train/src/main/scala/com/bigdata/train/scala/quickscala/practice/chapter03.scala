package com.bigdata.train.scala.quickscala.practice

import java.util.TimeZone

import scala.util.Random
import scala.collection.mutable.ArrayBuffer

/**
  * Created with IDEA  
  * author 郭帅 
  * date 13:12 2018/12/29
  * Array
  **/
object chapter03 {
    /*
     * 移除除第一个负数外的所有负数
     * */
    def oneWay(): Unit = {
      val a = ArrayBuffer(2, -3, 5, -7, -11)
      for (elem <- a if elem % 2 == 0) yield 2 * elem
      var first = true
      var n = a.length
      var i = 0
      while (i < n) {
        if (a(i) >= 0) i += 1
        else {
          if (first) {
            first = false; i += 1
          }
          else {
            a.remove(i); n -= 1
          }
        }
      }
      println(a)
    }
    /*在数组缓冲中移除元素效率较慢 下面更优*/
    def otherWay() {
      var first = true
      val a = ArrayBuffer(2, -3, 5, -7, -11)
      val indexes = for (i <- 0 until a.length if first || a(i) >= 0) yield {//第一次遍历 一直取  知道拿到负数,然后 只取正数
        if (a(i) < 0) first = false; i
      }
      /*然后将 indexes 的值覆盖  a 的值   截掉多余的 */
      for (j <- 0 until indexes.length) a(j) = a(indexes(j))
      a.trimEnd(a.length - indexes.length)
      println(a)
    }

    /*println(BigInt.probablePrime(100, Random))*/
    /*println("hello".distinct)*/

   /*
    * 3.1
    * 编写一段代码，将a设置为一个n个随机整数的数组，要求随机数介于0和n之间。
    */
    def answer01(n:Int): Unit ={
      val b = for( i <- 1 to n) yield Random.nextInt(n)  //Vector(7, 8, 8, 7, 5, 8, 2, 8, 5, 7)
      print(b +" ")
     val a = (for( i <- 1 to n) yield Random.nextInt(n)).toArray
      for(elem <- a ) print(elem+" ")
    }

    /*
     * 3.2
     * 编写一个循环，将整数数组中相邻的元素置换。
     * 遍历使用 until
     * 默认是偶数对  奇数对 最后一个不转换
     */
      def answer02(a :Array[Int]): Array[Int] ={
        /* 先看下输入的值 */
        for(elem <- a) println("输入的数组元素: "+elem+" ")
        val len = a.length
        for (i <- 0 until (if (len % 2 ==0) len else len-1 ,2)){
          println("取得下标: "+i +" ")
          val j =i+1
          val tmp = a(i)
          a(i) = a(j)
          a(j) = tmp
        }
        /*输出看一下*/
        for(elem <- a) println("转换后的元素 "+elem+" ")
        a
      }

    /*
     * 3.3
     * 重复前一个练习，不过这次生成一个新的值交换过的数组
     * 用for/yield。
     */
      def answer03(a:Array[Int]):Array[Int] ={
        val len =a.length
        for(i <- 0 until (if(len %2==0) len else len-1,2))
          yield {
            val j=i+1
            val tmp= a(i)
            a(i)=a(j)
            a(j)=tmp
          }
        for(elem <-a) println(elem)
        a
      }

    /*
     * 3.4
     * 给定一个整数数组，产出一个新的数组，包含原数组中的所有正值，以原有顺序排列，
     * 之后的元素是所有零或负值，以原有顺序排列。
     * 先获取整数  再在后面拼接0 和负数
     * 数组拼接  ++
     * filter: a.filter = a filter   遍历每一个
     */
      def answer04(a:Array[Int]): Array[Int] ={
        val b = (a filter(_>0)) ++ ( a filter(_<=0))
        for(elem <-b) print(elem +" ")
        b
      }

    /*
     * 3.5
     * 如何计算Array[Double]的平均值？
     */
      def answer05(a: Array[Double]): Unit = {
        val b = a.sum / a.length
        println(b)
      }

    /*
     * 3.6
     * 如何重新组织Array[Int]的元素将它们反序排列？对于ArrayBuffer[Int]你又会怎么做呢？
     */
      def answer06(a:Array[Int]): Unit ={
        /*第一种写法*/
        /*for(elem <- a reverse) print(elem+" ")
        for(elem <- a reverse) yield{
          print(" "+elem +" ")
        }*/

        //第二种 todo 最优
        a reverse

        /*测试结果值*/
        val b = (a reverse)
        for(elem <-b) print(elem+" ")
      }

    /*
     * 3.7
     * 编写一段代码，输出数组中的所有值，去掉重复项。
     */
        def answer07(a:Array[Int]): Unit ={

          a distinct

          /*测试结果*/
          val b = (a distinct) //todo 需要添加括号  不然找不到边界
          for(elem <-b ) print(elem+" ")
        }

    /*
     * 3.8
     * 移除除第一个负数外的所有负数。
     * 收集负值元素的下标，反序，去掉最后一个下标，然后对每一个下标调用a.remove(i)。
     * 比较这样做的效率和3.4节中另外两种方法的效率。
     */
      def answer08(a:ArrayBuffer[Int]): Unit ={
        val len = a.length
        val index = ArrayBuffer[Int]()
        for(i <- 0 until len  if(a(i)<0)) index.insert(0,i)   //在下标0之前添加
        index.init foreach(a remove _) //init  取出最后一个下标

        /*测试结果*/
        for(elem <- a) print(elem+" ")
      }
      /*测试ArrayBuffer 的  init算子:去除最后一个元素*/
      def  testInit(): Unit ={
        val a = ArrayBuffer[Int](-1,-2,-3,0,4,5,6)
          println(a.init)
      }

      /*
      * 3.9
      * 创建一个由java.util.TimeZone.getAvailableIDs返回的时区集合，判断条件是它们在美洲，去掉”America/“前缀并排序。
      * 使用Java的包
      */
      def answer09(): Unit ={
       /*测试结果
        val a = (TimeZone.getAvailableIDs).toArray
        for(elem <-a ) print(elem+" ")*/
        TimeZone.getAvailableIDs filter {_.startsWith("America")} map {_ drop (8) } sortBy {x=>x}

        /*测试结果*/
        val  a =(TimeZone.getAvailableIDs filter {_.startsWith("America")} map {_ drop (8) } sortBy {x=>x}).toArray
        for(elem <- a ) print(elem+" ")


      }

      /*
       * 3.10
       * 引入java.awt.datatransfer._并构建一个类型为SystemFlavorMap类型的对象，
       * 然后以DataFlavor.imageFlavor为参数调用getNativesForFlavor方法，以Scala缓冲保存返回值。
       */
        def answer10(): Unit ={

        }
      def main(args: Array[String]): Unit = {
        //oneWay
        //otherWay
        //answer01(10)
        //answer02(Array(1,2,3,4,5,6))
        //answer03(Array(1,2,3,4,5,6))
        //answer04(Array(-1,-2,-3,0,4,5,6))
        //answer05(Array(-3.0,-2.0,-1.0,0,1,2,3,4))
        //answer06(Array(0,1,2,3,4,5,6))
        //answer07(Array(0,1,2,3,4,5,5,6,6))
        //answer08(ArrayBuffer(-1,-2,-3,0,4,5,6))
        //testInit
        answer09
      }
}

package com.bigdata.train.scala.quickscala.basis

import java.util

import scala.collection.mutable


/**
  * Created with IDEA  
  * author 郭帅 
  * date 16:55 2018/12/29
  *  Map Tuple Zip  Set 队列
  **/
object basic03 {

    /*不可变映射  值不可被改变*/
    val scores =Map("A"->1,"B"->2,"C"->3) // Map[String,Int]

    /*可变映射   需要引入mutable  值可以改变*/
    import scala.collection.mutable.Map
    //在这里引入包  不会影响上面的Map
    val scores1 =Map("A"->1,"B"->2,"C"->3)

    /*空的映射Map*/
    //private val hashMap = new mutable.HashMap[String,Int]()

    private val hashMap =  scala.collection.mutable.Map("A"->1,"B"->2,"C"->3)
    /*获取映射Map中的值*/
    val A =hashMap("A") ;println(A)
    /*更新映射的值*/
    hashMap("B") = 222
    /*增加一个映射*/
    println("B: "+hashMap("B"))
    hashMap("D") =4 ;println("D: "+hashMap("D"))

    /*增加多个映射 += */
    hashMap += (("E",5),("F",6))

    /*移除映射   -=(指定某个键) */
    hashMap -= ("B")

    //遍历Map(映射)  todo 跟列表 数组一样
    for(i<-hashMap) println(i)

    /* keySet,values 同样适用*/
    println(hashMap.keySet)  // Set(D, A, C, F, E)
    for(elem <- hashMap.values) println(elem)

    /*反转 Map   todo for((k,v)<-hashMap) yield (v,k) */
    val hashMapReverse = for((k,v)<-hashMap) yield (v,k) //遍历 + yield
    for(elem <- hashMapReverse) println(elem)


    //Java Map 转成Scala Map
    import  scala.collection.JavaConversions.mapAsScalaMap
    val score : scala.collection.mutable.Map[String,Int] = new java.util.TreeMap[String,Int]

    /*----------------------------Tuple 元组------------------------*/
    val t1=(1,"二",3.0)
    /*_1,_2,_3 分别代表元组中的第一第二第三个值*/
    println(t1._1,t1._3)

    /*使用模式匹配获取Tuple的值*/


    /*-----------Zip 拉练操作----------*/
    val z1 = Array("<","!","-",">")
    val z2 = Array(1,1,10,1)
    val zip = z1.zip(z2)
    for(elem <- zip) println(elem)

    /*Console 打印输出: <!----------> */
    for((k,v) <-zip) Console.print(k * v)
    def main(args: Array[String]): Unit = {

    }
}

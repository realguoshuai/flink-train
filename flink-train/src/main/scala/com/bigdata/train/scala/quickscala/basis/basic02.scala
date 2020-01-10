package com.bigdata.train.scala.quickscala.basis

import scala.collection.mutable.ArrayBuffer
/**
  * Created with IDEA  
  * author 郭帅 
  * date 11:24 2018/12/28
  * 数组Array 列表List
  **/

object basic02 {
   /*实例化*/
    val numArray = new Array[Int](10)
    val strArray = new Array[String](10)
    /*添加数据*/
    strArray(0)="First Element"
    strArray.update(1,"Second Element")
    /*数值型数组 初始值为0 复杂对象类型 初始值为null*/

    val strArray2=Array("First","Second")
    /*scala中的Array 就是Java中Array实现*/
    for(i<- 1 to 3; j<- 4 to 7 reverse) println("i+j: "+ i+j +" i: "+i +" j: "+j)
    for(i<- 1 to 3; j<- 4 to 7 if j!=5) println("i+j: "+ i+j +" i: "+i +" j: "+j)

    /*---------变长数组,需要引入包-----*/
    val strArrayVar=ArrayBuffer[String]()
    //添加数据
    strArrayVar+="hello" ;println(strArrayVar)
    //添加多条数据
    strArrayVar+=("scala","and","java");println(strArrayVar)
    //todo ++= 用于向数组中追加内容，++=右侧可以是任何集合
    //追加数组
    strArrayVar++=Array("scala","and","java")
    //追加列表list
    strArrayVar++=List("scala","and","java")

    //删除末尾n个元素
    strArrayVar.trimEnd(5);println(strArrayVar)


    /*-------------数组遍历--------------*/
    //to

    //until

    //增强for循环 (推荐)
    for(i<-strArrayVar) println(i)

    //步长为2
    for(i <- 0 until (strArrayVar.length,2))println(strArrayVar(i))

    //倒序输出
    for( i<- (0 until strArrayVar.length).reverse){}

    /*-------------数组转换 yield--------------*/
    var intArrayVar2 = for (i <- strArrayVar) yield i * 2
    val intArrBuffer =ArrayBuffer(1,2,3)

    //加入过滤条件
    var intArrayNoBuffer2=for(i <- intArrBuffer if i >= 2) yield i*2
    def main(args: Array[String]): Unit = {

    /*---------------------Array常用算子---------------------*/
    val intArr=Array(1,2,3,4,5,6,7,8,9,10)
     //求和
     intArr.sum
     //求最大值
     intArr.max
     //求最小值
     intArr.min
     //toString()
     intArr.toString()
     //mkString()
     intArr.mkString(",")
     intArr.mkString("<",",",">")


     /*TODO -----------------------------List列表------------------------------*/
     /*字符串类型List*/
     val fruit=List("Apple","Banana","Orange")
     val fruits=List.apply("Apple","Banana","Orange")

     /*数值类型List*/
     val nums=List(1,2,3,4,5)

     /*多重List*/
     val diagMatrix=List(List(1,0,0),List(0,1,0),List(0,0,1))

     /*遍历List*/
     for (i <- nums) println(i)


     //todo  List一旦创建  值不能改变   ;List 具有递归结构

     /*List 构建 ::  */
     val list=1::2::3::4::Nil   // List[Int] = List(1, 2, 3, 4)
     for(i <- list) println(i)

     /*---------------------List常用算子---------------------*/
     //判断是否为空 isEmpty
     nums.isEmpty
     //取第一个无素 head
     nums.head
     //取除第一个元素外剩余的元素，返回的是列表  tail
     nums.tail
     //取列表第二个元素 tail.head
     nums.tail.head
     //插入排序算法实现
     def isort(xs: List[Int]): List[Int] =
      if (xs.isEmpty) Nil
      else insert(xs.head, isort(xs.tail))

     def insert(x: Int, xs: List[Int]): List[Int] =
      if (xs.isEmpty || x <= xs.head) x :: xs
      else xs.head :: insert(x, xs.tail)
     //List连接操作
     List(1,2,3):::List(4,5,6)
     //取除最后一个元素外的元素，返回的是列表  init
     nums.init
     //取列表最后一个元素  last
     nums.last
     //列表元素倒置 reverse
     nums.reverse

     //一些好玩的方法调用
     nums.reverse.reverse==nums  //返回true
     nums.reverse.init //List[Int] = List(4, 3, 2)
     nums.tail.reverse //List[Int] = List(4, 3, 2)

     //丢弃前n个元素 drop n
     nums drop 3
     //获取前n个元素 take n
     nums take 1
     //将列表进行分割  splitAt(2)
     nums.splitAt(2)    //(List[Int], List[Int]) = (List(1, 2),List(3, 4))
     (nums.take(2),nums.drop(2))

     //List toString方法
     nums.toString

     //mkString方法
     nums.mkString  // String = 1234

     //转换成数组
     nums.toArray
    }
}

package com.bigdata.train.scala.quickscala.basis

/**
  * Created with IDEA  
  * author 郭帅 
  * date 10:32 2018/12/28
  * 循环控制  函数
  **/
object basic01 {
  /*val 常量不可变 var 变量可变*/
  //if
  val x = if ("hello" == "hell") 1 else 0

  //while todo (不推荐)
  /*:Long 返回类型为Long类型
   * Unit 没有返回类型,类似void*/
  def gcdLoop(x: Long, y: Long): Long = {
    var a = x
    var b = y
    while (a != 0) {
      val temp = a
      a = b % a
      b = temp
    }
    /*类似Java的 return */
    b
  }

  //使用if替代while循环 todo (推荐)
  def gcd(x: Long, y: Long): Long = {
    if (y == 0) x else gcd(y, x % y)
    println("x:" + x + " y:" + y)
    x
  }

  /*这样减少了var变量的使用,程序结构也更简单,表达能力更强*/


  //todo do-while循环 不推荐用
  //readLine() 获取到用户输入
  // var line = ""
  // do {
  //  line = readLine()
  //  println("Read: " + line)
  //} while (line != "")
  /*与if不同的是，while与do while不能用作表达式，
  也即其返回值为Unit，
  在某些函数式编程语言中，删除了while与do while程序控制结构，
  但scala仍然保留了while与do while，
  可见Scala并不是纯函数式编程语言
  （另外一个重要原因是，scala函数定义时仍然可以用var指定参数）*/

  //for循环
  for (i <- 1 to 5) println("Iteration use to: " + i)
  for (i <- 1 until 5) println("Iteration use until: " + i)
  /* <- :生成器  to:包含  until:不包含 */

  //for+if
  for (i <- 1 to 5 if i != 3) println("Iteration use if: " + i)


  //函数

  /*变长参数 */
  def sum(args:Int*):Int={
    var result =0
    for(arg <- args) result += arg
    result
  }

  /*程序执行顺序: 自上而下 */
  def main(args: Array[String]): Unit = {
    /* 在scala中，main函数必须定义在object中（如Java中，main函数必须是class中的static方法）*/
    gcd(1, 2)
    println(sum(10,20,30))
  }
}

package com.bigdata.train.scala.quickscala.operator

/**
  * Created with IDEA  
  * author 郭帅 
  * date 9:59 2018/12/29
  * 练习一些复杂算子 foldLeft apply()
  *
  **/
object seniorOperator {

    /*foldLeft: a.foldLeft(1){_*_}  1会累乘 a遍历后的每一个值
    * */
    //foldLeft 源码实现
      /*//z是B类型的对象 第二个参数的返回类型为B类型 :B foldLeft的返回类型
      def foldLeft[B](z:B)(op:(B:A)=>B):B={
      var result = z
      this.seq foreach(x=>result =op(result,x))
      result
    }*/
  def useFoldLeft(): Unit ={

      val lst =List(20,30,40,50)
      val rs =lst.foldLeft(0)((b,a)=>{
        println(b+a)
        b+a
      })
      /* 0 代表初始值 ,b代表返回结果对象,a表示lst中的每个值*/
    }

  //apply()  源码
  /* def apply(n: Int): Char */
  //todo 用括号传递给变量(对象)一个或多个参数时，Scala 会把它转换成对 apply 方法的调用
  /* val numNames = Array("zero", "one", "two")
   * val numNames = Array.apply("zero", "one", "two")
   */
  def useApply()={
   println("hello"(4))
    "hello".apply(0)
  }




  def main(args: Array[String]): Unit = {
    useFoldLeft
    useApply
  }

}

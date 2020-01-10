package com.bigdata.train.scala.quickscala.practice

/**
  * Created with IDEA  
  * author 郭帅 
  * date 15:46 2018/12/28
  **/
object chapter02 {
    /* 2.1
     * 一个数字如果为正数，则它的signum为1;
     * 如果是负数,则signum为-1;
     * 如果为0,则signum为0.编写一个函数来计算这个值
     * */
    def signum(x:Int)={/*括号可以胜略*/
      if(x>0) 1 else if(x<0)-1 else 0
    }
    /*
     * 2.2
     * 一个空的块表达式{}的值是什么？类型是什么？
     * */
    //def nullType()={ "()="可以省略,调用时 但也不需要()
    def nullType{
      println("值是: "+{}   +" 类型: "+{}.getClass)
    }
   /*
    * 2.3
    * 指出在Scala中何种情况下赋值语句x=y=1是合法的。
    * (提示：给x找个合适的类型定义)
    * 拆分 x= y=1   只有x属于某一种数据类型时,x=y=1才合法
    */
    def checAssignLegal{
      var x:Unit=()
      println("x's type is: "+x.getClass)
      var y=1
      x=y=1
    }
    /*
     * 2.4
     * 针对下列Java循环编写一个Scala版本:
     * for(int i=10;i>=0;i–)System.out.println(i);
     * 分析 :10 9 8 7 ...0
     */
    def scalaTypeForEach: Unit ={
      for(i <- 1 to 10 reverse) println(i)//(1)
      (1 to 10 reverse) foreach println   //(2) todo 更难看懂,代码更加函数式
      /*reverse  反转 1 to 10 reverse -->  10到1  foreach 遍历
       *             10 to 1  不支持*/
    }
    /*
     * 2.5
     * 编写一个过程countdown(n:Int)，打印从n到0的数字
     * 分析: n 三种情况 n>0  n=0 n<0 第一想法是使用 match case 3种情况
     *   但是: 代码最好写的精简 因为 to 可以包含本身 ,所以 0可以加载>= 或<= 上
     */
    def countdown(n: Int): Int = {
      n match {
        case n if n >= 0 => {
          (0 to n ) foreach println
        }
        /*case n if n == 0 => {
          println(n)
        }*/
        case n if n < 0 => {
          (n to 0) foreach println
        }
      }
      n
    }
    /*
     * 2.6
     * 编写一个for循环,计算字符串中所有字母的Unicode代码的乘积。
     * 举例来说，"Hello"中所有字符串的乘积为9415087488L
     */
    def calculationStrUnicode(str: String): Unit = {
      var result: Long = 1
      str foreach {result *= _.toLong}
      println(result)
    }
    /*
    * 2.7
    *  计算字符串中所有字母的Unicode代码的乘积,不使用循环
    * （提示：在Scaladoc中查看String提供的函数）
    */
    def calculationStrUnicode2(s:String): Unit ={
      val result = s.foldLeft(1.toLong){//s.foldLeft(1.toLong) :1会对遍历s的每一个进行操作
        _*_
      }
      println(result)
    }
    /*
     * 2.8
     * 编写一个函数product(s:String
     * 计算前面练习中提到的乘积
     * 2.9
     * 把前一个练习中的函数改成递归函数
     */
    def product(s:String): Long ={
      if(s.length()==1)  s(0) toLong
      else s(0).toLong * product(s.tail)     //head表示第一个元素,tail表示其它元素
    }


  def main(args: Array[String]): Unit = {
    //nullType
    //checAssignLegal
    //scalaTypeForEach
    //countdown(0)
    //calculationStrUnicode("Hello")
    //calculationStrUnicode2("Hello")
    println(product("Hello"))
  }
}

package com.bigdata.train.scala.high

import org.junit.{Rule, Test}
import org.junit.rules.TemporaryFolder

/**
  * Description  Scala函数柯里化  && Scala 偏函数
  *  函数柯里化:
  *  函数柯里化:
  *     将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
  *     新的函数返回一个以原有第二个参数为参数的函数。
  *
  *  偏函数:就是固定部分参数，生成另外一个参数更少的方法
  *
  *  闭包:闭包是一个函数，返回值依赖于声明在函数外部的一个或多个变量。
  *
  * Created with guoshuai 
  * date 2020/1/10 10:46
  **/
class Currying {

    val _tempFolder = new TemporaryFolder

    @Rule //规则
    def temporaryFolder:TemporaryFolder = _tempFolder

    @Test //测试柯里化
    def testCurrying():Unit={
        def normal(x:Int,y:Int) = x+y //普通写法
        def curry(x:Int)(y:Int) = x+y //函数柯里化
        //测试结果一样
        println(normal(1,10))
        println(curry(1)(10))
    }


    @Test //偏函数:不需要提供函数需要的所有参数，只需要提供部分，或不提供所需参数
    def partial():Unit={
        import PartialFunction._
        def strangeConditional(other: Any): Boolean = cond(other) {
            case x: String if x == "abc" || x == "def" => true
            case x: Int => true
        }
        println(strangeConditional(123))
        println(strangeConditional("abcd"))
    }

    @Test //闭包
    def closure():Unit={
        var factor = 3 //构建自由变量
        val multiplier = (i:Int) => i * factor //闭包函数
        //multiplier 就形成一个闭包:它引用函数外面的变量factor
        //定义这个闭包函数时,将这个自由变量捕获而构成一个封闭的函数
        println(multiplier(1))
    }

}

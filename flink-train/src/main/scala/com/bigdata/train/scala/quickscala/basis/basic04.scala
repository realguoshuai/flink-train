package com.bigdata.train.scala.quickscala.basis

import org.junit.{Rule, Test}
import org.junit.rules.TemporaryFolder

/**
  * Description  字符串插值器
  * Created with guoshuai 
  * date 2020/1/6 11:35
  **/
class basic04 {
    @Rule
    def tempFolder: TemporaryFolder = new TemporaryFolder()

    //字符串插值器 's'
    @Test
    def testS(): Unit = {
        val i = 1234
        println(s"${i}")
    }

    @Test
    def testS1():Unit ={
        val i = 1234
        println(s"${i} 123")
    }
}

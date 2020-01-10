package com.bigdata.train.scala.junit

import org.junit.{Rule, Test}
import org.junit.rules.TemporaryFolder

/**
  * Description Scala单元测试
  * Created with guoshuai 
  * date 2020/1/10 10:07
  **/
class JunitTest {
    //构建Junit
    val _tempFolder = new TemporaryFolder

    /**
      * Description @Rule 构造测试用具（fixture）时初始化方法和清理方法的替代和补充
      *
      * return org.junit.rules.TemporaryFolder
      **/
    @Rule
    def tempFolder: TemporaryFolder = _tempFolder

    @Test
    def testJunit(): Unit = {
        println("junit test")
    }
}

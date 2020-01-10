package com.bigdata.train.scala.basic.traitscala

/**
  * Description 
  * Created with guoshuai 
  * date 2020/1/10 13:51
  **/
trait Equal {
    //未实现
    def isEqual(x: Any): Boolean
    //已实现
    def notEqual(x: Any): Boolean = !isEqual(x)
}

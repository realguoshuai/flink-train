package com.bigdata.train.scala.basic.traitscala

/**
  * Description 
  * Created with guoshuai 
  * date 2020/1/10 13:54
  **/
object Test {
    def main(args: Array[String]) {
        val p1 = new Point(2, 3)
        val p2 = new Point(2, 4)
        val p3 = new Point(3, 3)
        println(p1.notEqual(p2))
        println(p1.notEqual(p3))
        println(p1.notEqual(2))
    }
}

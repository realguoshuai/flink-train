package com.bigdata.train.scala.basic.traitscala

/**
  * Description 
  * Created with guoshuai 
  * date 2020/1/10 13:53
  **/
class Point(xc: Int, yc: Int) extends  Equal {
    var x: Int = xc
    var y: Int = yc
    //需要实现未实现的方法
    def isEqual(obj: Any) = {
        obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x
    }
    //trait 可以不实现
    override def notEqual(x: Any): Boolean = super.notEqual(x)
}

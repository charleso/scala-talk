object Main {

  def main(args: Array[String]) {
    println("Hello world")
  }

  def typeInference = {
    val listOfMaps = List(Map("a" -> Map("b" -> "c")))
    val mapOfMaps = listOfMaps(0)
    val map = mapOfMaps.get("foo").get
  }

  def dsl1 = {

    def someMethod(a: Int, b: Int) = {}

    someMethod(1, 2)

    def hasBlock(o: AnyRef)(f: => Unit) = f

    hasBlock("") {
      println("block")
    }
  }

  def dsl2 = {

    class Register(var value: Int) {
      def setValue(i: Int) = value = i
      def getValue() = value
      def :=(i: Int) = value = i
      def <<(i: Int) = value << i
    }

    def accum(r1: Register, r2: Register) {
      r1.setValue(r2.getValue() << 4)
    }

    def accum2(r1: Register, r2: Register) {
      r1 := r2 << 4
    }

    accum(new Register(2), new Register(3))
  }

  def innerDef = {
    def someMethod = {
      def helpMethod(i: Int, j: Int) = i + j
      helpMethod(1, 2)
      helpMethod(3, 4)
    }
  }

  def defaultParams = {
    def method1(a: String, b: Int = 0, c: String = "") = a + b + c

    method1("a")
    method1("a", 1, "c")
    method1("a", c = "c")
    method1("a", c = "c", b = 1)
  }

  def functions = {
    val plus = (a: Int, b: Int) => a + b
    val multi:Function2[Int, Int, Int] = (a: Int, b: Int) => a * b
    plus(1, 2)
    multi(2, 3)

    def combine(f: (Int, Int) => Int) = f(2, 3)

    combine(plus)
    combine(multi)
    combine((a: Int, b: Int) => a / b)
    combine(_ + _)
    combine(_ * _)

    val l = List("a", "b")
    val s = l(3)

    object List2 {
        def apply(args:String*) = new List2(args.toArray)
    }
    class List2(args:Array[String]) {
        def apply(i:Int) = args(i)
    }
    List("a", "b", "c")

    val map = Map("a" -> "b")
    val b = map("a")
  }

  def armBlock = {
    import java.io.{ Closeable, FileInputStream }

    def arm[C <: Closeable](closable: C)(f: (C) => Unit) = {
      try {
        f(closable)
      } finally {
        closable.close()
      }
    }

    arm(new FileInputStream("foo.txt")) { in =>
      // Do something
    }
  }

  def collections = {
    import scala.collection.mutable.MutableList

    //   val list = MutableList(1, 2, 3, 4)
    val list = List(1, 2, 3, 4)
    list.map((a: Int) => a + "")
    list.map((a) => a + "")
    list.map(_ + "")
    list.map(_ + 1)
    list.filter(_ > 2)
    list.sort(_ > _)
    list.reverse

    list ++ list
    val (small, big) = list partition (_ < 3)

    val map = Map("a" -> "b")
    map.mapElements(_ toUpperCase)
    map.filterKeys(_ contains "x")

    // Parallel

    list.par map (_ + 1)
    list.par filter (_ > 2)

    for (i <- List(1, 3, 4, 7)) {
      println(i)
    }

    for (i <- 1 until 10) {
      println(i)
    }

    for {
      i <- List(1, 3, 4, 7)
      j <- List(2, 4, 6, 8)
      if i + j > 3
    } {
      // do something
    }

  }

  def caseClass = {

    case class Bean(var x: Int, var y: Int)
    val bean = new Bean(1, 2)
    bean.x = 3

    val b = Bean(1, 2)
    b match {
      case Bean(3, 4) => 3 + 4
      case Bean(a, b) if a == b => a + b
      case _ => throw new RuntimeException("")
    }
  }

  def properties = {

    case class Bean(private var _x: Int, var y: Int) {
      def x = _x
      def x_=(v: Int) { _x = v }
    }
    val bean = new Bean(1, 2)
    bean.x = 3
  }

  def deathToNull = {

    def doSomething(a: String) = println(a)

    val map = Map("a" -> Map("b" -> "c"))
    val a = map.get("a")
    if (a.isDefined) {
      val b = a.get.get("b")
      if (b.isDefined) {
        doSomething(b.get)
      }
    }

    val map2 = Map("a" -> Map("b" -> "c"))
    for (a <- map2.get("a"); b <- a.get("b")) {
      doSomething(b)
    }

    val option: Option[String] = None
    val string: String = option.getOrElse("a")
    option.map(_ toUpperCase)
    option.filter(_ contains "x")
    option.foreach(println _)

  }

  def pimp = {

    class RichInt(val i: Int) {
      def plus(j: Int): Int = i + j
    }

    implicit def int2rich(i: Int): RichInt = new RichInt(i)

    1 plus 2
  }
}
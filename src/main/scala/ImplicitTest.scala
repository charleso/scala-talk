object ImplicitTest {

  def main(args: Array[String]) {
    class RichInt(var i: Int) {
      def a: String = i + ""
      def b: Int = i
      def c: String = i + ""
    }
    class RichInt2(var i: Int) {
      def a: String = i + ""
      def b: String = i + ""
      def c: AnyRef = i + ""
    }
    implicit def x(i: Int): RichInt = new RichInt(i)
    implicit def y(i: Int): RichInt2 = new RichInt2(i)

    // None of these compile
    // val a: String = 1.a
    // val b1: String = 1.b
    // val b2: Int = 1.b
    // val c: String = 1.c
  }
}
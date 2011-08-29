object TraitTest {

  def main(args: Array[String]) {
    trait A {
      def a = println("A")
    }
    trait B {
      def a = println("B")
    }
    // class X extends A with B

    trait C {
      def a = println("C"); ""
    }
    trait D {
      def a = println("D"); 0
    }
   //  class Y extends C with D
  }
}
================
A Taste of Scala
================

----

Me
==

* Charles O'Farrell
* Mincom (4 years)
* @charlesofarrell
* https://github.com/charleso/scala-talk

----

Introduction
============

* "Scalable Language"
* Martin Odersky (1999 - 2003)
* JVM (obviously)
* "Multi-paradigm" programming language
* Object Orientated
* Functional
* **Statically typed**

----

Big names
=========

- `Twitter <http://www.twitter.com/>`_
- `Foursquare <https://foursquare.com/>`_
- `LinkedIn <http://www.linkedin.com/>`_
- `Guardian.co.uk <http://www.guardian.co.uk/>`_

----

Hello World
===========

.. code-block:: scala

    object Main {
        def main(args:Array[String]) {
            println("Hello world")
        }
    }

.notes: Note the of semi-colon

----

Companion Objects
=================

* Java

.. code-block:: java

    public class Main {

        private static String staticField;
        private String normalField;
        
        public static void main(String[] args) { }
        
        public void memberMethod() { }
        
        public static void utilMethod() { }
    }

----

Companion Objects
=================

* Scala

.. code-block:: scala

    object Main {
        var staticField:String;
        def main(args:Array[String]) {
            objectAsType(this)
        }
        
        def utilMethod() = {}
        
        def objectAsType(m:Main.type) = {
            m.utilMethod()
        }
    }
    class Main {
        var normalField;
        def memberMethod() = {}
    }

----

Val vs Var
==========

* Emphasis on immutability in Scala
    - Better for concurrency
    - No locks
    - Easier to reason about

.. code-block:: scala

    val s = "testing"
    // Compile error
    // s = "testing 2"
    var s2 = "testing"
    s2 = "testing 2" // OK

----

Type Inference
==============

* Java 7 - now with New and Improved™ diamond operator

.. code-block:: java

    List<Map<String, Map<String, String>>> listOfMaps = new ArrayList<>()
    Map<String, Map<String, String>> mapOfMaps = listOfMaps.get(0)
    Map<String, String> map = mapOfMaps.get("foo")

* Scala

.. code-block:: scala

    val listOfMaps = List(Map("a" -> Map("b" -> "c")))
    val mapOfMaps = listOfMaps(0)
    val map = mapOfMaps.get("foo").get

* Not as good as Haskell (eg parameters always need types)
* Often need types to help compiler

----

DSLs
====

* Optional syntax

.. code-block:: scala

    a.someMethod("one value");
    a someMethod "one value"
    a someMethod ("two", "values")

* Unicode method names
    - *not* operator overloading

.. code-block:: scala

    class Int {
        def +(b:Int) = ...
    }
    1 + 2 == 1.+(2)

----

DSL
===

* Java 7 - now with New and Improved™ ARM block

.. code-block:: java

    try (FileInputStream in = new FileInputStream("foo.txt")) {
        // Do something
    }

* Last argument can be passed as block

.. code-block:: scala

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

* Better version here:
    - http://wiki.github.com/jsuereth/scala-arm/

----

DSLs
====

* Real world example - Gameboy Emulator

.. code-block:: scala

    def accum(r1: Register, r2: Register) {
        r1.setValue(r2.getValue() << 4)
    }

.. code-block:: scala

    class Register(var value: Int) {
        def :=(i:Int) = { value = i }
        def <<(i: Int) = value << i
    }

    def accum(r1: Register, r2: Register) {
        r1 := r2 << 4
    }

----

Inner defs
==========

* Java

.. code-block:: java

    public void someMethod() {
        helpMethod(1, 2)
        helpMethod(3, 4)
    }
    
    private int helpMethod(int i, int j) {
        return i + j
    }

* Relies on IDE to warn about unused methods

.. code-block:: scala

    def someMethod = {
        def helpMethod(i: Int, j: Int) = i + j
        helpMethod(1, 2)
        helpMethod(3, 4)
    }

----

Imports
=======

* Java

.. code-block:: java

    import java.util.List
    import java.util.Map
    import java.util.Set

* Scala

.. code-block:: scala

    import java.util.{List, Map, Set}
    import java.util.{Collections => C}
    import java.util.{Arrays => _}
    import java.util._
    
    def someMethod = {
        import java.util.ConcurrentMap
    }

----

Default parameters
==================

* Java - who has written this?

.. code-block:: java

    public void method1(String a) {
        method1(a, 0)
    }
    
    public void method1(String a, int b) {
        method1(a, b, "")
    }
    
    public void method1(String a, int b, String c) {
        // Do something
    }

* Scala

.. code-block:: scala

    def method1(a:String, b:Int = 0, c:String = "") = ...

    method1("a")
    method1("a", 1, "c")
    method1("a", c = "c")
    method1("a", c = "c", b = 1)
    // etc

----

Functions
=========

* First class functions

.. code-block:: scala

    val plus = (a:Int, b:Int) => a + b
    val multi:Function2[Int, Int, Int] = (a:Int, b:Int) => a * b
    plus(1, 2)
    multi(2, 3)
    
    def combine(f:(Int, Int) => Int) = f(2, 3)
    
    combine(plus)
    combine(multi)
    combine((a:Int, b:Int) => a / b)
    combine(_ + _)
    combine(_ * _)

----

Functions
=========

* Every object is a function

.. code-block:: scala

    object List {
        def apply(args:String*) = new List(args.toArray)
    }
    class List(args:Array[String]) {
        def apply(i:Int) = args(i)
    }

    val l = List("a", "b", "c")
    val string = l(3)
    val map = Map("a" -> "b")
    val b = map("a")

----

Collections
===========

* Google Collections (Guava)

.. code-block:: java

    List<Integer> ints = Arrays.asList(1, 2, 3, 4);
    List<String> strings = Lists.transform(strings , new Function<String, Integer>() {
            @Override
            public String apply(Integer i) {
                return i + "";
            }
    });

    // Less lines of code
    // Easier to read?
    List<String> strings = new ArrayList<String>() {
    for (Integer i : ints) {
        strings.add(i + "")
    }

----

Collections
===========

* Scala has an amazing collections library
* Immutable by default, otherwise import mutable package

.. code-block:: scala

    val list = List(1, 2, 3, 4)
    list.map((a:Int) => a + "")
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

* Powerful and easy parallel support

.. code-block:: scala

    list.par map(_ + 1)
    list.par filter(_ > 2)

----

Collections
===========

* For comprehensions

.. code-block:: scala

    for (i <- List(1, 3, 4, 7)) {
        // do something
    }

    for (i <- 1 until 10) {
        // do something
    }

    for {
        i <- List(1, 3, 4, 7)
        j <- List(2, 4, 6, 8)
        if i + j > 3
    } {
        // do something
    }

----

Tuples
======

* Returning two or more objects from a method
* Have to create a 'temporary' class just for that method

.. code-block:: java

    public TempObject<String, Integer> returnSomething() = {
        return new TempObject("foo", 1);
    }
    TempObject<String, Integer> t = returnSomething(1);
    t.first.substring(t.second);

* Scala

.. code-block:: scala

    def returnSomething(i:Int) = (i.toString, i)
    val (a, b) = returnSomething(1)
    // Type safe
    a.substring(b)
    val t = returnSomething(1)
    t._1.substring(t._2)

----

Pattern Matching
================

* Java 7 - now with New and Improved™ string switch/case

.. code-block:: java

    switch(string) {
        case "a": ... ; break;
        case "b": ... ; break;
        default: ...
    }

* Scala

.. code-block:: scala

    string match {
        case "a" => ...
        case "b" => ...
        case _ => ...
    }

.notes: No breaks

----

Pattern Matching
================

* But wait, there's more

.. code-block:: scala

    val t = (1, 2)
    t match {
      case (2, _) => ...
      case (_, 3) => ...
      case (2, 4) | (1, 3) => ...
      case (a, b) if a == b => ...
      case (a, b) if a > b => ...
      case _ => ...
    }

----

Traits
======

* Analogous to interfaces, but can have implementation as well.
* aka Mixins
* Multiple inheritance

.. code-block:: scala

    trait Ordered[A] {
        def compare(that: A): Int

        def <  (that: A): Boolean = (this compare that) <  0
        def >  (that: A): Boolean = (this compare that) >  0
        def <= (that: A): Boolean = (this compare that) <= 0
        def >= (that: A): Boolean = (this compare that) >= 0
    }

    class Something(val s:String) extends Ordered[String] {
        override compare(that:String) = s compare that
    }
    
    new Something("a") < "b"

----

Case Classes
============

.. code-block:: java

    public class Bean {
        private final int x;
        private final int y;

        public Bean(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int hashCode() {
            return x + y;
        }

        public String toString() {
            return "Bean(" + x + "," + y + ")";
        }
    }

----

Case Classes
============

.. code-block:: scala

    case class Bean(var x: Int, var y: Int)
    val bean = new Bean(1, 2)
    bean.x = 3

* Free hashCode/toString methods
* Free 'companion' object with apply
* Can be used in pattern matching

.. code-block:: scala

    val b = Bean(1, 2)
    b match {
      case Bean(3, 4) => ...
      case Bean(a, b) if a == b => ...
      case _ => ...
    }

----

Properties
==========

* What happens if you need to change behaviour of setter/getter?

.. code-block:: scala

    case class Bean(private var _x: Int, var y: Int) {
      def x = _x
      def x_=(v: Int) { _x = v }
    }
    bean.x = 3

----

Death to null
=============

* `"Billion-dollar mistake" <http://en.wikipedia.org/wiki/Pointer_%28computing%29#Null_pointer>`_
    - Tony Hoare - Algol W (1965)

* Who has written this?

.. code-block:: java

    Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
    Map<String, String> a = map.get("a");
    if (a != null) {
        String b = a.get("b");
        if (b != null) {
            doSomething(b)
        }
    }

-----

Death to null
^^^^^^^^^^^^^

.. code-block:: java

    /**
     * May return null
     */
    public String get(String key) {
        if (containsKey(value)) {
            return value;
        }
        return null;
    }

* Introducing Option (aka Maybe in Haskell)
* Collection of None or Some element

.. code-block:: scala

    def get(val:String):Option[String] = {
        if (containsKey(val)) {
            Some(value)
        } else {
            None // ie null
        }
    }

* Types as documentation
* Much more powerful than @NotNull annotation

-----

Death to null
^^^^^^^^^^^^^

* Better?

.. code-block:: scala

    val map = Map("a" -> Map("b" -> "c"))
    val a = map.get("a")
    if (a.isDefined) {
        val b = a.get.get("b")
        if (b.isDefined) {
            doSomething(b.get)
        }
    }

-----

Death to null
^^^^^^^^^^^^^

* Only joking. :)

.. code-block:: scala

    val map = Map("a" -> Map("b" -> "c"))
    for (a <- map.get("a"); b <- a.get("b")) {
        doSomething(b)
    }

* Just a collection - lots of useful functions

.. code-block:: scala

    val option:Option[String] = None
    val string:String = option.getOrElse("a")
    option.map(_ toUpperCase)
    option.filter(_ contains "x")
    option.foreach(println _)

* Nulls are only needed in Scala for Java compatibility

----

Pimp my library
===============

* Wouldn't it be nice if we could add methods to existing classes?
* Odersky - http://www.artima.com/weblogs/viewpost.jsp?thread=179766

.. code-block:: scala

    for (i <- 1 until 10) {
        // ...
    }
    
* 'until' is actually not defined on Int - RichInt

.. code-block:: scala

    class RichInt(val i:Int) {
        def until(j:Int):Range[Int] = ...
    }

    implicit def int2rich(i:Int):RichInt = new RichInt(i)
    
    for (i <- 1 until 10) ...
    
    for (i <- int2rich(1).until(10)) ...

* Searches the 'scope' for methods that return a type with missing method
* Very useful - but use with care!

----

What's the catch?
=================

* Slow compiler
* IDE support still lacking
* Traits depend on compiled version of library
    - ie Causes binary incompatibility

----

Resources
=========

* "Programming in Scala" by Martin Odersky, Lex Spoon, and Bill Venners
    - 1st Edition - http://www.artima.com/pins1ed/
    - 2nd Edition - http://www.amazon.com/Programming-Scala-Comprehensive-Step-Step/dp/0981531644
* Scala for Java Refugees
    - http://www.codecommit.com/blog/scala/roundup-scala-for-java-refugees
* Brisbane Functional Programming Group (BFPG)
    - http://www.bfpg.org/
    - Don't be afraid! Join us. :)

----

Conclusion
==========

* Scala is like Java, only better
* Much, much more - only scratched the surface
* Full compatibility with Java
* Start using it today!

----

Thank you
=========

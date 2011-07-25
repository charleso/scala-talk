Scala
=====

----

Introduction
============

* Martin Odersky
* "Scalable Language"
* "Multi-paradigm" programming language
    - Object Orientated
    - Functional
* `Big names <http://www.scala-lang.org/node/1658>`_
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

----

Companion Objects
=================

* Java

.. code-block:: java

    public class Main {
        
    }

* Scala

.. code-block:: scala

    object Main {
        def main(args:Array[String]) {
            println("Hello world")
        }
    }

.notes: Note the of semi-colon

----

Val vs Var
==========

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

    List<Map<String, String>> listOfMap = new ArrayList<>()
    List<Map<String, String>> listOfMap2 = listOfMap

* Scala

.. code-block:: scala

    val listOfMap = List(Map("a" -> "b"))
    val listOfMap2 = listOfMap // inferred

----

Syntax
======

.. code-block:: scala

    a.someMethod("value")
    a someMethod("value")
    a someMethod "value"

* Good for DSLs
* TODO Example

.. code-block:: scala

    1 + 2
    // Actually
    1.+(2)

----

Method names
============

.. code-block:: scala

    def +(i:Int, j:Int) = ...
    +(1, 2)
    // TODO

----

Inner defs
==========

.. code-block:: java

    public void someMethod() {
        helpMethod(1, 2)
        helpMethod(3, 4)
    }
    
    private int helpMethod(int i, int j) {
        return i + j
    }

* Relies on IDE to warn about unused

.. code-block:: scala

    def someMethod = {
        def helpMethod(i: Int, j: Int) = i + j
        helpMethod(1, 2)
        helpMethod(3, 4)
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

Collections
===========

* Very powerful

.. code-block:: java

    // TODO Google collection example

* Scala

.. code-block:: scala

    val list = List(1, 2, 3, 4)
    println(list.map((a:String) => a + 1))
    // List(2,3,4,5)
    println(list.map(_ + 1))

----

Tuples
======

* Java

.. code-block:: java

    public Tuple<String, Integer> returnSomething() = {
        return new Tuple("a", 1)
    }
    Tuple<String, Integer> t = returnSomething(1)
    println(t.first.contains("1"))

* Scala

.. code-block:: scala

    def returnSomething(i:Int) = (i.toString, i)
    val (a, b) = returnSomething(1)
    // Type safe
    println(a.contains("1"))
    val t = returnSomething(1)
    println(t._1.contains("1"))

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

.. code-block:: scala

    string bean {
        case Bean(1, 2) => ...
        case Bean(3, 4) => ...
        case _ => ...
    }

----

Objects
=======

* TODO

----

Functions
=========

* TODO

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

.notes: Cheating - you _can_ 'generate' getters/settings in Java

----

Properties
==========

.. code-block:: scala

    case class Bean(private var _x: Int, var y: Int) {
      def x = _x
      def x_=(v: Int) { _x = v }
    }
    // Still works
    bean.x = 3

----

Death to null
=============

* Who has written this?

.. code-block:: java

    String a = someMethod()
    String b = someOtherMethod()
    if (a != null && b != null && 
        a.contains("hello") && b.contains("world")) {
        doSomething()
    }

-----

Death to null
^^^^^^^^^^^^^

* TODO - Do I want to show this?!?

.. code-block:: scala

    trait Option[T]
    case class Some[T](val t:T) extends Option[T]
    case object None extends Option[Nothing]

-----

Death to null
^^^^^^^^^^^^^

.. code-block:: scala

    val value = Some("not null")

-----

Death to null
^^^^^^^^^^^^^

* TODO - Bad example!

.. code-block:: scala

    for {
        a <- someMethod
        b <- someOtherMethod
        if (a contains "hello")
        if (b contains "world")
    } {
        doSomething
    }

----

Pimp my library
===============

* Wouldn't it be nice if we could add methods to existing classes?

.. code-block:: scala

    for (i <- 1 until 10) {
        // ...
    }
    
* 'until' is actually not defined on Int
    - RichInt

.. code-block:: scala

    class RichInt(val i:Int) {
        def plus(j:Int):Int = 1 + j
    }

    implicit def int2rich(i:Int):RichInt = new RichInt(i)
    
    println(1 plus 2)
    // 3

* Searches the 'scope' for methods that return a type with missing method
* Very useful - but use with care!

----

Conclusion
==========

* Scala is awesome
* 99% Backwards compatibility with Java
* Start tomorrow!

----

Resources
=========

* "Programming in Scala" by Martin Odersky, Lex Spoon, and Bill Venners
    - 1st Edition
        - Free
        - http://www.artima.com/pins1ed/
    - 2nd Edition
        - Updated collection chapters for Scala 2.8
        - http://www.amazon.com/Programming-Scala-Comprehensive-Step-Step/dp/0981531644
* Scala for Java Refugees
    - http://www.codecommit.com/blog/scala/roundup-scala-for-java-refugees
* Brisbane Functional Programming Group (BFPG)
    - http://www.bfpg.org/
    - Don't be afraid! Join us. :)

----

Thank you
=========

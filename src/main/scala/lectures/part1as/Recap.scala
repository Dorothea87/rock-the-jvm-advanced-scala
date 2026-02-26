package lectures.part1as

import scala.annotation.tailrec
import scala.jdk.Accumulator

object Recap extends App {
  val aCondition: Boolean = false
  val aConditionedVal = if (aCondition) 42 else 65
  // instructions vs expressions

  //compiler infers types for us
  val aCodeBlock = {
    if (aCondition) 54
    56
  }

  //unit (side effects only)
  val theUnit = println("hello, Scala")

  //function
  def aFunction(x: Int): Int = x + 1

  //recursion: stack and tail
  @tailrec def factorial(n: Int, accumulator: Int): Int =
    if (n <= 0) accumulator
    else factorial(n - 1, n * accumulator)

  //object-oriented programming

  class Animal

  class Dog extends Animal

  val aDog: Animal = new Dog //subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch!")
  }

  //method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog //natural language pattern

  //anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar!")
  }

  //generics
  abstract class MyList[+A] //variance and variance problems in THIS course
  //singletons and companions
  object MyList

  //case classes
  case class Person(name: String, age: Int)

  //exceptions and try/catch/finally
  val throwsException = throw new RuntimeException() //type is nothing, can't be instantiated
  val aPotentialFailure = try {
    throw new RuntimeException {
    } catch {
      case e: Exception => "I caught an exception"
    } finally {
      println("some logs")
    }
  }

  //packaging and imports
  
  //functional programming
  val incrementer = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }
  incrementer(1)
  
  val anonymousIncrementer = (x: Int) => x + 1
  List(1, 2, 3).map(anonymousIncrementer)  //higher order functions
  
  //map, flatMap, filter
  
  //for-comprehension
  val pairs = for {
    num <- List(1, 2, 3) //if condition
    char <- List('a', 'b', 'c')
  } yield num + "-" + char
  
  //Scala collections: Seq, Array, Vectors, Maps, Tuples
  val aMap = Map(
    "Daniel" -> 789,
    "Jess" -> 555
  )
  
  //"collections": Option, Try
  val anOption = Some(2)
  
  //pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x + "th"
  }
  
  val bob = Person("bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
  }
}

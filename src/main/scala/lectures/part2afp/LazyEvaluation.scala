package lectures.part2afp

object LazyEvaluation extends App {

//  lazy val x: Int = throw new RuntimeException //only evaluated once but only one time
//  //delays the evaluation of values, only on a by need basis
  //only evaluated when needed, runtime will detect if necessary or not and might not evaluate it
//  println(x)

  lazy val x: Int = {
    println("hello")
    42
  }
  println(x)
  println(x)

  //examples of implications:
  //side effects
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simplCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simplCondition && lazyCondition) "yes" else "no")

  //in conjunction with call by name
  def byNameMethod(n: => Int):Int = {
    lazy val t = n //only once
    t + t + t + 1 // <= call by need
  }

  def retrieveMAgicValue: Int = {
    //side effect or long computation
    println("waiting")
    Thread.sleep(1000)
    42
  }
  println(byNameMethod(retrieveMAgicValue))

  //use lazy vals

  //filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less then 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is less then 20?")
    i > 20
  }
  val numbers = List(1, 25, 40, 5, 23)
  val lt30 =  numbers.filter(lessThan30)
  val gt20 =  lt30.filter(greaterThan20)
  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30) //lazy vals under the hood
  val gt20lazy = lt30lazy.withFilter(greaterThan20)
  println
  gt20lazy.foreach(println)

  //for-comprehensions uns withFilter with guards
  for {
    a <- List(1, 2, 3) if a % 2 == 0 //use lazy vals!
  } yield a + 1
  List(1, 2, 3).withFilter(_ % 2 == 0 ).map(_ + 1) // List[Int]

  

}

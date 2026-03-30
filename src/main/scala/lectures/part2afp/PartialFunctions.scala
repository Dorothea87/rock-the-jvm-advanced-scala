package lectures.part2afp

object PartialFunctions extends App {
  val aFunction = (x: Int) => x + 1 //Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x ==5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }
  //{1, 2, 5} => Int, partial function from Int to Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } //partial function value

  println(aPartialFunction(2))

  //PF utilities
  println(aPartialFunction.isDefinedAt(67))

  //lift
  val lifted = aPartialFunction.lift //Int => Option[Int]
  println(lifted(2))
  println(lifted(98))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67 //takes another partial function as argument
  }

  println(pfChain(2))
  println(pfChain(45))

  //PF extend normal functions
  val atotalFunction: Int => Int = {
  case 1 => 99
  }
  //HOFs accept partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  /*
  Note: pf can only have one parameter type
   */

  /*
  *Exercises
  1 - construct a PF instance yourself (anonymous class)
  2 - implement a small chatbot as a partial function
   */
  val aConversation: PartialFunction[String, String] = {
    case "hello" => "How are you?"
    case "good" => "How lovely. How is the weather?"
    case "rainy" => "That's a shame"
    case "sunny" => "How nice, now log off and enjoy the good weather"
  }
  scala.io.Source.stdin.getLines().takeWhile(_ != "quit").foreach { line =>
    if(aConversation.isDefinedAt(line))
      println(aConversation(line))

    else
      println("Sorry, I don't understand that")
  }




}

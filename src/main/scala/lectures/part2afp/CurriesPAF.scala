package lectures.part2afp

object CurriesPAF extends App {
  //curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) //curried function

  def curriedAdder(x: Int)(y: Int): Int = x + y //curried method

  val add4: Int => Int = curriedAdder(4)
  //lifting = ETA-Expansion
  //functions != methods (JVM limitation)

  def inc(x: Int) = x + 1
  List(1, 2, 3).map(inc) //ETA-expansion (x => inc(x))

  //partial function application
  val add5 = curriedAdder(5) _ //do the eta expansion Int => Int
  //Exercise
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMEthod(x: Int, y: Int): Int = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  //add7: Int => Int = y => 7 + y
  // as many different implementations of add7 as you can -> be creative

  val add7 = (x: Int) => simpleAddFunction(7, x)
  val add7_1 = (x: Int) => simpleAddMEthod(7, x)
  val add7_2 = (x: Int) => curriedAddMethod(x)(7)

  val add7_3 = simpleAddFunction.curried(7)

  val add7_4 = curriedAddMethod(7) _ //PAF
  val add7_5 = curriedAddMethod(7)(_) //PAF = alternative syntax

  val add7_6 = simpleAddMEthod(7, _: Int) //alternative syntax for turning methods into function values
  // y => simpleAddMethod(7, y)

  val add7_7 = simpleAddFunction(7, _: Int)


  //underscores are powerful
  def concatenator(a: String, b: String, c: String): String = a + b + c
  val insertName = concatenator("Hello, I am ", _: String, ", how are you?") //x" String => concatenator(hello, x hay?)
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("hello, ", _: String, _: String)
  println(fillInTheBlanks("Dorothea", " Scala rocks"))

  //Exercises
  //1 - Process a list of numbers and return their string representations with different formats
  //- use %4.2f, %8.6f and %14.12f with a curreid formater function ("%4.2".format(Math.PI))

  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _ //lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(simpleFormat))
  println(numbers.map(seriousFormat))
  println(numbers.map(preciseFormat))

  //2 - difference between functions vs methods and parameters vs 0-lambda
  // - define two methods
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  //calling byName and byFunction with the following expressions: Int, method, parenMethod, lambda and PAF - do they compile and why?
  byName(23) // ok
  byName(method) //ok
  byName(parenMethod()) // ok
  // byName(parenMethod) does not compile
  //byName(() => 42) not the same as a function parameter
  byName((() => 42)()) //ok
  // byName(parenMethod _)

  // byFunction(45) expects a lambda
  //byFunction(method) parameterless method because it evaluates to 42 won't compile, compiler doesn't do ETA exp here
  byFunction(parenMethod) //does ETA expansion
  byFunction(() => 46) //works because it is function value
  byFunction(parenMethod _) //unnecessary because compiler knows that it has to do eta exp.
}

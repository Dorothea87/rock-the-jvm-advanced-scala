package exercises

import scala.annotation.tailrec

//Exercise: implement a lazily evaluated singly linked stream of elements.
abstract class MyStream[A] {
  def isEmpty: Boolean

  def head: A

  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] //prepend operator

  def ++[B >: A](anotherStream: B): MyStream[B] //concatenate 2 streams

  def foreach(f: A => Unit): Unit

  def map[B](f: A => B): MyStream[B]

  def flatMap[B](f: A => MyStream[B]): MyStream[B]

  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // takes the first n elements out of this stream, finite stream

  def takeAsList(n: Int): List[A]

  @tailrec
  final def toList[B >: A](acc: List[A] = Nil): List[B] =
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
}

object EmptyStream extends MyStream[Nothing] {
  //Exercise: implement a lazily evaluated singly linked stream of elements.
  def isEmpty: Boolean = true

  def head: Nothing = throw new NoSuchElementException

  def tail: MyStream[Nothing] = throw new NoSuchElementException

  def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)//prepend operator

  def ++[B >: Nothing](anotherStream: B): MyStream[B] = MyStream[B] = anotherStream//concatenate 2 streams

  def foreach(f: Nothing => Unit): Unit = ()

  def map[B](f: Nothing => B): MyStream[B] =  this

  def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  def take(n: Int): MyStream[Nothing] = this // takes the first n elements out of this stream, finite stream

  def takeAsList(n: Int): List[Nothing] = Nil
}

class Cons[+A](hd: A, tail: => MyStream[A]) extends MyStream[A] {
  def isEmpty: Boolean = false

  override val head: A = hd

  override lazy val tail: MyStream[A] = tl //call by need

  def #::[B >: A](element: B): MyStream[B]  = new Cons(element, this)//prepend operator

  def ++[B >: A](anotherStream: B): MyStream[B] = new Cons(head, tail ++ anotherStream)//concatenate 2 streams

  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  def map[B](f: A => B): MyStream[B] = new Cons(f(head), tail.map(f)) //preserves lazy evaluation

  def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

  def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new Cons(head, tail.filter(predicate))
    else tail.filter(predicate) //preserves lazy evaluation

  def take(n: Int): MyStream[A] = // takes the first n elements out of this stream, finite stream
    if (n <= 0) EmptyStream
    else if (n ==1) new Cons(head, EmptyStream)
    else new Cons(head, tail.take(n-1))

  def takeAsList(n: Int): List[A]
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons(start, MyStream.from(generator(start)))
}

object StreamPlayground extends App {

}

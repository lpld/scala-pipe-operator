package com.github.lpld.pipeoperator

import cats.{Functor, Monad}
import com.github.lpld.pipeoperator.pipe._

import scala.concurrent.duration.DurationLong
import scala.concurrent.{Await, ExecutionContext, Future}
import ExecutionContext.Implicits._

/**
  * @author leopold
  * @since 3/10/18
  */
object PipeDemo extends App {

  "Hi there" |> println

  // or:
  val x = 5
  x >= 5 |> require

  // doesn't make much sense with methods:
  List(1, 2, 3) |> (_.map(_ * 10)) |> (_.mkString) |> println

  // playing with futures:
  val await: Future[_] ⇒ Unit = Await.result(_, 5.seconds)

  Thread.currentThread().getName |> println

  Future { Thread.currentThread().getName |> println } |> await

  // apparently this doesn't work, println is executed in main thread:
  Thread.currentThread().getName |> println |> (Future.apply[Unit](_)) |> await

  // playing with cats:
  import cats._, instances.all._, syntax.all._
  import language.higherKinds

  // if we use parentheses, we can make multiline expressions with pipe operator:
  (1 :: 2 :: 3 :: 4 :: 5 :: Nil
   |> map(_ * 2)
   |> flatMap(x ⇒ List(x, x * 10, x * 100))
   |> foldMap(_.show)
   |> println
  )

  // but we have to redefine all these functions to take parameters in different order:
  def map[F[_] : Functor, A, B](f: A ⇒ B)(fa: F[A]): F[B] = Functor[F].fmap(fa)(f)
  def flatMap[F[_] : Monad, A, B](f: A ⇒ F[B])(fa: F[A]): F[B] = Monad[F].flatMap(fa)(f)
  def foldMap[F[_] : Foldable, A, B: Monoid](f: A ⇒ B)(fa: F[A]): B = Foldable[F].foldMap(fa)(f)
}

package ai.nodesense.monads.Monads

// Run this with: scala Monad.scala

trait Monad[M[_]] {
  // in haskell, called return, but that's a reserved word
  // constructs a Monad instance from the given value, e.g. List(1)
  def apply[T](v: T): M[T]
  // flatMap function, in scala:
  def bind[T,U](m: M[T])(fn: (T) => M[U]): M[U]
  // Laws these must follow are:
  // identities:
  //  bind(apply(x))(fn) == fn(a)

  //  bind(m)(apply _) == m
  // associativity on bind (you can either bind f first, or f to g:
  //  bind(bind(m)(f))(g) == bind(m) { x => bind(f(x))(g) }
}

object Monad {
  def apply[M[_]](implicit monad: Monad[M]): Monad[M] = monad
  def bind[M[_],T,U](m: M[T])(fn: (T) => M[U])(implicit monad: Monad[M]) = monad.bind(m)(fn)

  // Some instances of the Monad typeclass:
  implicit val listMonad: Monad[List] = ListMonad
  implicit val optionMonad: Monad[Option] = OptionMonad

  // Set up the syntax magic (allow .pure[Int] syntax
  // and >>= like Haskell): bind method
  implicit def pureOp[A](a: A) = new PureOp(a)
  implicit def operators[A,M[A]](m: M[A])(implicit monad: Monad[M]) =
    new MonadOperators(m)(monad)
}

// This is the enrichment pattern to allow syntax like: 1.pure[List] == List(1)
// if we put a pure method in Monad, it would take two type parameters, only one
// of which could be inferred, and that' annoying to write Monad.pure[Int,List](1)
class PureOp[A](a: A) {
  def pure[M[_]](implicit monad: Monad[M]) = monad(a)
}

class MonadOperators[A,M[A]](m: M[A])(implicit monad: Monad[M]) {
  // This is called fmap in haskell (and in Functor, not Monad)
  def map[U](fn: (A) => U): M[U] = flatMap { (a: A) => monad(fn(a)) }
  def flatMap[U](fn: (A) => M[U]): M[U] = monad.bind(m)(fn)

  // Haskell syntax
  // to link Bind method
  def >>=[U](fn: (A) => M[U]) = flatMap(fn)
  def >>[T,U](m: M[U]) = flatMap { _ => m }
}

//////////////////
// Implementations:

object ListMonad extends Monad[List] {
  def apply[T](v: T) = List(v)
  def bind[T,U](m: List[T])(fn: (T) => List[U]) = m.flatMap(fn)
}

object OptionMonad extends Monad[Option] {
  def apply[T](v: T) = Option(v)
  def bind[T,U](o: Option[T])(fn: (T) => Option[U]) = o.flatMap(fn)
}

/////////////////
// Example:

object MonadExampleApp extends App {

  import Monad._

  println(List(1,2,3,4) >>= { v => (v * 2).pure[List] })
  println(Option(1) >>= { v => (v * 2).pure[Option] })

  //bind(m)(apply _) == m

  println(ListMonad.bind(List(1,2,3,4))(ListMonad apply _) == List(1,2,3,4))

  //println(bind(List(1,2,3,4))) (apply) == List(1,2,3,4));






}
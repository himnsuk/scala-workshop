package workshop.section01_basics.lesson03.methods

/*
  Variable number of arguments

 */
object VarArgs extends App {

  def printAll(names: String*)  {
    names.foreach(println);
  }

  def printNumbers(numbers: Int*): Unit = {
    numbers.foreach(println)
  }

  printAll(); // error due to multiple default
  printAll("Hello");
  printAll("Welcome", "to", "Scala")
  printNumbers(1, 2, 3)

  // List to splat/spread, making an List to spread

  // only works with * arguments
  val fruits = List("Apple", "Orange", "Mango")
  printAll(fruits: _*)


  printNumbers(List(100, 200, 300): _*)

}

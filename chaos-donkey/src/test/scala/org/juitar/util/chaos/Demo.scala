package org.juitar.util.chaos

import java.text.SimpleDateFormat
import java.util.Date

object Demo extends App {

  import ChaosGenerators._
  import org.juitar.util.chaos.Chaos._

  implicit val chaosContext = new DefaultContext(
    donkeyName = "BilAm",
    awake = true,
    config = Map(
      "org.juitar.util.chaos.sleep" -> "3 second",
      "org.juitar.util.chaos.memory" -> (1024 * 1024 * 10).toString
    )
  )
  implicit val sleep: DoSomethingStupid = everyNCalls(2, ChaosGenerators.sleep)
  implicit val `throw`: DoSomethingStupid = everyNCalls(3, ChaosGenerators.exception)
  implicit val memory: DoSomethingStupid = eatMemory(chaosContext)

  info("Executing sleepy...")
  sleepy() withChaosDonkey sleep
  info("Executing throwy...")
  throwy() withChaosDonkey `throw`
  info("Executing memy...")
  memy() withChaosDonkey memory
  info("Executing sleepy...")
  sleepy() withChaosDonkey sleep
  info("Executing throwy...")
  throwy() withChaosDonkey `throw`
  info("Executing memy...")
  memy() withChaosDonkey memory
  info("Executing sleepy...")
  sleepy() withChaosDonkey sleep
  info("Executing memy...")
  memy() withChaosDonkey memory
  info("Executing sleepy...")
  sleepy() withChaosDonkey sleep
  info("Executing throwy...")
  throwy() withChaosDonkey `throw`


  def sleepy(): Unit = {}
  def throwy(): Unit = {}
  def memy(): Unit = {}
  def info(msg: => String) = println(s"${new SimpleDateFormat("mm:ss.SSS").format(new Date())}: $msg"); System.out.flush()
}

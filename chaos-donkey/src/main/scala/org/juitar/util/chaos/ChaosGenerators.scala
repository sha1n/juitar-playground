package org.juitar.util.chaos

import org.juitar.util.chaos.Chaos.DoSomethingStupid

import scala.collection.immutable.IndexedSeq
import scala.concurrent.duration._

object ChaosGenerators {

  def exception(ctx: ChaosContext) = throw new RuntimeException(s"This is a preset from Chaos Donkey ${ctx.donkeyName}")

  def sleep(ctx: ChaosContext) = Thread sleep Duration(ctx.config("org.juitar.util.chaos.sleep")).toMillis

  def eatMemory(ctx: ChaosContext): DoSomethingStupid = {
    val mem = ctx.config("org.juitar.util.chaos.memory").toInt / 8
    @volatile var bytes: IndexedSeq[Any] = IndexedSeq()

    def allocate(ctx: ChaosContext): Unit =
      bytes = bytes ++ "".padTo(mem / 2, "1")

    allocate
  }

  def everyNCalls(n: Int, f: DoSomethingStupid)(implicit ctx: ChaosContext): DoSomethingStupid = {
    @volatile var count = 0

    def everyN(ctx: ChaosContext): Unit = {
      count += 1

      if (count % n == 0) f.apply(ctx)
    }


    everyN
  }

}

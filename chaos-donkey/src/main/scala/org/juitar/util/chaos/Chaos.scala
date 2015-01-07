package org.juitar.util.chaos

object Chaos {

  type DoSomethingStupid = (ChaosContext) => Unit

  def runWithChaosDonkey[T <: Any](action: => T, doSomethingStupid: DoSomethingStupid)(implicit chaosContext: ChaosContext): T = {
    if (chaosContext.awake) doSomethingStupid(chaosContext)
    action
  }

  implicit class TimeSamplerWithTitle[T <: Any](action: => T) {

    def withChaosDonkey(doSomethingStupid: DoSomethingStupid)(implicit chaosContext: ChaosContext): T =
      runWithChaosDonkey(action, doSomethingStupid)
  }
}

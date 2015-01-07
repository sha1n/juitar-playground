package org.juitar.util.chaos

trait ChaosContext {
  def donkeyName: String
  def awake: Boolean
  def config: Map[String, String]
}

case class DefaultContext(donkeyName: String, awake: Boolean, config: Map[String, String] = Map()) extends ChaosContext

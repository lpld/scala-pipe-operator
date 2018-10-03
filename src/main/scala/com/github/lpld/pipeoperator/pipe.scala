package com.github.lpld.pipeoperator

/**
  * @author leopold
  * @since 3/10/18
  */
object pipe {

  implicit class PipeOps[T](x: ⇒ T) {

    def |>[U](f: T ⇒ U) = f(x)
  }
}

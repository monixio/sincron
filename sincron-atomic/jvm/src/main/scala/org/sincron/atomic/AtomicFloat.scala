/*
 * Copyright (c) 2016 by its authors. Some rights reserved.
 * See the project homepage at: https://sincron.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sincron.atomic

import org.sincron.atomic.PaddingStrategy.NoPadding
import org.sincron.atomic.boxes.{Factory, BoxedInt}
import scala.annotation.tailrec
import java.lang.Float.{intBitsToFloat, floatToIntBits}

final class AtomicFloat private (private[this] val ref: BoxedInt)
  extends AtomicNumber[Float] {

  def get: Float = intBitsToFloat(ref.volatileGet())
  def set(update: Float): Unit = ref.volatileSet(floatToIntBits(update))
  def lazySet(update: Float): Unit = ref.lazySet(floatToIntBits(update))

  def compareAndSet(expect: Float, update: Float): Boolean = {
    val expectLong = floatToIntBits(expect)
    val updateLong = floatToIntBits(update)
    ref.compareAndSet(expectLong, updateLong)
  }

  def getAndSet(update: Float): Float = {
    intBitsToFloat(ref.getAndSet(floatToIntBits(update)))
  }

  @tailrec
  def increment(v: Int = 1): Unit = {
    val current = get
    val update = incrementOp(current, v)
    if (!compareAndSet(current, update))
      increment(v)
  }

  @tailrec
  def add(v: Float): Unit = {
    val current = get
    val update = plusOp(current, v)
    if (!compareAndSet(current, update))
      add(v)
  }

  @tailrec
  def incrementAndGet(v: Int = 1): Float = {
    val current = get
    val update = incrementOp(current, v)
    if (!compareAndSet(current, update))
      incrementAndGet(v)
    else
      update
  }

  @tailrec
  def addAndGet(v: Float): Float = {
    val current = get
    val update = plusOp(current, v)
    if (!compareAndSet(current, update))
      addAndGet(v)
    else
      update
  }

  @tailrec
  def getAndIncrement(v: Int = 1): Float = {
    val current = get
    val update = incrementOp(current, v)
    if (!compareAndSet(current, update))
      getAndIncrement(v)
    else
      current
  }

  @tailrec
  def getAndAdd(v: Float): Float = {
    val current = get
    val update = plusOp(current, v)
    if (!compareAndSet(current, update))
      getAndAdd(v)
    else
      current
  }

  @tailrec
  def subtract(v: Float): Unit = {
    val current = get
    val update = minusOp(current, v)
    if (!compareAndSet(current, update))
      subtract(v)
  }

  @tailrec
  def subtractAndGet(v: Float): Float = {
    val current = get
    val update = minusOp(current, v)
    if (!compareAndSet(current, update))
      subtractAndGet(v)
    else
      update
  }

  @tailrec
  def getAndSubtract(v: Float): Float = {
    val current = get
    val update = minusOp(current, v)
    if (!compareAndSet(current, update))
      getAndSubtract(v)
    else
      current
  }

  def decrement(v: Int = 1): Unit = increment(-v)
  def decrementAndGet(v: Int = 1): Float = incrementAndGet(-v)
  def getAndDecrement(v: Int = 1): Float = getAndIncrement(-v)

  private[this] def plusOp(a: Float, b: Float): Float = a + b
  private[this] def minusOp(a: Float, b: Float): Float = a - b
  private[this] def incrementOp(a: Float, b: Int): Float = a + b
}

object AtomicFloat {
  def apply(initialValue: Float)(implicit strategy: PaddingStrategy = NoPadding): AtomicFloat =
    new AtomicFloat(Factory.newBoxedInt(
      floatToIntBits(initialValue),
      boxStrategyToPaddingStrategy(strategy)))
}


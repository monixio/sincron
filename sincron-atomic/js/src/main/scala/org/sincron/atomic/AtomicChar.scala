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

final class AtomicChar private[atomic]
  (initialValue: Char) extends AtomicNumber[Char] {

  private[this] var ref = initialValue
  private[this] val mask = 255 + 255 * 256

  def getAndSet(update: Char): Char = {
    val current = ref
    ref = update
    current
  }

  def compareAndSet(expect: Char, update: Char): Boolean = {
    if (ref == expect) {
      ref = update
      true
    }
    else
      false
  }

  def set(update: Char): Unit = {
    ref = update
  }

  def get: Char = ref

  def getAndSubtract(v: Char): Char = {
    val c = ref
    ref = ((ref - v) & mask).asInstanceOf[Char]
    c
  }

  def subtractAndGet(v: Char): Char = {
    ref = ((ref - v) & mask).asInstanceOf[Char]
    ref
  }

  def subtract(v: Char): Unit = {
    ref = ((ref - v) & mask).asInstanceOf[Char]
  }

  def getAndAdd(v: Char): Char = {
    val c = ref
    ref = ((ref + v) & mask).asInstanceOf[Char]
    c
  }

  def getAndIncrement(v: Int = 1): Char = {
    val c = ref
    ref = ((ref + v) & mask).asInstanceOf[Char]
    c
  }

  def addAndGet(v: Char): Char = {
    ref = ((ref + v) & mask).asInstanceOf[Char]
    ref
  }

  def incrementAndGet(v: Int = 1): Char = {
    ref = ((ref + v) & mask).asInstanceOf[Char]
    ref
  }

  def add(v: Char): Unit = {
    ref = ((ref + v) & mask).asInstanceOf[Char]
  }

  def increment(v: Int = 1): Unit = {
    ref = ((ref + v) & mask).asInstanceOf[Char]
  }

  def decrement(v: Int = 1): Unit = increment(-v)
  def decrementAndGet(v: Int = 1): Char = incrementAndGet(-v)
  def getAndDecrement(v: Int = 1): Char = getAndIncrement(-v)
}

object AtomicChar {
  def apply(initialValue: Char): AtomicChar =
    new AtomicChar(initialValue)
}

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

package org.sincron.macros

import minitest.SimpleTestSuite
import org.sincron.macros.test.TestFunctionInlineMacros


object FunctionInlinerSuite extends SimpleTestSuite{

  test("should inline a symbol in a block of code") {
    assert(TestFunctionInlineMacros.testInlineParamMacro())
  }

  test("inline a function") {
    assert(TestFunctionInlineMacros.testInlineMacro())
  }

  test("inline a function with 2 params") {
    assert(TestFunctionInlineMacros.testInlineMultipleArgsMacro())
  }
}

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

package org.sincron.atomic.boxes.right64Java8;

import org.sincron.misc.UnsafeAccess;
import java.lang.reflect.Field;

abstract class BoxedIntImpl implements org.sincron.atomic.boxes.BoxedInt {
    public volatile int value;
    public static final long OFFSET;

    static {
        try {
            Field field = BoxedIntImpl.class.getDeclaredField("value");
            OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(field);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public BoxedIntImpl(int initialValue) {
        this.value = initialValue;
    }

    public int volatileGet() {
        return value;
    }

    public void volatileSet(int update) {
        value = update;
    }

    public void lazySet(int update) {
        UnsafeAccess.UNSAFE.putOrderedInt(this, OFFSET, update);
    }

    public boolean compareAndSet(int current, int update) {
        return UnsafeAccess.UNSAFE.compareAndSwapInt(this, OFFSET, current, update);
    }

    public int getAndSet(int update) {
        return UnsafeAccess.UNSAFE.getAndSetInt(this, OFFSET, update);
    }
}

public final class BoxedInt extends BoxedIntImpl {
    public volatile long p1, p2, p3, p4, p5, p6 = 7;
    public long sum() { return p1 + p2 + p3 + p4 + p5 + p6; }

    public BoxedInt(int initialValue) {
        super(initialValue);
    }
}

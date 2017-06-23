/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package ru.andreych;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class MyBenchmark {

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public List<Integer> add(TestData d) {
    List<Integer> target = new ArrayList<>();
    d.objects.stream().map(i -> i / 2).forEach(target::add);
    return target;
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public List<Integer> addNoMap(TestData d) {
    List<Integer> target = new ArrayList<>();
    d.objects.stream().forEach(i -> target.add(i / 2));
    return target;
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public List<Integer> addNoStream(TestData d) {
    List<Integer> target = new ArrayList<>();
    d.objects.forEach(i -> target.add(i / 2));
    return target;
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public List<Integer> addLoop(TestData d) {
    List<Integer> target = new ArrayList<>();
    for (Integer i : d.objects) {
      target.add(i / 2);
    }
    return target;
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public List<Integer> addAll(TestData d) {
    final List<Integer> source = d.objects.stream().map(i -> i / 2).collect(Collectors.toList());
    List<Integer> target = new ArrayList<>();
    target.addAll(source);
    return target;
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public List<Integer> addAllOnInit(TestData d) {
    final List<Integer> source = d.objects.stream().map(i -> i / 2).collect(Collectors.toList());
    return new ArrayList<>(source);
  }

  @State(Scope.Thread)
  public static class TestData {

    List<Integer> objects;

    @Setup(Level.Iteration)
    public void setUp() {
      objects = new ArrayList<>(5000);
      Random r = new Random();
      for (int i = 0; i < 5000; i++) {
        objects.add(r.nextInt());
      }
    }
  }
}

/*
 * Copyright (C) 2014 Pedro Vicente GÃ³mez SÃ¡nchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pedrovgs.mincostmaxflow;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinCostMaxFlowLLMTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNegativeMaxFlowOption() {
    MinCostMaxFlow flow = new MinCostMaxFlow(2);
    flow.addEdge(0, 1, 1, 1);
    MinCostMaxFlow.FlowOptions options = MinCostMaxFlow.FlowOptions.defaults().withMaxFlow(-1);
    flow.minCostMaxFlow(0, 1, options);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectOutOfBoundsSourceInMinCostMaxFlow() {
    MinCostMaxFlow flow = new MinCostMaxFlow(2);
    flow.minCostMaxFlow(-1, 1);
  }

  @Test
  public void shouldReturnZeroWhenSinkIsUnreachable() {
    MinCostMaxFlow flow = new MinCostMaxFlow(3);
    flow.addEdge(0, 1, 1, 1);
    Pair<Integer, Long> result = flow.minCostMaxFlow(0, 2);
    assertEquals(new Pair<Integer, Long>(0, 0L), result);
  }

  @Test
  public void shouldWorkWithBellmanFordInitialPotentials() {
    MinCostMaxFlow flow = new MinCostMaxFlow(4);
    flow.addEdge(0, 1, 2, 2);
    flow.addEdge(0, 2, 1, 6);
    flow.addEdge(1, 2, 1, 2);
    flow.addEdge(1, 3, 1, 1);
    flow.addEdge(2, 3, 2, 2);
    MinCostMaxFlow.FlowOptions options = MinCostMaxFlow.FlowOptions.defaults()
        .withBellmanFordInitPotentials(true);

    Pair<Integer, Long> result = flow.minCostMaxFlow(0, 3, options);
    assertEquals(new Pair<Integer, Long>(3, 17L), result);
  }

  @Test
  public void shouldWorkWithConservationCheckEnabled() {
    MinCostMaxFlow flow = new MinCostMaxFlow(4);
    flow.addEdge(0, 1, 2, 2);
    flow.addEdge(0, 2, 1, 6);
    flow.addEdge(1, 2, 1, 2);
    flow.addEdge(1, 3, 1, 1);
    flow.addEdge(2, 3, 2, 2);
    MinCostMaxFlow.FlowOptions options = MinCostMaxFlow.FlowOptions.defaults()
        .withConservationCheck(true);

    Pair<Integer, Long> result = flow.minCostMaxFlow(0, 3, options);
    assertEquals(new Pair<Integer, Long>(3, 17L), result);
  }
}

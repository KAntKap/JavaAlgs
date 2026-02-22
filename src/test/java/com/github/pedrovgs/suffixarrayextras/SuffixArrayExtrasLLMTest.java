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
package com.github.pedrovgs.suffixarrayextras;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SuffixArrayExtrasLLMTest {

  @Test
  public void shouldFindLongestRepeatedSubstring() {
    assertEquals("ana", SuffixArrayExtras.longestRepeatedSubstringNaive("banana"));
    assertEquals("", SuffixArrayExtras.longestRepeatedSubstringNaive("abcd"));
  }

  @Test
  public void shouldComputeZAlgorithm() {
    int[] z = SuffixArrayExtras.zAlgorithm("aabcaabxaaaz");
    assertArrayEquals(new int[] {0, 1, 0, 0, 3, 1, 0, 0, 2, 2, 1, 0}, z);
  }

  @Test
  public void shouldComputePrefixFunction() {
    int[] pi = SuffixArrayExtras.prefixFunction("ababcababd");
    assertArrayEquals(new int[] {0, 0, 1, 2, 0, 1, 2, 3, 4, 0}, pi);
  }

  @Test
  public void shouldRoundTripBurrowsWheelerTransform() {
    SuffixArrayExtras.BwtResult result = SuffixArrayExtras.burrowsWheelerTransform("banana");
    assertEquals("banana", SuffixArrayExtras.inverseBurrowsWheeler(result));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNullForLongestRepeatedSubstring() {
    SuffixArrayExtras.longestRepeatedSubstringNaive(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNullForInverseBurrowsWheeler() {
    SuffixArrayExtras.inverseBurrowsWheeler(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNullForZAlgorithm() {
    SuffixArrayExtras.zAlgorithm(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNullForPrefixFunction() {
    SuffixArrayExtras.prefixFunction(null);
  }
}

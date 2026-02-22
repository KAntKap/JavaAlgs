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
package com.github.pedrovgs.ahocorasickadvanced;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AhoCorasickAdvancedLLMTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectEmptyPatternWhenStrictValidationEnabled() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.setStrictValidation(true);
    ac.addPattern("", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectZeroWeightWhenStrictValidationEnabled() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.setStrictValidation(true);
    ac.addPattern("a", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNegativeWeightWhenStrictValidationEnabled() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.setStrictValidation(true);
    ac.addPattern("a", -1);
  }

  @Test(expected = IllegalStateException.class)
  public void shouldRejectBuildWithoutPatternsWhenStrictValidationEnabled() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.setStrictValidation(true);
    ac.build();
  }

  @Test(expected = IllegalStateException.class)
  public void shouldRejectSearchBeforeBuild() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.searchAll("abc");
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectNegativeSearchLimit() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.addPattern("a", 1);
    ac.build();
    ac.searchAll("aaa", -1);
  }

  @Test
  public void shouldReturnEmptyListWhenSearchLimitIsZero() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.addPattern("a", 1);
    ac.build();
    assertTrue(ac.searchAll("aaa", 0).isEmpty());
  }

  @Test
  public void shouldLimitSearchResults() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.addPattern("a", 1);
    ac.addPattern("aa", 2);
    ac.build();
    assertEquals(1, ac.searchAll("aaaa", 1).size());
  }

  @Test
  public void shouldReturnNullForFirstMatchWhenNoMatchExists() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.addPattern("abc", 1);
    ac.build();
    assertNull(ac.searchFirst("zzz"));
  }

  @Test
  public void shouldApplyCaseInsensitiveAndWhitespaceNormalization() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.setCaseInsensitive(true);
    ac.setNormalizeWhitespace(true);
    ac.addPattern("A B", 7);
    ac.build();

    int[] counts = ac.countOccurrencesByPattern("x a\tb y");
    assertEquals(1, counts[0]);
  }

  @Test(expected = IllegalStateException.class)
  public void shouldRejectChangingSettingsAfterBuild() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.addPattern("a", 1);
    ac.build();
    ac.setCaseInsensitive(true);
  }

  @Test
  public void shouldSelectNonOverlappingMatchesWithHighScore() {
    AhoCorasickAdvanced ac = new AhoCorasickAdvanced();
    ac.addPattern("a", 1);
    ac.addPattern("aa", 2);
    ac.build();

    assertEquals(3, ac.selectMaxScoreNonOverlapping("aaa").size());
  }
}

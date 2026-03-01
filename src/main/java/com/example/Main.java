// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import org.jetbrains.annotations.Nullable;

/**
 * Demonstrates the SpotBugs false positive.
 */
public class Main {

  /**
   * This method triggers the SpotBugs false positive.
   *
   * <p>SpotBugs reports: NP_NULL_PARAM_DEREF: Null passed for non-null parameter
   * of ImmutableDataBuilder.nullableField(String)
   *
   * <p>However, the field is annotated with @Nullable, so null is a valid value.
   * The generated builder correctly accepts null values.
   */
  public ImmutableData createData(@Nullable String maybeNull, String required) {
    // This line triggers the false positive
    // SpotBugs thinks nullableField() doesn't accept null, but it does
    return ImmutableData.builder()
      .nullableField(maybeNull)  // SpotBugs reports NP_NULL_PARAM_DEREF here
      .requiredField(required)
      .build();
  }

  /**
   * This works fine because we're passing a literal null.
   */
  public ImmutableData createWithNullLiteral(String required) {
    return ImmutableData.builder()
      .nullableField(null)  // No warning for literal null
      .requiredField(required)
      .build();
  }

  public static void main(String[] args) {
    var main = new Main();
    var data1 = main.createData(null, "test");
    var data2 = main.createWithNullLiteral("test");
    System.out.println("data1: " + data1);
    System.out.println("data2: " + data2);
  }
}

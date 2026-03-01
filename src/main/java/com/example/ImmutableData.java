// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import org.immutables.builder.Builder;
import org.jetbrains.annotations.Nullable;

/**
 * Demonstrates SpotBugs false positive with Immutables @Builder and @Nullable.
 *
 * <p>The generated builder correctly accepts null values for the nullable field,
 * but SpotBugs reports NP_NULL_PARAM_DEREF when passing a nullable variable
to the builder.
 */
@Builder
public record ImmutableData(
  @Nullable String nullableField,
  String requiredField
) {

  public static ImmutableDataBuilder builder() {
    return new ImmutableDataBuilder();
  }
}

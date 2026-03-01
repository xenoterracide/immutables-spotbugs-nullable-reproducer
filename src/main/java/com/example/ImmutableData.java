// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

/**
 * Demonstrates SpotBugs false positive with Immutables @Value.Immutable and @Nullable.
 *
 * <p>The generated builder correctly accepts null values for the nullable field,
 * but SpotBugs reports NP_NULL_PARAM_DEREF when passing a nullable variable
to the builder.
 */
@Value.Immutable
public interface ImmutableData {

  @Nullable
  String nullableField();

  String requiredField();
}

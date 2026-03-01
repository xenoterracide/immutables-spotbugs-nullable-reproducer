// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import org.immutables.value.Value;
import org.jspecify.annotations.Nullable;

/**
 * Uses JSpecify @Nullable - this triggers SpotBugs false positive.
 *
 * <p>SpotBugs reports NP_NULL_PARAM_DEREF because it doesn't recognize
 * JSpecify's @Nullable as overriding @ParametersAreNonnullByDefault.</n */
@Value.Immutable
public interface JSpecifyData {

  @Nullable
  String nullableField();

  String requiredField();
}

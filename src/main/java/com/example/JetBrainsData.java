// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

/**
 * Uses JetBrains @Nullable - this works correctly with SpotBugs.
 *
 * <p>SpotBugs correctly recognizes JetBrains @Nullable as overriding
 * @ParametersAreNonnullByDefault, so no false positive is reported.
 */
@Value.Immutable
public interface JetBrainsData {

  @Nullable
  String nullableField();

  String requiredField();
}

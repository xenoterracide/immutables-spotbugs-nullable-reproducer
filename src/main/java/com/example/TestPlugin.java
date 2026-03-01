// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jspecify.annotations.Nullable;

/**
 * A test plugin that demonstrates the SpotBugs false positive with JSpecify.
 */
public class TestPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    // This will trigger SpotBugs when using JSpecify
    project.getExtensions().create("testConfig", TestExtension.class);
  }

  /**
   * This method triggers the SpotBugs false positive when using JSpecify.
   *
   * <p>SpotBugs reports: NP_NULL_PARAM_DEREF: Null passed for non-null parameter
   * of ImmutableJSpecifyDataBuilder.nullableField(String)
   *
   * <p>This is a false positive because the field is annotated with JSpecify @Nullable,
   * but SpotBugs doesn't recognize it as overriding @ParametersAreNonnullByDefault.
   */
  public JSpecifyData createJSpecifyData(@Nullable String maybeNull, String required) {
    return ImmutableJSpecifyData.builder()
      .nullableField(maybeNull)  // SpotBugs reports NP_NULL_PARAM_DEREF here
      .requiredField(required)
      .build();
  }

  /**
   * This method works fine with JetBrains annotations.
   *
   * <p>No SpotBugs warning because it recognizes JetBrains @Nullable.
   */
  public JetBrainsData createJetBrainsData(@Nullable String maybeNull, String required) {
    return ImmutableJetBrainsData.builder()
      .nullableField(maybeNull)  // No warning - JetBrains @Nullable recognized
      .requiredField(required)
      .build();
  }

  /**
   * Literal null works fine even with JSpecify.
   */
  public JSpecifyData createJSpecifyWithNullLiteral(String required) {
    return ImmutableJSpecifyData.builder()
      .nullableField(null)  // No warning for literal null
      .requiredField(required)
      .build();
  }
}

// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

package com.example;

import javax.inject.Inject;
import org.gradle.api.Project;

/**
 * Extension for the test plugin.
 */
public class TestExtension {

  private final Project project;

  @Inject
  public TestExtension(Project project) {
    this.project = project;
  }
}

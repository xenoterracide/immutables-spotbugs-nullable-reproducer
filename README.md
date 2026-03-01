# SpotBugs JSpecify Support Issue Reproducer

This repository demonstrates that **SpotBugs does not recognize JSpecify `@Nullable` annotations**
when using `java-gradle-plugin`, causing false positive `NP_NONNULL_PARAM_VIOLATION` warnings.

## Issue Summary

When using [Immutables](https://immutables.github.io/) `@Value.Immutable` with JSpecify null annotations
in a **Gradle plugin project** (`java-gradle-plugin`):

1. Immutables generates a builder class annotated with `@ParametersAreNonnullByDefault` (JSR-305)
2. The builder's setter methods are annotated with `@Nullable` for nullable fields
3. **SpotBugs does not recognize JSpecify's `@Nullable`** as overriding the default non-null behavior
4. This causes SpotBugs to report `NP_NONNULL_PARAM_VIOLATION` when passing null to the builder

The same code with **JetBrains `@Nullable`** works correctly - SpotBugs recognizes it and produces no warning.

## Key Finding

The issue only manifests when using `java-gradle-plugin`. This is likely because:
- Gradle bundles its own version of annotations (possibly JSpecify)
- SpotBugs uses the Gradle API classpath which shadows/conflicts with explicit dependencies
- The annotation classes are not properly visible to SpotBugs' analysis

## Code Comparison

### JSpecify (triggers false positive)

```java
@Value.Immutable
public interface JSpecifyData {
  @org.jspecify.annotations.Nullable  // JSpecify
  String nullableField();
  
  String requiredField();
}

// In a Gradle plugin:
public JSpecifyData create(@Nullable String maybeNull) {
  return ImmutableJSpecifyData.builder()
    .nullableField(maybeNull)  // SpotBugs: NP_NONNULL_PARAM_VIOLATION ⚠️
    .build();
}
```

### JetBrains (works correctly)

```java
@Value.Immutable
public interface JetBrainsData {
  @org.jetbrains.annotations.Nullable  // JetBrains
  String nullableField();
  
  String requiredField();
}

// In a Gradle plugin:
public JetBrainsData create(@Nullable String maybeNull) {
  return ImmutableJetBrainsData.builder()
    .nullableField(maybeNull)  // No warning ✓
    .build();
}
```

## Running the Reproducer

```bash
./gradlew spotbugsMain
```

### Expected Output

```
H D NP_NONNULL_PARAM_VIOLATION: Null passed for non-null parameter of 
   ImmutableJSpecifyData$Builder.nullableField(String) in 
   com.example.TestPlugin.createJSpecifyWithNullLiteral(String) at TestPlugin.java:[line 55]
   
M D NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION: ...
```

**Total: 4 warnings for JSpecify, 0 warnings for JetBrains.**

## Environment

| Component | Version |
|-----------|---------|
| SpotBugs | 4.9.3 |
| SpotBugs Gradle Plugin | 6.1.6 |
| Immutables | 2.10.1 |
| JSpecify | 1.0.0 |
| JetBrains Annotations | 24.1.0 |
| Java | 21 |
| Gradle | 8.12 |

## Related Issues

- [spotbugs/spotbugs#3143](https://github.com/spotbugs/spotbugs/issues/3143) - Fully Support JSpecify 1.0
- [spotbugs/spotbugs-jspecify-plugin](https://github.com/spotbugs/spotbugs-jspecify-plugin) - **ARCHIVED** March 2025

## Workarounds

1. **Use JetBrains annotations** instead of JSpecify for nullable parameters in Gradle plugins
2. **Use `@SuppressFBWarnings("NP_NONNULL_PARAM_VIOLATION")`** to suppress the warning
3. **Exclude generated code** from SpotBugs analysis (if your build supports it)

## License

MIT

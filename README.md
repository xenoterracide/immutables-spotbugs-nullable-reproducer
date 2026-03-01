# Immutables + SpotBugs + @Nullable Reproducer

This repository demonstrates a false positive in SpotBugs when using Immutables `@Builder` 
with nullable fields.

## The Issue

When a record field is annotated with `@Nullable` and uses Immutables `@Builder`, 
SpotBugs incorrectly reports `NP_NULL_PARAM_DEREF` when passing a nullable variable 
to the builder's setter method.

### Example

```java
@Builder
public record ImmutableData(
  @Nullable String nullableField,
  String requiredField
) {}

// In calling code:
public ImmutableData createData(@Nullable String maybeNull, String required) {
  return ImmutableData.builder()
    .nullableField(maybeNull)  // SpotBugs reports NP_NULL_PARAM_DEREF here
    .requiredField(required)
    .build();
}
```

## Expected Behavior

SpotBugs should recognize that `nullableField()` accepts null values because:
1. The field is annotated with `@Nullable`
2. The generated builder method accepts null
3. The code compiles and works correctly at runtime

## Actual Behavior

SpotBugs reports:
```
M C NP_NULL_PARAM_DEREF: Null passed for non-null parameter of 
ImmutableDataBuilder.nullableField(String) in com.example.Main.createData(...)
```

## Workarounds

1. **Use literal null**: `nullableField(null)` doesn't trigger the warning
2. **Use JetBrains @Nullable**: Immutables recognizes `org.jetbrains.annotations.Nullable` 
   better than `org.jspecify.annotations.Nullable`
3. **Suppress the warning**: Use `@SuppressFBWarnings("NP_NULL_PARAM_DEREF")`

## Running the Reproducer

```bash
./gradlew spotbugsMain
```

This will fail with the NP_NULL_PARAM_DEREF error.

## Environment

- Immutables 2.10.1
- SpotBugs 6.1.6
- JetBrains Annotations 24.1.0
- Java 21

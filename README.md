# Background

KSP support was recently released for Dagger. However, there are some known issues with wiring in things like Data Bindings and the Build Config. See [here](https://github.com/google/dagger/issues/4049) for more.

In the issue comments, an interim fix was proposed. See [here](https://github.com/google/dagger/issues/4049#issuecomment-1749067296). However, there was a special case for which the fix failed to save us. Either there is a further issue with Dagger Assisted Injection for these files, or we are doing something very wrong (but that `kapt` allowed).

# Usage

Compile with `./gradlew assembleDebug`

You will see output similar to this:
```
> Task :app:compileDebugKotlin
w: file:///Users/joshuaabrams/Duolingo/dev/projects/HiltKspViewBindingTestAppWithWorkaround/app/src/main/java/com/example/hiltkspviewbindingtestapp/MainActivity.kt:29:13 Variable 'viewModel' is never used

> Task :app:compileDebugJavaWithJavac FAILED
/Users/joshuaabrams/Duolingo/dev/projects/HiltKspViewBindingTestAppWithWorkaround/app/build/generated/ksp/debug/java/com/example/hiltkspviewbindingtestapp/AssistedViewModel_Factory_Impl.java:19: error: AssistedViewModel_Factory_Impl is not abstract and does not override abstract method create(NullableContainer) in Factory
public final class AssistedViewModel_Factory_Impl implements AssistedViewModel.Factory {
             ^
/Users/joshuaabrams/Duolingo/dev/projects/HiltKspViewBindingTestAppWithWorkaround/app/build/generated/ksp/debug/java/com/example/hiltkspviewbindingtestapp/AssistedViewModel_Factory_Impl.java:27: error: name clash: create(NullableContainer<Void>) in AssistedViewModel_Factory_Impl and create(NullableContainer) in Factory have the same erasure, yet neither overrides the other
  public AssistedViewModel create(NullableContainer<Void> emptyGeneric) {
                           ^
/Users/joshuaabrams/Duolingo/dev/projects/HiltKspViewBindingTestAppWithWorkaround/app/build/generated/ksp/debug/java/com/example/hiltkspviewbindingtestapp/AssistedViewModel_Factory_Impl.java:26: error: method does not override or implement a method from a supertype
  @Override
  ^
3 errors

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:compileDebugJavaWithJavac'.
> Compilation failed; see the compiler error output for details.

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 1s
```

package com.example.hiltkspviewbindingtestapp

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import androidx.lifecycle.ViewModel


class AssistedViewModel
@AssistedInject
constructor(
  @Assisted private val emptyGeneric: NulledContainer,
  private val testClass: TestClass,
) : ViewModel() {

  /** Assisted DI factory for this ViewModel. */
  @AssistedFactory
  interface Factory {
    /** Create a new instance of this view model. */
    fun create(
      emptyGeneric: NulledContainer,
    ): AssistedViewModel
  }
}

open class NullableContainer<T>(val value: T) {
  fun get(): T = value

  fun nullify(): NullableContainer<Nothing?> = NullableContainer(null)
}

class NulledContainer : NullableContainer<Nothing?>(null) {}

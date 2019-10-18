package com.zhi.common.app


import javax.inject.Scope

/**
 * Indicate that the injected var should be contained in Activity.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

/**
 * Indicate that the injected var should be contained in Fragment.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope
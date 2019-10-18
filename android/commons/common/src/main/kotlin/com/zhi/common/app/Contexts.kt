package com.zhi.common.app

import javax.inject.Qualifier


/**
 * Indicate the bound Context should be an instance of application.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContext

/**
 * Indicate the bound Context should be an instance of activity.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

/**
 * Indicate the bound Context should be an instance of fragment.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentContext
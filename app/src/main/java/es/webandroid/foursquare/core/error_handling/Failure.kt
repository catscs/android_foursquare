package es.webandroid.foursquare.core.error_handling

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */

sealed class Failure {
    sealed class NetworkError : Failure() {
        data class Fatal(val reason: String?) : NetworkError()
        data class Recoverable(val reason: String?) : NetworkError()
        object NoConnection : NetworkError()
    }

    object DBError : Failure()
}

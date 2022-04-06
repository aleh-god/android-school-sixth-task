package by.godevelopment.sixthtask.domain.models

sealed interface Event

sealed class ListFragmentEvent(): Event

class SnackbarEvent(val message: String): ListFragmentEvent()
class DialogEvent(val message: String): ListFragmentEvent()

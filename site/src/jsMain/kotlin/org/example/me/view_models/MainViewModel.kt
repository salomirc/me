package org.example.me.view_models

import kotlinx.coroutines.delay
import org.example.me.error_handling.IErrorHandlerBroadcastService
import org.example.me.error_handling.MessageResourceIdWrapper
import org.example.me.models.ui.NavItem

class MainViewModel (
    private val broadcastService: IErrorHandlerBroadcastService
) : BaseViewModel<MainViewModel.Model, MainViewModel.Event>(
    model = Model(
        messageResourceIdWrapper = null,
        navItems = listOf(
            NavItem(title = "Home", iconName = "home", target = "/"),
            NavItem(title = "About", iconName = "address-card", target = "/about"),
            NavItem(title = "Experience", iconName = "scissors", target = "/experience"),
            NavItem(title = "Projects", iconName = "plane-departure", target = "/projects"),
            NavItem(title = "Contact", iconName = "square-envelope", target = "/contact"),
//            NavItem(title = "Backend", iconName = "square-envelope", target = "/custom-backend-demo"),
        )
    )
) {
    data class Model(
        val messageResourceIdWrapper: MessageResourceIdWrapper?,
        val navItems: List<NavItem>
    )

    sealed interface Event {
        data object CollectMessageResourceIdWrapper: Event
        data object StartProcessNextMessageLoop: Event
        data object LogOut : Event
    }

    override suspend fun processEvent(event: Event) {
        when (event) {
            Event.CollectMessageResourceIdWrapper -> {
                collectMessageResourceIdWrapper()
            }
            Event.StartProcessNextMessageLoop -> {
                startProcessNextMessageLoop()
            }
            Event.LogOut -> {
                console.log("ToastMessage", "MainViewModel LogOut event called")
            }
        }
    }

    suspend fun collectMessageResourceIdWrapper() {
        broadcastService.messageResourceIdWrapper.collect { messageResourceIdWrapper ->
            updateModelState { model ->
                model.copy(messageResourceIdWrapper = messageResourceIdWrapper)
            }
            console.log("ToastMessage", "messageResourceIdWrapper: $messageResourceIdWrapper")
        }
    }

    suspend fun startProcessNextMessageLoop() {
        while (true) {
            broadcastService.processNextMessage()
            console.log("ToastMessage", "MainViewModel processNextMessage() called")
            delay(3000L)
        }
    }
}
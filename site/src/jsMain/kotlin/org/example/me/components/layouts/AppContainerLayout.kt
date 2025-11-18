package org.example.me.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.varabyte.kobweb.core.layout.Layout
import org.example.me.api_caller.WebApiCaller
import org.example.me.error_handling.ErrorHandler
import org.example.me.error_handling.ErrorHandlerBroadcastService
import org.example.me.models.ui.NavItem
import org.example.me.repositories.BlogRepository
import org.example.me.repositories.SendMailRepository
import org.example.me.use_cases.GetUsersUseCase
import org.example.me.view_models.ContactViewModel
import org.example.me.view_models.CustomBackendDemoViewModel
import org.example.me.view_models.MainViewModel

@Layout
@Composable
fun AppContainerLayout(content: @Composable AppContainerLayoutScope.() -> Unit) {
    val scope = remember { AppContainerLayoutScope() }

    scope.content()
}

class AppContainerLayoutScope {
    init {
        console.log("AppContainerLayoutScope init = ${this.hashCode()}")
    }

    val apiCaller = WebApiCaller()
    val mainViewModel = MainViewModel(
        broadcastService = ErrorHandlerBroadcastService,
        navItems = listOf(
            NavItem(title = "Home", iconName = "home", target = "/"),
            NavItem(title = "About", iconName = "address-card", target = "/about"),
            NavItem(title = "Experience", iconName = "scissors", target = "/experience"),
            NavItem(title = "Projects", iconName = "plane-departure", target = "/projects"),
            NavItem(title = "Contact", iconName = "square-envelope", target = "/contact"),
//            NavItem(title = "Backend", iconName = "square-envelope", target = "/custom-backend-demo"),
        )
    )

    fun provideMainViewModel(): MainViewModel = mainViewModel

    fun provideCustomBackendDemoViewModel(): CustomBackendDemoViewModel {
        return CustomBackendDemoViewModel(
            getUsersUseCase = GetUsersUseCase(
                repository = BlogRepository(
                    apiCaller = apiCaller
                )
            ),
            errorHandler = ErrorHandler(
                viewModelName = CustomBackendDemoViewModel.TAG
            )
        )
    }

    fun provideContactViewModel(): ContactViewModel {
        return ContactViewModel(
            sendMailRepository = SendMailRepository(
                apiCaller = apiCaller
            ),
            errorHandler = ErrorHandler(
                viewModelName = ContactViewModel.TAG
            )
        )
    }
}
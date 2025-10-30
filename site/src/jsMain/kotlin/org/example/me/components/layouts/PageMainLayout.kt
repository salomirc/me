package org.example.me.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import org.example.me.SiteColors
import org.example.me.components.sections.NavHeader
import org.example.me.error_handling.ErrorAction
import org.example.me.error_handling.MessageResourceIdWrapper
import org.example.me.view_models.MainViewModel
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Layout(".components.layouts.AppContainerLayout")
@Composable
fun AppContainerLayoutScope.PageMainLayout(
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    val mainViewModel: MainViewModel = remember { this.provideMainViewModel() }
    val model by mainViewModel.modelStateFlow.collectAsState()

    MainLayout(
        model = model,
        processEvent = mainViewModel::processEvent,
        content = content,
    )
}

@Composable
fun AppContainerLayoutScope.MainLayout(
    model: MainViewModel.Model,
    processEvent: suspend (MainViewModel.Event) -> Unit,
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    val colorMode by ColorMode.currentState

    LaunchedEffect(Unit) {
        processEvent(MainViewModel.Event.CollectMessageResourceIdWrapper)
    }

    LaunchedEffect(Unit) {
        processEvent(MainViewModel.Event.StartProcessNextMessageLoop)
    }

    NavHeader(
        globalMessageContent = {
            GlobalActionAndMessageToastSetUp(
                colorMode = colorMode,
                messageResourceIdWrapper = model.messageResourceIdWrapper,
                processEvent = processEvent
            )
        }
    )
    this.content()
}

@Composable
private fun GlobalActionAndMessageToastSetUp(
    colorMode: ColorMode,
    messageResourceIdWrapper: MessageResourceIdWrapper?,
    processEvent: suspend (MainViewModel.Event) -> Unit,
) {
    var toastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(messageResourceIdWrapper) {
        messageResourceIdWrapper?.let { wrapper ->
            console.log("MainLayout toastMessage LaunchedEffect have been called with: $messageResourceIdWrapper")
            wrapper.message?.let { s ->
                toastMessage = s
            }
            console.log("MainLayout toastMessage have been set to: ${wrapper.message}")
            console.log("MainLayout errorAction have been set to: ${wrapper.errorAction}")

            wrapper.errorAction?.let { errorAction ->
                when (errorAction) {
                    ErrorAction.LOG_OUT -> {
                        processEvent(MainViewModel.Event.LogOut)
                    }
                }
            }

            delay(2000)
            toastMessage = null
        }
    }

    toastMessage?.let { message ->
        Div(attrs = {
            style {
                padding(8.px)
                textAlign("center")
                color(SiteColors.ultraLightGray)
            }
        }) {
            Span(attrs = {
                style {
                    fontWeight(FontWeight.Bold)
                }
            }) {
                Text(message)
            }
        }
    }
}
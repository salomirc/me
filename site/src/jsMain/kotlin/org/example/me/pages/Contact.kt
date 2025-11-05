package org.example.me.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.example.me.AppStyles
import org.example.me.SiteStyleSheet.Companion.screenBreakMinTo799px
import org.example.me.components.layouts.AppContainerLayoutScope
import org.example.me.components.widgets.Lorem
import org.example.me.components.widgets.PageTitle
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.ThreeColumnsToRowContainer
import org.example.me.repositories.ResponseState.ActiveResponseState.*
import org.example.me.repositories.ResponseState.Idle
import org.example.me.view_models.ContactViewModel
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*


object ContactStyleSheet: StyleSheet() {
    val formInput by style {
        fontSize(1.4.cssRem)
        padding(4.px)
        width(100.percent)
    }

    val boxCssRules: CSSBuilder.() -> Unit = {
        borderRadius(8.px)
    }

    val boxOne by style {
        boxCssRules()
        width(40.percent)

        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                width(100.percent)
            }
        }
    }

    val boxTwo by style {
        boxCssRules()
        width(30.percent)

        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                width(100.percent)
            }
        }
    }

    val boxThree by style {
        boxCssRules()
        width(30.percent)

        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                width(100.percent)
            }
        }
    }
}

@Page
@Composable
@Layout(".components.layouts.PageFooterLayout")
fun AppContainerLayoutScope.ContactPage() {
    val viewModel: ContactViewModel = remember { this.provideContactViewModel() }
    val model by viewModel.modelStateFlow.collectAsState()
    Contact(
        model = model,
        processEvent = viewModel::processEvent
    )
}

@Composable
fun Contact(
    model: ContactViewModel.Model,
    processEvent: suspend (ContactViewModel.Event) -> Unit,
) {
    Style(ContactStyleSheet)

    Div(attrs = {
        classes(AppStyles.siteStyleSheet.pageContainerClass)
    }) {
        PageTitle("Contact")
        ThreeColumnsToRowContainer(
            boxOneClass = listOf(ContactStyleSheet.boxOne),
            boxTwoClass = listOf(ContactStyleSheet.boxTwo),
            boxThreeClass = listOf(ContactStyleSheet.boxThree),
            contentOne = {
                ContactForm(model, processEvent)
            },
            contentTwo = {
                ContentOne()
            },
            contentThree = {
                ContentOne()
            }
        )
    }
}

@Composable
fun ContentOne() {
    Div(attrs = {
        style {
            padding(32.px)
        }
    }) {
        Text(Lorem.lenght50)
    }
}

@Composable
fun ContactForm(
    model: ContactViewModel.Model,
    processEvent: suspend (ContactViewModel.Event) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    var colorMode: ColorMode by ColorMode.currentState

    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.FlexStart)
            gap(8.px)

            padding(32.px)
        }
    }) {
        FieldName("Name :")
        Input(type = InputType.Text, attrs = {
            maxLength(50)
            name("name")
            autoComplete(AutoComplete.name)
            value(name)
            onInput { name = it.value }
            classes(ContactStyleSheet.formInput)
        })
        FieldName("Email :")
        Input(type = InputType.Email, attrs = {
            maxLength(50)
            name("email")
            autoComplete(AutoComplete.email)
            value(email)
            onInput { email = it.value }
            classes(ContactStyleSheet.formInput)
        })
        FieldName("Message :")
        TextArea(attrs = {
            placeholder("Your message...")
            rows(10)
            name("message")
            value(message)
            onInput { message = it.value }
            classes(ContactStyleSheet.formInput)
        })
        Row(modifier = Modifier.margin(top = 32.px)) {
            ContactFormButton(
                text = "Send Message",
                name = "send message",
                onClick = {
                    coroutineScope.launch {
                        processEvent(
                            ContactViewModel.Event.SendEmail(
                                name = name,
                                email = email,
                                message = message
                            )
                        )
                    }
                }
            )
            Spacer(width = 8.px)
            ContactFormButton(
                text = "ClearForm",
                name = "clear form",
                onClick = {
                    name = ""
                    email = ""
                    message = ""
                    coroutineScope.launch {
                        processEvent(ContactViewModel.Event.ResetResponseState)
                    }
                }
            )
        }

        Div {
            when (model.sendMailResponseState) {
                Idle -> {}
                Loading -> {
                    Text("Sending the message...")
                }
                is Success -> {
                    val response = model.sendMailResponseState.data
                    if (response.isSuccess) {
                        Text("✅ Message sent!")
                    } else {
                        Text("❌ Server failed to send the message!")
                    }
                }
                is Failure -> {
                    Text("❌ Sorry, something went wrong!")
                }
            }
        }
    }
}

@Composable
fun FieldName(name: String) {
    Div { Text(name) }
}

@Composable
fun ContactFormButton(
    text: String,
    name: String,
    onClick: () -> Unit
) {
    Button(attrs = {
        type(ButtonType.Button)
        name(name)
        onClick {
            onClick()
        }
        style {
            display(DisplayStyle.InlineBlock)
        }
    }) {
        Text(text)
    }
}

@Serializable
data class ContactData(val name: String, val email: String, val message: String)


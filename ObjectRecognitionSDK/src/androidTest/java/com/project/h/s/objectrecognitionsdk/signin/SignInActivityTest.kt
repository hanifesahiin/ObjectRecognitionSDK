package com.project.h.s.objectrecognitionsdk.signin

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.nfcreader.FakeReadIdCardActivity
import com.project.h.s.objectrecognitionsdk.presentation.signin.SignInScreen
import com.project.h.s.objectrecognitionsdk.presentation.signin.SignInViewModel
import com.project.h.s.objectrecognitionsdk.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@HiltAndroidTest
class SignInActivityTest {
    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<FakeSignInActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    private fun createSignInScreen(signIn: () -> Unit = {}) {
        composeTestRule.activity.setContent {
            val viewmodel = composeTestRule.activity.viewModels<SignInViewModel>().value
            SignInScreen(
                viewmodel.uiFlow,
                viewmodel.eventFlow,
                signIn = signIn
            )
        }
    }

    @Test
    fun ui_components() {
        createSignInScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_text).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.user_sign_in_text).assertTextEquals(
            composeTestRule.activity.getString(
                R.string.user_sign_in
            )
        )

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_email).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.user_sign_in_email).assert(
            hasText(
                composeTestRule.activity.getString(
                    R.string.user_name
                )
            )
        )

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_password).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.user_sign_in_password).assert(
            hasText(
                composeTestRule.activity.getString(
                    R.string.password
                )
            )
        )

        composeTestRule.onNodeWithTag(TestTags.sign_in_button).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.sign_in_button).assert(
            hasText(
                composeTestRule.activity.getString(
                    R.string.sign_in
                )
            )
        )
    }

    @Test
    fun enter_userName() {
        createSignInScreen()

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_email).performTextReplacement("michael")
        composeTestRule.waitForIdle()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag(TestTags.user_sign_in_email).onChildren().filter(
                hasText(
                    "michael", ignoreCase = true
                )
            ).fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun enter_password() {
        createSignInScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_password)
            .performTextInput("success-password")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestTags.user_sign_in_password).assert(
            hasText(
                "success-password", ignoreCase = true
            )
        )
    }

    @Test
    fun perform_button_but_not_clicked() {
        val signIn = mock<() -> Unit>()
        createSignInScreen(signIn)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.sign_in_button)
            .assertIsDisplayed().performClick()

        verify(signIn, never()).invoke()
    }

    @Test
    fun perform_button_and_clicked_as_success() {
        val signIn = mock<() -> Unit>()
        createSignInScreen(signIn)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_email)
            .performTextInput("michael")

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_password)
            .performTextInput("success-password")

        composeTestRule.onNodeWithTag(TestTags.sign_in_button)
            .assertIsDisplayed().performClick()

        verify(signIn).invoke()

        composeTestRule.waitUntil(3000) {
            FakeReadIdCardActivity.isFinishActivityCalled
        }
    }

    @Test
    fun perform_button_and_clicked_as_fail() {
        val signIn = mock<() -> Unit>()
        createSignInScreen(signIn)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_email)
            .performTextInput("michael")

        composeTestRule.onNodeWithTag(TestTags.user_sign_in_password)
            .performTextInput("failed-password")

        composeTestRule.onNodeWithTag(TestTags.sign_in_button)
            .assertIsDisplayed().performClick()

        verify(signIn).invoke()

        composeTestRule.onNodeWithTag(TestTags.sign_in_error)
            .assertIsDisplayed()
    }
}
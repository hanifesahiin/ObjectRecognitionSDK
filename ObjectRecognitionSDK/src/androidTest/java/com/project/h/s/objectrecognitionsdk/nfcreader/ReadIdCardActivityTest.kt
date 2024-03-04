package com.project.h.s.objectrecognitionsdk.nfcreader

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.presentation.readidcard.ReadIdCardActivity
import com.project.h.s.objectrecognitionsdk.presentation.readidcard.ReadIdCardScreen
import com.project.h.s.objectrecognitionsdk.presentation.readidcard.ReadIdCardViewModel
import com.project.h.s.objectrecognitionsdk.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ReadIdCardActivityTest {
    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<ReadIdCardActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    private fun createCardReaderScreen(uiFlow: StateFlow<ReadIdCardViewModel.UiFlow>? = null) {
        composeTestRule.activity.setContent {
            val viewModel = composeTestRule.activity.viewModels<ReadIdCardViewModel>().value
            ReadIdCardScreen(
                uiFlow = uiFlow ?: viewModel.uiFlow
            )
        }
    }

    @Test
    fun ui_components() {
        createCardReaderScreen(
            uiFlow = MutableStateFlow(
                ReadIdCardViewModel.UiFlow(
                    name = "Michael",
                    lastName = "asd"
                )
            )
        )
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.read_nfc).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.read_nfc).assert(
            hasText(
                composeTestRule.activity.getString(
                    R.string.read_nfc
                )
            )
        )

        composeTestRule.onNodeWithTag(TestTags.white_card_container).onChildren().filter(
            hasText(
                String.format(composeTestRule.activity.getString(
                    R.string.ad
                ),"Michael")
            )
        ).onFirst().assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.white_card_container).onChildren().filter(
            hasText(
                String.format(composeTestRule.activity.getString(
                    R.string.soyad
                ),"asd")
            )
        ).onFirst().assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.complete_button).assertIsDisplayed()
    }

    @Test
    fun perform_click() {
        createCardReaderScreen()
        composeTestRule.onNodeWithTag(TestTags.complete_button).performClick()
    }
}
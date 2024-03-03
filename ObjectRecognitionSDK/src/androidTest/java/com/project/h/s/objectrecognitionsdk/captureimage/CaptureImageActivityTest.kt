package com.project.h.s.objectrecognitionsdk.captureimage

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.rule.GrantPermissionRule
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.presentation.captureimage.CaptureImageActivity
import com.project.h.s.objectrecognitionsdk.presentation.captureimage.CaptureImageScreen
import com.project.h.s.objectrecognitionsdk.presentation.captureimage.CaptureImageViewModel
import com.project.h.s.objectrecognitionsdk.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CaptureImageActivityTest {
    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<CaptureImageActivity>()

    @get:Rule()
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.CAMERA
    )

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    private fun createCaptureImageScreen(
        tensorflow: (Bitmap) -> Unit = {},
    ) {
        composeTestRule.activity.setContent {
            val viewModel = composeTestRule.activity.viewModels<CaptureImageViewModel>().value
            CaptureImageScreen(
                viewModel.uiFlow,
                tensorflow
            )
        }
    }

    @Test
    fun ui_components() {
        createCaptureImageScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.camera_screen_component).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.camera_permission_screen_component)
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag(TestTags.android_view_capture).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.processed_object).assertIsDisplayed()

        assert(
            composeTestRule.onNodeWithTag(TestTags.processed_object).onChildren().assertAny(
                hasText(
                    composeTestRule.activity.getString(
                        R.string.captured_object
                    )
                )
            ).fetchSemanticsNodes().isNotEmpty()
        )

        assert(
            composeTestRule.onNodeWithTag(TestTags.processed_object).onChildren().assertAny(
                hasTestTag(TestTags.processed_image)
            ).fetchSemanticsNodes().isEmpty()
        )

        composeTestRule.onNodeWithTag(TestTags.android_view_capture).onChildren()
            .filter(hasTestTag(TestTags.complete_button)).onFirst().assertIsDisplayed()
    }

    @Test
    fun android_view_capture() {
        createCaptureImageScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.android_view_capture).onChildren()
            .filter(hasTestTag(TestTags.complete_button)).onFirst().performClick()

        assert(
            composeTestRule.onNodeWithTag(TestTags.processed_object).onChildren().assertAny(
                hasTestTag(TestTags.processed_image)
            ).fetchSemanticsNodes().isNotEmpty()
        )

        assert(
            composeTestRule.onNodeWithTag(TestTags.processed_object).onChildren().assertAny(
                hasText("apple")
            ).fetchSemanticsNodes().isNotEmpty()
        )
    }
}
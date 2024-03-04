package com.project.h.s.objectrecognitionsdk.userlist

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.presentation.userlist.UserListActivity
import com.project.h.s.objectrecognitionsdk.presentation.userlist.UserListScreen
import com.project.h.s.objectrecognitionsdk.presentation.userlist.UserListViewModel
import com.project.h.s.objectrecognitionsdk.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@HiltAndroidTest
class UserListActivityTest {
    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<UserListActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    private fun createUserListScreen(
        uiFlow: StateFlow<UserListViewModel.UiFlow>? = null,
        confirm: (Int) -> Unit = {},
        logout: () -> Unit = {},
    ) {
        composeTestRule.activity.setContent {
            val viewModel = composeTestRule.activity.viewModels<UserListViewModel>().value
            UserListScreen(
                uiFlow ?: viewModel.uiFlow,
                viewModel.eventFlow,
                confirm = confirm,
                logout = logout
            )
        }
    }

    @Test
    fun ui_components() {
        createUserListScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.users_waiting_for_approval).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.users_waiting_for_approval).assertTextEquals(
            composeTestRule.activity.getString(
                R.string.users_waiting_for_approval
            )
        )

        composeTestRule.onNodeWithTag(TestTags.user_list_lazy_column).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.user_list_item).assertIsDisplayed()
    }

    @Test
    fun user_list() {
        createUserListScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.user_list_lazy_column).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.user_list_item).assertIsDisplayed()

        val text = "1 Michael michael@mailinator.com"
        composeTestRule.onNodeWithTag(TestTags.user_list_item).onChildren().assertAny(hasText(text))
        val size = composeTestRule.onNodeWithTag(TestTags.user_list_item).onChildren()
            .filter(hasTestTag(TestTags.confirm_button)).fetchSemanticsNodes().size
        Assert.assertEquals(1, size)
    }

    @Test
    fun perform_click() {
        val confirm = mock<(Int) -> Unit>()
        createUserListScreen(
            confirm = confirm
        )
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.confirm_button).performClick()

        verify(confirm).invoke(1)
    }
}
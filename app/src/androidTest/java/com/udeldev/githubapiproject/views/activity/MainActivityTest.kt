package com.udeldev.githubapiproject.views.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.udeldev.githubapiproject.R
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testFavoriteMenuIntent() {
        onView(withId(R.id.favoritePage)).perform(click())
        Intents.intended(hasComponent(FavoriteActivity::class.java.name))
    }
}

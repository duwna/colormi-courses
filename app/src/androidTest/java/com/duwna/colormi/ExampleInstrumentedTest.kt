package com.duwna.colormi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun testValidation() {
        onView(withId(R.id.navigation_profile)).perform(click())
        onView(withId(R.id.btn_registration)).perform(click())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Имя не должено быть пустым.")))
        onView(withId(R.id.et_first_name)).perform(typeText("Name"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Фамилия не должена быть пустым.")))
        onView(withId(R.id.et_last_name)).perform(typeText("Lastname"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Телефон должен начинаться с '+' и содержать 11 цифр.")))
        onView(withId(R.id.et_phone)).perform(typeText("12345"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Телефон должен начинаться с '+' и содержать 11 цифр.")))
        onView(withId(R.id.et_phone)).perform(clearText(), typeText("+72895255"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Телефон должен начинаться с '+' и содержать 11 цифр.")))
        onView(withId(R.id.et_phone)).perform(clearText(), typeText("+78695412563"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
    }
}

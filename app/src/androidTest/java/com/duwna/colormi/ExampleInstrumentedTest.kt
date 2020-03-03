package com.duwna.colormi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
    fun testRegistrationValidation() {

        //first screen

        //go to auth
        onView(withId(R.id.navigation_profile)).perform(click())
        //go to registration
        onView(withId(R.id.btn_registration)).perform(click())

        //check name
        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Имя не должено быть пустым.")))
        onView(withId(R.id.et_first_name)).perform(typeText("Name"), closeSoftKeyboard())
        //check last name
        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Фамилия не должена быть пустым.")))
        onView(withId(R.id.et_last_name)).perform(typeText("Lastname"), closeSoftKeyboard())
        //check phone
        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Телефон должен начинаться с '+' и содержать 11 цифр.")))
        onView(withId(R.id.et_phone)).perform(typeText("12345"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Телефон должен начинаться с '+' и содержать 11 цифр.")))
        onView(withId(R.id.et_phone)).perform(clearText(), typeText("+72895255"), closeSoftKeyboard())

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Телефон должен начинаться с '+' и содержать 11 цифр.")))
        onView(withId(R.id.et_phone)).perform(clearText(), typeText("+78695412563"), closeSoftKeyboard())

        Thread.sleep(2000)
        onView(withId(R.id.btn_next)).perform(click())
        
        //second screen

        //check email
        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Email не должен быть пустым.")))
        onView(withId(R.id.et_email)).perform(clearText(), typeText("email"), closeSoftKeyboard())

        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Email не является валидным.")))
        onView(withId(R.id.et_email)).perform(clearText(), typeText("email..@@d."), closeSoftKeyboard())

        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Email не является валидным.")))
        onView(withId(R.id.et_email)).perform(clearText(), typeText("email@example.com"), closeSoftKeyboard())

        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Email не является валидным.")))
        onView(withId(R.id.et_password)).perform(clearText(), typeText("pass"), closeSoftKeyboard())
        //check password length
        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Пароль должен содержать минимум 6 символов.")))
        onView(withId(R.id.et_password)).perform(clearText(), typeText("mypassword"), closeSoftKeyboard())
        //check password match
        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Пароли не совпадают.")))
        onView(withId(R.id.et_re_password)).perform(clearText(), typeText("mypass"), closeSoftKeyboard())

        onView(withId(R.id.btn_register)).perform(click())
        onView(withId(R.id.snackbar_text))
            .check(matches(withText("Пароли не совпадают.")))
        onView(withId(R.id.et_re_password)).perform(clearText(), typeText("mypassword"), closeSoftKeyboard())
    }
}

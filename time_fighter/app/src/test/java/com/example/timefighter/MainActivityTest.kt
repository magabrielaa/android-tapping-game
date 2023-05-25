package com.example.timefighter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.timefighter.R.id.game_score_text_view
import com.example.timefighter.R.id.tap_me_button
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun startsWithZeroInitialScore() {
        val (_,systemUnderTest) = setUpTestableActivity()
        val scoreTextView = systemUnderTest.findViewById(game_score_text_view) as TextView

        assertEquals("Your Score: 0", scoreTextView.text)
    }

    @Test
    fun increasesScore_whenButtonPressed() {
        val (_,systemUnderTest) = setUpTestableActivity()
        val scoreTextView = systemUnderTest.findViewById(game_score_text_view) as TextView
        val tapMeButton = systemUnderTest.findViewById(tap_me_button) as Button

        tapMeButton.performClick()

        assertEquals("Your Score: 1", scoreTextView.text)
    }

    @Test
    fun sameScore_whenRotateDevice() {
        val bundle = Bundle()

        // De-structure return from setUpTestableActivity()
        var (controller,systemUnderTest) = setUpTestableActivity()

        val scoreTextView = systemUnderTest.findViewById(game_score_text_view) as TextView
        val tapMeButton = systemUnderTest.findViewById(tap_me_button) as Button

        tapMeButton.performClick()

        assertEquals("Your Score: 1", scoreTextView.text)

        // Destroy the original activity
        controller
            .saveInstanceState(bundle)
            .pause()
            .stop()
            .destroy()

        // Bring up a new activity
       controller = Robolectric.buildActivity(MainActivity::class.java)
            .create(bundle)
            .start()
            .restoreInstanceState(bundle)
            .resume()
            .visible()

        val newSystemUnderTest = controller.get()
        val newScoreTextView = newSystemUnderTest.findViewById(game_score_text_view) as TextView

        // Assert score before rotation is the same as score after rotation
        assertEquals(scoreTextView.text, newScoreTextView.text)
    }


    // Use Pair type to return two variables from setUpTestableActivity()
    private fun setUpTestableActivity(): Pair<ActivityController<MainActivity>, MainActivity> {
        val controller = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .visible()
        return Pair(controller, controller.get())
    }
}
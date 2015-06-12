package br.com.nglauber.intelsoftwaredaydemo;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;

    public EspressoTest() {
        super(MainActivity.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mainActivity = getActivity();
    }

    @Test
    public void testAdicionarFavorito() {
        String bookTitle = "NoSQL Essencial";

        onView(withId(R.id.recyclerViewWeb)).perform(
                RecyclerViewActions.actionOnItem(hasDescendant(withText(bookTitle)), click()));

        waitFor(1000);

        onView(withId(R.id.txtTitulo)).check(matches(withText(bookTitle)));

        waitFor(1000);

        onView(withId(R.id.action_favorito)).perform(click());

        waitFor(1000);

        pressBack();

        waitFor(1000);

        onView(withId(R.id.pager)).perform(swipeLeft());

        waitFor(1000);

        onView(withId(R.id.recyclerViewFavoritos)).check(matches(hasDescendant(withText(bookTitle))));

        waitFor(3000);
    }

    private void waitFor(long t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
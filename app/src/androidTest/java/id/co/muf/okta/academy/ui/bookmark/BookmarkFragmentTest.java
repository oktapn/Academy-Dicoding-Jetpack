package id.co.muf.okta.academy.ui.bookmark;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import id.co.muf.okta.academy.R;
import id.co.muf.okta.academy.testing.SingleFragmentActivity;
import id.co.muf.okta.academy.utils.EspressoIdlingResource;
import id.co.muf.okta.academy.utils.RecyclerViewItemCountAssertion;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class BookmarkFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private BookmarkFragment bookmarkFragment = new BookmarkFragment();

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
        activityRule.getActivity().setFragment(bookmarkFragment);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadBookmarks() {
        onView(withId(R.id.rv_bookmark)).check(new RecyclerViewItemCountAssertion(5));
    }

}
package com.neiljaywarner.bibleshareandstudy;

import android.test.ActivityInstrumentationTestCase2;


/**
 * Created by nwarner on 7/13/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mMainActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();

    }

    public void testPreConditions() {
        assertNotNull(mMainActivity);
    }

    public void testStartingTitle_isSeekinGod() {
        final String expected = "SeekingGod";
        //todo; use string as test_string from strings.xml

        String startingTitle = mMainActivity.getActionBar().getTitle().toString();
        //getTitle is null until you wait.

        //  assertEquals(expected, startingTitle);
        assertEquals("WrongValue", startingTitle);
    }


    public void testLoadVerseBiblegatewayHtml() {
        //could be open in webview for all 7 webviews, would users prefer this? (especially when i get to the swipe)
        //so i could use a VerseTextFragmetn
        String firstStudyTextUrl = "https://www.biblegateway.com/passage/?search=Jer%2029:11-13;Matt%207:7-11;Matt%206:25-34;Acts%2017:22-31&interface=print";
        // assertEquals(false, mMainActivity.openLinkInBrowser("https://www.biblegateway.com/passage/?search=Jer%2029:11-13;Matt%207:7-11;Matt%206:25-34;Acts%2017:22-31&interface=print"));
        //probably go ahead and use webview.
        // assertEquals(false, mMainActivity.);

        String wrongValue = "<html></html>";
        String rightValue = "dummyText";
        assertEquals(rightValue, mMainActivity.loadTextFromFile("/file/dummyfile.txt"));

        //note: https://www.biblegateway.com/legal/terms/
        // Summary: Less than 250 verses, less than 25% of the text of the work.  not sure if source code comprises text of the work?
    }
    //this test should fail.


}

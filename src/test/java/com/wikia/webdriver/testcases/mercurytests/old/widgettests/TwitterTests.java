package com.wikia.webdriver.testcases.mercurytests.old.widgettests;

import com.wikia.webdriver.common.contentpatterns.MercuryMessages;
import com.wikia.webdriver.common.contentpatterns.MercurySubpages;
import com.wikia.webdriver.common.contentpatterns.MercuryWikis;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.InBrowser;
import com.wikia.webdriver.common.core.helpers.Browser;
import com.wikia.webdriver.common.core.helpers.Emulator;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.elements.common.Navigate;
import com.wikia.webdriver.elements.mercury.Navigation;
import com.wikia.webdriver.elements.mercury.TopBar;
import com.wikia.webdriver.pageobjectsfactory.pageobject.widget.TwitterWidgetPageObject;

import org.testng.annotations.Test;

@Execute(onWikia = MercuryWikis.MERCURY_AUTOMATION_TESTING)
@InBrowser(
    browser = Browser.CHROME,
    emulator = Emulator.GOOGLE_NEXUS_5
)
public class TwitterTests extends NewTestTemplate {

  private TopBar topBar;
  private Navigation navigation;
  private Navigate navigate;
  private TwitterWidgetPageObject widget;

  private static final String TWITTER_ONE_WIDGET_ARTICLE_NAME = "TwitterMercury/OneWidget";
  private static final String TWITTER_MULTIPLE_WIDGETS_ARTICLE_NAME = "TwitterMercury/MultipleWidgets";
  private static final String TWITTER_INCORRECT_WIDGET_ARTICLE_NAME = "TwitterMercury/IncorrectWidget";
  private static final String MAPS_ARTICLE_NAME = "Map";

  private void init() {
    this.topBar = new TopBar(driver);
    this.navigation = new Navigation(driver);
    this.navigate = new Navigate(driver);
    this.widget = new TwitterWidgetPageObject(driver);
  }

  @Test(groups = "MercuryTwitterWidgetTest_001")
  public void MercuryTwitterWidgetTest_001_isLoadedOnFirstVisitDirectlyFromUrl() {
    init();

    widget.create(TWITTER_ONE_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + TWITTER_ONE_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercuryTwitterWidgetTest_002")
  public void MercuryTwitterWidgetTest_002_isLoadedOnFirstVisitFromDifferentArticle() {
    init();

    widget.create(TWITTER_ONE_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + MercurySubpages.MAIN_PAGE);
    topBar.openNavigation();
    navigation.navigateToPage(TWITTER_ONE_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercuryTwitterWidgetTest_003")
  public void MercuryTwitterWidgetTest_003_isLoadedOnSecondVisitFromDifferentArticle() {
    init();

    widget.create(TWITTER_ONE_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + TWITTER_ONE_WIDGET_ARTICLE_NAME);
    topBar.openNavigation();
    navigation.navigateToPage(MAPS_ARTICLE_NAME);
    topBar.openNavigation();
    navigation.navigateToPage(TWITTER_ONE_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercuryTwitterWidgetTest_004")
  public void MercuryTwitterWidgetTest_004_areLoadedOnFirstVisitDirectlyFromUrl() {
    init();

    widget.createMultiple(TWITTER_MULTIPLE_WIDGETS_ARTICLE_NAME);
    navigate.toPage("/wiki/" + TWITTER_MULTIPLE_WIDGETS_ARTICLE_NAME);

    Assertion.assertTrue(
        widget.areAllValidSwappedForIFrames(),
        MercuryMessages.SOME_VALID_WIDGETS_WERE_NOT_SWAPPED_MSG
    );

    Assertion.assertTrue(widget.areLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercuryTwitterWidgetTest_005", enabled = false)
  public void MercuryTwitterWidgetTest_005_isErrorPresent() {
    init();

    widget.createIncorrect(TWITTER_INCORRECT_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + TWITTER_INCORRECT_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isErrorPresent(), MercuryMessages.INVISIBLE_MSG);
  }
}

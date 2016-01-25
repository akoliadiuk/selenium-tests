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
import com.wikia.webdriver.pageobjectsfactory.pageobject.widget.SoundCloudWidgetPageObject;

import org.testng.annotations.Test;

@Execute(onWikia = MercuryWikis.MERCURY_AUTOMATION_TESTING)
@InBrowser(
    browser = Browser.CHROME,
    emulator = Emulator.GOOGLE_NEXUS_5
)
public class SoundCloudTests extends NewTestTemplate {

  private TopBar topBar;
  private Navigation navigation;
  private SoundCloudWidgetPageObject widget;
  private Navigate navigate;

  private static final String SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME = "SoundCloudMercury/OneWidget";
  private static final String SOUND_CLOUD_MULTIPLE_WIDGETS_ARTICLE_NAME = "SoundCloudMercury/MultipleWidgets";
  private static final String MAPS_ARTICLE_NAME = "Map";

  private void init() {
    this.topBar = new TopBar(driver);
    this.navigation = new Navigation(driver);
    this.navigate = new Navigate(driver);
    this.widget = new SoundCloudWidgetPageObject(driver);
  }

  @Test(groups = "MercurySoundCloudWidgetTest_001")
  public void MercurySoundCloudWidgetTest_001_isLoadedOnFirstVisitDirectlyFromUrl() {
    init();

    widget.create(SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercurySoundCloudWidgetTest_002")
  public void MercurySoundCloudWidgetTest_002_isLoadedOnFirstVisitFromDifferentArticle() {
    init();

    widget.create(SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + MercurySubpages.MAIN_PAGE);
    topBar.openNavigation();
    navigation.navigateToPage(SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercurySoundCloudWidgetTest_003")
  public void MercurySoundCloudWidgetTest_003_isLoadedOnSecondVisitFromDifferentArticle() {
    init();

    widget.create(SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);
    navigate.toPage("/wiki/" + SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);
    topBar.openNavigation();
    navigation.navigateToPage(MAPS_ARTICLE_NAME);
    topBar.openNavigation();
    navigation.navigateToPage(SOUND_CLOUD_ONE_WIDGET_ARTICLE_NAME);

    Assertion.assertTrue(widget.isLoaded(), MercuryMessages.INVISIBLE_MSG);
  }

  @Test(groups = "MercurySoundcloudWidgetTest_004")
  public void MercurySoundcloudWidgetTest_004_areLoadedOnFirstVisitDirectlyFromUrl() {
    init();

    widget.createMultiple(SOUND_CLOUD_MULTIPLE_WIDGETS_ARTICLE_NAME);
    navigate.toPage("/wiki/" + SOUND_CLOUD_MULTIPLE_WIDGETS_ARTICLE_NAME);

    Assertion.assertTrue(widget.areLoaded(), MercuryMessages.INVISIBLE_MSG);
  }
}

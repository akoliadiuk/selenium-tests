package com.wikia.webdriver.Common.Core;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.wikia.webdriver.Common.DriverProvider.DriverProvider;
import com.wikia.webdriver.Common.Logging.PageObjectLogging;
import com.wikia.webdriver.Common.Properties.Properties;

public class CommonFunctions {
	static By logInAjax = By.className("ajaxLogin");
	static By userNameField = By.cssSelector("[name='username']");
	static By passwordField = By.cssSelector("[name='password']");
	static By remeberMeCheckBox = By.cssSelector("input[type=checkbox]");
	static By submitButton = By.cssSelector("input[type='submit']");

	private static WebDriver driver;
	private static WebDriverWait wait;

	// public CommonFunctions()
	// {
	// driver = DriverProvider.getWebDriver();
	// wait = new WebDriverWait(driver, 30);
	// }

	/**
	 * log in by overlay available from main menu
	 * 
	 * @param userName
	 * @param password
	 * @author: Karol Kujawiak
	 */

	private static void clickLogInAjax() {
		driver = DriverProvider.getWebDriver();
		WebElement loginButton = driver.findElement(logInAjax);
		loginButton.click();

		PageObjectLogging.log("logIn", "log in ajax button clicked", true,
				driver);
	}

	private static void typeInUserName(String userName) {
		driver = DriverProvider.getWebDriver();
		WebElement userNameInAjax = driver.findElement(userNameField);

		userNameInAjax.sendKeys(userName);
		PageObjectLogging.log("logIn", "user name field populated", true,
				driver);
	}

	private static void typeInUserPass(String password) {
		driver = DriverProvider.getWebDriver();
		WebElement passwordInAjax = driver.findElement(passwordField);
		passwordInAjax.sendKeys(password);
		PageObjectLogging
				.log("logIn", "password field populated", true, driver);
	}

	private static void clickSubmitLoginButton(String userName) {
		driver = DriverProvider.getWebDriver();
		WebElement submitLoginButton = driver.findElement(submitButton);
		submitLoginButton.click();
		PageObjectLogging.log("logIn", "submit button clicked", true, driver);
	}

	private static void verifyUserIsLoggedIn(String userName) {
		driver.findElement(By.cssSelector("a[href*='/User:" + userName + "']"));
		PageObjectLogging.log("verifyUserIsLoggedIn",
				"verified user is logged in", true, driver);
	}

	public static void logIn(String userName, String password) {
		driver = DriverProvider.getWebDriver();
		driver.manage().deleteAllCookies();
		String temp = driver.getCurrentUrl();
		try {
			driver.get(Global.DOMAIN + "wiki/Special:UserLogin");
		} catch (TimeoutException e) {
			PageObjectLogging.log("logIn",
					"page loads for more than 30 seconds", true, driver);
		}
		WebElement userNameField = driver.findElement(By
				.cssSelector("#WikiaArticle input[name='username']"));
		WebElement passwordField = driver.findElement(By
				.cssSelector("#WikiaArticle input[name='password']"));
		WebElement submitButton = driver.findElement(By
				.cssSelector("#WikiaArticle input.login-button.big"));
		userNameField.sendKeys(userName);
		passwordField.sendKeys(password);
		try {
			submitButton.click();
		} catch (TimeoutException e) {
			PageObjectLogging.log("logIn",
					"page loads for more than 30 seconds", true, driver);
			try {
				driver.navigate().refresh();
			} catch (TimeoutException f) {
				PageObjectLogging.log("logIn",
						"page loads for more than 30 seconds", true, driver);
			}
		}
		driver.findElement(By.cssSelector(".AccountNavigation a[href*='"
				+ userName + "']"));// only for verification
		try {
			if (!(temp.contains("Special:UserLogout")||temp.contains("Specjalna:Wyloguj"))) {
				driver.get(temp);
			}
		} catch (TimeoutException e) {
			PageObjectLogging.log("logIn",
					"page loads for more than 30 seconds", true, driver);
		}
		driver.findElement(By.cssSelector(".AccountNavigation a[href*='"
				+ userName + "']"));
		// driver = DriverProvider.getWebDriver();
		// wait = new WebDriverWait(driver, 30);
		// WebElement logInAjaxElem = driver.findElement(logInAjax);
		// logInAjaxElem.click();
		// PageObjectLogging.log("logIn", "log in ajax button clicked", true,
		// driver);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='username']")));
		// WebElement userNameFieldElem = driver.findElement(userNameField);
		// userNameFieldElem.sendKeys(userName);
		// PageObjectLogging.log("logIn", "user name field is populated with " +
		// userName, true, driver);
		// try {
		// Thread.sleep(500);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// WebElement passwordFieldElem = driver.findElement(passwordField);
		// PageObjectLogging.log("logIn", "password field is populated", true,
		// driver);
		// passwordFieldElem.sendKeys(password);
		// WebElement submitButtonElem = driver.findElement(submitButton);
		// submitButtonElem.click();
		// PageObjectLogging.log("logIn", "submit button clicked", true,
		// driver);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/User:"+userName+"']")));
	}

	public static void logInSpecialUserLogin(String userName, String password,
			String userNameEnc) {
		driver = DriverProvider.getWebDriver();
		driver.manage().deleteAllCookies();
		String temp = driver.getCurrentUrl();
		try {
			driver.get(Global.DOMAIN + "wiki/Special:UserLogin");
		} catch (TimeoutException e) {
			PageObjectLogging.log("logIn",
					"page loads for more than 30 seconds", true, driver);
		}
		WebElement userNameField = driver.findElement(By
				.cssSelector("#WikiaArticle input[name='username']"));
		WebElement passwordField = driver.findElement(By
				.cssSelector("#WikiaArticle input[name='password']"));
		WebElement submitButton = driver.findElement(By
				.cssSelector("#WikiaArticle input.login-button.big"));
		userNameField.sendKeys(userName);
		passwordField.sendKeys(password);
		try {
			submitButton.click();
		} catch (TimeoutException e) {
			PageObjectLogging.log("logIn",
					"page loads for more than 30 seconds", true, driver);
			try {
				driver.navigate().refresh();
			} catch (TimeoutException f) {
				PageObjectLogging.log("logIn",
						"page loads for more than 30 seconds", true, driver);
			}
		}
		driver.findElement(By.cssSelector(".AccountNavigation a[href*='"
				+ userNameEnc + "']"));// only for verification
		try {
			if (!(temp.contains("Special:UserLogout")||temp.contains("Specjalna:Wyloguj"))) {
				driver.get(temp);
			}
		} catch (TimeoutException e) {
			PageObjectLogging.log("logIn",
					"page loads for more than 30 seconds", true, driver);
		}
		driver.findElement(By.cssSelector(".AccountNavigation a[href*='"
				+ userNameEnc + "']"));
	}

	/**
	 * log in by overlay available from main menu
	 * 
	 * @param userName
	 * @param password
	 * @author: Karol Kujawiak
	 */

	public static void logInDriver(String userName, String password,
			WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		WebElement logInAjaxElem = driver.findElement(logInAjax);
		logInAjaxElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("input[name='username']")));
		WebElement userNameFieldElem = driver.findElement(userNameField);
		userNameFieldElem.sendKeys(userName);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement passwordFieldElem = driver.findElement(passwordField);
		passwordFieldElem.sendKeys(password);
		WebElement submitButtonElem = driver.findElement(submitButton);
		submitButtonElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("a[href*='/User:" + userName + "']")));
	}

	public static void logInDropDown(String userName, String password,
			String userNameEnc) {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		WebElement logInAjaxElem = driver.findElement(logInAjax);
		logInAjaxElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("input[name='username']")));
		WebElement userNameFieldElem = driver.findElement(userNameField);
		userNameFieldElem.sendKeys(userName);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement passwordFieldElem = driver.findElement(passwordField);
		passwordFieldElem.sendKeys(password);
		WebElement submitButtonElem = driver.findElement(submitButton);
		submitButtonElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("a[href*='" + userNameEnc + "']")));
	}
	
	public static void logInDropDownFB(){
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		WebElement logInAjaxElem = driver.findElement(logInAjax);
		logInAjaxElem.click();
		PageObjectLogging.log("logInDropDownFB", "login ajax clicked", true);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.className("wikia-button-facebook")));
		((JavascriptExecutor) driver)
		.executeScript("$('.wikia-button-facebook').click()");
		PageObjectLogging.log("logInDropDownFB", "facebook button clicked", true);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		WebElement fbConnect = driver.findElement(By.className("wikia-button-facebook"));		
//		fbConnect.click();
		//getting window handles
		Object[] windows = driver.getWindowHandles().toArray();
		int delay = 500;
		int sumDelay = 500;
		while(windows.length==1){
			try {
				Thread.sleep(delay);
				windows = driver.getWindowHandles().toArray();
				sumDelay+=500;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(sumDelay>5000){
				PageObjectLogging.log("logInDropDownFB", "facebook button clicked but without result", false);
				break;
			}
			
		}
		driver.switchTo().window(windows[1].toString());
		PageObjectLogging.log("logInDropDownFB", "facebook popup window detected", true);
		PageObjectLogging.log("logInDropDownFB", "switching to facebook pop-up window", true);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pass")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='login']")));
		WebElement em = driver.findElement(By.id("email"));
		WebElement pa = driver.findElement(By.id("pass"));
		WebElement sm = driver.findElement(By.cssSelector("input[name='login']"));
		em.clear();
		pa.clear();
		em.sendKeys(Properties.userNameFB);
		PageObjectLogging.log("logInDropDownFB", "facebook username filled", true);
		pa.sendKeys(Properties.passwordFB);
		PageObjectLogging.log("logInDropDownFB", "facebook password filled", true);
		sm.click();
		PageObjectLogging.log("logInDropDownFB", "facebook log in button clicked", true);
		driver.switchTo().window(windows[0].toString());
		PageObjectLogging.log("logInDropDownFB", "switching to main window", true);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a img.avatar")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("a[href*='Patrick_test_07-Feb-12_1313']")));
		PageObjectLogging.log("logInDropDownFB", "facebook login verification passed", true);
	}

	/**
	 * log in by overlay available from main menu
	 * 
	 * @param userName
	 * @param password
	 * @author: Karol Kujawiak
	 */

	public static void logIn(String userName, String password, WebDriver driver) {
		String temp = driver.getCurrentUrl();
		driver.get(Global.DOMAIN + "wiki/Special:UserLogin");
		WebElement userNameField = driver.findElement(By
				.cssSelector("#WikiaArticle input[name='username']"));
		WebElement passwordField = driver.findElement(By
				.cssSelector("#WikiaArticle input[name='password']"));
		String submitButtonSelector = "#WikiaArticle input[class='login-button big']";
		WebElement submitButton = driver.findElement(By
				.cssSelector(submitButtonSelector));
		userNameField.sendKeys(userName);
		passwordField.sendKeys(password);
		submitButton.click();
		driver.findElement(By.cssSelector(".AccountNavigation a[href*='User:"
				+ userName + "']"));// only for verification
		driver.get(temp);
	}

	/**
	 * 
	 * @param userName
	 * @author: Karol Kujawiak
	 */
	public static void logOut(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		try {
			driver.manage().deleteAllCookies();
			driver.get(Global.DOMAIN + "wiki/Special:UserLogout?noexternals=1");
		} catch (TimeoutException e) {
			PageObjectLogging.log("logOut",
					"page loads for more than 30 seconds", true);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector("a[data-id='login']")));
		PageObjectLogging.log("logOut", "uses is logged out", true, driver);
	}

	public static void logOutMobile(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		try {
			driver.manage().deleteAllCookies();
			driver.get(Global.DOMAIN + "wiki/Special:UserLogout?noexternals=1");
		} catch (TimeoutException e) {
			PageObjectLogging.log("logOut",
					"page loads for more than 30 seconds", true);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(".tgl.lgdout")));
		PageObjectLogging.log("logOut", "uses is logged out", true, driver);
	}
	
	/**
	 * log in by overlay available from main menu, using generic credentials
	 * 
	 * @author: Karol Kujawiak
	 */
	public static void logIn() {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		WebElement logInAjaxElem = driver.findElement(logInAjax);
		logInAjaxElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("input[name='username']")));
		WebElement userNameFieldElem = driver.findElement(userNameField);
		userNameFieldElem.sendKeys(Properties.userName);
		WebElement passwordFieldElem = driver.findElement(passwordField);
		passwordFieldElem.sendKeys(Properties.password);
		WebElement submitButtonElem = driver.findElement(submitButton);
		submitButtonElem.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.cssSelector("header#WikiaHeader a.ajaxLogin")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.cssSelector("header#WikiaHeader a.ajaxRegister")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("a[href*='/User:" + Properties.userName + "']")));
		PageObjectLogging.log("logIn ", "Normal user logged in", true, driver);
	}

	/**
	 * log in by overlay available from main menu, using generic credentials
	 * with stay logged in check-box checked
	 * 
	 * @author: Karol Kujawiak
	 */
	public static void logInRememberMe() {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		WebElement logInAjaxElem = driver.findElement(logInAjax);
		logInAjaxElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("input[name='username']")));
		WebElement userNameFieldElem = driver.findElement(userNameField);
		userNameFieldElem.sendKeys(Properties.userName);
		WebElement passwordFieldElem = driver.findElement(passwordField);
		passwordFieldElem.sendKeys(Properties.password);
		WebElement rememberMeCheckBox = driver.findElement(remeberMeCheckBox);
		rememberMeCheckBox.click();
		WebElement submitButtonElem = driver.findElement(submitButton);
		submitButtonElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("a[href*='/User:" + Properties.userName + "']")));
		PageObjectLogging.log("logIn ", "Normal user logged in", true, driver);
	}

	/**
	 * Obsolete below method - should be fixed
	 * */

	public static void logInAsStaff() {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		WebElement logInAjaxElem = driver.findElement(logInAjax);
		logInAjaxElem.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("input[name='username']")));
		WebElement userNameFieldElem = driver.findElement(userNameField);
		userNameFieldElem.sendKeys(Properties.userNameStaff);
		WebElement passwordFieldElem = driver.findElement(passwordField);
		passwordFieldElem.sendKeys(Properties.passwordStaff);
		WebElement submitButtonElem = driver.findElement(submitButton);
		submitButtonElem.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.cssSelector("header#WikiaHeader a.ajaxLogin")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.cssSelector("header#WikiaHeader a.ajaxRegister")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("a[href*='/User:" + Properties.userNameStaff
						+ "']")));
		PageObjectLogging.log("logInAsStaff ", "Staff user logged in", true,
				driver);
	}
//
//	/**
//	 * verifies whether pattern and current string are the same and log to log
//	 * file
//	 * 
//	 * @param pattern
//	 * @param current
//	 * @author: Karol Kujawiak
//	 */
//	public static void assertString(String pattern, String current) {
//
//		try {
//			Assert.assertEquals(pattern, current);
//			PageObjectLogging.log("assertString", "pattern string: " + pattern
//					+ " <br/>current string: " + current + "<br/>are the same",
//					true);
//		} catch (AssertionError e) {
//			PageObjectLogging.log("assertString",
//					"pattern string: " + pattern + " <br/>current string: "
//							+ current + "<br/>are different", false);
//		}
//	}
//	
//	
//	/**
//	 * Verify actual number is the same as expected number
//	 * 
//	 * @param aNumber
//	 * @param secondNumber
//	 * @author: Piotr Gabryjeluk
//	 */
//	public static void assertNumber(Number expected, Number actual,
//			String message) {
//		try {
//			Assert.assertEquals(expected, actual);
//			PageObjectLogging.log("assertNumber", message + ", expected: "
//					+ expected + ", got: " + actual, true);
//		} catch (AssertionError e) {
//			PageObjectLogging.log("assertNumber", message + ", expected: "
//					+ expected + ", got: " + actual, false);
//		}
//	}

	/**
	 * 
	 * @param attributeName
	 * @return
	 * @author: Karol Kujawiak
	 */
	public static String currentlyFocusedGetAttributeValue(String attributeName) {

		String currentlyFocusedName = getCurrentlyFocused().getAttribute(
				attributeName);
		return currentlyFocusedName;
	}

	/**
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 * @author: Karol Kujawiak
	 */
	public static String getAttributeValue(WebElement element,
			String attributeName) {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		return element.getAttribute(attributeName);
	}

	/**
	 * 
	 * @return author: Karol Kujawiak
	 */
	public static WebElement getCurrentlyFocused() {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		return driver.switchTo().activeElement();
	}

	/**
	 * Scroll to the given element
	 * <p>
	 * This mehtod is used mostly because Chrome does not scroll to elements
	 * automaticly (18 july 2012)
	 * <p>
	 * This method uses JavascriptExecutor
	 * 
	 * @author Michal Nowierski
	 * @param element
	 *            Webelement to be scrolled to
	 */
	public static void scrollToElement(WebElement element) {
		driver = DriverProvider.getWebDriver();
		wait = new WebDriverWait(driver, 30);
		int y = element.getLocation().getY();
		// firstly make sure that window scroll is set at the top of browser (if
		// not method will scroll up)
		((JavascriptExecutor) driver)
				.executeScript("window.scrollBy(0,-3000);");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + y
				+ ");");
	}

	/**
	 * Move cursor to the given X and Y coordinates
	 * 
	 * @author Michal Nowierski
	 * @param x
	 * @param y
	 */
	public static void MoveCursorTo(int x, int y) {
		Robot robot = null;
		try {
			Thread.sleep(1000);
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.mouseMove(x, y);
	}

	/**
	 * Move cursor to Element existing in default DOM, by its Location
	 * 
	 * @author Michal Nowierski
	 * @param elem1_location
	 *            Location of WebElement (getLocation method)
	 */
	public static void MoveCursorToElement(Point elem1_location) {
		// Toolkit toolkit = Toolkit.getDefaultToolkit ();
		// Dimension dim = toolkit.getScreenSize();
		// double ScreenHeight = dim.getHeight();

		// int FireFoxStatusBarHeight = 20;
		driver = DriverProvider.getWebDriver();
		int pixDiff = 0;
		if (Global.BROWSER.equals("FF")) {
			pixDiff = 6;
		}
		int elem_Y = elem1_location.getY();
		int elem_X = elem1_location.getX();

		Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		int ScreenHeightWithoutTaskBarHeight = maxBounds.height;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object visibleDomHeightJS = js
				.executeScript("return $(window).height()");
		int VisibleDomHeight = Integer.parseInt(visibleDomHeightJS.toString());

		Object invisibleUpperDomHeightJS = js
				.executeScript("return document.documentElement.scrollTop");
		int invisibleUpperDomHeight = Integer
				.parseInt(invisibleUpperDomHeightJS.toString());
		MoveCursorTo(elem_X + 10, elem_Y + ScreenHeightWithoutTaskBarHeight
				- VisibleDomHeight - pixDiff + 1 - invisibleUpperDomHeight);
	}

	public static void MoveCursorToElement(Point elem1_location,
			WebDriver driver) {
		// Toolkit toolkit = Toolkit.getDefaultToolkit ();
		// Dimension dim = toolkit.getScreenSize();
		// double ScreenHeight = dim.getHeight();

		// int FireFoxStatusBarHeight = 20;

		int pixDiff = 0;
		if (Global.BROWSER.equals("FF")) {
			pixDiff = 6;
		}
		int elem_Y = elem1_location.getY();
		int elem_X = elem1_location.getX();

		Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		int ScreenHeightWithoutTaskBarHeight = maxBounds.height;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object visibleDomHeightJS = js
				.executeScript("return window.innerHeight");
		int VisibleDomHeight = Integer.parseInt(visibleDomHeightJS.toString());
		Object invisibileUpperDomHeightJS = js
				.executeScript("return window.pageYOffset");
		int invisibileUpperDomHeight = Integer
				.parseInt(invisibileUpperDomHeightJS.toString());

		MoveCursorTo(elem_X + 10, elem_Y + ScreenHeightWithoutTaskBarHeight
				- VisibleDomHeight - invisibileUpperDomHeight - pixDiff + 1);
	}

	/**
	 * Move cursor to Element existing in an IFrame DOM, by its By locator, and
	 * the Iframe Webelement
	 * 
	 * @author Michal Nowierski
	 * @param IframeElemBy
	 *            By selector of element to be hovered over
	 * @param IFrame
	 *            IFrame where the element exists
	 */
	public static void MoveCursorToIFrameElement(By IframeElemBy,
			WebElement IFrame) {
		driver = DriverProvider.getWebDriver();
		Point IFrameLocation = IFrame.getLocation();
		driver.switchTo().frame(IFrame);
		wait.until(ExpectedConditions.visibilityOfElementLocated(IframeElemBy));
		Point IFrameElemLocation = driver.findElement(IframeElemBy)
				.getLocation();
		IFrameElemLocation = IFrameElemLocation.moveBy(IFrameLocation.getX(),
				IFrameLocation.getY());
		driver.switchTo().defaultContent();
		MoveCursorToElement(IFrameElemLocation);
	}

	public static void ClickElement() {
		Robot robot = null;
		try {
			Thread.sleep(300);
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	/**
	 * Move cursor to from current position by given x and y
	 * 
	 * @author Michal Nowierski
	 * @param x
	 *            horrizontal move
	 * @param y
	 *            vertical move
	 */
	public static void DragFromCurrentCursorPositionAndDrop(int x, int y) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		java.awt.Point CurrentCursorPosition = MouseInfo.getPointerInfo()
				.getLocation();
		int currentX = (int) CurrentCursorPosition.getX();
		int currentY = (int) CurrentCursorPosition.getY();
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(currentX + x, currentY + y);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public static void removeChatModeratorRights(String userName,
			WebDriver driver) {
		driver.get(Global.DOMAIN + "wiki/Special:UserRights?user=" + userName);
		PageObjectLogging.log("enterUserRightsPage", "user rights page opened",
				true);
		WebElement chatModeratorChkbox = driver.findElement((By
				.cssSelector("input#wpGroup-chatmoderator")));
		WebElement submitButton = driver.findElement((By
				.cssSelector("input[title='[alt-shift-s]']")));
		chatModeratorChkbox.click();
		submitButton.click();
	}

	public static String logInCookie(String userName, String password) {
		if (!Global.LOGIN_BY_COOKIE)
		{
			CommonFunctions.logIn(userName, password);
			return null;
		}
		else{
			try {
				driver = DriverProvider.getWebDriver();
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httpPost = new HttpPost(Global.DOMAIN + "api.php");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();

				nvps.add(new BasicNameValuePair("action", "login"));
				nvps.add(new BasicNameValuePair("format", "xml"));
				nvps.add(new BasicNameValuePair("lgname", userName));
				nvps.add(new BasicNameValuePair("lgpassword", password));

				httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

				HttpResponse response = null;

				response = httpclient.execute(httpPost);

				HttpEntity entity = response.getEntity();
				String xmlResponse = null;

				xmlResponse = EntityUtils.toString(entity);

				String[] xmlResponseArr = xmlResponse.split("\"");
				String token = xmlResponseArr[5];

				// System.out.println(token);

				while (xmlResponseArr.length < 11) {// sometimes first request does
													// not contain full information,
													// in such situation
													// xmlResponseArr.length < 11
					List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();

					nvps2.add(new BasicNameValuePair("action", "login"));
					nvps2.add(new BasicNameValuePair("format", "xml"));
					nvps2.add(new BasicNameValuePair("lgname", userName));
					nvps2.add(new BasicNameValuePair("lgpassword", password));
					nvps2.add(new BasicNameValuePair("lgtoken", token));

					httpPost.setEntity(new UrlEncodedFormEntity(nvps2, HTTP.UTF_8));

					response = httpclient.execute(httpPost);

					entity = response.getEntity();

					xmlResponse = EntityUtils.toString(entity);

					xmlResponseArr = xmlResponse.split("\"");
				}

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("$.cookie('" + xmlResponseArr[11] + "_session', '"
						+ xmlResponseArr[13] + "', {'domain': 'wikia.com'})");
				js.executeScript("$.cookie('" + xmlResponseArr[11] + "UserName', '"
						+ xmlResponseArr[7] + "', {'domain': 'wikia.com'})");
				js.executeScript("$.cookie('" + xmlResponseArr[11] + "UserID', '"
						+ xmlResponseArr[5] + "', {'domain': 'wikia.com'})");
				js.executeScript("$.cookie('" + xmlResponseArr[11] + "Token', '"
						+ xmlResponseArr[9] + "', {'domain': 'wikia.com'})");
				try{						
					driver.get(Global.DOMAIN+"Special:Random");
				}
				catch (TimeoutException e) {
					PageObjectLogging.log("loginCookie",
							"page timeout after login by cookie", true);
				}

				driver.findElement(By
						.cssSelector(".AccountNavigation a[href*='User:" + userName
								+ "']"));// only for verification
				PageObjectLogging.log("loginCookie",
						"user was logged in by cookie", true, driver);
				return xmlResponseArr[11];
			} catch (UnsupportedEncodingException e) {
				PageObjectLogging.log("logInCookie", "UnsupportedEncodingException", false);
				return null;
			} catch (ClientProtocolException e) {
				PageObjectLogging.log("logInCookie", "ClientProtocolException", false);
				return null;
			} catch (IOException e) {
				PageObjectLogging.log("logInCookie", "IOException", false);
				return null;
			} catch (Exception e) {
				PageObjectLogging.log("logInCookie", e.getClass().getCanonicalName(), false);
				return null;
				
			}
		}
		
	}

	public static String logInCookie(String userName, String password,
			WebDriver driver) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(
					"http://mediawiki119.wikia.com/api.php");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			nvps.add(new BasicNameValuePair("action", "login"));
			nvps.add(new BasicNameValuePair("format", "xml"));
			nvps.add(new BasicNameValuePair("lgname", userName));
			nvps.add(new BasicNameValuePair("lgpassword", password));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = null;

			response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			String xmlResponse = null;

			xmlResponse = EntityUtils.toString(entity);

			String[] xmlResponseArr = xmlResponse.split("\"");
			String token = xmlResponseArr[5];

			while (xmlResponseArr.length < 11) {// sometimes first request does
												// not contain full information,
												// in such situation
												// xmlResponseArr.length < 11
				List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();

				nvps2.add(new BasicNameValuePair("action", "login"));
				nvps2.add(new BasicNameValuePair("format", "xml"));
				nvps2.add(new BasicNameValuePair("lgname", userName));
				nvps2.add(new BasicNameValuePair("lgpassword", password));
				nvps2.add(new BasicNameValuePair("lgtoken", token));

				httpPost.setEntity(new UrlEncodedFormEntity(nvps2, HTTP.UTF_8));

				response = httpclient.execute(httpPost);

				entity = response.getEntity();

				xmlResponse = EntityUtils.toString(entity);

				xmlResponseArr = xmlResponse.split("\"");
			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("$.cookie('" + xmlResponseArr[11] + "_session', '"
					+ xmlResponseArr[13] + "', {'domain': 'wikia.com'})");
			js.executeScript("$.cookie('" + xmlResponseArr[11] + "UserName', '"
					+ xmlResponseArr[7] + "', {'domain': 'wikia.com'})");
			js.executeScript("$.cookie('" + xmlResponseArr[11] + "UserID', '"
					+ xmlResponseArr[5] + "', {'domain': 'wikia.com'})");
			js.executeScript("$.cookie('" + xmlResponseArr[11] + "Token', '"
					+ xmlResponseArr[9] + "', {'domain': 'wikia.com'})");
			try{
				driver.get(Global.DOMAIN+"Special:Random");
				}
			catch (TimeoutException e) {
				PageObjectLogging.log("loginCookie",
						"page timeout after login by cookie", true);
			}

			driver.findElement(By
					.cssSelector(".AccountNavigation a[href*='User:" + userName
							+ "']"));// only for verification
			PageObjectLogging.log("loginCookie",
					"user was logged in by cookie", true, driver);
			return xmlResponseArr[11];
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void logoutCookie(String wiki) {
		driver = DriverProvider.getWebDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("$.cookie('" + wiki + "_session', null)");
		js.executeScript("$.cookie('" + wiki + "UserName', null)");
		js.executeScript("$.cookie('" + wiki + "UserID', null)");
		js.executeScript("$.cookie('" + wiki + "Token', null)");
	}

}

package week4.day4.windows;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Amazon {

	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1.Load the URL https://www.amazon.in/
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 1.Find the element using XPath
		// enter one plus 9 pro and press enter
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("oneplus 9 pro", Keys.ENTER);

		// write a XPath for finding the full items in the list of WebElement price
		List<WebElement> searchElement = driver.findElements(By.xpath("//div[contains(@class,'s-result-item')]"));
		Thread.sleep(2000);
		// 2.Get the price of the first product
		String textOfPrice = searchElement.get(2).getText();
		// Split the words in to array
		String[] text = textOfPrice.split("\\n");
		// convert string into integer and get the text of the price in the index 4 and
		// remove the symbol
		int price = Integer.parseInt(text[4].replaceAll("[^0-9]", ""));
		// print the price
		System.out.println("The price of the first product is :" + price);

		// 3. Print the number of customer ratings for the first displayed product
		WebElement rating = driver.findElement(By.xpath("//span[text()='4.1 out of 5 stars']"));
		// Get the text of the element using innerHTML
		// The innerHTML property sets or returns the HTML content (inner HTML) of an
		// element
		System.out.println("Rating for the first product is: " + rating.getAttribute("innerHTML"));

		// 4. Click the first text link of the first image
		System.out.println("Text Link displayed for the first image: " + text[0]);

		// 5.Click the first image
		WebElement image = driver.findElement(By.xpath("//img[@class='s-image']"));
		// create a Actions class
		Actions builder = new Actions(driver);
		// move the element and perform the click action
		builder.moveToElement(image).click().perform();

		// To get all the window handles
		Set<String> windowHandles = driver.getWindowHandles();
		// convert it to list for getting particular window
		List<String> handles = new ArrayList<String>(windowHandles);
		// System.out.println(handles);
		// move the control to the second window
		driver.switchTo().window(handles.get(1));
		String windowHandle = driver.getWindowHandle();
		System.out.println("The value of second window is :" + windowHandle);

		// 6. Create a file and store the snapshot of the first image
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("./Snaps/oneplus9pro.png");
		FileUtils.copyToDirectory(source, destination);

		// 7.Click 'Add to Cart' button
		driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
		WebElement subTotalAmount = driver.findElement(By.xpath("//span[@id='attach-accessory-cart-subtotal']"));
		Thread.sleep(3000);
		String str = subTotalAmount.getAttribute("innerHTML");
		// convert string into integer and get the text of the price in the index 4 and
		// remove the symbol
		int cartPrice = Integer
				.parseInt(str.replaceAll("[^0-9]", "").substring(0, str.replaceAll("[^0-9]", "").length() - 2));
		System.out.println("Subtotal is " + cartPrice);
		// Verify the price and cardPrice is equal or not
		if (price == cartPrice)
			System.out.println("Cart subtotal is valid amount");
		else {
			System.out.println("Cart subtotal is  not valid amount");

		}
		// close all the opened windows
		driver.quit();
	}

}

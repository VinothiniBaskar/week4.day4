package week4.day4.windows;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1.Load the URL https://www.amazon.in/
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 2) Mouseover on Brands and Search L'Oreal Paris
		WebElement brands = driver.findElement(By.xpath("//a[text()='brands']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(brands).perform();
		driver.findElement(By.id("brandSearchBox")).sendKeys("L'Oreal Paris", Keys.ENTER);

		// 3) Click L'Oreal Paris
		Thread.sleep(2000);
		WebElement paris = driver.findElement(By.xpath("//div[@class='css-ov2o3v']//a"));
		builder.moveToElement(paris).click().perform();

		// 4) Check the title contains L'Oreal Paris
		System.out.println("The title of the page is :" + driver.getTitle());
		String text = driver.getTitle();
		if (text.contains("Buy L'Oreal Paris products ")) {
			System.out.println("The title is verfied");

		} else {
			System.out.println("The title is not verfied");
		}

		// 5) Click sort By and select customer top rated
		WebElement sort = driver.findElement(By.xpath("//span[text()='Sort By : popularity']"));
		builder.moveToElement(sort).click().perform();
		driver.findElement(By.xpath("//span[text()='customer top rated']")).click();

		// 6) Click Category and click Hair->Click haircare->Shampoo
		Thread.sleep(3000);
		WebElement category = driver.findElement(By.xpath("//div[@id='first-filter']//span[1]"));
		builder.moveToElement(category).click().perform();
		// 7) Click Category and click Hair->Click haircare->Shampoo
		Thread.sleep(3000);
		WebElement hair = driver.findElement(By.xpath("//span[text()='Hair']"));
		builder.moveToElement(hair).click().perform();
		WebElement haircare = driver
				.findElement(By.xpath("//ul[@class='scroll css-1e7z8zv']//li//div//span[text()='Hair Care']"));
		builder.moveToElement(haircare).click().perform();

		WebElement shampoo = driver.findElement(By.xpath("//input[@id='checkbox_Shampoo_316']"));
		builder.moveToElement(shampoo).click().perform();

		// 8)Click->Concern->Color Protection
		Thread.sleep(3000);
		WebElement concern = driver.findElement(By.xpath("//div[@class='css-w2222k']/div/span[text()='Concern']"));
		builder.moveToElement(concern).click().perform();
		WebElement protect = driver.findElement(
				By.xpath("//div[@class='control-box css-1ap0cm9']/label/div/span[text()='Color Protection']"));
		builder.moveToElement(protect).click().perform();

		// 9) Click on L'Oreal Paris Colour Protect Shampoo
		WebElement lorParis = driver.findElement(By.xpath("//div[@class='css-43m2vm']//img"));
		builder.moveToElement(lorParis).click().perform();
		
		// 10)Print the MRP of the product
		String price = driver.findElement(By.xpath("//div[@class='css-1d0jf8e']//span[2]")).getText();
		int mrpPrice = Integer.parseInt(price.replaceAll("[^0-9]", ""));
		System.out.println("The MRP price of the product is :" + mrpPrice);

		// to get all the window handles
		Set<String> windowHandles = driver.getWindowHandles();
		// convert it to list for getting particular window
		List<String> handles = new ArrayList<String>(windowHandles);
		// System.out.println(handles);
		// move the control to the second window
		driver.switchTo().window(handles.get(1));
		String windowHandle = driver.getWindowHandle();
		System.out.println(windowHandle);

		// 12) Click on ADD to BAG
		Thread.sleep(2000);
		WebElement bag = driver.findElement(By.xpath("//div[@class='css-vp18r8']//button//span"));
		builder.moveToElement(bag).click().perform();
		// 13) Go to Shopping Bag
		WebElement viewBag = driver.findElement(By.xpath("//div[@class='css-0 e1ewpqpu1']//button"));
		builder.moveToElement(viewBag).click().perform();

		// 14) Print the Grand Total amount
		WebElement frame = driver.findElement(By.xpath("//iframe[@class='css-acpm4k']"));
		driver.switchTo().frame(frame);
		String grandTotal = driver.findElement(By.xpath("//div[text()='259']")).getText();
		int total = Integer.parseInt(grandTotal.replaceAll("[^0-9]", ""));
		System.out.println("The Grand Total amount of product is  :" + total);

		// 15) Click Proceed
		driver.findElement(By.xpath("//span[text()='Proceed']/ancestor::button")).click();
		// 16) Click on Continue as Guest
		driver.findElement(By.xpath("//div[text()='Checkout as guest']/following-sibling::button")).click();
		// 17) Print the Grand total amount
		WebElement grandTotalPrice = driver
				.findElement(By.xpath("//div[@class='payment-details-tbl grand-total-cell prl20']//span"));
		String text1 = grandTotalPrice.getText();
		int grandTotalPriceAmount = Integer.parseInt(text1.replaceAll("[^0-9]", ""));
		System.out.println("The GrandTotalPriceAmount is :" + grandTotalPriceAmount);

		// 18) verify the grand total amount
		if (total == grandTotalPriceAmount) {
			System.out.println("The Grant Total amount is valid");
		} else {
			System.out.println("The Grant Total amount is not  valid");

		}
	}

}
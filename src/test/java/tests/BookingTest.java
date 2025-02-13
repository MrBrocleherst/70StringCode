package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;


public class BookingTest {
    private WebDriver driver;

    @BeforeSuite
    public void setUp() {// Укажи свой путь
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testOpenBookingHomePage() {
        driver.get("https://www.booking.com/");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Booking.com"), "Заголовок страницы некорректный!");
    }

    @Test
    public void testHotelSearch() {
        driver.get("https://www.booking.com/");

        // Вводим город в поле поиска
        WebElement searchField = driver.findElement(By.xpath("//input[@name='ss']"));
        searchField.sendKeys("Paris");

        // Вводим даты (можно использовать календарь, для простоты добавим конкретные даты)
        WebElement checkInField = driver.findElement(By.cssSelector(".xp__dates__checkin"));
        WebElement checkOutField = driver.findElement(By.cssSelector(".xp__dates__checkout"));
        checkInField.click();
        WebElement checkInDate = driver.findElement(By.xpath("//td[@data-date='2025-03-01']"));
        checkInDate.click();
        checkOutField.click();
        WebElement checkOutDate = driver.findElement(By.xpath("//td[@data-date='2025-03-10']"));
        checkOutDate.click();

        // Нажимаем кнопку поиска
        WebElement searchButton = driver.findElement(By.className("sb-searchbox__button"));
        searchButton.click();

        // Ждем, пока загрузятся результаты (можно использовать WebDriverWait)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("sr_property_block")));

        // Проверяем, что результаты отелей отображаются
        List<WebElement> hotelList = driver.findElements(By.className("sr_property_block"));
        Assert.assertTrue(hotelList.size() > 0, "Результаты поиска не отображаются.");
    }

    @Test
    public void testPriceFilter() {
        driver.get("https://www.booking.com/");

        // Вводим город и даты, как в предыдущем тесте
        WebElement searchField = driver.findElement(By.id("ss"));
        searchField.sendKeys("Paris");
        WebElement searchButton = driver.findElement(By.className("sb-searchbox__button"));
        searchButton.click();

        // Ждем загрузки результатов
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("sr_property_block")));

        // Применяем фильтр по цене
        WebElement priceFilter = driver.findElement(By.xpath("//label[@for='price-0-100']"));
        priceFilter.click();

        // Проверяем, что фильтр применился
        WebElement firstHotelPrice = driver.findElement(By.xpath("//div[@data-testid='price-and-discounted-price']"));
        String priceText = firstHotelPrice.getText();
        Assert.assertTrue(priceText.contains("€100"), "Фильтр по цене не применился корректно.");
    }

    @Test
    public void testLogin() {
        driver.get("https://www.booking.com/");

        // Нажимаем на кнопку для входа
        WebElement loginButton = driver.findElement(By.className("header-sign-in"));
        loginButton.click();

        // Вводим логин и пароль
        WebElement emailField = driver.findElement(By.id("username"));
        emailField.sendKeys("test@example.com");  // Подставь свой тестовый email
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("password123");  // Подставь свой тестовый пароль

        // Нажимаем на кнопку входа
        WebElement submitButton = driver.findElement(By.className("bui-button--primary"));
        submitButton.click();

        // Ждем, пока загрузится страница после входа
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("profile-name")));

        // Проверяем, что пользователь залогинен
        WebElement profileName = driver.findElement(By.className("profile-name"));
        Assert.assertTrue(profileName.isDisplayed(), "Пользователь не залогинен.");
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

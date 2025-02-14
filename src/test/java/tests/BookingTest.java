package tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class BookingTest {

    @BeforeClass
    public void setUp() {
        Configuration.baseUrl = "https://www.booking.com";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000; // Ждем до 5 сек, если элемент не найден сразу
    }

    @Test
    public void testLogin() {
        // Открываем страницу
        open("/");

        // Кликаем по кнопке входа
        $(".bui-button--secondary").click();

        // Вводим email
        $("#username").setValue("your-email@example.com").pressEnter();

        // Вводим пароль (после перехода на страницу ввода пароля)
        $("#password").setValue("your-password").pressEnter();

        // Проверяем, что мы залогинились (например, по наличию профиля)
        $(".header-user-name").shouldBe(visible);
    }
}

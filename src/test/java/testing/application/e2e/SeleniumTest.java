package testing.application.e2e;


import ge.tsu.DemoCarsShopApplication.DemoCarsShopApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoCarsShopApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeleniumTest {

    private WebDriver driver;

    @LocalServerPort
    private int port;


    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @AfterAll
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldDisplayFiveCarTitlesOnCarPage() {
        driver.get("http://localhost:" + port + "/car/");
        assertTrue(driver.getCurrentUrl().contains("/"));

        List<WebElement> titles = driver.findElements(By.xpath("//h3[@class='panel-title']"));
        System.out.println(titles.size());
        assertEquals(titles.size(), 5);

    }


    @Test
    void registerTest() {
        driver.get("http://localhost:" + port + "/car/register");
        driver.findElement(By.xpath("//input[@id = 'usernameField']")).sendKeys("myName");
        driver.findElement(By.xpath("//input[@id = 'passwordField']")).sendKeys("MyPassword");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();


        WebElement usernameInput = driver.findElement(By.xpath("//div[@class = 'alert alert-success alert-dismissible fade show']"));
        assertTrue(usernameInput.isDisplayed(), "Username field is not visible");




    }
}
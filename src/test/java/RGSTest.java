import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class RGSTest {
    private WebDriver driver;
    private String baseUrl;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Desktop/WebDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://www.rgs.ru/";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);



    }

    @Test
    public void checkTitleTest(){

        WebDriverWait wait = new WebDriverWait(driver,30);
        driver.get(baseUrl);

        driver.findElement(By.xpath("//body/div[@id='main-navbar']/div[@class='container-navbar-rgs']/div[@id='main-navbar-collapse']/ol[@class='nav navbar-nav navbar-nav-rgs-menu pull-left']/li[@class='dropdown adv-analytics-navigation-line1-link current']/a[1]")).click();
        driver.findElement(By.xpath("//div[@class='grid__col-sm-3']//div[3]//ul[1]//li[2]//a[1]")).click();
        driver.findElement(By.xpath("//p[1]//strong[text() = 'Добровольное медицинское страхование']"));
        driver.findElement(By.xpath("//*[@class='btn btn-default text-uppercase hidden-xs adv-analytics-navigation-desktop-floating-menu-button'][contains(text(), 'Отправить заявку')]")).click();
        driver.findElement(By.xpath("//h4[@class='modal-title']//b[text() = 'Заявка на добровольное медицинское страхование']"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='LastName']")));
        driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys("Иванов");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='FirstName']")));
        driver.findElement(By.xpath("//input[@name='FirstName']")).sendKeys("Иван");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='MiddleName']")));
        driver.findElement(By.xpath("//input[@name='MiddleName']")).sendKeys("Иванович");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='Region']")));
        driver.findElement(By.xpath("//select[@name='Region']")).sendKeys("Владимирская область");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[5]//input[1]")));
        driver.findElement(By.xpath("//div[5]//input[1]")).sendKeys("1234567891");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='Email']")));
        driver.findElement(By.xpath("//input[@name='Email']")).sendKeys("qwertyqwerty");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@name='Comment']")));
        driver.findElement(By.xpath("//textarea[@name='Comment']")).sendKeys("Привет мир!");
        driver.findElement(By.xpath("//input[@class='checkbox']")).click();


        assertEquals("Фамилии не совпадают", "Иванов", driver.findElement(By.xpath("//input[@name='LastName']")).getAttribute("value"));
        assertEquals("Имена не совпадают", "Иван", driver.findElement(By.xpath("//input[@name='FirstName']")).getAttribute("value"));
        assertEquals("Отчества не совпадают", "Иванович", driver.findElement(By.xpath("//input[@name='MiddleName']")).getAttribute("value"));
        assertEquals("Регионы не совпадают", "33", driver.findElement(By.xpath("//select[@name='Region']")).getAttribute("value"));
        assertEquals("Номера телефонов не совпадают", "+7 (123) 456-78-91", driver.findElement(By.xpath("//div[5]//input[1]")).getAttribute("value"));
        assertEquals("Электронные почты не совпадают", "qwertyqwerty", driver.findElement(By.xpath("//input[@name='Email']")).getAttribute("value"));
        assertEquals("Комментарии не совпадают", "Привет мир!", driver.findElement(By.xpath("//textarea[@name='Comment']")).getAttribute("value"));
        assertEquals("В чек-боксе не поставлена «галка» о согласии на обработку персональных данных", true, driver.findElement(By.xpath("//input[@class='checkbox']")).isSelected());


        driver.findElement(By.xpath("//button[@id='button-m']")).click();

        assertEquals("У Поля Эл. почта не присутствует сообщение об ошибке Введите корректный email", "Введите адрес электронной почты",  driver.findElement(By.className("validation-error-text")).getAttribute("innerText"));

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);

    }
}

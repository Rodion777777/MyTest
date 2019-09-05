import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class RGSTest {
    private static WebDriver driver;
    private static String baseUrl = "https://www.rgs.ru/";


    @BeforeClass
    public static void setUp() throws Exception {
        System.getProperty("driver");
        System.setProperty("webdriver.gecko.driver", "D:/Desktop/WebDrivers/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "D:/Desktop/WebDrivers/chromedriver.exe");


        if (System.getProperty("driver").equals("chrome")) {
            driver = new ChromeDriver();
        }
        if (System.getProperty("driver").equals("firefox")) {
            driver = new FirefoxDriver();
        }
        else System.out.println("Введите \"mvn clean test -Ddriver=firefox\" или \"mvn clean test -Ddriver=chrome\"," +
                " чтобы выбрать необходимый веб-драйвер.");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);

        driver.get(baseUrl);

        driver.findElement(By.xpath("//body/div[@id='main-navbar']/div[@class='container-navbar-rgs']/div[@id='main-navbar-collapse']/ol[@class='nav navbar-nav navbar-nav-rgs-menu pull-left']/li[@class='dropdown adv-analytics-navigation-line1-link current']/a[1]")).click();
        driver.findElement(By.xpath("//div[@class='grid rgs-main-menu-links']//div[1]//div[2]//ul[1]//li[2]//a[1]")).click();
        driver.findElement(By.xpath("//p[1]//strong[text() = 'Добровольное медицинское страхование']"));
        driver.findElement(By.xpath("//*[@class='btn btn-default text-uppercase hidden-xs adv-analytics-navigation-desktop-floating-menu-button'][contains(text(), 'Отправить заявку')]")).click();
        driver.findElement(By.xpath("//h4[@class='modal-title']//b[text() = 'Заявка на добровольное медицинское страхование']"));

    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Семенов", "Аркадий", "Иванович"},
                {"Петров", "Юрий", "Александрович"},
                {"Полежаев", "Семен", "Акакиевич"}
        });
    }

    @Parameterized.Parameter
    public String lastName;

    @Parameterized.Parameter(1)
    public String firstName;

    @Parameterized.Parameter(2)
    public String middleName;

    @Test(timeout = 60000)
    public void checkTitleTest(){

        driver.findElement(By.xpath("//input[@name='LastName']")).clear();
        driver.findElement(By.xpath("//input[@name='FirstName']")).clear();
        driver.findElement(By.xpath("//input[@name='MiddleName']")).clear();
        driver.findElement(By.xpath("//input[@name='Email']")).clear();
        driver.findElement(By.xpath("//textarea[@name='Comment']")).clear();

        driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys(lastName);
        driver.findElement(By.xpath("//input[@name='FirstName']")).sendKeys(firstName);
        driver.findElement(By.xpath("//input[@name='MiddleName']")).sendKeys(middleName);
        WebElement dropdown = driver.findElement(By.xpath("//select[@name='Region']"));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Владимирская область");
//        driver.findElement(By.xpath("//select[@name='Region']")).sendKeys("Владимирская область");
        driver.findElement(By.xpath("//div[5]//input[1]")).sendKeys("1234567891");
        driver.findElement(By.xpath("//input[@name='Email']")).sendKeys("qwertyqwerty");
        driver.findElement(By.xpath("//textarea[@name='Comment']")).sendKeys("Привет мир!");


        assertEquals("Фамилии не совпадают", lastName, driver.findElement(By.xpath("//input[@name='LastName']")).getAttribute("value"));
        assertEquals("Имена не совпадают", firstName, driver.findElement(By.xpath("//input[@name='FirstName']")).getAttribute("value"));
        assertEquals("Отчества не совпадают", middleName, driver.findElement(By.xpath("//input[@name='MiddleName']")).getAttribute("value"));
        assertEquals("Регионы не совпадают", "33", driver.findElement(By.xpath("//select[@name='Region']")).getAttribute("value"));
        assertEquals("Номера телефонов не совпадают", "+7 (123) 456-78-91", driver.findElement(By.xpath("//div[5]//input[1]")).getAttribute("value"));
        assertEquals("Электронные почты не совпадают", "qwertyqwerty", driver.findElement(By.xpath("//input[@name='Email']")).getAttribute("value"));
        assertEquals("Комментарии не совпадают", "Привет мир!", driver.findElement(By.xpath("//textarea[@name='Comment']")).getAttribute("value"));



    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }


}

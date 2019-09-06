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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class RGSTest {

    private static WebDriver driver;
    private static String baseUrl = "https://www.rgs.ru/";


    WebElement checkBoxElement = driver.findElement(By.xpath("//input[@class='checkbox']"));
    WebElement firstNameElement = driver.findElement(By.xpath("//input[@name='FirstName']"));
    WebElement lastNameElement = driver.findElement(By.xpath("//input[@name='LastName']"));
    WebElement regionElement = driver.findElement(By.xpath("//select[@name='Region']"));
    Select select = new Select(regionElement);
    WebElement middleNameElement = driver.findElement(By.xpath("//input[@name='MiddleName']"));
    WebElement emailElement = driver.findElement(By.xpath("//input[@name='Email']"));
    WebElement commentElement = driver.findElement(By.xpath("//textarea[@name='Comment']"));
    WebElement phoneElement = driver.findElement(By.xpath("//div[5]//input[1]"));

    @FindBy(xpath = "//span[.='Введите адрес электронной почты']")
    private WebElement errorEmailElement;

    public void fillPhone(String phone) {
        phoneElement.clear();
        phoneElement.sendKeys(phone);
    }

    public void fillComment(String comment) {
        commentElement.clear();
        commentElement.sendKeys(comment);
    }

    public void fillEmail(String email) {
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public void fillMiddleName(String middleName) {
        middleNameElement.clear();
        middleNameElement.sendKeys(middleName);
    }

    public void fillLastName(String lastName) {
        lastNameElement.clear();
        lastNameElement.sendKeys(lastName);
    }

    public void fillFirstName(String firstName) {
        firstNameElement.clear();
        firstNameElement.sendKeys(firstName);
    }


    public void checkBoxClick() {
        checkBoxElement.click();
    }


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

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);

        driver.get(baseUrl);


        driver.findElement(By.xpath("//a[@class = 'hidden-xs' and contains(text(), 'Меню')]")).click();
        driver.findElement(By.xpath("//div[@class='grid rgs-main-menu-links']//a[contains(.,'ДМС')]")).click();
        assertEquals("Ожидаемый текст \"Добровольное медицинское страхование\" не совпадает с фактическим",
                "Добровольное медицинское страхование", driver.findElement(By.xpath("//p[1]//strong[1]"))
                        .getText());
        driver.findElement(By.xpath("//a[contains(.,'Отправить заявку')]")).click();

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

    public void checkTitleTest() {
        RGSTest rgsTest = PageFactory.initElements(driver, RGSTest.class);
        fillLastName(lastName);
        fillFirstName(firstName);
        fillMiddleName(middleName);
        fillEmail("qwertyqwerty");
        fillComment("Привет мир!");
        select.selectByVisibleText("Владимирская область");
        fillPhone("1234567891");
        if (!checkBoxElement.isSelected()){
            checkBoxClick();}


        assertEquals("Фамилии не совпадают", lastName, lastNameElement.getAttribute("value"));
        assertEquals("Имена не совпадают", firstName, firstNameElement.getAttribute("value"));
        assertEquals("Отчества не совпадают", middleName, middleNameElement.getAttribute("value"));
        assertEquals("Регионы не совпадают", "33", regionElement.getAttribute("value"));
        assertEquals("Номера телефонов не совпадают", "+7 (123) 456-78-91", phoneElement.getAttribute("value"));
        assertEquals("Электронные почты не совпадают", "qwertyqwerty", emailElement.getAttribute("value"));
        assertEquals("Комментарии не совпадают", "Привет мир!", commentElement.getAttribute("value"));
        assertEquals("У Поля Эл. почта не присутствует сообщение об ошибке Введите корректный email", "Введите адрес электронной почты",  rgsTest.errorEmailElement.getAttribute("innerText"));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }


}

package tests;

import com.google.inject.Inject;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import modules.CourseUrlProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTest {
    private String title;
    private Date startDate;
    private String url;
    private WebDriver driver;

    @Inject
    public CourseTest(WebDriver driver) {
        this.driver = driver;
        this.url = "/course/";
    }

    public CourseTest(String title, Date startDate) {
        this.title = title;
        this.startDate = startDate;
        this.url = "/course/";
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Date getStartDate() {
        return startDate;
    }

    @И("я ищу курсы, стартующие с {string}")
    public List<CourseTest> searchCoursesStartingWith(String date) {
        List<WebElement> courseCards = driver.findElements(By.cssSelector(".course-card"));

        return courseCards.stream()
                .map(card -> {
                    String title = card.findElement(By.cssSelector(".course-card__title")).getText();
                    String dateString = card.findElement(By.cssSelector(".course-card__start-date")).getText();
                    Date startDate = parseDate(dateString);
                    return new CourseTest(title, startDate);
                })
                .filter(course -> course.getStartDate().after(parseDate(date)))
                .toList();
    }

    private Date parseDate(String dateString) {
        try {
            var dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru"));
            return dateFormat.parse(dateString.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Тогда("я вывожу курсы с их названиями и датами старта")
    public void verifyCourseDetails(List<CourseTest> courses) {
        for (CourseTest course : courses) {
            String courseUrl = CourseUrlProvider.getCourseUrl() + course.getUrl();
            driver.get(courseUrl);

            WebElement coursePageTitle = driver.findElement(By.cssSelector(".course-page__title"));
            WebElement coursePageStartDate = driver.findElement(By.cssSelector(".course-page__start-date"));

            String coursePageStartDateString = coursePageStartDate.getText().trim();
            assertEquals(course.getTitle(), coursePageTitle.getText().trim());
            assertTrue(coursePageStartDateString.contains(course.getStartDate().toString()),
                    "Дата на странице курса не совпадает с датой курса: " + coursePageStartDateString);
        }
    }
}
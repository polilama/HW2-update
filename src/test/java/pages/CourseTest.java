package pages;


import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTest {
    private final static String OTUS_URL = "https://otus.ru";
    private String title;
    private Date startDate;
    private String url;
    private WebDriver driver;

    @Inject
    public CourseTest(WebDriver driver) {
        this.driver = driver;
    }

    @Inject
    public CourseTest(String title, Date startDate) {
        this.title = title;
        this.startDate = startDate;
        this.url = "/course/";
    }

    @Inject
    public CourseTest(String title, Date startDate, WebDriver driver) {
        this.title = title;
        this.startDate = startDate;
        this.url = "/course/";
        this.driver = driver;
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
    public static List<CourseTest> fromWebElements(List<WebElement> courseCards) {
        return courseCards.stream()
                .map(card -> {
                    String title = card.findElement(By.cssSelector(".course-card__title")).getText();
                    String dateString = card.findElement(By.cssSelector(".course-card__start-date")).getText();
                    Date startDate = parseDate(dateString);
                    return new CourseTest(title, startDate);
                })
                .toList();
    }

    public static Optional<CourseTest> findEarliestCourse(List<CourseTest> courses) {
        return courses.stream()
                .reduce((course1, course2) -> course1.getStartDate().before(course2.getStartDate()) ? course1 : course2);
    }

    public static Optional<CourseTest> findLatestCourse(List<CourseTest> courses) {
        return courses.stream()
                .reduce((course1, course2) -> course1.getStartDate().after(course2.getStartDate()) ? course1 : course2);
    }

    private static Date parseDate(String dateString) {
        try {
            var dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.forLanguageTag("ru"));
            return dateFormat.parse(dateString.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    @Тогда("я вывожу курсы с их названиями и датами старта")
    public static void verifyCourseDetails(WebDriver driver, CourseTest course) {
        String courseUrl = OTUS_URL + course.getUrl();
        driver.get(courseUrl);

        WebElement coursePageTitle = driver.findElement(By.cssSelector(".course-page__title"));
        WebElement coursePageStartDate = driver.findElement(By.cssSelector(".course-page__start-date"));

        String coursePageStartDateString = coursePageStartDate.getText().trim();
        assertEquals(course.getTitle(), coursePageTitle.getText().trim());
        assertTrue(coursePageStartDateString.contains(course.getStartDate().toString()),
                "Дата на странице курса не совпадает с датой курса: " + coursePageStartDateString);
    }
}

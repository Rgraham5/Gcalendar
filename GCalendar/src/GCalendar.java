import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GCalendar extends Application {
  @Override
  public void start(Stage primaryStage) {
    BorderPane pane = new BorderPane();
    CalendarPane calendarPane = new CalendarPane();
    pane.setCenter(calendarPane);

    Button btPrior = new Button("Previous");
    Button btNext = new Button("Next");
    HBox hBox = new HBox(5);
    hBox.getChildren().addAll(btPrior, btNext);
    pane.setBottom(hBox);
    hBox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(pane, 700, 400);
    primaryStage.setTitle("Calendar");
    primaryStage.setScene(scene);
    primaryStage.show();

    btPrior.setOnAction(e -> {
      int currentMonth = calendarPane.getMonth();
      if (currentMonth == 0) { // months 0-11
        calendarPane.setYear(calendarPane.getYear() - 1);
        calendarPane.setMonth(11);
      } else {
        calendarPane.setMonth((currentMonth - 1) % 13);
      }
    });

    btNext.setOnAction(e -> {
      int currentMonth = calendarPane.getMonth();
      if (currentMonth == 11)
        calendarPane.setYear(calendarPane.getYear() + 1);

      calendarPane.setMonth((currentMonth + 1) % 13);
    });
  }

  public static void main(String[] args) {
    launch(args);
  }

  public int getActualMaximum(String dayOfMonth) {
    return 0;
  }

  public void add(String date2, int i) {
  }
}

class CalendarPane extends BorderPane {
  private String[] monthName = { "January", "Feburary", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December" };

  private Label lblHeader = new Label();

  private Label[] lblDay = new Label[49];

  private GregorianCalendar calendar;
  private int month; // current month
  private int year; // current year

  public CalendarPane() {

    for (int i = 0; i < 49; i++) {
      lblDay[i] = new Label();
      lblDay[i].setTextAlignment(TextAlignment.LEFT);
    }

    lblDay[0].setText("Sun.");
    lblDay[1].setText("Mon.");
    lblDay[2].setText("Tues.");
    lblDay[3].setText("Wed.");
    lblDay[4].setText("Thurs.");
    lblDay[5].setText("Fri.");
    lblDay[6].setText("Sat.");

    GridPane dayPane = new GridPane();
    dayPane.setAlignment(Pos.CENTER);

    dayPane.setHgap(10);
    dayPane.setVgap(10);
    for (int i = 0; i < 49; i++) {
      dayPane.add(lblDay[i], i % 7, i / 7);
    }

    this.setTop(lblHeader);
    BorderPane.setAlignment(lblHeader, Pos.CENTER);
    this.setCenter(dayPane);

    // Set current month and year
    calendar = new GregorianCalendar();
    month = calendar.get(Calendar.MONTH);
    year = calendar.get(Calendar.YEAR);
    updateCalendar();

    showHeader();
    showDays();
  }

  public void showHeader() {
    lblHeader.setText(monthName[month] + ", " + year);
  }

  public void showDays() {
    // first day in a month
    int startingDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

    Calendar cloneCalendar = (Calendar) calendar.clone();
    cloneCalendar.add(Calendar.DATE, -1);
    int daysInPrecedingMonth = cloneCalendar.getActualMaximum(
        Calendar.DAY_OF_MONTH);

    for (int i = 0; i < startingDayOfMonth - 1; i++) {
      lblDay[i + 7].setTextFill(Color.LIGHTGRAY);
      lblDay[i + 7].setText(daysInPrecedingMonth
          - startingDayOfMonth + 2 + i + "");
    }

    // Display days of current month
    int daysInCurrentMonth = calendar.getActualMaximum(
        Calendar.DAY_OF_MONTH);
    for (int i = 1; i <= daysInCurrentMonth; i++) {
      lblDay[i - 2 + startingDayOfMonth + 7].setTextFill(Color.BLACK);
      lblDay[i - 2 + startingDayOfMonth + 7].setText(i + "");
    }

    int j = 1;
    for (int i = daysInCurrentMonth - 1 + startingDayOfMonth + 7; i < 49; i++) {
      lblDay[i].setTextFill(Color.RED);
      lblDay[i].setText(j++ + "");
    }
  }

  public void updateCalendar() {
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DATE, 1);
  }

  public int getMonth() {
    return month;
  }

  // sets a new month//
  public void setMonth(int newMonth) {
    month = newMonth;
    updateCalendar();
    showHeader();
    showDays();
  }

  public int getYear() {
    return year;
  }

  // sets a new year//
  public void setYear(int newYear) {
    year = newYear;
    updateCalendar();
    showHeader();
    showDays();
  }
}
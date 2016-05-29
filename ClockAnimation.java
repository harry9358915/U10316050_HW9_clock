import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;
import java.util.Calendar; 
import java.util.GregorianCalendar;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class ClockPane extends Pane {
  private int hour;
  private int minute;
  private int second;
  Circle circle;
  Text t1;
  Text t2;
  Text t3;
  Text t4;
  Text t5;
  Text t6;
  Text t7;
  Line sLine;
  Line mLine;
  Line hLine;
private int visible;
  /** Construct a default clock with the current time*/
  public ClockPane() {
    setCurrentTime();
  }

  /** Construct a clock with specified hour, minute, and second */
  public ClockPane(int hour, int minute, int second) {
    this.hour = hour;
    this.minute = minute;
    this.second = second;
  }

  /** Return hour */
  public int getHour() {
    return hour;
  }

  /** Set a new hour */
  public void setHour(int hour) {
    this.hour = hour;
    paintClock();
  }

  /** Return minute */
  public int getMinute() {
    return minute;
  }

  /** Set a new minute */
  public void setMinute(int minute) {
    this.minute = minute;
    paintClock();
  }

  /** Return second */
  public int getSecond() {
    return second;
  }

  /** Set a new second */
  public void setSecond(int second) {
    this.second = second;
    paintClock();
  }
  
  /* Set the current time for the clock */
  public void setCurrentTime() {
    // Construct a calendar for the current date and time
    Calendar calendar = new GregorianCalendar();

    // Set current hour, minute and second
    this.hour = calendar.get(Calendar.HOUR_OF_DAY);
    this.minute = calendar.get(Calendar.MINUTE);
    this.second = calendar.get(Calendar.SECOND);
    
    paintClock(); // Repaint the clock
  }
  
  /** Paint the clock */
  private void paintClock() {
    // Initialize clock parameters
    double clockRadius = 
      Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
    double centerX = getWidth() / 2;
    double centerY = getHeight() / 2;

    // Draw circle
    circle = new Circle(centerX, centerY, clockRadius);
    circle.setFill(Color.WHITE);
    circle.setStroke(Color.BLACK);
    t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
    t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
    t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
    t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");
    
    // Draw second hand
    double sLength = clockRadius * 0.8;
    double secondX = centerX + sLength * 
      Math.sin(second * (2 * Math.PI / 60));
    double secondY = centerY - sLength * 
      Math.cos(second * (2 * Math.PI / 60));
    sLine = new Line(centerX, centerY, secondX, secondY);
    sLine.setStroke(Color.RED);

    // Draw minute hand
    double mLength = clockRadius * 0.65;
    double xMinute = centerX + mLength * 
      Math.sin(minute * (2 * Math.PI / 60));
    double minuteY = centerY - mLength * 
      Math.cos(minute * (2 * Math.PI / 60));
    mLine = new Line(centerX, centerY, xMinute, minuteY);
    mLine.setStroke(Color.BLUE);
    
    // Draw hour hand
    double hLength = clockRadius * 0.5;
    double hourX = centerX + hLength * 
      Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
    double hourY = centerY - hLength *
      Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
    hLine = new Line(centerX, centerY, hourX, hourY);
    hLine.setStroke(Color.GREEN);
    
    getChildren().clear();  
    getChildren().addAll(circle, t1, t2, t3, t4, sLine, mLine, hLine);
    if(visible == 1){
    	getChildren().clear(); 
    	t5 = new Text(centerX + 50, centerY, Integer.toString((int)second));
    	t6 = new Text(centerX , centerY, Integer.toString((int)minute));
    	t7 = new Text(centerX - 50, centerY, Integer.toString((int)hour));
    	getChildren().addAll(t5, t6, t7);
    	
    }
    
  }
  
  @Override
  public void setWidth(double width) {
    super.setWidth(width);
    paintClock();
  }
  
  @Override
  public void setHeight(double height) {
    super.setHeight(height);
    paintClock();
  }

public void digit(Timeline animation) {
	// TODO Auto-generated method stub
	 visible = 1;
	 paintClock();
}
public void Nodigit(Timeline animation) {
	// TODO Auto-generated method stub
	visible = 0;
	paintClock();
}
}

public class ClockAnimation extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ClockPane clock = new ClockPane(); // Create a clock
	HBox hBox = new HBox(20);
	Button digit = new Button("digit");
	Button Nodigit = new Button("No digit");
	hBox.getChildren().addAll(digit, Nodigit);
	hBox.setAlignment(Pos.CENTER);
    // Create a handler for animation
    // Create a handler for animation
    EventHandler<ActionEvent> eventHandler = e -> {
      clock.setCurrentTime(); // Set a new clock time
    };
    
    // Create an animation for a running clock
    Timeline animation = new Timeline(
      new KeyFrame(Duration.millis(1000), eventHandler));
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play(); // Start animation
    digit.setOnAction(e->clock.digit(animation));
    Nodigit.setOnAction(e->clock.Nodigit(animation));
	BorderPane pane = new BorderPane();
	pane.setCenter(clock);
	pane.setBottom(hBox);
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 500, 500);
    primaryStage.setTitle("ClockAnimation"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}

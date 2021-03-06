import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import timetable.AlgoDriver;
import timetable.Course;
import timetable.Day;
import timetable.DbDriver;

/**
 *
 * @author brianbett
 */
public class DisplayTT implements Initializable {
    private ArrayList<Course> courses;
    @FXML public ComboBox<String> course;
    @FXML public ComboBox<Integer> year;
    @FXML public TableColumn<Day, String> c11;
    @FXML public TableColumn<Day, String> c10;
    @FXML public TableColumn<Day, String> c9;
    @FXML public TableColumn<Day, String> c8;
    @FXML public TableColumn<Day, String> c7;
    @FXML public TableColumn<Day, String> c6;
    @FXML public TableColumn<Day, String> c5;
    @FXML public TableColumn<Day, String> c4;
    @FXML public TableColumn<Day, String> c3;
    @FXML public TableColumn<Day, String> c2;
    @FXML public TableColumn<Day, String> c1;

    @FXML
    private TableView<Day> timetable;

    @FXML
    private TableColumn<Day, String> dayColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //dayColumn.setCellValueFactory(new PropertyValueFactory<Day, String>("Day"));

        //timetable.getItems().setAll(getDays());
        DbDriver dbDriver = new DbDriver();
        courses = dbDriver.getCourses();
        Set<String> courseNames = new HashSet<>();
        //Set<Integer> courseYears = new HashSet<>();
        for(Course c: courses){
            courseNames.add(c.getName());
            //courseYears.add(c.getYear());
        }
        course.setItems(FXCollections.observableList(new ArrayList<>(courseNames)));
        //year.setItems(FXCollections.observableList(new ArrayList<>(courseYears)));
    }

    public void backButt(ActionEvent event) throws IOException {
        Parent backBut = FXMLLoader.load(getClass().getResource("homeV2.fxml"));
        Scene backButScene = new Scene(backBut);

        Stage addRoomWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addRoomWindow.setScene(backButScene);
        addRoomWindow.show();
    }
    public void selectCourse(){
        String courseName = course.getValue();
        ArrayList<Integer> courseYears = new ArrayList<>();
        for(Course c : courses)
            if(c.getName().compareTo(courseName) == 0)
                courseYears.add(c.getYear());
        year.setItems(FXCollections.observableList(courseYears));
    }
    public void selectYear(){
        //show timetable on table
        //first create the days objects representing the timetable for this specific course and year.
        Day[] days = new Day[5];

        //get selected course
        String courseName = course.getValue();
        int courseYear = 0;
        if(year.getValue() == null)
            return;
        else courseYear = year.getValue();
        Course selectedCourse = null;
        for(Course c : courses)
            if(c.getName().compareTo(courseName) == 0 && c.getYear() == courseYear)
                selectedCourse = c;
        //loop through days finding classes for each day
        AlgoDriver driver = new AlgoDriver();
        for(int i = 0; i < days.length; i++){
            days[i] = driver.getClasses(i, selectedCourse);
            //System.out.println(i+"\n"+ (days[i] == null));
            //System.out.println("DisplayTT107"+"\t"+days[i].getWeekDay()+"\t"+days[i].getC1()+"\t"+days[i].getC2()+"\t"+days[i].getC3()+"\t"+days[i].getC4()+"\t"+days[i].getC5()+"\t"+days[i].getC6()+"\t"+days[i].getC7()+"\t"+days[i].getC8()+"\t"+days[i].getC9()+"\t"+days[i].getC10()+"\t"+days[i].getC11());


        }

        ObservableList<Day> data =
                FXCollections.observableArrayList(
                       //new Day("Monday")
                        days
                );
        timetable.setItems(data);
        dayColumn.setCellValueFactory(
                new PropertyValueFactory<>("weekDay")
        );
        c1.setCellValueFactory(
                new PropertyValueFactory<>("c1")
        );
        c2.setCellValueFactory(
                new PropertyValueFactory<>("c2")
        );
        c3.setCellValueFactory(
                new PropertyValueFactory<>("c3")
        );
        c4.setCellValueFactory(
                new PropertyValueFactory<>("c4")
        );
        c5.setCellValueFactory(
                new PropertyValueFactory<>("c5")
        );
        c6.setCellValueFactory(
                new PropertyValueFactory<>("c6")
        );
        c7.setCellValueFactory(
                new PropertyValueFactory<>("c7")
        );
        c8.setCellValueFactory(
                new PropertyValueFactory<>("c8")
        );
        c9.setCellValueFactory(
                new PropertyValueFactory<>("c9")
        );
        c10.setCellValueFactory(
                new PropertyValueFactory<>("c10")
        );
        c11.setCellValueFactory(
                new PropertyValueFactory<>("c11")
        );

        timetable.getColumns().get(0).setVisible(false);
        timetable.getColumns().get(0).setVisible(true);
    }
}

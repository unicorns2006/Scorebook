package windowgui;

import java.util.List;
import java.util.stream.Collectors;

import capstone.scorebook.data.concrete.Athlete;
import capstone.scorebook.data.concrete.ScoreDiscus;
import capstone.scorebook.data.concrete.ScoreShotput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AthleteThrowController extends SomeStuffController {
	@FXML
	private TextField nameField, meetField, feetField, inchField;
	@FXML
	private ComboBox autoBox, eventBox, weatherBox, windBox, throwBox;
	@FXML
	private Button enter;

	public void eventOptions() {
		eventBox = new ComboBox(FXCollections.observableArrayList("Discus", "Shotput"));
	}

	public void weatherOptions() {
		weatherBox = new ComboBox(FXCollections.observableArrayList("Sunny", "Rainy"));
	}
	
	public void windOptions() {
		windBox = new ComboBox(FXCollections.observableArrayList("None", "Little","Medium", "High"));
	}
	
	public void throwOptions() {
		throwBox = new ComboBox(FXCollections.observableArrayList("1", "2", "3", "4"));
	}

	public void autoComplete() {
		List<String> names = db.getAllAthletes().stream().map(Athlete::getFullName).collect(Collectors.toList());

		ObservableList<String> options = FXCollections.observableArrayList();

		for (String str : names) {
			if (str.contains(getName())) {
				options.add(str); // add name to drop down
			}
		}

		autoBox = new ComboBox<String>(options); // present drop down
	}

	public String getName() {
		return nameField.getText();
	}

	public String getMeet() {
		return meetField.getText();
	}

	public String getAthleteID() {
		List<Athlete> athletes = db.getAllAthletes();

		for (Athlete a : athletes)
			if (a.getFullName().equals(getName()))
				return a.getID();

		return null;
	}

	public String getWeather() {
		return (String) weatherBox.getValue();
	}

	public String getWind() {
		return (String) windBox.getValue();
	}

	public int getThrow() {
		return (int) throwBox.getValue();
	}

	public int getDistance() {
		return Integer.parseInt(feetField.getText()) * 12 + Integer.parseInt(inchField.getText());
	}

	public void enter() {
		// enter into database
		if (eventBox.getValue().equals("Discus")) {
			db.insert(new ScoreDiscus(getMeet(), getAthleteID(), getWeather(), getThrow(), getDistance()));
		} else if (eventBox.getValue().equals("Shotput")) {
			db.insert(new ScoreShotput(getMeet(), getAthleteID(), getWeather(), getThrow(), getDistance()));
		}
		nameField.clear();
		feetField.clear();
		inchField.clear();
		autoBox.getItems().clear();
	}

}

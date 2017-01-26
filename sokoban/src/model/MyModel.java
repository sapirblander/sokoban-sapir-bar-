package model;

import java.util.Arrays;
import java.util.Observable;
import model.data.Level;
import model.policy.MySokobanPolicy;
import model.policy.SokobanPolicy;

public class MyModel extends Observable implements Model {
	private Level myLevel;
	private SokobanPolicy myPolicy;

	public MyModel() {
		myLevel = new Level();
		myPolicy = new MySokobanPolicy();
	}

	public SokobanPolicy getMyPolicy() {
		return myPolicy;
	}

	public void setMyPolicy(SokobanPolicy myPolicy) {
		this.myPolicy = myPolicy;
	}

	@Override
	public Level getCurrentLevel() {
		return this.myLevel;
	}

	@Override
	public void checkChanges() {
		if (myLevel.isGameUpdated()) {
			setChanged();
			notifyObservers(Arrays.asList("Display"));
			myLevel.setGameUpdated(false);
		}
	}

	@Override
	public SokobanPolicy getCurrentPolicy() {
		return getMyPolicy();
	}
}

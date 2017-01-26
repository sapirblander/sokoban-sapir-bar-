package controller;

import java.util.Observer;

public interface Controller extends Observer
{
	void start();
	void stop();
	void insertCommand(Commands command);
}

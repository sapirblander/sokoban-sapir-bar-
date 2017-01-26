package view;

public interface View 
{
	public SokobanDisplay getCurrentDisplayer();
	void displayError(String msg);
}

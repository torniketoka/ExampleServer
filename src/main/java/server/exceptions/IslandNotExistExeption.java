package server.exceptions;

public class IslandNotExistExeption extends GenericExampleException{

	public IslandNotExistExeption(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}
}

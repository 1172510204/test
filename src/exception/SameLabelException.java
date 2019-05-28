package exception;

public class SameLabelException extends Exception {

	/**
	 * 应用中存在标签相同的物体
	 */
	private static final long serialVersionUID = 1L;
	public SameLabelException(){
		super();
	}
	/*public SameLabelException(String message){
		super(message);
	}*/

}

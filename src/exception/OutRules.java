package exception;

public class OutRules {
	
	public static class DecimalsException extends Exception{
		/**
		 * 小数的位数错误
		 */
		private static final long serialVersionUID = 1L;
		public DecimalsException(){
			super();
		}
		/*public DecimalsException(String message){
			super(message);
		}*/
	}
	
	public static class MetresFalseException extends Exception{
		/**
		 * 比赛的项目只能是100|200|400，其他的错误
		 */
		private static final long serialVersionUID = 1L;
		public MetresFalseException(){
			super();
		}
		/*public MetresFalseException(String message){
			super(message);
		}*/
	}
	
	public static class NotCaptialLetterException extends Exception{
		/**
		 * 要求三位大写字母但却使用了非大写字母
		 */
		private static final long serialVersionUID = 1L;
		public NotCaptialLetterException(){
			super();
		}
		/*public NotCaptialLetterException(String message){
			super(message);
		}*/
	}
	
	
	public static class InformationNumIncorrectException extends Exception{
		/**
		 * 读入物体的信息分量数量不正确（缺少或者多）
		 */
		private static final long serialVersionUID = 1L;
		public InformationNumIncorrectException(){
			super();
		}
		/*public InformationNumIncorrectException(String message){
			super(message);
		}*/
	}

	public static class TransposeException extends Exception{
		/**
		 * 读入运动员名字和号码的顺序颠倒
		 */
		private static final long serialVersionUID = 1L;
		public TransposeException(){
			super();
		}
		/*public TransposeException(String message){
			super(message);
		}*/
	}
	
	public static class ElementException extends Exception{
		/**
		 * AtomStructure中的元素名称不符合要求
		 */
		private static final long serialVersionUID = 1L;
		public ElementException(){
			super();
		}
		/*public ElementException(String message){
			super(message);
		}*/
	}
	
	
}

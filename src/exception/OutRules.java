package exception;

public class OutRules {
	
	public static class DecimalsException extends Exception{
		/**
		 * С����λ������
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
		 * ��������Ŀֻ����100|200|400�������Ĵ���
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
		 * Ҫ����λ��д��ĸ��ȴʹ���˷Ǵ�д��ĸ
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
		 * �����������Ϣ������������ȷ��ȱ�ٻ��߶ࣩ
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
		 * �����˶�Ա���ֺͺ����˳��ߵ�
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
		 * AtomStructure�е�Ԫ�����Ʋ�����Ҫ��
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

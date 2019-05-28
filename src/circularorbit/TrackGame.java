package circularorbit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.PhysicalObject;
import physicalobject.PhysicalObjectFactory;
import physicalobject.PhysicalTrackGameFactory;
import centralobject.CentralObject;
import centralobject.CentralTrackGame;
import exception.SameLabelException;
import exception.OutRules.DecimalsException;
import exception.OutRules.InformationNumIncorrectException;
import exception.OutRules.MetresFalseException;
import exception.OutRules.NotCaptialLetterException;
import exception.OutRules.TransposeException;
import track.Track;
import track.TrackFactory;
import track.TrackFactoryTrackGame;

@SuppressWarnings("hiding")
public class TrackGame<CentralObject,PhysicalObject> extends ConcreteCircularOrbit<CentralObject, PhysicalObject> {

	//AF
	//����ConcreteCircularOrbit�е�
	//�����������������ܵ���Ŀ�����в������˶�Ա
	//RI
	//allObjects��Ϊ��,���������4-10���˶�Ա�����ܳ����ܵ���(��TrackGameAPI��legal���ж���)
	// Safety from rep exposure:
	//����ConcreteCircularOrbit�е�
    //   all the fields are private
	//centre��metres��tracknumsΪ���ɱ�ģ�allObjects�ǿɱ�ģ�������ʽ����
	private CentralObject centre=createCentralObject();
	private int metres;
	private int tracknums;
	private List<PhysicalObject> allObjects=new ArrayList<>();
	
	
	//��������:��֤RI��ȷ
	public void checkRep() {
		assert allObjects!=null;
		assert tracknums>=4&&tracknums<=10;
	}
		
	@SuppressWarnings("unchecked")
	private CentralObject createCentralObject() {
		CentralObject c=(CentralObject) new CentralTrackGame();
		centerObject=c;
		return c;
	}
	
	@SuppressWarnings("unchecked")
	public PhysicalObject createPhysicalObject(String name,int num,String nation,int age,double result) {
		PhysicalObjectFactory factory=new PhysicalTrackGameFactory();
		return (PhysicalObject) factory.createPhysicalObject1(name,num,nation,age,result);
	}
	
	
	//�������ԣ�assert�ж��ַ�������Ϊ��
	@Override
	public void readFile(String filename) throws MetresFalseException, InformationNumIncorrectException, TransposeException, NotCaptialLetterException, DecimalsException, SameLabelException {
		assert filename!=null :"�ļ�����Ϊ��";
		List<String> content = new ArrayList<>();
		try {			
			FileReader fr = new FileReader(filename);			
			BufferedReader bf = new BufferedReader(fr);			
			String str;			// ���ж�ȡ�ַ���			
			while ((str = bf.readLine()) != null) {				
				content.add(str);			
			}			
			bf.close();			
			fr.close();		
		} catch (IOException e) {			
			e.printStackTrace();		
		}
		Pattern pattern1=Pattern.compile("Game ::= ");
		Pattern pattern2=Pattern.compile("Athlete ::= <");
		Pattern pattern3=Pattern.compile("NumOfTracks ::= ");
		for(String each:content) {
			Matcher matcher1= pattern1.matcher(each);
			Matcher matcher2= pattern2.matcher(each);
			Matcher matcher3= pattern3.matcher(each);
			if(matcher1.find()) {
				String m=each.substring(9, 12);
				metres=Integer.valueOf(m).intValue();
				if(metres!=100 && metres!=200 && metres!=400) {
					System.out.println("��ȡ�ļ��б�����������");
					throw new MetresFalseException();
				}
			}
			else if(matcher2.find()) {
				Pattern patternnum=Pattern.compile("([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+)");
				Matcher matchernum= patternnum.matcher(each);
				if(!matchernum.find()) {
					System.out.println("�˶�Ա��Ϣ������������ȷ");
					throw new InformationNumIncorrectException();
				}
				Pattern pattern=Pattern.compile("([A-Za-z0-9]+),([A-Za-z0-9]+),([A-Za-z]{3}),([1-9]\\d*),([1-9][0-9]?\\.\\d*)");
				Matcher matcher= pattern.matcher(each);
				if(matcher.find()) {
					String m1=matcher.group(1);
					String m2=matcher.group(2);
					Pattern patternm1=Pattern.compile("[1-9][0-9]*");
					Pattern patternm2=Pattern.compile("[A-Za-z]+");
					Matcher matcherm1= patternm1.matcher(m1);
					Matcher matcherm2= patternm2.matcher(m2);
					if(matcherm1.find()&&matcherm2.find()) {
						System.out.println("��ȡ�ļ����˶�Ա���������ߵ�");
						throw new TransposeException();
					}
					String name=matcher.group(1);
					int num=Integer.valueOf(matcher.group(2)).intValue();
					String nation=matcher.group(3);
					boolean flagUpper=true;
					for(int i=0;i<nation.length();i++) {
						if(!Character.isUpperCase(nation.charAt(i))) {
							flagUpper=false;
						}
					}
					if(!flagUpper) {
						System.out.println("��ȡ�ļ����˶�Ա����������ĸ����ȫ����д");
						throw new NotCaptialLetterException();
					}
					int age=Integer.valueOf(matcher.group(4)).intValue();
					Pattern patterre=Pattern.compile("[1-9][0-9]?\\.\\d{2}");
					Matcher matcherre=patterre.matcher(matcher.group(5));
					if(!matcherre.find()) {
						System.out.println("�˶�Ա��óɼ���С��λ������");
						throw new DecimalsException();
					}
					double result=Double.parseDouble(matcher.group(5));
					PhysicalObject athlete=createPhysicalObject(name,num,nation,age,result);
					for(PhysicalObject each1:allObjects) {
						String nameString=((physicalobject.PhysicalObject) each1).getName();
						if(nameString.equals(name)) {
							System.out.println("�˶�Ա������"+name+"�ظ�");
							throw new SameLabelException();
						}
					}
					allObjects.add(athlete);
				}
			}
			else if(matcher3.find()) {
				String m=each.substring(16,17);
				tracknums=Integer.valueOf(m).intValue();
			}
		}
		initialModel();
		checkRep();
	}
	
	private void initialModel() {
		for(int i=0;i<tracknums;i++) {
			addTrack(i,i);
		}
		addCenterObject(centre);
	}
	
	
	
	public CentralObject getCentre() {
		return centre;
	}

	public int getMetres() {
		return metres;
	}

	//�������ԣ��жϷ���ֵ�����4-10
	public int getTracknums() {
		assert tracknums<=10&&tracknums>=4;
		return tracknums;
	}
	//�������ԣ��жϷ���ֵ�˶�Ա��list��Ϊ��
	public List<PhysicalObject> getAllObjects() {
		assert allObjects!=null;
		return new ArrayList<>(allObjects);
	}

	
}

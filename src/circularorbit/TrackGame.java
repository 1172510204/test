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
	//除了ConcreteCircularOrbit中的
	//包含比赛的米数、跑道数目和所有参赛的运动员
	//RI
	//allObjects不为空,轨道数量在4-10，运动员数不能超过跑道数(在TrackGameAPI的legal里判断了)
	// Safety from rep exposure:
	//除了ConcreteCircularOrbit中的
    //   all the fields are private
	//centre、metres、tracknums为不可变的，allObjects是可变的，做防御式拷贝
	private CentralObject centre=createCentralObject();
	private int metres;
	private int tracknums;
	private List<PhysicalObject> allObjects=new ArrayList<>();
	
	
	//防御策略:保证RI正确
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
	
	
	//防御策略：assert判断字符串不能为空
	@Override
	public void readFile(String filename) throws MetresFalseException, InformationNumIncorrectException, TransposeException, NotCaptialLetterException, DecimalsException, SameLabelException {
		assert filename!=null :"文件名字为空";
		List<String> content = new ArrayList<>();
		try {			
			FileReader fr = new FileReader(filename);			
			BufferedReader bf = new BufferedReader(fr);			
			String str;			// 按行读取字符串			
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
					System.out.println("读取文件中比赛米数错误");
					throw new MetresFalseException();
				}
			}
			else if(matcher2.find()) {
				Pattern patternnum=Pattern.compile("([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+),([A-Za-z0-9\\.]+)");
				Matcher matchernum= patternnum.matcher(each);
				if(!matchernum.find()) {
					System.out.println("运动员信息分量数量不正确");
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
						System.out.println("读取文件中运动员名字与号码颠倒");
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
						System.out.println("读取文件中运动员国籍三个字母不是全部大写");
						throw new NotCaptialLetterException();
					}
					int age=Integer.valueOf(matcher.group(4)).intValue();
					Pattern patterre=Pattern.compile("[1-9][0-9]?\\.\\d{2}");
					Matcher matcherre=patterre.matcher(matcher.group(5));
					if(!matcherre.find()) {
						System.out.println("运动员最好成绩的小数位数错误");
						throw new DecimalsException();
					}
					double result=Double.parseDouble(matcher.group(5));
					PhysicalObject athlete=createPhysicalObject(name,num,nation,age,result);
					for(PhysicalObject each1:allObjects) {
						String nameString=((physicalobject.PhysicalObject) each1).getName();
						if(nameString.equals(name)) {
							System.out.println("运动员的名字"+name+"重复");
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

	//防御策略：判断返回值轨道数4-10
	public int getTracknums() {
		assert tracknums<=10&&tracknums>=4;
		return tracknums;
	}
	//防御策略：判断返回值运动员的list不为空
	public List<PhysicalObject> getAllObjects() {
		assert allObjects!=null;
		return new ArrayList<>(allObjects);
	}

	
}

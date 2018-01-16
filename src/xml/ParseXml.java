package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Configuration;

public class ParseXml {
	public ParseXml(){
		Configuration con = Configuration.getInstance();
			try{
				File file = new File("src/xml/Serwer.xml");
			   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			   DocumentBuilder db = dbf.newDocumentBuilder();
			   Document doc = db.parse(file);
			   doc.getDocumentElement().normalize();

			   NodeList nodeLst = doc.getElementsByTagName("DaneStartowe");
			     Node fstNode = nodeLst.item(0);
			     if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

			      Element fstElmnt = (Element) fstNode;
			      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("Port");
			     Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			     NodeList fstNm = fstNmElmnt.getChildNodes();
			     con.setPort(Integer.parseInt( ((Node) fstNm.item(0)).getNodeValue()));
			   
			      NodeList numberOfPlayer = fstElmnt.getElementsByTagName("LiczbaGraczy");
			     Element numberOfPlayerElemnt = (Element) numberOfPlayer.item(0);
			     NodeList nodePlayer = numberOfPlayerElemnt.getChildNodes();
			     con.setNumberOfPlayer(Integer.parseInt(((Node) nodePlayer.item(0)).getNodeValue()));
			     
			     NodeList host = fstElmnt.getElementsByTagName("host");
			     Element hostElement = (Element) host.item(0);
			     NodeList nodehost = hostElement.getChildNodes();
			     con.setNameHost(((Node) nodehost.item(0)).getNodeValue());
			    
			     NodeList firstCard = fstElmnt.getElementsByTagName("firstCard");
			     Element firstCardElement = (Element) firstCard.item(0);
			     NodeList nodeFirstCard = firstCardElement.getChildNodes();
			     con.setFirstCardPostion(Integer.parseInt(((Node) nodeFirstCard.item(0)).getNodeValue()));
			     
			     NodeList secondCard = fstElmnt.getElementsByTagName("secondCard");
			     Element secondCardElement = (Element) secondCard.item(0);
			     NodeList nodesecondCard = secondCardElement.getChildNodes();
			     con.setSecondCardPostion(Integer.parseInt(((Node) nodesecondCard.item(0)).getNodeValue()));
			     
			     NodeList thirdCard = fstElmnt.getElementsByTagName("thirdCard");
			     Element thirdCardElement = (Element) thirdCard.item(0);
			     NodeList nodethirdCard = thirdCardElement.getChildNodes();
			     con.setThirdCardPostion(Integer.parseInt(((Node) nodethirdCard.item(0)).getNodeValue()));
			     
			     
			     NodeList fourthCard = fstElmnt.getElementsByTagName("fourthCard");
			     Element fourthCardElement = (Element) fourthCard.item(0);
			     NodeList nodefourthCard = fourthCardElement.getChildNodes();
			     con.setFourthCardPostion(Integer.parseInt(((Node) nodefourthCard.item(0)).getNodeValue()));
			     
			     NodeList fifthCard = fstElmnt.getElementsByTagName("fifthCard");
			     Element fifthCardElement = (Element) fifthCard.item(0);
			     NodeList fifthCardNode = fifthCardElement.getChildNodes();
			     con.setFifthCardPostion(Integer.parseInt(((Node) fifthCardNode.item(0)).getNodeValue()));
			     
			     
			     NodeList sixthCard = fstElmnt.getElementsByTagName("sixthCard");
			     Element sixthCardElement = (Element) sixthCard.item(0);
			     NodeList sixthCardNode = sixthCardElement.getChildNodes();
			     con.setSixthCardPostion(Integer.parseInt(((Node) sixthCardNode.item(0)).getNodeValue()));
			     
			     NodeList sevethCard = fstElmnt.getElementsByTagName("sevethCard");
			     Element sevethCardElement = (Element) sevethCard.item(0);
			     NodeList sevethCardNode = sevethCardElement.getChildNodes();
			     con.setSeventhCardPosition(Integer.parseInt(((Node) sevethCardNode.item(0)).getNodeValue()));
			     
			     NodeList ypostion = fstElmnt.getElementsByTagName("ypostion");
			     Element ypostionElement = (Element) ypostion.item(0);
			     NodeList ypostionNode = ypostionElement.getChildNodes();
			     con.setYposition(Integer.parseInt(((Node) ypostionNode.item(0)).getNodeValue()));
			     
			     
			    }
			}
			   catch (Exception e) {
			   e.printStackTrace();
			  }
			
	}
	

	
	
}

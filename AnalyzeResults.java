import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalyzeResults {
	
	public static class Constituency {
		
		String constName;
		int constNumber;
		String leadingCandidate;
		String leadingParty;
		String trailingCandidate;
		String trailingParty;
		int currentMargin;
		String previousWinningParty;
		int previousMargin;
		
		public Constituency() {
			
		}
	}
	
	public static void main(String[] args) {
		
		
		List<Document> resultPages = new ArrayList<Document>();
		List<Constituency> constituencies = new ArrayList<Constituency>();
		
		for(int cnt=0; cnt<=18; cnt++) {
			
			String pageURL;
			try {
				if (cnt != 0) {
					TimeUnit.SECONDS.sleep(10);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if (cnt == 0) {
				pageURL = "http://eciresults.nic.in/StatewiseS06.htm";
			}
			
			else {
				
				pageURL = "http://eciresults.nic.in/StatewiseS06"+ String.valueOf(cnt) + ".htm";
			}
			
			boolean connectStatus = false;
			while (connectStatus == false) {
			try {
				Document tempDoc = Jsoup.connect(pageURL).get();
				System.out.println("Processing the URL: " + pageURL);
				resultPages.add(tempDoc);
				connectStatus = true;
				
			} catch(Exception e) { connectStatus = false; System.out.println("IO Error"); }
			}
			
		}
			
		
		for(int cnt=0; cnt<=18; cnt++) {
			
			String content = resultPages.get(cnt).getElementById("divACList").outerHtml();
		
			Document doc2 = Jsoup.parse(content);
			Element trElm = doc2.select("tr").get(4);
		
			for (int cnt1=1; cnt1<=10; cnt1++) {
			
				
				if (cnt==18) {
					if(cnt1!=10) {
						cnt1 = 9;}
				}
				Constituency tempConstituency = new Constituency();
				
				Elements tdElms = trElm.children();
		
				Element tdElm = tdElms.first();
				String constName = tdElm.text();
				tempConstituency.constName = constName;
				System.out.println(constName);
		
				tdElm = tdElm.nextElementSibling();
				int constNumber = Integer.parseInt( tdElm.text() );
				tempConstituency.constNumber = constNumber;
				System.out.println(constNumber);
					
				tdElm = tdElm.nextElementSibling();
				String leadingCandidate;
				if (tdElm.childNodeSize() == 1) {
			
					leadingCandidate = tdElm.text();
					tempConstituency.leadingCandidate = leadingCandidate;
					System.out.println(leadingCandidate);
				}
				else {
			
					Element tempElm = tdElm.children().first();
					Element tempElm1 = tempElm.children().first();
					Element tempElm2 = tempElm1.children().first();
					Elements tempElms = tempElm2.children();
					leadingCandidate = tempElms.first().text();
					tempConstituency.leadingCandidate = leadingCandidate;
					System.out.println(leadingCandidate);
			
				}
		
				tdElm = tdElm.nextElementSibling();
				String leadingParty;
				if (tdElm.childNodeSize() == 1) {

					leadingParty = tdElm.text();
					tempConstituency.leadingParty = leadingParty;
					System.out.println(leadingParty);
				}
				else {
			
					Element tempElm = tdElm.children().first();
					Element tempElm1 = tempElm.children().first();
					Element tempElm2 = tempElm1.children().first();
					Elements tempElms = tempElm2.children();
					leadingParty = tempElms.first().text();
					tempConstituency.leadingParty = leadingParty;
					System.out.println(leadingParty);
			
				}
		
				tdElm = tdElm.nextElementSibling();
				String trailingCandidate;
				if (tdElm.childNodeSize() == 1) {

					trailingCandidate = tdElm.text();
					tempConstituency.trailingCandidate = trailingCandidate;
					System.out.println(trailingCandidate);
				}
				else {
			
					Element tempElm = tdElm.children().first();
					Element tempElm1 = tempElm.children().first();
					Element tempElm2 = tempElm1.children().first();
					Elements tempElms = tempElm2.children();
					trailingCandidate = tempElms.first().text();
					tempConstituency.trailingCandidate = trailingCandidate;
					System.out.println(trailingCandidate);
			
				}
		
				tdElm = tdElm.nextElementSibling();
				String trailingParty;
				if (tdElm.childNodeSize() == 1) {

					trailingParty = tdElm.text();
					tempConstituency.trailingParty = trailingParty;
					System.out.println(trailingParty);
				}
				else {
			
					Element tempElm = tdElm.children().first();
					Element tempElm1 = tempElm.children().first();
					Element tempElm2 = tempElm1.children().first();
					Elements tempElms = tempElm2.children();
					trailingParty = tempElms.first().text();
					tempConstituency.trailingParty = trailingParty;
					System.out.println(trailingParty);
			
				}
		
				tdElm = tdElm.nextElementSibling();
				int currentMargin = Integer.parseInt( tdElm.text() );
				tempConstituency.currentMargin = currentMargin;
				System.out.println(currentMargin);
		
				tdElm = tdElm.nextElementSibling().nextElementSibling().nextElementSibling();
				String previousWinningParty = tdElm.text();
				tempConstituency.previousWinningParty = previousWinningParty;
				System.out.println(previousWinningParty);

				tdElm = tdElm.nextElementSibling();
				int previousMargin = Integer.parseInt( tdElm.text() );
				tempConstituency.previousMargin = previousMargin;
				System.out.println(previousMargin);
			
				constituencies.add(tempConstituency);
			
				trElm = trElm.nextElementSibling();
			
			}
		
		}

		System.out.println("Hello");
		System.out.println("Number of Constituencies fetched = " + constituencies.size());
		int swingCounter = 0;
		
		for (int cnt2=0; cnt2<constituencies.size(); cnt2++) {
			
			Constituency tempConst = constituencies.get(cnt2);
			if(!tempConst.leadingParty.equalsIgnoreCase(tempConst.previousWinningParty)) {
				
				swingCounter++;
				System.out.println(tempConst.constName + " ; " + tempConst.constNumber + " ; " + tempConst.leadingCandidate + " ; " + tempConst.leadingParty + " ; " + tempConst.currentMargin + " ; " + tempConst.previousWinningParty + " ; " + tempConst.previousMargin);
			}
		}
		System.out.println("No. of constituencies which swung to another party : " + swingCounter);
		
		
	}
	
}

package com.yuanhai.test;



import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
//�ο�����
//1.http://blog.csdn.net/zstu_cc/article/details/39250903
//2.http://blog.csdn.net/cslie/article/details/48735261
public class HtmlUnitAndJsoup {
	
	/*����˵˵HtmlUnit�����HttpClient�������Ե�һ���ô�,
	��HtmlUnit���������������ҳ���󣬸����ܿɹ�����������������ҳ�����л������������¼���
	���ںܶ���վʹ�ô���ajax����ͨ�����޷���ȡjs���ɵ����ݡ�*/
	
	 
	/*������jar��
	 * commons-lang3-3.1.jar
	 * htmlunit-2.13.jar
	 * htmlunit-core-js-2.13.jar
	 * httpclient-4.3.1.jar
	 * httpcore-4.3.jar
	 * httpmime-4.3.1.jar
	 * sac-1.3.jar
	 * xml-apis-1.4.01.jar
	 * commons-collections-3.2.1.jar
	 * commons-io-2.4.jar
	 * xercesImpl-2.11.0.jar
	 * xalan-2.7.1.jar
	 * cssparser-0.9.11.jar
	 * nekohtml-1.9.19.jar
	 * */
	//�ٶ����Ÿ߼�����
	@Test
	public void HtmlUnitBaiduAdvanceSearch(){
	    try {
			// �õ����������ֱ��Newһ�����ܵõ������ھͺñ�˵��õ���һ���������  
			WebClient webclient = new WebClient();  
     
			// ����������һ�²�����css��javaScript,���������ܼ򵥣��ǲ���  
			webclient.getOptions().setCssEnabled(false);  
			webclient.getOptions().setJavaScriptEnabled(false);  
     
			// ���ĵ�һ���£�ȥ�õ������ҳ��ֻ��Ҫ����getPage�����������  
			HtmlPage htmlpage = webclient.getPage("http://news.baidu.com/advanced_news.html");  
     
			// �������ֵõ�һ���������鿴���������ҳ��Դ������Է��ֱ��������ֽС�f��  
			final HtmlForm form = htmlpage.getFormByName("f");  
			System.out.println(form);
			// ͬ����������ȡ���ٶ�һ�¡������ť  
			final HtmlSubmitInput button = form.getInputByValue("�ٶ�һ��");  
			System.out.println(button);
			// �õ�������  
			final HtmlTextInput textField = form.getInputByName("q1");  

			System.out.println(textField);  
			
			// ������ǳ۱Ƚϻ�ѽ������������һ���������������롱���ǳۡ�  
			textField.setValueAttribute("���ǳ�");  
			// ������ˣ����ǵ�һ�������ť  
			final HtmlPage nextPage = button.click();  
			// �Ұѽ��ת��String 
			System.out.println(nextPage);
			
			String result = nextPage.asXml();  
			  
			System.out.println(result);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		
	}
	
	
	//����������̳��½����  HtmlUnit  ҳ��JS���Զ���ת����Ӧ����200��������Ӧ��ҳ�����һ��JS��
	//httpClient���鷳��
	@Test
	public void TianyaTestByHtmlUnit(){
		
		 try {
			WebClient webClient = new WebClient();  
			  
			    //The ScriptException is raised because you have a syntactical error in your javascript.
			    //Most browsers manage to interpret the JS even with some kind of errors 
			    //but HtmlUnit is a bit inflexible in that sense.
			    //���ص�ҳ����js�﷨������׳��쳣
			    
			    webClient.getOptions().setJavaScriptEnabled(true); //����JS��������Ĭ��Ϊtrue  
			    webClient.getOptions().setCssEnabled(false); //����css֧��  
			    webClient.getOptions().setThrowExceptionOnScriptError(false); //js���д���ʱ���Ƿ��׳��쳣  
			 
			    // �õ������ҳ  
			    HtmlPage page = webClient.getPage("http://passport.tianya.cn/login.jsp");  

			    // �����û���������  
			    HtmlInput username = (HtmlInput) page.getElementById("userName");  
			    username.type("u_110486326");  
			    HtmlInput password = (HtmlInput) page.getElementById("password");  
			    password.type("X0up4d65");  

			    // �ύ  
			    HtmlButton submit = (HtmlButton) page.getElementById("loginBtn");  
			    HtmlPage nextPage = submit.click();  
			    System.out.println(nextPage.asXml());
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}

	
	//jsoup�����ĵ�
	@Test
	public void jsoupParse(){
		
		 
	        try {
				/**HtmlUnit����webҳ��*/  
				WebClient wc = new WebClient();  
				wc.getOptions().setJavaScriptEnabled(true); //����JS��������Ĭ��Ϊtrue  
				wc.getOptions().setCssEnabled(false); //����css֧��  
				wc.getOptions().setThrowExceptionOnScriptError(false); //js���д���ʱ���Ƿ��׳��쳣  
				wc.getOptions().setTimeout(10000); //�������ӳ�ʱʱ�� ��������10S�����Ϊ0���������ڵȴ�  
				HtmlPage page = wc.getPage("http://passport.tianya.cn/login.jsp");  
				String pageXml = page.asXml(); //��xml����ʽ��ȡ��Ӧ�ı�  
  
				/**jsoup�����ĵ�*/  
				//��Stringת����document��ʽ 
				Document doc = Jsoup.parse(pageXml);  
				Element loginBtn = doc.select("#loginBtn").get(0);  
				System.out.println(loginBtn.text());  
				Assert.assertTrue(loginBtn.text().contains("��¼")); 
  
			
			} catch (Exception e) {
				e.printStackTrace();
			} 
	}
	
	
	//jsoup���󲢽���
	
	@Test
	public void jsoupCrawl() throws IOException{
	
   String url="http://passport.tianya.cn/login.jsp";
        Connection con = Jsoup.connect(url);//��ȡ��������  
     //������ɽ��ܵ�MIME���͡�  
      con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
     con.header("Accept-Encoding", "gzip, deflate");  
      con.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");  
      con.header("Connection", "keep-alive");  
      con.header("Host", url);  
      con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");  
        Document doc=con.get();  
        Elements loginBtn=doc.select("#loginBtn"); 
        System.out.println(loginBtn.text());//��ȡ�ڵ��е��ı���������js�еķ���  
    }  
}
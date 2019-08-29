package testOutput.runner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailHtml {
	
	/**
     * @throws MessagingException 
     * @throws AddressException 
     * 
     */
//	private static String host = "smtp.163.com"; //163的服务器
//    private final static String formName = "15951644332@163.com";//你的邮箱
//    private final static String password = "longtengyun12345"; //授权码
//    private final static String replayAddress = "15951644332@163.com"; //你的邮箱
//    private final static String subject = "自动化测试报告"; //主题
//    private final static String toAddress = "yongjian.zhao@wuliangroup.com,15951644332@163.com"; //收件人地址
//    private final static String content = "<div>你不在学校吗？</div><br/><hr/><div>记得28号来学校</div>"; 
//    private final static String [] fileName={"D:/workspace/com.wtt.runner.android/src/testOutput/junit-report2.html"};
//    
    
    public static Message getMessage(Info info) throws AddressException, MessagingException{
    	Properties propert=System.getProperties();
    	propert.setProperty("mail.smtp.host",info.getHost());
    	propert.setProperty("mail.smtp.auth", "true");
    	propert.setProperty("mail.smtp.user", info.getFormName());
    	propert.setProperty("mail.smtp.pass", info.getPassword());
    	
    	// 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(propert, new Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(propert.getProperty("mail.smtp.user"),propert.getProperty("mail.smtp.pass"));
            }
        });
        session.setDebug(true);
        Message message = new MimeMessage(session);
        //消息发送的主题
        message.setSubject(info.getSubject());
        //接受消息的人
        message.setReplyTo(InternetAddress.parse(info.getReplayAddress()));
        //消息的发送者
        try {
			message.setFrom(new InternetAddress(propert.getProperty("mail.smtp.user"),"测试部赵永健"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        // 创建邮件的接收者地址，并设置到邮件消息中
//        message.setRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));
        message.setRecipients(Message.RecipientType.TO,Address(info.getToAddress()));//发送给多人
        // 消息发送的时间
        message.setSentDate(new Date());

        return message ;
    	
    }
    
    /**
     * 发送html
     * @throws Exception
     */
    public static void sendHtmlMail(Info info)throws Exception{
        Message message = getMessage(info);
        
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
     // 设置HTML内容
       
        html.setContent(info.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
         //将MiniMultipart对象设置为邮件内容
      //保存多个附件 
        if(info.getFileName()!=null){ 
            addTach(info.getFileName(), mainPart); 
        } 
        message.setContent(mainPart);
        Transport.send(message);
    }
    /**
     * 发送test
     * @throws Exception
     */
    public static void sendTextMail(Info info) throws Exception {
    	Message message = getMessage(info);
        //消息发送的内容
    	 // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
     // 设置HTML内容
       
        html.setContent(info.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
         //将MiniMultipart对象设置为邮件内容
      //保存多个附件 
        if(info.getFileName()!=null){ 
            addTach(info.getFileName(), mainPart); 
        } 
        message.setContent(mainPart);
        Transport.send(message);
    }
    
    /**
     * 添加多个附件 
     * @param fileList
     * @param multipart
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
  	public static void addTach(String fileList[], Multipart multipart) throws MessagingException, UnsupportedEncodingException { 
  	    for (int index = 0; index < fileList.length; index++) { 
  	         MimeBodyPart mailArchieve = new MimeBodyPart(); 
  	         FileDataSource fds = new FileDataSource(fileList[index]); 
  	         mailArchieve.setDataHandler(new DataHandler(fds)); 
  	         mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),"UTF-8","B")); 
  	         multipart.addBodyPart(mailArchieve); 
          }   
  	} 
  	
  	/**
  	 * 发送给多个收件人
  	 * @return
  	 */
  	public static  InternetAddress[] Address(String toAddress){
//  	         String str="xxx@xxx.com,xxx@xxx.com";
  	         InternetAddress[] address=null;
  	         try {
  	             List list = new ArrayList();//不能使用string类型的类型，这样只能发送一个收件人
  	             String []median = toAddress.split(",");//对输入的多个邮件进行逗号分割
  	             for(int i=0;i<median.length;i++){
  	                 list.add(new InternetAddress(median[i]));
  	             }
  	             address =(InternetAddress[])list.toArray(new InternetAddress[list.size()]);
  	        } catch (AddressException e) {
  	            e.printStackTrace();
  	        }
  	         return address;
  	     }

}

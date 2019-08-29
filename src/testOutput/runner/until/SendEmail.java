package testOutput.runner.until;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
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

/**
 * Created by 赵永健  on 2017/12/27.
 * 功能：1、将手机的xml文件拉取到电脑 2、将xml文件转化成html 3、邮件发送html(附件),支持发送多人
 */
public class SendEmail {
//	private static String strCmd = "cmd /c start D:\\workspace/com.wtt.runner.android/src/testOutput/get.bat";
	private static String strCmd = "cmd /c start D:\\code/auxiliaryFunction/src/getReports.bat";
	private final static String host = "smtp.163.com"; //163的服务器
    private final static String formName = "15951644332@163.com";//你的邮箱
    private final static String password = "longtengyun12345"; //授权码
    private final static String replayAddress = "15951644332@163.com"; //你的邮箱
    private final static String subject = "自动化测试报告"; //主题
    private final static String toAddress = "yongjian.zhao@wuliangroup.com,15951644332@163.com"; //收件人地址
    private final static String content = "<div>你不在学校吗？</div><br/><hr/><div>记得28号来学校</div>"; 
//    private final static String [] fileName={"D:/workspace/com.wtt.runner.android/src/testOutput/junit-report2.html"};
    private final static String [] fileName={"D:/code/auxiliaryFunction/src/testOutput/runner/until/junit-report2.html"};

    
	public static void main(String[] args) {
        SendEmail sendEmail=new SendEmail();
//        sendEmail.runCmd(strCmd);
        try {
//			sendEmail.sendHtmlMail(formName,password,toAddress,content,fileName);
			sendEmail.sendTextMail(formName, password, toAddress, "你好！",fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     *1、将xml转化为html
     * @param strCmd
     */
    public void runCmd(String strCmd){
        Runtime runtime =Runtime.getRuntime();//Runtime.getRuntime()返回当前应用程序的Runtime对象
        Process process=null;//Process可以控制该子进程的执行或获取该子进程的信息。
        try {
            process=runtime.exec(strCmd);//该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
            try {
                process.waitFor();//等待子进程完成再往下执行。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        process.destroy();
        process = null;
    }
    
    /**
     * @throws MessagingException 
     * @throws AddressException 
     * 
     */
    public static Message getMessage(String formName,String password,String toAddress ) throws AddressException, MessagingException{
    	Properties propert=System.getProperties();
    	propert.setProperty("mail.smtp.host", host);
    	propert.setProperty("mail.smtp.auth", "true");
    	propert.setProperty("mail.smtp.user", formName);
    	propert.setProperty("mail.smtp.pass", password);
    	
    	// 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(propert, new Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(propert.getProperty("mail.smtp.user"),propert.getProperty("mail.smtp.pass"));
            }
        });
        session.setDebug(true);
        Message message = new MimeMessage(session);
        //消息发送的主题
        message.setSubject(subject);
        //接受消息的人
        message.setReplyTo(InternetAddress.parse(replayAddress));
        //消息的发送者
        try {
			message.setFrom(new InternetAddress(propert.getProperty("mail.smtp.user"),"测试部赵永健"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        // 创建邮件的接收者地址，并设置到邮件消息中
//        message.setRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));
        message.setRecipients(Message.RecipientType.TO,Address(toAddress));//发送给多人
        // 消息发送的时间
        message.setSentDate(new Date());

        return message ;
    	
    }
    
    /**
     * 发送html
     * @param toAddress
     * @param content
     * @throws Exception
     */
    public static void sendHtmlMail(String formName,String password, String toAddress,String content,String fileList[])throws Exception{
        Message message = getMessage(formName,password,toAddress);
        
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
     // 设置HTML内容
       
        html.setContent(content, "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
         //将MiniMultipart对象设置为邮件内容
      //保存多个附件 
        if(fileList!=null){ 
            addTach(fileList, mainPart); 
        } 
        message.setContent(mainPart);
        Transport.send(message);
    }
    /**
     * 发送test
     * @param formName
     * @param password
     * @param toAddress
     * @param content
     * @throws Exception
     */
    public static void sendTextMail(String formName,String password, String toAddress,String content,String fileList[]) throws Exception {
    	Message message = getMessage(formName,password,toAddress);
        //消息发送的内容
    	 // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
     // 设置HTML内容
       
        html.setContent(content, "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
         //将MiniMultipart对象设置为邮件内容
      //保存多个附件 
        if(fileList!=null){ 
            addTach(fileList, mainPart); 
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

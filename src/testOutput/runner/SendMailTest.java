package testOutput.runner;

public class SendMailTest {
//	private static String strCmd = "cmd /c start D:\\workspace/com.wtt.runner.android/src/get.bat";
	private static String strCmd = "cmd /c start D:\\code/auxiliaryFunction/src/getReports.bat";
	private final static String host = "smtp.163.com"; //163的服务器
    private final static String formName = "15951644332@163.com";//你的邮箱
    private final static String password = "longtengyun12345"; //授权码
    private final static String replayAddress = "15951644332@163.com"; //你的邮箱
    private final static String subject = "Android自动化测试报告"; //主题
    private final static String toAddress = "yongjian.zhao@wuliangroup.com,1309817607@qq.com"; //收件人地址,支持多人，逗号隔开
//    private final static String content = "各位领导、同事，好! 附件为自动化测试执行结果，针对基本功能。";

//    private final static String [] fileName={"D:/workspace/com.wtt.runner.android/src/testOutput/junit-report2.html"};
	private final static String [] fileName={"D:/code/auxiliaryFunction/src/testOutput/runner/until/junit-report2.html"};
	public static void main(String[] args) {
		Info info=new Info();
//		changeXMLToHtml cxth=new changeXMLToHtml();
		EmailHtml eh=new EmailHtml();
		contents con=new contents();
//		info.setStrCmd(strCmd);
		info.setHost(host);
		info.setFormName(formName);
		info.setPassword(password);
		info.setReplayAddress(replayAddress);
		info.setSubject(subject);
		info.setToAddress(toAddress);
		info.setContent(con.getText());
		info.setFileName(fileName);
//		cxth.runCmd(info);
//		try {
//			Thread thread=Thread.currentThread();
//			thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		try {
			eh.sendTextMail(info);
			System.out.println("邮件发送成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

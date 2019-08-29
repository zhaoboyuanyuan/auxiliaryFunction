package testOutput.runner;

import java.io.IOException;

public class changeXMLToHtml {
	 /**
     *1、将xml转化为html
     * @param
     */
    public void runCmd(Info info){
        Runtime runtime =Runtime.getRuntime();//Runtime.getRuntime()返回当前应用程序的Runtime对象
        Process process=null;//Process可以控制该子进程的执行或获取该子进程的信息。
        try {
            process=runtime.exec(info.getStrCmd());//该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
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

}

package test;
import utils.MailMessage;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMail {

	@Test
	void test() {
		MailMessage mmsg = new MailMessage();
		mmsg.init("CUGB"); // 选择smtp的邮箱服务器：CUGB、QQ
		try {
			Message msg = mmsg.createMessage("2643841145@qq.com", "测试", "<a href=\"http://localhost:8080/RestaurantPlatform/register\">点击此处进行激活</a>");
			Transport.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
//wgejnxrwvbftchef
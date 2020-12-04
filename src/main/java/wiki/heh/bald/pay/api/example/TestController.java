package wiki.heh.bald.pay.api.example;

import net.mfeng.pay.api.PayClient;
import net.mfeng.pay.api.model.AliPayQrParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hehua
 * @date 2020/12/4
 */
@RestController
public class TestController {
    @Autowired
    PayClient client;

    @GetMapping("notifyUrl")
    public void notifyUrl(Object o) {
        System.out.println("收到支付中心的回调信息");
        System.out.println(o.toString());
    }

    @GetMapping("test")
    public void test() {
        AliPayQrParam param = new AliPayQrParam();
        param.setMchId("10000");
        param.setMchOrderNo("12332444444");
        param.setAmount("11110");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setSubject("这里是标题");
        param.setBody("这里是商品介绍呀");
        System.out.println(client.aliPayQr(param));
    }

}

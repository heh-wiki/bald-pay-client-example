package wiki.heh.bald.pay.api.example;
import net.mfeng.pay.api.model.ChannelEnum;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.mfeng.pay.api.PayClient;
import net.mfeng.pay.api.model.AliPayQrParam;
import net.mfeng.pay.api.model.UnifiedRefundParam;
import net.mfeng.pay.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hehua
 * @date 2020/12/4
 */
@RestController
public class TestController {
    @Autowired
    PayClient client;

    @PostMapping("notifyUrl")
    public String notifyUrl(@RequestParam Map o) {
        System.out.println("收到支付中心的回调信息");
        System.out.println(o.toString());
        return "success";
    }

    @GetMapping("refund")
    public String refund() {
        UnifiedRefundParam param = new UnifiedRefundParam();
        param.setPayOrderId("P0020201221111507000001");
        param.setMchRefundNo("RR2020120221060400053119");
        param.setChannelId(ChannelEnum.ALIPAY_QR);
        param.setChannelUser("支付宝帐号");
        param.setUserName("张三李四都可以");
        param.setRemarkInfo("取消订单");
        param.setMchId("10000000");
        param.setMchOrderNo("2020120221060400052");
        param.setAmount("10");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        String s = client.tradeRefund(param);
        System.out.println();
        return s;
    }

    @GetMapping("test")
    public String test() {
        AtomicLong seq = new AtomicLong(0L);
        String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
        AliPayQrParam param = new AliPayQrParam();
        param.setMchId("10000000");
        param.setMchOrderNo(goodsOrderId);
        param.setAmount("11110");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setSubject("这里是标题");
        param.setBody("这里是商品介绍呀");
        String s = client.aliPayQr(param);
        System.out.println();
        return s;
    }

    @PostMapping("test1")
    public String test11(@RequestBody TestUnifiedPayForm fee) {
        return aliPayMobile(fee.getFee());
    }

    @GetMapping("aliPayMobile")
    public String aliPayMobile(String fee) {
        if (StringUtil.isNotEmpty(fee)) {
            AtomicLong seq = new AtomicLong(0L);
            String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
            AliPayQrParam param = new AliPayQrParam();
            param.setMchId("10000000");
            param.setMchOrderNo(goodsOrderId);
            param.setAmount(fee);
            param.setClientIp("192.168.1.22");
            param.setDevice("android");
            param.setSubject("这里是标题");
            param.setBody("这里是商品介绍呀");
            String s = client.aliPayMobile(param);
            return s;
        }
        return TestResult.file("支付金额不能为空").toString();
    }

}

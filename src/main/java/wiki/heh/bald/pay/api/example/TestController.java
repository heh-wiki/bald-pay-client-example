package wiki.heh.bald.pay.api.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.heh.api.PayClient;
import wiki.heh.api.model.AliPayQrParam;
import wiki.heh.api.model.ChannelEnum;
import wiki.heh.api.model.UnifiedRefundParam;
import wiki.heh.api.model.UnifiedTransferForm;
import wiki.heh.api.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hehua
 * @date 2020/12/4
 */
@RestController
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    PayClient client;

    @PostMapping("notifyUrl")
    public String notifyUrl(@RequestParam Map o) {
        log.info("********收到支付中心的回调信息********");
        log.info("回调参数：{}", o.toString());
        return "success";//收到回调信息需要返回“success”，否则会重复回调5次
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
            System.out.println(s);
            return s;
        }
        return TestResult.file("支付金额不能为空").toString();
    }

    @GetMapping("aliPayQr")
    public String aliPayQr() {
        AtomicLong seq = new AtomicLong(0L);
        String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
        AliPayQrParam param = new AliPayQrParam();
        param.setMchId("10000002");
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

    @GetMapping("aliTransfer")
    public String aliTransfer() {
        UnifiedTransferForm param = new UnifiedTransferForm();
        param.setMchTransNo("T2020122202855213");
        param.setChannelUser("vkuact5643@sandbox.com");
        param.setUserName("vkuact5643");
        param.setRemarkInfo("订餐费用");
        param.setMchId("10000000");
        param.setAmount("10");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setExtra("");
        param.setParam1("方芳芳");
        param.setParam2("");
        String s = client.aliTransfer(param);
        System.out.println();
        return s;
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







    @PostMapping("test1")
    public String test11(@RequestBody TestUnifiedPayForm fee) {
        return aliPayMobile(fee.getFee());
    }



}

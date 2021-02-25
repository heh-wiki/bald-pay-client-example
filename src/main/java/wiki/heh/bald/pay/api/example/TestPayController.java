package wiki.heh.bald.pay.api.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.heh.api.PayClient;
import wiki.heh.api.model.*;
import wiki.heh.api.model.param.JsApiPayParam;
import wiki.heh.api.model.param.UnifiedPayParam;
import wiki.heh.api.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author heh
 * @date 2020/12/4
 */
@RestController
public class TestPayController {
    Logger log = LoggerFactory.getLogger(TestPayController.class);
    @Autowired
    PayClient client;
    private int count = 1;

    /**
     * 支付中心回调地址
     *
     * @param o
     * @return
     */
    @PostMapping("notifyUrl")
    public String notifyUrl(@RequestParam Map o) {
        log.info("********收到支付中心的回调信息********");
        log.info("回调参数：{}", o.toString());
        return "success";//收到回调信息需要返回“success”，否则会重复回调

    }

    @GetMapping("notifyUrl")
    public String notifyUrl1(@RequestParam Map o) {
        log.info("********收到支付中心的回调信息********");
        log.info("回调参数：{}", o.toString());
        return "success";//收到回调信息需要返回“success”，否则会重复回调5次
    }

    /**
     * 微信app支付
     *
     * @param fee
     * @return
     */
    @GetMapping("wxPayMobile")
    public JSONObject wxPayMobile(String fee) {
        if (StringUtil.isNotEmpty(fee)) {
            AtomicLong seq = new AtomicLong(0L);
            String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
            UnifiedPayParam param = new UnifiedPayParam();
            param.setMchId("10000000");
            param.setMchOrderNo(goodsOrderId);
            param.setAmount(fee);
            param.setClientIp("192.168.1.22");
            param.setDevice("android");
            param.setSubject("这里是标题");
            param.setBody("这里是商品介绍呀");
            String s = client.wxPayMobile(param);
            JSONObject jsonpObject = JSON.parseObject(s);
            JSONObject data = jsonpObject.getJSONObject("data");
            jsonpObject.remove("data");
            jsonpObject.put("data", data.getJSONObject("payParams"));
            System.out.println(s);
            return jsonpObject;

        }
        return null;
    }

    /**
     * 支付宝app支付
     *
     * @param fee
     * @return
     */
    @GetMapping("aliPayMobile")
    public String aliPayMobile(String fee) {
        if (StringUtil.isNotEmpty(fee)) {
            AtomicLong seq = new AtomicLong(0L);
            String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
            UnifiedPayParam param = new UnifiedPayParam();
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
        return "支付金额不能为空";
//        return TestResult.file("支付金额不能为空").toString();
    }

    /**
     * 支付宝二维码支付
     *
     * @return
     */
    @GetMapping("aliPayQr")
    public String aliPayQr() {
        AtomicLong seq = new AtomicLong(0L);
        String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
        UnifiedPayParam param = new UnifiedPayParam();
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

    /**
     * 微信二维码支付
     *
     * @return
     */
    @GetMapping("wxPayQr")
    public String wxPayQr() {
        AtomicLong seq = new AtomicLong(0L);
        String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
        JsApiPayParam param = new JsApiPayParam();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", "132");
        param.setExtra(jsonObject);
        param.setMchId("10000000");
        param.setMchOrderNo(goodsOrderId);
        param.setAmount("1");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setSubject("这里是标题");
        param.setBody("这里是商品介绍呀");
        String s = client.wxPayNative(param);
        System.out.println();
        return s;
    }

    /**
     * 微信小程序支付
     *
     * @return
     */
    @GetMapping("wxPayJsApi")
    public String wxPayJsApi() {
        AtomicLong seq = new AtomicLong(0L);
        String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
        JsApiPayParam param = new JsApiPayParam();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId", "123");
        param.setExtra(jsonObject);
        param.setMchId("10000000");
        param.setMchOrderNo(goodsOrderId);
        param.setAmount("1");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setSubject("这里是标题");
        param.setBody("这里是商品介绍呀");
        String s = client.wxPayJsApi(param);
        System.out.println();
        return s;
    }

    /**
     * 支付宝小程序支付
     *
     * @return
     */
    @GetMapping("aliPayJsApi")
    public String aliPayTrade() {
        AtomicLong seq = new AtomicLong(0L);
        String goodsOrderId = String.format("%s%s%06d", "G", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);
        JsApiPayParam param = new JsApiPayParam();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId", "2088702153462831");
        param.setExtra(jsonObject);
        param.setMchId("10000002");
        param.setMchOrderNo(goodsOrderId);
        param.setAmount("1");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setSubject("这里是标题");
        param.setBody("这里是商品介绍呀");
        String s = client.aliPayJsApi(param);
        System.out.println();
        return s;
    }

    /**
     * 支付宝交易转账
     *
     * @return
     */
    @GetMapping("aliTransfer")
    public String aliTransfer() {
        UnifiedTransferForm param = new UnifiedTransferForm();
        AtomicLong seq = new AtomicLong(0L);
        String mchTransNo = String.format("%s%s%06d", "T", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);

        param.setMchTransNo(mchTransNo);
        param.setChannelUser("vkuact5643@sandbox.com");
        param.setUserName("vkuact5643");
        param.setRemarkInfo("订餐费用");
        param.setMchId("10000000");
        param.setAmount("10");
        param.setClientIp("192.168.1.22");
        param.setDevice("android");
        param.setExtra("");
        param.setParam1("qwert");
        param.setParam2("");
        String s = client.aliTransfer(param);
        System.out.println();
        return s;
    }

    /**
     * 支付订单退款
     *
     * @return
     */
    @GetMapping("refund")
    public String refund() {
        UnifiedRefundParam param = new UnifiedRefundParam();
        AtomicLong seq = new AtomicLong(0L);
        String mchRefundNo = String.format("%s%s%06d", "R", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), (int) seq.getAndIncrement() % 1000000);

        param.setPayOrderId("P0020201229110029000053");
        param.setMchRefundNo(mchRefundNo);
        param.setChannelId(ChannelEnum.ALIPAY_MOBILE);
        param.setChannelUser("支付宝帐号");
        param.setUserName("梁玲");
        param.setRemarkInfo("取消订单测试退款");
        param.setMchId("10000002");
        param.setMchOrderNo("793433251757735936");
        param.setAmount("2200");
        param.setClientIp("192.168.1.22");
        param.setDevice("iOS");
        String s = client.tradeRefund(param);
        System.out.println();
        return s;
    }


//    @PostMapping("wx")
//    public Object wx(@RequestBody TestUnifiedPayForm fee) {
//        return wxPayMobile(fee.getFee());
//    }
//
//    @PostMapping("ali")
//    public Object ali(@RequestBody TestUnifiedPayForm fee) {
//        return aliPayMobile(fee.getFee());
//    }


}

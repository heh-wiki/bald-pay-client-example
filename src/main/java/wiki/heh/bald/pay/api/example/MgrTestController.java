package wiki.heh.bald.pay.api.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.heh.api.MchClient;
import wiki.heh.api.model.ParamBuilder;
import wiki.heh.api.model.param.*;

/**
 * @author heh
 * @date 2020/12/4
 */
@RestController
public class MgrTestController {
    Logger log = LoggerFactory.getLogger(MgrTestController.class);
    @Autowired
    MchClient client;

    /**
     * 新增商户
     *
     * @return
     */
    @GetMapping("add")
    public Object addMgr() {
        MchInfoParam param = new MchInfoParam();
        param.setName("test_1");
        param.setType("2");
        param.setReqKey("1111111");
        param.setResKey("0000000");
        String s = client.saveMchInfo(param);
        System.out.println(s);
        return s;
    }

    /**
     * 更新商户信息
     *
     * @return
     */
    @GetMapping("update")
    public Object updatedMgr() {
        MchInfoParam param = new MchInfoParam();
        param.setMchId("10000004");
        param.setName("test123");
        param.setType("2");
        param.setReqKey("1111111");
        param.setResKey("0000000");
        param.setState((byte) 0);
        String s = client.saveMchInfo(param);
        System.out.println(s);
        return s;
    }

    /**
     * 获取商户信息
     *
     * @return
     */
    @GetMapping("get")
    public Object getMgr() {
        String s = client.getMchInfo("10000004");
        System.out.println(s);
        return s;
    }


    /**
     * 新增商户支付渠道
     * 当渠道名称为WX时，参数为：{"mchId":"xxx", "appId":"xxx", "key":"xxx", "certLocalPath":"xxx", "certPassword":"xxx"}
     * <p>
     * 当渠道名称为ALIPAY时，参数为：{"isSandbox":1,"appid": "xxx", "private_key": "xxx", "alipay_public_key": "xxx"}
     *
     * @return
     */
    @GetMapping("c/add")
    public Object addChannel() {
        ParamBuilder.ChannelParam a = ParamBuilder.aliBuilder()
                .addIsSanBox(0)
                .addPrivate_key("123")
                .addPublic_key("123")
                .addAppId("444");
        JSONObject aa = JSONObject.parseObject(JSON.toJSONString(a));
        PayChannelParam param = PayChannelParam.builder()
                .addParam(aa)
                .addChannelMchId("")
                .addMchId("10000004")
                .addChannelId(PayChannel.ALIPAY_QR)
                .addState(0)
                .addRemark("");
        String s = client.saveChannel(param);
        System.out.println(s);
        return s;
    }

    @GetMapping("c/update")
    public Object updateChannel() {
        ParamBuilder.ChannelParam a = ParamBuilder.aliBuilder()
                .addIsSanBox(0)
                .addPrivate_key("123")
                .addPublic_key("123")
                .addAppId("444");
        JSONObject aa = JSONObject.parseObject(JSON.toJSONString(a));
        PayChannelParam param = PayChannelParam.builder()
                .addId(14)
                .addParam(aa)
                .addChannelMchId("111111")
                .addMchId("10000004")
                .addChannelId(PayChannel.ALIPAY_QR)
                .addState(0)
                .addRemark("");
        String s = client.saveChannel(param);
        System.out.println(s);
        return s;
    }

    @GetMapping("c/get")
    public Object getChannel() {
        String s = client.getChannel("10000004", "14");
        System.out.println(s);
        return s;
    }

    @GetMapping("c/list")
    public Object channelList() {
        SearchPayChannel param = new SearchPayChannel();
        param.setChannelMchId("10000004");
        String s = client.getChannelList(param);
        System.out.println(s);
        return s;
    }

}

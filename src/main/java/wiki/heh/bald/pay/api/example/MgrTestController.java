package wiki.heh.bald.pay.api.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wiki.heh.api.MgrClient;
import wiki.heh.api.model.param.MchInfoParam;

/**
 * @author heh
 * @date 2020/12/4
 */
@RestController
public class MgrTestController {
    Logger log = LoggerFactory.getLogger(MgrTestController.class);
    @Autowired
    MgrClient client;

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

}

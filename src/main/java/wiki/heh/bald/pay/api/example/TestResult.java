package wiki.heh.bald.pay.api.example;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author hehua
 * @date 2020/12/7
 */
public class TestResult {

    public static final String SUCCESSFUL_CODE = "000000";
    public static final String SUCCESSFUL_msg = "处理成功";

    private String code;
    private String msg;
    private String sign;
    private Instant time;

    public TestResult() {
        this.time = ZonedDateTime.now().toInstant();
    }

    private TestResult(String sign, String code, String msg) {
        this.sign = sign;
        this.code = code;
        this.msg = msg;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param sign
     * @return Result
     */
    public static TestResult success(String sign) {
        return new TestResult(sign, SUCCESSFUL_CODE, SUCCESSFUL_msg);
    }

    public static TestResult file(String msg) {
        return new TestResult(null, SUCCESSFUL_CODE, msg);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", sign='" + sign + '\'' +
                ", time=" + time +
                '}';
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static TestResult success() {
        return success(null);
    }

}

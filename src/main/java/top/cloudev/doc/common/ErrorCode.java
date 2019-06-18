package top.cloudev.doc.common;

/**
 * 错误码枚举类
 * Created by Mac.Manon on 2018/04/04
 */
public enum ErrorCode {
    //TODO 在这里定义错误码，并将key加入国际化语言包。key组成规则："ErrorCode."+ code
    //User_Username_Exists("10001");

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "[" + this.code + "] : " + I18nUtils.getMessage("ErrorCode." + this.code);
    }
}

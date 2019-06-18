package top.cloudev.doc.common;

/**
 * 自定义业务异常类
 * Created by Mac.Manon on 2018/04/04
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(Object Obj) {
        super(Obj.toString());
    }
}

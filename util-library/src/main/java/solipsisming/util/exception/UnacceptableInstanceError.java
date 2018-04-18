package solipsisming.util.exception;

/**
 * 禁止工具类实例</p>
 * 创建于 2015-6-8 21:27:19
 *
 * @author 洪东明
 * @version 1.0
 */
public class UnacceptableInstanceError extends Error {
    private static final long serialVersionUID = 1L;

    public UnacceptableInstanceError(String msg) {
        super(msg);
    }

    public UnacceptableInstanceError() {
        super("unacceptable instance!!");
    }
}

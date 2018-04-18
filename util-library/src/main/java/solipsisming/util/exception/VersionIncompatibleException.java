package solipsisming.util.exception;

/**
 * 使用的方法与当前版本不匹配</p>
 * 创建于2015-6-16 21:26:28
 *
 * @author 洪东明
 * @version 1.0
 */
public class VersionIncompatibleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public VersionIncompatibleException() {
    }

    public VersionIncompatibleException(String msg) {
        super(msg);
    }
}

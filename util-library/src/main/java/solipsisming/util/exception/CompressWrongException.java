package solipsisming.util.exception;

import java.io.IOException;

/**
 * 压缩图片失败异常</p>
 * 创建于 2015-6-15 20:56:10
 *
 * @author 洪东明
 * @version 1.0
 */
public class CompressWrongException extends IOException {
    private static final long serialVersionUID = 1L;

    public CompressWrongException(String msg) {
        super(msg);
    }

    public CompressWrongException() {
        this("unable compress to need size,because the bitmap quantity is 0 that can't continue compress");
    }
}

package solipsisming.util.exception;

import java.io.File;

/**
 * 文件不存在异常</p>
 * 创建于 2015-07-04 22:44:25
 *
 * @author 洪东明
 * @version 1.0
 */
public class FileNotExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileNotExistsException() {
    }

    public FileNotExistsException(File file) {
        super(("File " + file + " not exists"));
    }

}

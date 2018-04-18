/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package solipsisming.util.exception;

import java.io.File;
import java.io.IOException;

/**
 * 文件已经存在异常</p>
 * 创建于 2015-07-04 16:38:34
 *
 * @author 洪东明
 * @version 1.0
 */
public class FileExistsException extends IOException {

    private static final long serialVersionUID = 1L;

    public FileExistsException() {
        super();
    }

    public FileExistsException(String message) {
        super(message);
    }

    /**
     * 文件已经存在异常
     *
     * @param file 指定的文件
     */
    public FileExistsException(File file) {
        super("File " + file + " exists");
    }

}

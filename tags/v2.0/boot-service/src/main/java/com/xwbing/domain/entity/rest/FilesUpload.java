package com.xwbing.domain.entity.rest;

import com.xwbing.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiangwb
 * @version $Id: FilesUpload.java, v 0.1 2018年06月13日 17:23 wb-xwb402636 Exp $
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FilesUpload extends BaseEntity {
    private static final long serialVersionUID = 3284231281346882055L;
    public static String table = "file_upload";
    private String name;
    private String type;
    private String data;
}

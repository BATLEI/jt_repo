package com.jt.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;

public interface FileService {

	ImageVO updateFile(MultipartFile uploadFile);

}

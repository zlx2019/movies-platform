package com.movies.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author mr.zhang
 * @date 2019/8/27
 */
@Slf4j
public class FileUtil {
	private FileUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 将MultipartFile 转化为 com.movies.common.model.File对象
	 * @Author lx Zhang.
	 * @Date 2021/4/2 8:16 下午
	 * @Param [file]
	 * @return com.movies.common.model.File
	 **/
	public static com.movies.common.model.File getFileInfo(MultipartFile file) throws Exception {
		String md5 = fileMd5(file.getInputStream());
		com.movies.common.model.File fileInfo = new com.movies.common.model.File();
		// 将文件的md5设置为文件表的id
		fileInfo.setId(md5);
		fileInfo.setName(file.getOriginalFilename());
		fileInfo.setContentType(file.getContentType());
		fileInfo.setIsImg(fileInfo.getContentType().startsWith("image/"));
		fileInfo.setSize(file.getSize());
		fileInfo.setCreateTime(new Date());
		return fileInfo;
	}

	/**
	 * 将文件序列化为md5
	 * @param inputStream
	 * @return
	 */
	public static String fileMd5(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			log.error("fileMd5-error", e);
		}
		return null;
	}

	public static String saveFile(MultipartFile file, String path) {
		try {
			File targetFile = new File(path);
			if (targetFile.exists()) {
				return path;
			}
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			file.transferTo(targetFile);
			return path;
		} catch (Exception e) {
			log.error("saveFile-error", e);
		}
		return null;
	}

	public static boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			boolean flag = file.delete();
			if (flag) {
				File[] files = file.getParentFile().listFiles();
				if (files == null || files.length == 0) {
					file.getParentFile().delete();
				}
			}
			return flag;
		}
		return false;
	}
}
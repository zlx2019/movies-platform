package com.movies.file.service;

import com.movies.common.model.File;
import com.movies.common.model.base.K;
import com.movies.common.model.base.R;
import com.movies.common.service.ISuperService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件service
 *
 * @author lx Zhang.
 * @date 2019/8/27
*/
public interface IFileService extends ISuperService<File> {

	/**
	 * 文件上传
	 * @Author lx Zhang.
	 * @Date 2021/4/2 8:22 下午
	 * @Param [file]
	 * @return com.movies.common.model.File
	 **/
	R upload(MultipartFile file ) throws Exception;

	/**
	 * 根据文件ID删除文件
	 * @Author lx Zhang.
	 * @Date 2021/4/2 8:51 下午
	 * @Param [id]
	 * @return void
	 **/
	void delete(String id);

	/**
	 * 查询文件列表
	 * @Author lx Zhang.
	 * @Date 2021/4/2 8:51 下午
	 * @Param [params]
	 * @return com.movies.common.model.base.K<com.movies.common.model.File>
	 **/
	K<File> findList(Map<String, Object> params);



	File add(File fileInfo);
}

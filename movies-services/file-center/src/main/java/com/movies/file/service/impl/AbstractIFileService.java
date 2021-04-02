package com.movies.file.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movies.common.model.File;
import com.movies.common.model.base.K;
import com.movies.common.model.base.R;
import com.movies.common.service.impl.SuperServiceImpl;
import com.movies.file.mapper.FileMapper;
import com.movies.file.service.IFileService;
import com.movies.file.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * AbstractIFileService 抽取类
 *
 * @author lx Zhang
 * @date 2019/8/27
 */
@Slf4j
public abstract class AbstractIFileService extends SuperServiceImpl<FileMapper, File> implements IFileService {
    private static final String FILE_SPLIT = ".";

    /**
     * 文件上传
     * @Author lx Zhang.
     * @Date 2021/4/2 8:16 下午
     * @Param [file]
     * @return com.movies.common.model.File
     **/
    @Override
    public R upload(MultipartFile file) throws Exception {
        File fileModel = FileUtil.getFileInfo(file);
        File oldFileInfo = baseMapper.selectById(fileModel.getId());
        if (oldFileInfo != null) {
            return R.Failed("该文件已经上传过");
        }
        if (!fileModel.getName().contains(FILE_SPLIT)) {
            throw new IllegalArgumentException("缺少后缀名");
        }
        //将文件上传到 OSS
        uploadFile(file, fileModel);
        // 将文件信息保存到数据库
        baseMapper.insert(fileModel);
        return R.Success(fileModel);
    }


    /**
     * 上传文件
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, File fileInfo) throws Exception;

    /**
     * 删除文件
     * @param id 文件id
     */
    @Override
    public void delete(String id) {
        File file = baseMapper.selectById(id);
        if (file != null) {
            baseMapper.deleteById(file.getId());
            this.deleteFile(file);
        }
    }

    @Override
    public File add(File fileInfo) {
        if (StringUtils.isBlank(fileInfo.getUrl())){
            File oldFileInfo = baseMapper.selectById(fileInfo.getId());
            if (oldFileInfo != null) {
                return oldFileInfo;
            } else {
                return null;
            }
        } else{
            if (!fileInfo.getName().contains(FILE_SPLIT)) {
                throw new IllegalArgumentException("缺少后缀名");
            }
            // 将文件信息保存到数据库
            baseMapper.insert(fileInfo);
        }
        return fileInfo;
    }

    /**
     * 从云存储上删除文件
     * @param fileInfo
     * @return
     */
    protected abstract boolean deleteFile(File fileInfo);

    /**
     * 查询文件列表
     * @Author lx Zhang.
     * @Date 2021/4/2 8:30 下午
     * @Param [params]
     * @return com.movies.common.model.base.K<com.movies.common.model.File>
     **/
    @Override
    public K<File> findList(Map<String, Object> params) {
        Integer curPage = Optional.ofNullable(MapUtils.getInteger(params,"page")).orElse(1);
        Integer limit = Optional.ofNullable(MapUtils.getInteger(params,"limit")).orElse(-1);
        Page<File> page = new Page<>(curPage,limit);
        List<File> list = baseMapper.findList(page, params);
        return K.build(page,list);
    }
}

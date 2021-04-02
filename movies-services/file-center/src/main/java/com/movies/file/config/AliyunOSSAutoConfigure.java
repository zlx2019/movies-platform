package com.movies.file.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.movies.common.model.File;
import com.movies.file.properties.FileServerProperties;
import com.movies.file.service.impl.AbstractIFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 阿里云存储OSS配置
 * @author lx Zhang.
 * @date 2021/4/2 6:22 下午
 */
@Configuration
@ConditionalOnProperty(name = "movies.file-conf.type",havingValue = "aliyun")
public class AliyunOSSAutoConfigure {

    @Autowired
    private FileServerProperties properties;

    /**
     * 初始化OssClient
     * @Author lx Zhang.
     * @Date 2021/4/2 6:53 下午
     * @return com.aliyun.oss.OSS
     **/
//    @Bean
//    public OSS oss(){
//        OssProperties oss = properties.getOss();
//        return new OSSClientBuilder().build(oss.getEndpoint(),oss.getAccessKey(),oss.getAccessKeySecret());
//    }

    /**
     * 阿里云文件存储client
     * 只有配置了aliyun.oss.access-key才可以使用
     */
    @Bean
    public OSSClient ossClient() {
        OSSClient ossClient = new OSSClient(properties.getOss().getEndpoint()
                , new DefaultCredentialProvider(properties.getOss().getAccessKey(), properties.getOss().getAccessKeySecret())
                , null);
        return ossClient;
    }

    /**
     * 阿里云oss文件上传、删除
     */
    @Service
    public class AliyunOssServiceImpl extends AbstractIFileService{
        @Autowired
        private OSSClient client;

        /**
         * 文件上传
         * @Author lx Zhang.
         * @Date 2021/4/2 7:18 下午
         * @Param [file, fileInfo]
         * @return void
         **/
        @Override
        protected void uploadFile(MultipartFile file, File fileInfo) throws Exception {
            // ------- 修改文件名UUID
            String uuId = UUID.randomUUID().toString().replace("-", "");
            int index = fileInfo.getName().lastIndexOf(".");
            String substring = fileInfo.getName().substring(index, fileInfo.getName().length());
            uuId = uuId + substring;
            // ------- 修改文件名UUID
            client.putObject(properties.getOss().getBucketName(), uuId, file.getInputStream());
            fileInfo.setUrl(properties.getOss().getDomain() + "/" + uuId);
            fileInfo.setOssName(uuId);
        }

        /**
         * OSS文件删除
         * @Author lx Zhang.
         * @Date 2021/4/2 8:12 下午
         * @Param [fileInfo]
         * @return boolean
         **/
        @Override
        protected boolean deleteFile(File fileInfo) {
            //根据BucketName + 文件名删除
            client.deleteObject(properties.getOss().getBucketName(), fileInfo.getOssName());
            return true;
        }
    }
}

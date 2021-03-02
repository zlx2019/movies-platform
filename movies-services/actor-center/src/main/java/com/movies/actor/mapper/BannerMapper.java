package com.movies.actor.mapper;

import com.movies.common.model.Banner;
import com.movies.db.mapper.SupperMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Banner Mapper
 * @author lx Zhang.
 * @date 2021/3/1 11:00 下午
 */
@Mapper
public interface BannerMapper extends SupperMapper<Banner> {

    List<Banner> selectBannerList();
}

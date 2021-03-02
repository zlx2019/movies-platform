package com.movies.actor.service;

import com.movies.common.model.Banner;
import com.movies.common.service.ISuperService;

import java.util.List;

/**
 * @author lx Zhang.
 * @date 2021/3/1 11:05 下午
 */
public interface IBannerService extends ISuperService<Banner> {

    List<Banner> testBannerList();
}

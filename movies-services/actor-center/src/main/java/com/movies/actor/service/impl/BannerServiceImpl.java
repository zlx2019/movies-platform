package com.movies.actor.service.impl;

import com.movies.actor.mapper.BannerMapper;
import com.movies.actor.service.IBannerService;
import com.movies.common.model.Banner;
import com.movies.common.service.impl.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lx Zhang.
 * @date 2021/3/1 11:05 下午
 */
@Slf4j
@Service
public class BannerServiceImpl extends SuperServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> testBannerList() {
        return bannerMapper.selectBannerList();
    }


//    public R listPage(Integer current,Integer size){
//        Page page = new Page<>(current, size);
//        page = bannerMapper.selectMapsPage(page, null);
//        return R.Success(page);
//    }
}

package com.movies;

import com.movies.actor.service.IBannerService;
import com.movies.common.model.Banner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lx Zhang.
 * @date 2021/3/1 11:07 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MoviesActorTestApplication {
    @Autowired
    private IBannerService bannerService;

    @Test
    public void test(){
//        List<Banner> list = bannerService.list();

//        System.out.println(list);
        Banner b = new Banner();
        b.setTitle("titles");
        b.setImageUrl("image url");
        b.setLinkUrl("link url");
        b.setSort(1);
        bannerService.save(b);

        System.out.println(bannerService.list());
    }

    @Test
    public void testBanner(){
        List<Banner> list = bannerService.testBannerList();
        System.out.println(list);
    }
}

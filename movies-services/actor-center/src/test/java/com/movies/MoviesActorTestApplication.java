package com.movies;

import com.movies.actor.service.IBannerService;
import com.movies.common.model.Banner;
import com.movies.zookeeper.lock.ZkDistributedLock;
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
    @Autowired
    private ZkDistributedLock zkDistributedLock;

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

    @Test
    public void lock()throws Exception{
        zkDistributedLock.lock("testlock1", 10);
        for (int i = 0;i < 10;i++){
            Thread.sleep(3000);
            System.out.println(i);
        }
    }
}

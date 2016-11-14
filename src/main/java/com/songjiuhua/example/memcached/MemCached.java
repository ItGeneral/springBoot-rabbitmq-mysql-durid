package com.songjiuhua.example.memcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * Created on 2016/9/28.
 * @Author SongJiuHua.
 * @description memcached集成
 */
public class MemCached {

    public static MemCachedClient client;

    public void testMemCached(){
        String[] server = {"127.0.0.1:11211"};
        SockIOPool pool = SockIOPool.getInstance("testService");//testService是poolName
        pool.setServers(server);
        pool.setFailover(true);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(100);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setBufferSize(1024*1024*10);
        pool.setAliveCheck(true);
        pool.initialize();

        client = new MemCachedClient("testService");
        client.set("key", "MemCached test success");
        System.out.println("set......" + client.get("key"));

        client.delete("key");
        System.out.println("delete......" + client.get("key"));

        client.set("key", "MemCached test success");
        client.replace("key", "MemCached test success replaced");

    }

}

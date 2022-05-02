package com.joush.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Rex Joush
 * @time 2022.04.30
 */

/*
    创建 zookeeper 客户端
 */
public class ZkClient {

    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        // 此处多个不同节点之间的 ,不能包含多余空格
        String connectString = "hadoop01:2181,hadoop02:2181,hadoop03:2181";
        // 超时时间，2 秒
        int sessionTimeout = 10000;
        zkClient = new ZooKeeper(connectString, sessionTimeout, watchedEvent -> {
            List<String> children = null;// 此处监听器写 true，标识默认监听创建 client 对象时的监听器
            try {
                children = zkClient.getChildren("/sanguo", true);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }

            assert children != null;
            for (String child : children) {
                System.out.println(child);
            }
        });
    }

    // 创建节点
    @Test
    public void create() throws InterruptedException, KeeperException {
        String s = zkClient.create("/joush",   // 创建路径
                "rex".getBytes(), // 传入字符串 "rex"
                ZooDefs.Ids.OPEN_ACL_UNSAFE, // 任何人均可访问
                CreateMode.PERSISTENT); // 持久的
        System.out.println(s); // /joush
    }

    // 获取子节点 并 监听节点变化
    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/sanguo", true);// 此处监听器写 true，标识默认监听创建 client 对象时的监听器

        for (String child : children) {
            System.out.println(child);
        }
    }

    // 判断某个节点是否存在
    @Test
    public void exist() throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/joush", false);
        System.out.println(stat == null ? "not exit" : "exit");
    }
}

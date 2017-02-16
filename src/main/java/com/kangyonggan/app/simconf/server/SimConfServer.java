package com.kangyonggan.app.simconf.server;

import com.kangyonggan.app.simconf.util.PropertiesUtil;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kangyonggan
 * @since 2017/2/16
 */
@Log4j2
public class SimConfServer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new Thread(){
            @Override
            public void run() {
                try {
                    String port = PropertiesUtil.getPropertiesOrDefault("server.port", "17777");
                    ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
                    log.info("服务在{}端口监听...", port);

                    while (true) {
                        Socket socket = serverSocket.accept();
                        log.info("发现一个新的客户端连接...");

                        new SocketThread(socket).start();
                    }
                } catch (Exception e) {
                    log.error("服务异常", e);
                }
            }
        }.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

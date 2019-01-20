package com.pandora;

import com.pandora.spring.agent.ConvertCore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        // xml -> xls

        try {
            if( StringUtils.endsWith(args[0],"xml") && StringUtils.endsWith(args[1],"xls")) {
                ConvertCore.xml2xls(args[0],args[1]);
                // xls -> xml
            } else if( StringUtils.endsWith(args[0],"xls") && StringUtils.endsWith(args[1],"xml")) {
                ConvertCore.xls2xml(args[0],args[1]);
            } else {
                System.out.println("예제) gamelist.xml 파일을 out.xls 파일로 만들때");
                System.out.println("> java -jar openpandora-gamelist.jar gamelist.xml out.xls");
                System.out.println("예제) out.xls 파일을 gamelist2.xml 파일로 만들때");
                System.out.println("> java -jar openpandora-gamelist.jar out.xls gamelist2.xml");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
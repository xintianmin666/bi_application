package com.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 * 
 *
 */
@ComponentScan(basePackages={"com.carservice.*"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CarServiceApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtoolsun.restart.enabled", "false");
        SpringApplication.run(CarServiceApplication.class, args);
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .-----------------.\n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  ____  ____  | || |     _____    | || |  ____  ____  | || | _____  _____ | || | ____  _____  | |\n" +
                "| | |_  _||_  _| | || |    |_   _|   | || | |_  _||_  _| | || ||_   _||_   _|| || ||_   \\|_   _| | |\n" +
                "| |   \\ \\  / /   | || |      | |     | || |   \\ \\  / /   | || |  | |    | |  | || |  |   \\ | |   | |\n" +
                "| |    \\ \\/ /    | || |      | |     | || |    \\ \\/ /    | || |  | '    ' |  | || |  | |\\ \\| |   | |\n" +
                "| |    _|  |_    | || |     _| |_    | || |    _|  |_    | || |   \\ `--' /   | || | _| |_\\   |_  | |\n" +
                "| |   |______|   | || |    |_____|   | || |   |______|   | || |    `.__.'    | || ||_____|\\____| | |\n" +
                "| |              | || |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CarServiceApplication.class);

    }
}

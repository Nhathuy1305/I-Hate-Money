package com.main.ihatemoney;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = "com.vaadin.flow.spring.security")
@SpringBootApplication
@Theme(value = "ihatemoney", variant = Lumo.LIGHT)
//@Theme(value = "ihatemoney", variant = Lumo.DARK)
@PWA(
		name = "IHateMoney",
		shortName = "IHateMoney",
		offlinePath = "offline.html",
		offlineResources = {"images/offline.png"}
)
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class IHateMoneyApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(IHateMoneyApplication.class, args);
	}

}

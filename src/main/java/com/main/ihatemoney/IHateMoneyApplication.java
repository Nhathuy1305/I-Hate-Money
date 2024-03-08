package com.main.ihatemoney;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "IHateMoney", variant = Lumo.LIGHT)
//@Theme(value = "IHateMoney", variant = Lumo.DARK)
@PWA(
		name = "I-Hate-Money",
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

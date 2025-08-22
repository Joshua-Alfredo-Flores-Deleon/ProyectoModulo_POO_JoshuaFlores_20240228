package JoshuaAlfredoFloresDeleon_20240228.JoshuaAlfredoFloresDeleon_20240228;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProyectoModuloPooJoshuaFlores20240228Application.class);
	}

}

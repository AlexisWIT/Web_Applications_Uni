package gEapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class Config_Database {
	
	private String username = "yt120";
	private String password = "ooC7KeeR";
	
	// from campus
	private String HOST = "mysql.mcscw3.le.ac.uk";
	private int PORT = 3306;
	
	// off-campus (including eduroam) after executing 
	// ssh -fNg -L 3307:mysql.mcscw3.le.ac.uk:3306 yt120@xanthus.mcscw3.le.ac.uk
//	private String HOST = "127.0.0.1";
//	private int PORT = 3307;
	
	@Bean
    public DriverManagerDataSource dataSource() {		
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + username );
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}

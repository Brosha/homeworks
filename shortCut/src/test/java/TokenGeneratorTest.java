import org.example.TokenGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class TokenGeneratorTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Properties properties = new Properties();
        String path = TokenGeneratorTest.class.getClassLoader().getResource("conf.xml").getPath();
        properties.loadFromXML(new FileInputStream(path));
        System.out.println(properties.toString());
        String URL = "https://test.com/abobabi";
        URL url = new URL(URL);
        System.out.println(url.getHost());
        System.out.println(url.getPath());
        System.out.println(properties.getProperty("DOMAIN").equals(url.getHost()));
        TokenGenerator tokenGenerator = new TokenGenerator(properties);
        //Создали Юзера
        String user1= tokenGenerator.createUser();
        String user2= tokenGenerator.createUser();
        String link = tokenGenerator.createLink("https://www.sports.ru/", user1);
        String link2 = tokenGenerator.createLink("https://www.sports.ru/", user1);
        String link3 = tokenGenerator.createLink("https://www.sports.ru/", user1);
        //Проверяем, что разные коротки ссылки
        tokenGenerator.getUserProfile(user1);
        String token = tokenGenerator.getUrls().keySet().iterator().next();
        //Смотрим, как меняется количество кликов
        tokenGenerator.getLink(token);
        tokenGenerator.getLink(token);
        tokenGenerator.getLink(token);
        tokenGenerator.getUserProfile(user1);
        //Меняем параметры
        tokenGenerator.updateURLEntity(user1, token, 100000,100);
        tokenGenerator.getUserProfile(user1);
        tokenGenerator.updateURLEntity(user1, token, 8640000000L);
        tokenGenerator.getUserProfile(user1);
        //Смотрим, как удаляется ссылка из-за превышения кликов

        tokenGenerator.updateURLEntity(user1, token, 100000,2);
        tokenGenerator.getUserProfile(user1);
        System.out.println(tokenGenerator.getLink(token));
        System.out.println(tokenGenerator.getLink(token));
        System.out.println(tokenGenerator.getLink(token));
        tokenGenerator.getUserProfile(user1);

        //Смотрим, как удаляется ссылка из-за истечения срока жизни
        token = tokenGenerator.getUrls().keySet().iterator().next();
        tokenGenerator.updateURLEntity(user1, token, 100,2000);
        tokenGenerator.getUserProfile(user1);
        Thread.sleep(100);
        System.out.println(tokenGenerator.getLink(token));
        tokenGenerator.getUserProfile(user1);

        //Удаляем ссылку
        String link4 = tokenGenerator.createLink("https://www.sports.ru/", user1);
        String link5 = tokenGenerator.createLink("https://www.sports.ru/", user1);
        tokenGenerator.getUserProfile(user1);
        token = tokenGenerator.getUrls().keySet().iterator().next();
        tokenGenerator.removeURL(user1,token);
        tokenGenerator.getUserProfile(user1);

    }

}


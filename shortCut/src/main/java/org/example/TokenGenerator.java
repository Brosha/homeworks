package org.example;

import java.util.*;


public class TokenGenerator {
    private HashMap<String, List<URLEntity>> usersLinks = new HashMap<>();
    private HashMap<String, URLEntity> urls = new HashMap<>();

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;

    /**
     * <entry key="ALPHABET">abcdefghijklmnopqrstuvwxyz0123456789</entry>
     * <entry key="TOKEN_LENGTH">8</entry>
     * <entry key="LIFE_TIME">24</entry>
     * <entry key="LIMIT">10</entry>
     */
    private Properties properties;
    private StringBuilder stringBuilder;

    public TokenGenerator(Properties properties) {
        this.properties = properties;
        this.stringBuilder = new StringBuilder().append("https://").append(properties.getProperty("DOMAIN")).append("/");
    }

    public String createToken() {

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int token_length = getProperty("TOKEN_LENGTH");
        String ALPHABET = properties.getProperty("ALPHABET");
        for (int i = 0; i < token_length; i++) {
            stringBuilder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return stringBuilder.toString();
    }

    public String createUser() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        while (usersLinks.containsKey(id)) {
            uuid = UUID.randomUUID();
            id = uuid.toString();
        }
        ArrayList<URLEntity> userLinks = new ArrayList<>();
        usersLinks.put(id, userLinks);

        return id;

    }

    public boolean login(String id) {
        return usersLinks.containsKey(id);
    }

    public String createLink(String URL, String userID) {
        int LIFE_TIME = getProperty("LIFE_TIME");
        long life_time_mills = LIFE_TIME * 60 * 60 * 1000L;
        int limit = getProperty("LIMIT");
        return createLink(URL, limit, life_time_mills, userID);
    }

    public String createLink(String URL, int limit, String userID) {
        int LIFE_TIME = getProperty("LIFE_TIME");
        long life_time_mills = LIFE_TIME * 60 * 60 * 1000L;
        return createLink(URL, limit, life_time_mills, userID);

    }

    public String createLink(String URL, long lifetime, String userID) {
        Calendar calendar = Calendar.getInstance();
        long life_time = calendar.getTimeInMillis();
        int limit = getProperty("LIMIT");
        return createLink(URL, limit, life_time + lifetime, userID);
    }


    public String createLink(String URL, int limit, long lifetime, String userID) {
        String token = createToken();

        while (urls.containsKey(token)) {
            token = createToken();
        }
        Calendar calendar = Calendar.getInstance();
        long current = calendar.getTimeInMillis();


        URLEntity urlEntity = new URLEntity(URL, token, limit, current + lifetime, userID);
        usersLinks.get(userID).add(urlEntity);
        urls.put(token, urlEntity);
        return stringBuilder.toString() + token;

    }

    public String findLink(String userID, String token) {
        URLEntity urlEntity = urls.get(token);
        if (urlEntity == null) return "Not found";
        if (!urlEntity.getUserID().equals(userID)) return "Not found";
        int clicks = urlEntity.getClicks();
        if (urlEntity.getLifetime() < getCurrentTimeInMills()) {
            return "Expired";
        }
        if (clicks < urlEntity.getLimit() + 1) {
            return urlEntity.getURL();

        } else {
            removeURLEntity(token);
            return "Number of opens exceeded";
        }
    }


    public String getLink(String token) {
        URLEntity urlEntity = urls.get(token);
        if (urlEntity == null) return "Not found";
        int clicks = urlEntity.getClicks();
        if (urlEntity.getLifetime() < getCurrentTimeInMills()) {
            removeURLEntity(token);
            return "Expired";
        }
        if (clicks + 1 <= urlEntity.getLimit()) {
            urlEntity.setClicks(++clicks);
            return urlEntity.getURL();

        } else {
            removeURLEntity(token);
            return "Number of opens exceeded";
        }

    }

    public String removeURL(String userId, String token) {
        URLEntity urlEntity = urls.get(token);
        if (urlEntity == null) return "Not found";
        if (!urlEntity.getUserID().equals(userId)) return "Not found";
        int clicks = urlEntity.getClicks();
        if (urlEntity.getLifetime() < getCurrentTimeInMills()) {
            return "Expired";
        }
        if (clicks < urlEntity.getLimit() + 1) {
            removeURLEntity(token);
            return urlEntity.getURL();

        } else {
            removeURLEntity(token);
            return "Number of opens exceeded";
        }
    }

    public void getUserProfile(String userId) {
        List<URLEntity> urls = usersLinks.get(userId);
        System.out.println(userId + ":");
        if (urls.size() == 0) {
            System.out.println("0 Links");
        }
        for (URLEntity urlEntity : urls) {
            System.out.println(getURLWithParams(urlEntity));
        }
    }

    public String getURLEntity(String userId, String token) {
        URLEntity urlEntity = urls.get(token);
        if (!urlEntity.getUserID().equals(userId)) {
            System.out.println("mistake");
        }
        return urlEntity.toString();
    }

    public String updateURLEntity(String userId, String token, long lifetime) {
        Calendar calendar = Calendar.getInstance();
        long current = calendar.getTimeInMillis();
        return updateURLEntity(token, lifetime + current, urls.get(token).getLimit());
    }

    public String updateURLEntity(String userId, String token, int limit) {
        return updateURLEntity(token, urls.get(token).getLifetime(), limit);
    }

    public String updateURLEntity(String userId, String token, long lifetime, int limit) {
        Calendar calendar = Calendar.getInstance();
        long current = calendar.getTimeInMillis();
        return updateURLEntity(token, lifetime + current, limit);
    }

    public String updateURLEntity(String token, long lifetime, int limit) {
        URLEntity urlEntity = urls.get(token);
        urlEntity.setClicks(0);
        urlEntity.setLimit(limit);
        urlEntity.setLifetime(lifetime);
        return getURLWithParams(urlEntity);
    }

    public boolean deleteURL(String userId, String URL) {

        return true;
    }


    private int getProperty(String property) {
        return Integer.parseInt((String) properties.get(property));
    }

    private long getCurrentTimeInMills() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    private void removeURLEntity(String token) {
        URLEntity entity = urls.remove(token);
        usersLinks.get(entity.getUserID()).remove(entity);

    }

    public HashMap<String, List<URLEntity>> getUsersLinks() {
        return usersLinks;
    }

    public HashMap<String, URLEntity> getUrls() {
        return urls;
    }

    private String getURLWithParams(URLEntity urlEntity) {
        return "{URL='" + urlEntity.getURL() + '\'' +
                ", shortCut='" + stringBuilder.toString() + urlEntity.getShortCut() + '\'' +
                ", limit=" + urlEntity.getLimit() +
                ", clicks=" + urlEntity.getClicks() +
                ", lifetime=" + new Date(urlEntity.getLifetime()).toString() +
                '}';
    }

    @Override
    public String toString() {
        return "TokenGenerator{" +
                "usersLinks=" + usersLinks +
                '}';
    }
}

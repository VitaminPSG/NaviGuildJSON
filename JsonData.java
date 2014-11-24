public class JsonData {
    //Название города
    private String city;
    //Уникальное название для данных(имя файла БД)
    private String nameDb;
    //Карта улиц (Ключ - название улицы, объект - список зданий)
    private HashMap<String, ArrayList<JsonBuilding>> buildings;
    //Список остановок
    private ArrayList<JsonStop> stops;
    //Список маршрутов
    private ArrayList<JsonRoute> routes;
    //Дата создания
    private String date;

    public String getNameDb() {
        return nameDb;
    }

    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HashMap<String, ArrayList<JsonBuilding>> getBuildings() {
        return buildings;
    }

    public void setBuildings(HashMap<String, ArrayList<JsonBuilding>> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<JsonStop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<JsonStop> stops) {
        this.stops = stops;
    }

    public ArrayList<JsonRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<JsonRoute> routes) {
        this.routes = routes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

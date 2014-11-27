ublic class JsonStop {
    //ключ
    private int id;
    //Название
    private String name;
    //Координаты остановки [Latitude][Longitude]
    private double[] point;
    //Тип остановки, определяется из суммы уникальных типов транспорта. Определяет иконку для остановки
    private int transport;
      //Список ближайщих остановок
    private List<JsonNeighbor> neighbors;
    //Массив ключей маршрутов, проходящих через остановку
    private int[] routes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getPoint() {
        return point;
    }

    public void setPoint(double[] point) {
        this.point = point;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public int[] getRoutes() {
        return routes;
    }

    public void setRoutes(int[] routes) {
        this.routes = routes;
    }
}
